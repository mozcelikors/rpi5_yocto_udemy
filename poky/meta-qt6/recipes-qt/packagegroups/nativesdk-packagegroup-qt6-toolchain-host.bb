DESCRIPTION = "Qt6 development host packages"
LICENSE = "MIT"

# avoid warning with inherit_defer allarch
PACKAGE_ARCH = ""

inherit packagegroup nativesdk

PACKAGEGROUP_DISABLE_COMPLEMENTARY = "1"

RDEPENDS:${PN} += " \
    nativesdk-make \
    nativesdk-packagegroup-qt6-toolchain-host-essentials \
    nativesdk-packagegroup-qt6-toolchain-host-addons \
    ${@bb.utils.contains('QT_COMMERCIAL_MODULES', '1', 'nativesdk-packagegroup-qt6-toolchain-host-commercial', '', d)} \
    ${FORLINUXHOST} \
"

FORLINUXHOST:mingw32 = ""
FORLINUXHOST = " \
    nativesdk-cmake \
    nativesdk-ninja \
    nativesdk-perl-modules \
    nativesdk-python3-html5lib \
"
