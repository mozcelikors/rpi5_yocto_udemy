LICENSE = "(The-Qt-Company-Commercial | (GPL-3.0-only & Qt-GPL-exception-1.0) & GFDL-1.3-no-invariants-only & BSD-3-Clause) & Apache-2.0 & CC-BY-4.0 & CC-BY-SA-4.0"
LIC_FILES_CHKSUM = " \
    file://LICENSES/Apache-2.0.txt;md5=b4c615f64dff32f71eeed614d13dfd4c \
    file://LICENSES/BSD-3-Clause.txt;md5=cb40fa7520502d8c7a3aea47cae1316c \
    file://LICENSES/CC-BY-4.0.txt;md5=ed88d31cea57b15030a1f58ceb04e0d5 \
    file://LICENSES/CC-BY-SA-4.0.txt;md5=7130783469368ceb248a4f03e89ea4b8 \
    file://LICENSES/GFDL-1.3-no-invariants-only.txt;md5=a22d0be1ce2284b67950a4d1673dd1b0 \
    file://LICENSES/GPL-3.0-only.txt;md5=d32239bcb673463ab874e80d47fae504 \
    file://LICENSES/LicenseRef-Qt-Commercial.txt;md5=caa060942f6b722bc4329d4195584c38 \
    file://LICENSES/Qt-GPL-exception-1.0.txt;md5=9a13522cd91a88fba784baf16ea66af8 \
"

inherit qt6-cmake

include recipes-qt/qt6/qt6-git.inc
include recipes-qt/qt6/qt6.inc

DEPENDS += "\
    qtbase \
    qtcharts \
    qtdeclarative qtdeclarative-native \
    qtgraphs \
    qtlocation \
    qtmultimedia \
    qtpositioning \
    qtquick3d qtquick3d-native \
    qtsensors \
    qtshadertools-native \
    qtsvg \
    qtwebsockets \
"
DEPENDS:append:aarch64 = " qtpdf qtquick3dphysics"
DEPENDS:append:arm = " qtquick3dphysics"
DEPENDS:append:armv6 = " qtpdf"
DEPENDS:append:armv7a = " qtpdf"
DEPENDS:append:armv7ve = " qtpdf"
DEPENDS:append:x86 = " qtquick3dphysics"
DEPENDS:append:x86-64 = " qtpdf qtquick3dphysics"

PACKAGECONFIG ?= "examples"
