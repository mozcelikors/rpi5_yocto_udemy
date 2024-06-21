LICENSE = "(The-Qt-Company-Commercial | (GPL-3.0-only & Qt-GPL-exception-1.0) & (LGPL-3.0-only | GPL-2.0-only | GPL-3.0-only) & GFDL-1.3-no-invariants-only) & Apache-2.0 & BSD-3-Clause & BSL-1.0 & MIT"
LIC_FILES_CHKSUM = " \
    file://LICENSES/Apache-2.0.txt;md5=b4c615f64dff32f71eeed614d13dfd4c \
    file://LICENSES/BSD-3-Clause.txt;md5=cb40fa7520502d8c7a3aea47cae1316c \
    file://LICENSES/BSL-1.0.txt;md5=8c92b4c255bdcce2989707d5b8a4d302 \
    file://LICENSES/GFDL-1.3-no-invariants-only.txt;md5=a22d0be1ce2284b67950a4d1673dd1b0 \
    file://LICENSES/GPL-2.0-only.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://LICENSES/GPL-3.0-only.txt;md5=d32239bcb673463ab874e80d47fae504 \
    file://LICENSES/LGPL-3.0-only.txt;md5=e6a600fd5e1d9cbde2d983680233ad02 \
    file://LICENSES/LicenseRef-Qt-Commercial.txt;md5=40a1036f91cefc0e3fabad241fb5f187 \
    file://LICENSES/MIT.txt;md5=3605d54ecceddcd50962eb89318779ec \
    file://LICENSES/Qt-GPL-exception-1.0.txt;md5=9a13522cd91a88fba784baf16ea66af8 \
"

inherit qt6-cmake

include recipes-qt/qt6/qt6-git.inc
include recipes-qt/qt6/qt6.inc

SRC_URI += "\
    file://0001-Add-linux-oe-g-platform.patch \
    file://0002-qlibraryinfo-allow-to-set-qt.conf-from-the-outside-u.patch \
    file://0004-Fix-qt.toolchain.cmake-for-SDK-use.patch \
    file://0005-testlib-don-t-track-the-build-or-source-directories.patch \
"

DEPENDS += "\
    patchelf-native \
    freetype \
    pcre2 \
"
DEPENDS:remove:class-native = "qtbase-native"
RDEPENDS_${PN}:remove:class-native = "libssl-native"

RRECOMMENDS:${PN}:append:libc-glibc:class-target = " locale-base-c"
RRECOMMENDS:${PN}-ptest:append = " tzdata"

PACKAGECONFIG:class-native ?= "\
    gui widgets jpeg png dbus no-opengl openssl zlib zstd \
"
PACKAGECONFIG:class-nativesdk ?= "${PACKAGECONFIG:class-native}"
PACKAGECONFIG ?= "\
    ${PACKAGECONFIG_DEFAULT} \
    ${PACKAGECONFIG_GRAPHICS} \
    ${PACKAGECONFIG_X11} \
    ${PACKAGECONFIG_KDE} \
    ${PACKAGECONFIG_FONTS} \
    ${PACKAGECONFIG_SYSTEM} \
    ${PACKAGECONFIG_DISTRO} \
"

PACKAGECONFIG_GRAPHICS ?= "\
    ${@bb.utils.filter('DISTRO_FEATURES', 'vulkan', d)} \
    ${@bb.utils.filter('DISTRO_FEATURES', 'wayland', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', \
        bb.utils.contains('DISTRO_FEATURES', 'x11', 'gl', 'kms gbm gles2 eglfs', d), 'no-opengl linuxfb', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'directfb', 'directfb', '', d)} \
"
PACKAGECONFIG_X11 ?= "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'xcb', '', d)}"
PACKAGECONFIG_KDE ?= "${@bb.utils.contains('DISTRO_FEATURES', 'kde', 'sm cups kms gbm sql-sqlite', '', d)}"
PACKAGECONFIG_FONTS ?= ""
PACKAGECONFIG_SYSTEM ?= ""
PACKAGECONFIG_DISTRO ?= ""
PACKAGECONFIG_DEFAULT ?= "\
    accessibility \
    dbus \
    fontconfig \
    glib \
    gui \
    harfbuzz \
    icu \
    jpeg \
    libinput \
    ${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-gold', 'use-gold-linker', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'ld-is-lld', 'use-lld-linker', '', d)} \
    openssl  \
    png \
    udev \
    widgets \
    xkbcommon \
    zlib \
    zstd \
    ${@bb.utils.contains('SELECTED_OPTIMIZATION', '-Os', 'optimize-size ltcg', '', d)} \
"

