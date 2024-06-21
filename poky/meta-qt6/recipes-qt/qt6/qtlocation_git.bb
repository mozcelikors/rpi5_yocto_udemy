LICENSE = "The-Qt-Company-Commercial | (GPL-3.0-only & Qt-GPL-exception-1.0) & (LGPL-3.0-only | GPL-2.0-only | GPL-3.0-only) & GFDL-1.3-no-invariants-only & BSD-3-Clause"
LIC_FILES_CHKSUM = " \
    file://LICENSE.LGPL3;md5=e6a600fd5e1d9cbde2d983680233ad02 \
    file://LICENSE.GPL2;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://LICENSE.GPL3;md5=d32239bcb673463ab874e80d47fae504 \
    file://LICENSE.GPL3-EXCEPT;md5=763d8c535a234d9a3fb682c7ecb6c073 \
    file://LICENSE.FDL;md5=6d9f2a9af4c8b8c3c769f6cc1b6aaf7e \
"

inherit qt6-cmake

include recipes-qt/qt6/qt6-git.inc
include recipes-qt/qt6/qt6.inc

DEPENDS += "qtbase qtpositioning"

PACKAGECONFIG ?= "osm qml"
PACKAGECONFIG[esri] = "-DFEATURE_geoservices_esri=ON,-DFEATURE_geoservices_esri=OFF,"
PACKAGECONFIG[mapbox] = "-DFEATURE_geoservices_mapbox=ON,-DFEATURE_geoservices_mapbox=OFF,"
PACKAGECONFIG[nokia] = "-DFEATURE_geoservices_nokia=ON,-DFEATURE_geoservices_nokia=OFF,"
PACKAGECONFIG[osm] = "-DFEATURE_geoservices_osm=ON,-DFEATURE_geoservices_osm=OFF,"
PACKAGECONFIG[qml] = ",,qtdeclarative qtdeclarative-native"
