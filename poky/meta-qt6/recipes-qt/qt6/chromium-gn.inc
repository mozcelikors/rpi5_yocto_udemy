require gn-utils.inc

inherit linuxloader
inherit qemu

COMPATIBLE_MACHINE = "(-)"
COMPATIBLE_MACHINE:aarch64 = "(.*)"
COMPATIBLE_MACHINE:armv6 = "(.*)"
COMPATIBLE_MACHINE:armv7a = "(.*)"
COMPATIBLE_MACHINE:armv7ve = "(.*)"
COMPATIBLE_MACHINE:x86-64 = "(.*)"

# By default, passing is_official_build=true to GN causes its symbol_level
# variable to be set to "2". This means the compiler will be passed "-g2" and
# we will end up with a very large chrome binary (around 5Gb as of M58)
# regardless of whether DEBUG_BUILD has been set or not. In addition, binutils,
# file and other utilities are unable to read a 32-bit binary this size, which
# causes it not to be stripped.
# The solution is two-fold:
# 1. Make sure -g is not passed on 32-bit architectures via DEBUG_FLAGS. -g is
#    the same as -g2. -g1 generates an 800MB binary, which is a lot more
#    manageable.
# 2. Explicitly pass symbol_level=0 to GN. This causes -g0 to be passed
#    instead, so that if DEBUG_BUILD is not set GN will not create a huge debug
#    binary anyway. Since our compiler flags are passed after GN's, -g0 does
#    not cause any issues if DEBUG_BUILD is set, as -g1 will be passed later.
DEBUG_FLAGS:remove:arm = "-g"
DEBUG_FLAGS:append:arm = "-g1"
DEBUG_FLAGS:remove:x86 = "-g"
DEBUG_FLAGS:append:x86 = "-g1"

# As of Chromium 62.0.3202.94 and Yocto Rocko (GCC 7, binutils 2.29), passing
# -g to the compiler results in many linker errors on aarch64, such as:
# obj/third_party/WebKit/Source/modules/payments/libpayments.a(PaymentEventDataConversion.o)(.debug_loc+0x4e25): error: relocation overflow in R_AARCH64_ABS32
DEBUG_FLAGS:remove:aarch64 = "-g"
DEBUG_FLAGS:append:aarch64 = "-g1"

# As of Chromium 60.0.3112.101 and Yocto Pyro (GCC 6, binutils 2.28), passing
# -g to the compiler results in many linker errors on x86_64, such as:
# obj/third_party/WebKit/Source/core/loader/libloader.a(ModuleTreeLinker.o)(.debug_loc+0x1e9a5): error: relocation overflow: reference to local symbol 82 in obj/third_party/WebKit/Source/core/loader/libloader.a(ModuleTreeLinker.o)
# obj/third_party/WebKit/Source/core/libcore_generated.a(ScriptModule.o)(.debug_loc+0x253c): error: relocation overflow: reference to local symbol 31 in obj/third_party/WebKit/Source/core/libcore_generated.a(ScriptModule.o)
# so we have to use the same hack described above.
DEBUG_FLAGS:remove:x86-64 = "-g"
DEBUG_FLAGS:append:x86-64 = "-g1"

# V8's JIT infrastructure requires binaries such as mksnapshot and
# mkpeephole to be run in the host during the build. However, these
# binaries must have the same bit-width as the target (e.g. a x86_64
# host targeting ARMv6 needs to produce a 32-bit binary). Instead of
# depending on a third Yocto toolchain, we just build those binaries
# for the target and run them on the host with QEMU.
python do_create_v8_qemu_wrapper () {
    """Creates a small wrapper that invokes QEMU to run some target V8 binaries
    on the host."""
    qemu_libdirs = [d.expand('${STAGING_DIR_HOST}${libdir}'),
                    d.expand('${STAGING_DIR_HOST}${base_libdir}')]
    qemu_cmd = qemu_wrapper_cmdline(d, d.getVar('STAGING_DIR_HOST', True),
                                    qemu_libdirs)
    wrapper_path = d.expand('${B}')
    bb.utils.mkdirhier(wrapper_path)
    wrapper_path = os.path.join(wrapper_path, "v8-qemu-wrapper.sh")
    with open(wrapper_path, 'w') as wrapper_file:
        wrapper_file.write("""#!/bin/sh

# This file has been generated automatically.
# It invokes QEMU to run binaries built for the target in the host during the
# build process.

%s "$@"
""" % qemu_cmd)
    os.chmod(wrapper_path, 0o755)
}

# Some x86-64 target, such as intel-skylake-64, use AVX instructions that are
# not supported by QEMU (https://gitlab.com/qemu-project/qemu/-/issues/164).
# This causes the build tools to crash with QEMU when they are ran during
# to build. Instead of using QEMU, change the wrapper to try to run the
# tools directly, using the target dynloader.
python do_create_v8_qemu_wrapper:x86-64 () {
    wrapper_path = d.expand('${B}')
    bb.utils.mkdirhier(wrapper_path)
    wrapper_path = os.path.join(wrapper_path, "v8-qemu-wrapper.sh")
    with open(wrapper_path, 'w') as wrapper_file:
        wrapper_file.write("""#!/bin/sh

# This file has been generated automatically.
# It invokes target dynloader to run binaries built for the x86-64 target in the host during the
# build process.

LD_LIBRARY_PATH=%s:%s %s%s "$@"
""" % (d.expand('${STAGING_DIR_HOST}${libdir}'), d.expand('${STAGING_DIR_HOST}${base_libdir}'),
       d.expand('${STAGING_DIR_HOST}'), d.expand(get_linuxloader(d))))
    os.chmod(wrapper_path, 0o755)
}
do_create_v8_qemu_wrapper[dirs] = "${B}"
addtask create_v8_qemu_wrapper after do_configure before do_compile

python do_write_toolchain_file () {
    """Writes a BUILD.gn file for Yocto detailing its toolchains."""
    toolchain_dir = d.expand("${S}/src/3rdparty/chromium/build/toolchain/yocto")
    bb.utils.mkdirhier(toolchain_dir)
    toolchain_file = os.path.join(toolchain_dir, "BUILD.gn")
    write_toolchain_file(d, toolchain_file)
}
addtask write_toolchain_file after do_patch before do_configure

do_compile[progress] = "outof:^\[(\d+)/(\d+)\]\s+"

PACKAGE_DEBUG_SPLIT_STYLE = "debug-without-src"

# There is no need to ship empty -dev packages.
ALLOW_EMPTY:${PN}-dev = "0"
