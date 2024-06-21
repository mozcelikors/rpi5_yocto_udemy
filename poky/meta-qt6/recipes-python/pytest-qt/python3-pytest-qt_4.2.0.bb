SUMMARY = "pytest support for PyQt and PySide applications"
HOMEPAGE = "http://github.com/pytest-dev/pytest-qt"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=be0db96616c6ec3cabe975402c4c687f"

SRC_URI[sha256sum] = "00a17b586dd530b6d7a9399923a40489ca4a9a309719011175f55dc6b5dc8f41"

inherit pypi setuptools3

DEPENDS = "python3-setuptools-scm-native"

RDEPENDS:${PN} += "python3-pytest"
