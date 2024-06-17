SUMMARY = "Recipe file for ascii pypi package"
MAINTAINER = "Mustafa Ozcelikors <email>"
LICENSE = "CLOSED"
SRC_URI:append = " https://files.pythonhosted.org/packages/8b/85/917d6464eafc2f62b54295010154cad40b0ed76fad2f95c99979bb80e0b5/ascii-3.6.tar.gz"

SRC_URI[md5sum] = "ee92b9b924e261c63ab5ceda4e0748f9"
SRC_URI[sha256sum] = "b5ffc4cb20ac1789cac80a91319345f26a3e8f3c6d6feff3a220c1b6c5056141"

S = "${WORKDIR}/ascii-3.6"

inherit pypi setuptools3

PYPI_PACKAGE = "ascii"
RPROVIDES:${PN} = "ascii"

