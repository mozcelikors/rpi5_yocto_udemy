SUMMARY = "Recipe for software package that uses GNU Make"
LICENSE = "CLOSED"
SRC_URI = "git://github.com/mozcelikors/makefile-example.git;protocol=https;branch=master"
SRCREV = "5fc593af10244751039f2a1163134b6b60a7df37"

RDEPENDS:${PN}:append = " bash "
DEPENDS:append = " "

S = "${WORKDIR}/git"

do_compile(){
	export OECORE_TARGET_SYSROOT=${STAGING_DIR_TARGET}
	oe_runmake -C .
}

do_install(){
	install -d ${D}${bindir}
	install -m 0755 ${S}/app ${D}${bindir}/app
	install -m 0755 ${S}/app ${D}${bindir}/app2
}

PACKAGES = "${PN} \
            ${PN}-app \
			${PN}-app2 \
			${PN}-dbg \
"

FILES:${PN}:append = " ${bindir}/app "
FILES:${PN}-app:append = " ${bindir}/app "
FILES:${PN}-app2:append = " ${bindir}/app2 "
FILES:${PN}-dbg:append = "  ${bindir}/.debug ${bindir}/.debug/app ${bindir}/.debug/app2"
