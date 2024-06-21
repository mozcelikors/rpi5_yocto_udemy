DESCRIPTION = "Qt6 modules"
LICENSE = "MIT"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PACKAGEGROUP_DISABLE_COMPLEMENTARY = "1"

RDEPENDS:${PN} += " \
    packagegroup-qt6-essentials \
    packagegroup-qt6-addons \
    ${@bb.utils.contains('QT_COMMERCIAL_MODULES', '1', 'packagegroup-qt6-commercial-modules', '', d)} \
"
