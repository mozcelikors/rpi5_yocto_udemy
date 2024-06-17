SUMMARY = "${PN} file recipe"
LICENSE = "CLOSED"

SRC_URI = " file://mysource "

S = "${WORKDIR}/mysource"

RDEPENDS:${PN}:append = " helloworldmake-app2 "

TARGET_CC_ARCH += "${LDFLAGS}"

do_compile () {
    ${CC} ${S}/helloyp.c -o ${B}/helloyp
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${S}/helloyp ${D}${bindir}/
}