PACKAGECONFIG:remove:mingw32 = "openssl"

# Build type: Debug, Release, MinSizeRel, RelWithDebInfo
BUILD_TYPE ?= "Release"
# OpenSSL linking mode: runtime, linked
OPENSSL_LINKING_MODE ?= "runtime"

# Default platform plugin
QT_QPA_DEFAULT_PLATFORM ?= "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'xcb', \
    bb.utils.contains('PACKAGECONFIG', 'gles2', 'eglfs', 'linuxfb', d), d)}"

# at-spi bridge requires XCB currently
ACCESSIBILITY_DEPENDS = "${@bb.utils.contains("DISTRO_FEATURES", "x11", "at-spi2-core", "", d)}"

PACKAGECONFIG[ltcg] = "-DCMAKE_INTERPROCEDURAL_OPTIMIZATION=ON,-DCMAKE_INTERPROCEDURAL_OPTIMIZATION=OFF"
PACKAGECONFIG[optimize-size] = "-DFEATURE_optimize_size=ON,-DFEATURE_optimize_size=OFF"
PACKAGECONFIG[static] = "-DBUILD_SHARED_LIBS=OFF,-DBUILD_SHARED_LIBS=ON"
PACKAGECONFIG[developer-build] = "-DFEATURE_developer_build=ON,-DFEATURE_developer_build=OFF"
PACKAGECONFIG[use-gold-linker] = "-DFEATURE_use_gold_linker=ON"
PACKAGECONFIG[use-bfd-linker] = "-DFEATURE_use_bfd_linker=ON"
PACKAGECONFIG[use-lld-linker] = "-DFEATURE_use_lld_linker=ON"

PACKAGECONFIG[cups] = "-DFEATURE_cups=ON,-DFEATURE_cups=OFF,cups"
PACKAGECONFIG[dbus] = "-DFEATURE_dbus=ON,-DFEATURE_dbus=OFF,dbus"
PACKAGECONFIG[udev] = "-DFEATURE_libudev=ON,-DFEATURE_libudev=OFF,udev"
PACKAGECONFIG[zlib] = "-DFEATURE_system_zlib=ON,-DFEATURE_system_zlib=OFF,zlib"
PACKAGECONFIG[zstd] = "-DFEATURE_zstd=ON,-DFEATURE_zstd=OFF,zstd"

# corelib
PACKAGECONFIG[glib] = "-DFEATURE_glib=ON,-DFEATURE_glib=OFF,glib-2.0"
PACKAGECONFIG[icu] = "-DFEATURE_icu=ON,-DFEATURE_icu=OFF,icu"
PACKAGECONFIG[journald] = "-DFEATURE_journald=ON,-DFEATURE_journald=OFF,systemd"
PACKAGECONFIG[lttng] = "-DFEATURE_lttng=ON,-DFEATURE_lttng=OFF,lttng-ust"

# gui
PACKAGECONFIG[gui] = "-DFEATURE_gui=ON,-DFEATURE_gui=OFF"
PACKAGECONFIG[accessibility] = "-DFEATURE_accessibility=ON,-DFEATURE_accessibility=OFF,${ACCESSIBILITY_DEPENDS}"
PACKAGECONFIG[directfb] = "-DFEATURE_directfb=ON,-DFEATURE_directfb=OFF,directfb"
PACKAGECONFIG[fontconfig] = "-DFEATURE_fontconfig=ON,-DFEATURE_fontconfig=OFF,fontconfig"
PACKAGECONFIG[gbm] = "-DFEATURE_gbm=ON,-DFEATURE_gbm=OFF,virtual/libgbm"
PACKAGECONFIG[gl] = "-DFEATURE_opengl_desktop=ON,-DFEATURE_opengl_desktop=OFF,virtual/libgl"
PACKAGECONFIG[gles2] = "-DFEATURE_opengles2=ON,-DFEATURE_opengles2=OFF,virtual/libgles2 virtual/egl"
PACKAGECONFIG[eglfs] = "-DFEATURE_eglfs=ON,-DFEATURE_eglfs=OFF"
PACKAGECONFIG[eglfs-egldevice] = "-DFEATURE_eglfs_egldevice=ON,-DFEATURE_eglfs_egldevice=OFF"
PACKAGECONFIG[harfbuzz] = "-DFEATURE_harfbuzz=ON,-DFEATURE_harfbuzz=OFF,harfbuzz"
PACKAGECONFIG[jpeg] = "-DFEATURE_jpeg=ON,-DFEATURE_jpeg=OFF,jpeg"
PACKAGECONFIG[kms] = "-DFEATURE_kms=ON,-DFEATURE_kms=OFF,drm virtual/egl"
PACKAGECONFIG[libinput] = "-DFEATURE_libinput=ON,-DFEATURE_libinput=OFF,libinput"
PACKAGECONFIG[linuxfb] = "-DFEATURE_linuxfb=ON,-DFEATURE_linuxfb=OFF"
PACKAGECONFIG[mtdev] = "-DFEATURE_mtdev=ON,-DFEATURE_mtdev=OFF,mtdev"
PACKAGECONFIG[no-opengl] = "-DINPUT_opengl=no"
PACKAGECONFIG[png] = "-DFEATURE_system_png=ON,-DFEATURE_png=OFF,libpng"
PACKAGECONFIG[tslib] = "-DFEATURE_tslib=ON,-DFEATURE_tslib=OFF,tslib"
PACKAGECONFIG[vulkan] = "-DFEATURE_vulkan=ON,-DFEATURE_vulkan=OFF,vulkan-headers,vulkan-loader"
PACKAGECONFIG[wayland] = "-DFEATURE_wayland=ON,-DFEATURE_wayland=OFF,wayland wayland-native"
PACKAGECONFIG[xcb] = "-DFEATURE_xcb=ON,-DFEATURE_xcb=OFF,libxcb xcb-util-wm xcb-util-image xcb-util-keysyms xcb-util-renderutil xcb-util-cursor"
PACKAGECONFIG[xkbcommon] = "-DFEATURE_xkbcommon=ON,-DFEATURE_xkbcommon=OFF,libxkbcommon,xkeyboard-config"

