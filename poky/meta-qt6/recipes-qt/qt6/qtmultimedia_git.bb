LICENSE = "(The-Qt-Company-Commercial | (GPL-3.0-only & Qt-GPL-exception-1.0) & (LGPL-3.0-only | GPL-3.0-only) & (LGPL-3.0-only | GPL-2.0-only | GPL-3.0-only) & GFDL-1.3-no-invariants-only ) & Apache-2.0 & BSD-3-Clause & MPL-2.0"
LIC_FILES_CHKSUM = " \
    file://LICENSES/BSD-3-Clause.txt;md5=cb40fa7520502d8c7a3aea47cae1316c \
    file://LICENSES/GFDL-1.3-no-invariants-only.txt;md5=a22d0be1ce2284b67950a4d1673dd1b0 \
    file://LICENSES/GPL-2.0-only.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://LICENSES/GPL-3.0-only.txt;md5=d32239bcb673463ab874e80d47fae504 \
    file://LICENSES/LGPL-3.0-only.txt;md5=e6a600fd5e1d9cbde2d983680233ad02 \
    file://LICENSES/LicenseRef-Qt-Commercial.txt;md5=40a1036f91cefc0e3fabad241fb5f187 \
    file://LICENSES/Qt-GPL-exception-1.0.txt;md5=9a13522cd91a88fba784baf16ea66af8 \
    file://src/3rdparty/resonance-audio/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://src/3rdparty/eigen/COPYING.BSD;md5=bb155c6d1ebb4543dc4b7b5f33fa40ec \
    file://src/3rdparty/eigen/COPYING.MPL2;md5=815ca599c9df247a0c7f619bab123dad \
    file://src/3rdparty/pffft/LICENSE;md5=0f39e43e9bc20e7e103e54750e1ec3a2 \
"

inherit qt6-cmake

include recipes-qt/qt6/qt6-git.inc
include recipes-qt/qt6/qt6.inc

DEPENDS += "qtbase qtshadertools qtshadertools-native"

PACKAGECONFIG ?= "\
    ${@bb.utils.contains_any('LICENSE_FLAGS_ACCEPTED','commercial commercial_ffmpeg','ffmpeg','',d)} \
    gstreamer pulseaudio qml spatialaudio spatialaudio_quick3d"

PACKAGECONFIG[alsa] = "-DFEATURE_alsa=ON,-DFEATURE_alsa=OFF,alsa-lib"
PACKAGECONFIG[examples] = "-DQT_BUILD_EXAMPLES=ON,-DQT_BUILD_EXAMPLES=OFF,qtsvg"
PACKAGECONFIG[ffmpeg] = "-DFEATURE_ffmpeg=ON,-DFEATURE_ffmpeg=OFF,ffmpeg"
PACKAGECONFIG[gstreamer] = "-DFEATURE_gstreamer=ON,-DFEATURE_gstreamer=OFF,gstreamer1.0 gstreamer1.0-plugins-base gstreamer1.0-plugins-bad"
PACKAGECONFIG[pulseaudio] = "-DFEATURE_pulseaudio=ON,-DFEATURE_pulseaudio=OFF,pulseaudio"
PACKAGECONFIG[qml] = ",,qtdeclarative qtdeclarative-native"
PACKAGECONFIG[spatialaudio] = "-DFEATURE_spatialaudio=ON,-DFEATURE_spatialaudio=OFF"
PACKAGECONFIG[spatialaudio_quick3d] = "-DFEATURE_spatialaudio_quick3d=ON,-DFEATURE_spatialaudio_quick3d=OFF,qtquick3d qtquick3d-native"
PACKAGECONFIG[vaapi] = "-DFEATURE_vaapi=ON,-DFEATURE_vaapi=OFF,libva"

QT_DEFAULT_MEDIA_BACKEND ?= "${@bb.utils.contains('PACKAGECONFIG', 'gstreamer', 'gstreamer', 'ffmpeg', d)}"
EXTRA_OECMAKE += "-DQT_DEFAULT_MEDIA_BACKEND=${QT_DEFAULT_MEDIA_BACKEND}"
