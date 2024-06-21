LICENSE = "(The-Qt-Company-Commercial | (GPL-3.0-only & Qt-GPL-exception-1.0) & GFDL-1.3-no-invariants-only) & BSD-3-Clause & BSL-1.0 & MIT"
LIC_FILES_CHKSUM = " \
    file://LICENSES/BSD-3-Clause.txt;md5=cb40fa7520502d8c7a3aea47cae1316c \
    file://LICENSES/GFDL-1.3-no-invariants-only.txt;md5=a22d0be1ce2284b67950a4d1673dd1b0 \
    file://LICENSES/GPL-3.0-only.txt;md5=d32239bcb673463ab874e80d47fae504 \
    file://LICENSES/LicenseRef-Qt-Commercial.txt;md5=40a1036f91cefc0e3fabad241fb5f187 \
    file://LICENSES/Qt-GPL-exception-1.0.txt;md5=9a13522cd91a88fba784baf16ea66af8 \
    file://src/3rdparty/clipper/LICENSE;md5=703fd70389dc10159a3da376b5480d52 \
    file://src/3rdparty/clip2tri/LICENSE;md5=20ada30cde771326c364b7987ff5585a \
    file://src/3rdparty/poly2tri/LICENSE;md5=ee547afd72a735d8f02ff92a09cfe403 \
"

inherit qt6-cmake

include recipes-qt/qt6/qt6-git.inc
include recipes-qt/qt6/qt6.inc

DEPENDS += "qtbase"

PACKAGECONFIG ?= "nmea qml"
PACKAGECONFIG[geoclue] = ",,,geoclue"
PACKAGECONFIG[gypsy] = "-DFEATURE_gypsy=ON,-DFEATURE_gypsy=OFF,gconf gypsy"
PACKAGECONFIG[nmea] = ",,qtserialport"
PACKAGECONFIG[qml] = ",,qtdeclarative qtdeclarative-native"

