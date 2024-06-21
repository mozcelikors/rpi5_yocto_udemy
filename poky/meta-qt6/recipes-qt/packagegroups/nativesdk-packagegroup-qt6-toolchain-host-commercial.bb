DESCRIPTION = "Qt6 development host commercial packages"
LICENSE = "MIT"

# disable sanity check for allarch packagegroup
PACKAGE_ARCH = ""

inherit packagegroup nativesdk

PACKAGEGROUP_DISABLE_COMPLEMENTARY = "1"

RDEPENDS:${PN} += " \
    nativesdk-qmlcompilerplus-dev \
    nativesdk-qmlcompilerplus-tools \
"
