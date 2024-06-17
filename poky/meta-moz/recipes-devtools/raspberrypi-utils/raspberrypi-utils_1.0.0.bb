FILESEXTRAPATHS:prepend := "${THISDIR}/files2:"

SUMMARY = "Recipe file for a few raspberry pi tools"
LICENSE = "CLOSED"
RDEPENDS:${PN}:append = " bash "

SRC_URI = "git://github.com/raspberrypi/utils.git;protocol=https;branch=master"
SRC_URI += " file://git/CMakeLists.txt "

SRCREV = "b9c63214c535d7df2b0fa6743b7b3e508363c25a"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

do_install:append(){
	install -d ${D}${bindir}
	install -m 0755 ${B}/pinctrl/pinctrl ${D}${bindir}
	install -m 0755 ${S}/raspinfo/raspinfo ${D}${bindir}
}

FILES:${PN} += " /usr/share/bash-completion/completions/pinctrl"
FILES:${PN} += " ${bindir}/pinctrl"
FILES:${PN} += " ${bindir}/raspinfo"
