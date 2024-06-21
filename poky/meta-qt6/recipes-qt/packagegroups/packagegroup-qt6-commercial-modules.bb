DESCRIPTION = "Qt6 commercial addon modules"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup
include recipes-qt/qt6/qt6-commercial.inc

PACKAGEGROUP_DISABLE_COMPLEMENTARY = "1"

RDEPENDS:${PN} += " \
    qmlcompilerplus \
    qtinsighttracker \
    qtvncserver \
"
