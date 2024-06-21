LICENSE = "Squish-Commercial-License-Agreement"
LIC_FILES_CHKSUM = "file://LICENSE;md5=26ea9708fd32ef443b69239b5d9b343c"

EXCLUDE_FROM_WORLD = "1"

inherit qt6-qmake

SQUISH_MIRROR ?= "https://ci-files01-hki.ci.qt.io/input/squish/coin/66x/20240124"

SRC_URI = "\
    ${SQUISH_MIRROR}/squish-${PV}-qt66x-linux64.run;name=squish \
    ${SQUISH_MIRROR}/squish-${PV}-qt-embedded-src.tar.gz;name=qt-squish-embedded \
"

SRC_URI[squish.sha256sum] = "69689f25cecf07ce15f8e8975f9cf44d85cc1d0f628d3013dfb6dfc9f562aa91"
SRC_URI[qt-squish-embedded.sha256sum] = "8f0c03f3814eb44f2366f0599e7f6ef52446651fc1882bd487918e441ceac07a"

S = "${WORKDIR}/squish-${PV}-qt-embedded-src"
B = "${WORKDIR}/build"

DEPENDS += "\
    qtbase \
    qt5compat \
    qtdeclarative qtdeclarative-native \
    qtapplicationmanager \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'qtwayland qtwayland-native wayland wayland-native', '', d)} \
"

lcl_maybe_fortify = ""
OE_QMAKE_PATH_HOST_LIBEXECS = "${STAGING_DIR_NATIVE}/${QT6_INSTALL_LIBEXECDIR}"

do_install_squish[cleandirs] = "${WORKDIR}/squish"
do_install_squish[network] = "1"
do_install_squish() {
    chmod +x ${WORKDIR}/squish-${PV}-qt66x-linux64.run
    TMPDIR=${WORKDIR}/tmp XDG_RUNTIME_DIR=${WORKDIR}/tmp ${WORKDIR}/squish-${PV}-qt66x-linux64.run \
        -platform minimal unattended=1 targetdir=${WORKDIR}/squish ide=0 testcenter=0
}

do_configure() {
    ${S}/configure \
        --disable-all \
        --enable-qmake-config \
        --enable-qt \
        --enable-server \
        --enable-wayland \
        --with-qmake=${STAGING_DIR_NATIVE}${QT6_INSTALL_BINDIR}/qmake \
        --with-squishidl=${WORKDIR}/squish/bin/squishidl
}

do_compile() {
    ./build -j${@oe.utils.cpu_count()}
}

do_install() {
    DESTDIR=${D}/opt/squish
    ./build install DESTDIR=${DESTDIR}

    install -d ${D}${QT6_INSTALL_PLUGINSDIR}
    mv ${DESTDIR}/plugins/* ${D}${QT6_INSTALL_PLUGINSDIR}
    rmdir ${DESTDIR}/plugins

    sed -i -e 's|${RECIPE_SYSROOT}||' ${D}/opt/squish/etc/paths.ini

    install -d ${D}${sysconfdir}/profile.d
    echo "export SQUISH_PREFIX=/opt/squish" > ${D}${sysconfdir}/profile.d/squish.sh
}

FILES:${PN} += "\
    ${QT6_INSTALL_PLUGINSDIR} \
    /opt/squish \
"
FILES:${PN}-dev += "\
    /opt/squish/qtbuiltinhook.pri \
    /opt/squish/LICENSE \
    /opt/squish/include \
    /opt/squish/lib/cmake \
    /opt/squish/sdk \
"
FILES:${PN}-dev:remove = "${FILES_SOLIBSDEV}"
FILES:${PN}-staticdev += "\
    ${QT6_INSTALL_PLUGINSDIR}/generic/*.a \
    /opt/squish/lib/*.a \
    /opt/squish/lib/extensions/qt/*.a \
"

addtask install_squish after do_unpack before do_configure
