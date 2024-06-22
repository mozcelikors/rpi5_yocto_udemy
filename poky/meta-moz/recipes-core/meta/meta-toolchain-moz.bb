SUMMARY = "Meta package for building an installable Qt6 toolchain and SDK for Moz Distro"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

TOOLCHAIN_TARGET_TASK:append = " \
    packagegroup-qt6-essentials \
"

TOOLCHAIN_HOST_TASK:append = " \
    nativesdk-packagegroup-qt6-toolchain-host-essentials \
"

inherit populate_sdk populate_sdk_qt6_base