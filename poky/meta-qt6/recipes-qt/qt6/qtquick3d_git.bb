LICENSE = "(The-Qt-Company-Commercial | (GPL-3.0-only & Qt-GPL-exception-1.0) & (LGPL-3.0-only | GPL-2.0-only | GPL-3.0-only) & GFDL-1.3-no-invariants-only) & Apache-2.0 & BSD-3-Clause & MIT"
LIC_FILES_CHKSUM = " \
    file://LICENSES/BSD-3-Clause.txt;md5=cb40fa7520502d8c7a3aea47cae1316c \
    file://LICENSES/GFDL-1.3-no-invariants-only.txt;md5=a22d0be1ce2284b67950a4d1673dd1b0 \
    file://LICENSES/GPL-2.0-only.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://LICENSES/GPL-3.0-only.txt;md5=d32239bcb673463ab874e80d47fae504 \
    file://LICENSES/LGPL-3.0-only.txt;md5=e6a600fd5e1d9cbde2d983680233ad02 \
    file://LICENSES/LicenseRef-Qt-Commercial.txt;md5=40a1036f91cefc0e3fabad241fb5f187 \
    file://LICENSES/Qt-GPL-exception-1.0.txt;md5=9a13522cd91a88fba784baf16ea66af8 \
    file://src/3rdparty/assimp/LICENSE;md5=78dabdafb167945fef55b5c37ac94df3 \
    file://src/3rdparty/embree/LICENSE.txt;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://src/3rdparty/tinyexr/LICENSE;md5=27559be3d3cfab88d56b352c10fb9476 \
    file://src/3rdparty/xatlas/LICENSE;md5=8605e91c32ad7d58bd62c310eb2c3bf6 \
"

inherit qt6-cmake

include recipes-qt/qt6/qt6-git.inc
include recipes-qt/qt6/qt6.inc

ASSIMP_BRANCH = "qt6_assimp"

SRC_URI += " \
    ${QT_GIT}/${QT_GIT_PROJECT}/qtquick3d-assimp.git;name=qtquick3d-assimp;branch=${ASSIMP_BRANCH};protocol=${QT_GIT_PROTOCOL};destsuffix=git/src/3rdparty/assimp/src \
    file://0001-Skip-embree-on-mingw.patch \
"

DEPENDS = "qtbase qtdeclarative qtshadertools qtshadertools-native qtquick3d-native"

BBCLASSEXTEND = "native nativesdk"

PACKAGECONFIG:class-target ?= "qtquicktimeline"
PACKAGECONFIG[qtquicktimeline] = ",,qtquicktimeline"
PACKAGECONFIG[system-assimp] = "-DFEATURE_system_assimp=ON,-DFEATURE_system_assimp=OFF,assimp"

FILES:${PN}-qmlplugins += " \
  ${QT6_INSTALL_QMLDIR}/QtQuick3D/Helpers/meshes/*.mesh \
"

SRCREV_FORMAT = "qtquick3d_qtquick3d-assimp"