# widgets
PACKAGECONFIG[widgets] = "-DFEATURE_widgets=ON,-DFEATURE_widgets=OFF"
PACKAGECONFIG[gtk] = "-DFEATURE_gtk3=ON,-DFEATURE_gtk3=OFF,gtk+3 at-spi2-core"

# network
PACKAGECONFIG[brotli] = "-DFEATURE_brotli=ON,-DFEATURE_brotli=OFF,brotli"
PACKAGECONFIG[gssapi] = "-DFEATURE_gssapi=ON,-DFEATURE_gssapi=OFF,krb5"
PACKAGECONFIG[libproxy] = "-DFEATURE_libproxy=ON,-DFEATURE_libproxy=OFF,libproxy"
PACKAGECONFIG[openssl] = "-DFEATURE_openssl_${OPENSSL_LINKING_MODE}=ON,-DFEATURE_openssl=OFF,openssl,libssl"

# sqldrivers
PACKAGECONFIG[sql-mysql] = "-DFEATURE_sql_mysql=ON,-DFEATURE_sql_mysql=OFF,mysql5"
PACKAGECONFIG[sql-odbc] = "-DFEATURE_sql_odbc=ON,-DFEATURE_sql_odbc=OFF,unixodbc"
PACKAGECONFIG[sql-psql] = "-DFEATURE_sql_psql=ON,-DFEATURE_sql_psql=OFF,postgresql"
PACKAGECONFIG[sql-sqlite] = "-DFEATURE_system_sqlite=ON,-DFEATURE_sql_sqlite=OFF,sqlite3"


EXTRA_OECMAKE += "\
    -DCMAKE_BUILD_TYPE=${BUILD_TYPE} \
    -DQT_EDITION=${QT_EDITION} \
    -DQT_AVOID_CMAKE_ARCHIVING_API=ON \
"

EXTRA_OECMAKE:append:class-target = "\
    -DFEATURE_rpath=OFF \
    -DFEATURE_relocatable=OFF \
    -DQT_QPA_DEFAULT_PLATFORM=${QT_QPA_DEFAULT_PLATFORM} \
    -DQT_NO_GENERATE_QMAKE_WRAPPER_FOR_TARGET=ON \
"

EXTRA_OECMAKE:append:mingw32 = "\
    -DQT_GENERATE_WRAPPER_SCRIPTS_FOR_ALL_HOSTS=ON \
"

SYSROOT_DIRS += "${QT6_INSTALL_MKSPECSDIR}"

