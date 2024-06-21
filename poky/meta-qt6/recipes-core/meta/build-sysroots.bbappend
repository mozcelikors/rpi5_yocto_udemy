inherit populate_sdk_qt6_base

TARGET_SYSROOT = "${STANDALONE_SYSROOT}"
NATIVE_SYSROOT = "${STANDALONE_SYSROOT_NATIVE}"
SDK_OUTPUT = ""

do_qt6_support () {
    create_qt6_sdk_files
}
addtask do_qt6_support after do_build_native_sysroot before do_build
