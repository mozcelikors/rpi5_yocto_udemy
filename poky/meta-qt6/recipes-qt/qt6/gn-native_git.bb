require recipes-qt/qt6/qtwebengine.inc

inherit native

OECMAKE_SOURCEPATH = "${S}/src/gn"
OECMAKE_TARGET_COMPILE = "gn"

cmake_do_install() {
    eval DESTDIR='${D}' ${CMAKE_VERBOSE} cmake --install '${B}'
}

INSANE_SKIP:${PN} += "already-stripped"