do_install:append() {
    sed -i ${D}${QT6_INSTALL_LIBDIR}/cmake/Qt6BuildInternals/QtBuildInternalsExtra.cmake \
        -e '/QT_SOURCE_TREE/,+2d'

    sed -i ${D}${QT6_INSTALL_LIBDIR}/cmake/Qt6/Qt6Dependencies.cmake \
        -e '/set(__qt_platform_initial_qt_host/d'

    # remove mac and android specific scripts that depend on perl and bash
    # to avoid file-rdeps QA Issue.
    rm -f ${D}${QT6_INSTALL_LIBEXECDIR}/android_emulator_launcher.sh
    rm -f ${D}${QT6_INSTALL_MKSPECSDIR}/features/uikit/devices.py
    rm -f ${D}${QT6_INSTALL_MKSPECSDIR}/features/uikit/device_destinations.sh
    rm -f ${D}${QT6_INSTALL_MKSPECSDIR}/features/data/mac/objc_namespace.sh

    # remove unneeded files that contains reference to TMPDIR [buildpaths]
    rm -f ${D}${QT6_INSTALL_BINDIR}/host-*
    rm -f ${D}${QT6_INSTALL_BINDIR}/target_qt.conf

    install -d ${D}${datadir}/cmake/OEToolchainConfig.cmake.d
    RELPATH=${@os.path.relpath(d.getVar('prefix'), d.getVar('datadir') + '/cmake/OEToolchainConfig.cmake.d')}
    cat > ${D}${datadir}/cmake/OEToolchainConfig.cmake.d/OEQt6Toolchain.cmake <<EOF
get_filename_component(QT_HOST_PATH "\${CMAKE_CURRENT_LIST_DIR}/${RELPATH}" ABSOLUTE CACHE)
set(QT_BUILD_INTERNALS_NO_FORCE_SET_INSTALL_PREFIX ON CACHE BOOL "")
EOF

    RELPATH="${@os.path.relpath(d.getVar('bindir'), d.getVar('QT6_INSTALL_BINDIR'))}"
    sed -i ${D}${QT6_INSTALL_BINDIR}/* \
        -i ${D}${QT6_INSTALL_LIBEXECDIR}/* \
        -e "s|cmake_path=${RECIPE_SYSROOT_NATIVE}.*cmake|cmake_path=%script_dir_path%/$RELPATH/cmake.exe|" \
        -e "s|${RECIPE_SYSROOT_NATIVE}.*cmake|\$script_dir_path/$RELPATH/cmake|"

    RELPATH=${@os.path.relpath(d.getVar('prefix') + '/share/cmake/Qt6Toolchain.cmake', d.getVar('QT6_INSTALL_LIBDIR') + '/cmake/Qt6')}
    sed -i ${D}${QT6_INSTALL_LIBDIR}/cmake/Qt6/qt.toolchain.cmake \
        -e "s|/.*/toolchain.cmake|\${CMAKE_CURRENT_LIST_DIR}/$RELPATH|"
}

do_install:append:class-target() {
    sed >> ${D}${QT6_INSTALL_MKSPECSDIR}/linux-oe-g++/qmake.conf <<EOF \
        -e 's: ${lcl_maybe_fortify}: :' \
        -e 's:${DEBUG_PREFIX_MAP}::' \
        -e 's:${RECIPE_SYSROOT}:$$[QT_SYSROOT]:' \
        -e 's:${TARGET_PREFIX}:$$[QT_HOST_PREFIX]${bindir}/${TARGET_SYS}/${TARGET_PREFIX}:'

isEmpty(QMAKE_CC): {
    QMAKE_AR = ${AR} cqs
    QMAKE_AR_LTCG = ${HOST_PREFIX}gcc-ar cqs
    QMAKE_STRIP = ${STRIP}
    QMAKE_OBJCOPY = ${OBJCOPY}
    QMAKE_CC = ${HOST_PREFIX}gcc
    QMAKE_CFLAGS +=  ${TARGET_CC_ARCH}${TOOLCHAIN_OPTIONS}
    QMAKE_CXX = ${HOST_PREFIX}g++
    QMAKE_CXXFLAGS +=  ${TARGET_CC_ARCH}${TOOLCHAIN_OPTIONS}
    QMAKE_LINK = ${HOST_PREFIX}g++
    QMAKE_LFLAGS += ${TARGET_CC_ARCH}${TOOLCHAIN_OPTIONS} ${TARGET_LDFLAGS}
    }
EOF
}

INSANE_SKIP:${PN}-ptest += "arch"
INHIBIT_PACKAGE_STRIP_FILES = "\
    ${PKGD}${PTEST_PATH}/tests/auto/corelib/plugin/qpluginloader/elftest/corrupt2.elf64.so \
    ${PKGD}${PTEST_PATH}/tests/auto/corelib/plugin/qpluginloader/elftest/corrupt3.elf64.so \
    ${PKGD}${PTEST_PATH}/tests/auto/corelib/plugin/qpluginloader/elftest/debugobj.so \
"

BBCLASSEXTEND = "native nativesdk"

