LICENSE = "(The-Qt-Company-Commercial | (GPL-3.0-only & Qt-GPL-exception-1.0) & (LGPL-3.0-only | GPL-2.0-only | GPL-3.0-only) & GFDL-1.3-no-invariants-only) & BSD-3-Clause & CC-BY-SA-4.0 & CC0-1.0 & MIT & MPL-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSES/BSD-3-Clause.txt;md5=cb40fa7520502d8c7a3aea47cae1316c \
    file://LICENSES/GFDL-1.3-no-invariants-only.txt;md5=a22d0be1ce2284b67950a4d1673dd1b0 \
    file://LICENSES/GPL-2.0-only.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://LICENSES/GPL-3.0-only.txt;md5=d32239bcb673463ab874e80d47fae504 \
    file://LICENSES/LGPL-3.0-only.txt;md5=e6a600fd5e1d9cbde2d983680233ad02 \
    file://LICENSES/LicenseRef-Qt-Commercial.txt;md5=40a1036f91cefc0e3fabad241fb5f187 \
    file://LICENSES/Qt-GPL-exception-1.0.txt;md5=9a13522cd91a88fba784baf16ea66af8 \
    file://src/3rdparty/open62541/BSD-3-CLAUSE;md5=8647c60c0b1892cb8f30c8efd60b318f \
    file://src/3rdparty/open62541/CC-BY-SA-4.0;md5=bb082061306cc1dc0afcd128f972d344 \
    file://src/3rdparty/open62541/LICENSE-2.0.txt;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://src/3rdparty/open62541/LICENSE-CC0;md5=6888abe69dbc6330301f0467e21c0317 \
    file://src/3rdparty/open62541/MIT;md5=6b7814836306cd82b4f9ca8be2a8ce55 \
    file://src/3rdparty/open62541/mpl-2.0.815ca599c9df.txt;md5=815ca599c9df247a0c7f619bab123dad \
"

inherit qt6-cmake

include recipes-qt/qt6/qt6-git.inc
include recipes-qt/qt6/qt6.inc

PACKAGECONFIG ?= "qml"
PACKAGECONFIG[qml] = ",,qtdeclarative qtdeclarative-native"

# src/3rdparty/open62541.pri adds -Wno-format, causing following error
# because -Wformat-security cannot be used together with -Wno-format
# cc1: error: -Wformat-security ignored without -Wformat [-Werror=format-security]
SECURITY_STRINGFORMAT = ""

DEPENDS += "qtbase qtopcua-native"

BBCLASSEXTEND = "native nativesdk"
