LICENSE = "The-Qt-Company-Commercial"
LIC_FILES_CHKSUM = "file://LICENSES/LicenseRef-Qt-Commercial.txt;md5=40a1036f91cefc0e3fabad241fb5f187"

inherit qt6-cmake

include recipes-qt/qt6/qt6-git.inc
include recipes-qt/qt6/qt6.inc
include recipes-qt/qt6/qt6-commercial.inc

DEPENDS += "qtbase qtdeclarative qtdeclarative-native"

FILES:${PN} += "${QT6_INSTALL_DATADIR}/qtinsight"
