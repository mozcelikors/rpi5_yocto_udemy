SUMMARY = "Deploys x11 configuration"
LICENSE = "CLOSED"
SRC_URI:append = " file://99-vc4.conf "

S = "${WORKDIR}"

do_install:append(){
	install -d ${D}/etc/X11/xorg.conf.d
	cp -r ${S}/99-vc4.conf ${D}/etc/X11/xorg.conf.d
}

FILES:${PN}:append = " /etc/X11/xorg.conf.d/99-vc4.conf "