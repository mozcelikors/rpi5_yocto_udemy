DESCRIPTION = "Qt6 addon modules"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PACKAGEGROUP_DISABLE_COMPLEMENTARY = "1"

RDEPENDS:${PN} += " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'qt3d', '', d)} \
    qt5compat \
    qtapplicationmanager \
    qtcharts \
    qtcoap \
    qtconnectivity \
    ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'qtdatavis3d', '', d)} \
    qtdeviceutilities \
    qtdoc \
    qtgraphs \
    qtgrpc \
    qthttpserver \
    qtimageformats \
    qtinterfaceframework \
    qtlocation \
    qtlottie \
    qtmqtt \
    qtmultimedia \
    qtnetworkauth \
    qtopcua \
    qtpositioning \
    qtquick3d \
    qtquickdesigner-components \
    qtquicktimeline \
    qtremoteobjects \
    qtscxml \
    qtsensors \
    qtserialbus \
    qtserialbus-tools \
    qtserialport \
    qtshadertools \
    qtspeech \
    qtsvg \
    qttranslations \
    qtvirtualkeyboard \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'qtwayland', '', d)} \
    qtwebchannel \
    qtwebsockets \
"

RDEPENDS:${PN}:append:aarch64 = "\
    qtquick3dphysics \
    qtpdf \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webengine', 'qtwebengine qtwebview', '', d)} \
"
RDEPENDS:${PN}:append:arm = " qtquick3dphysics"
RDEPENDS:${PN}:append:armv6 = "\
    qtpdf \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webengine', 'qtwebengine qtwebview', '', d)} \
"
RDEPENDS:${PN}:append:armv7a = "\
    qtpdf \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webengine', 'qtwebengine qtwebview', '', d)} \
"
RDEPENDS:${PN}:append:armv7ve = "\
    qtpdf \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webengine', 'qtwebengine qtwebview', '', d)} \
"
RDEPENDS:${PN}:append:x86 = " qtquick3dphysics"
RDEPENDS:${PN}:append:x86-64 = "\
    qtquick3dphysics \
    qtpdf \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webengine', 'qtwebengine qtwebview', '', d)} \
"
