SUMMARY = "Minizip Compression Library"
DESCRIPTION = "Minizip is a part of Zlib, which is a general-purpose, patent-free, lossless data compression \
library which is used by many different programs."
HOMEPAGE = "http://www.winimage.com/zLibDll/minizip.html"
SECTION = "libs"
LICENSE = "Zlib"
LIC_FILES_CHKSUM = "file://zip.h;beginline=14;endline=30;md5=8eaa8535a3a1a2296b303f40f75385e7"

SRC_URI = "${SOURCEFORGE_MIRROR}/libpng/zlib/${PV}/zlib-${PV}.tar.xz"
UPSTREAM_CHECK_URI = "http://zlib.net/"

S = "${WORKDIR}/zlib-${PV}/contrib/minizip"

SRC_URI[md5sum] = "85adef240c5f370b308da8c938951a68"
SRC_URI[sha256sum] = "4ff941449631ace0d4d203e3483be9dbc9da454084111f97ea0a2114e19bf066"

DEPENDS = "zlib"

inherit autotools

BBCLASSEXTEND = "native nativesdk"
