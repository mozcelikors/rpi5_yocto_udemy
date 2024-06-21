LICENSE = "The-Qt-Company-Commercial"
LIC_FILES_CHKSUM = " \
    file://src/qmlcompilerplus/cppcodegen_p.h;endline=27;md5=6a1dccd03d0d5864357e72b67def8ff2 \
"

inherit qt6-cmake

include recipes-qt/qt6/qt6-git.inc
include recipes-qt/qt6/qt6.inc
include recipes-qt/qt6/qt6-commercial.inc

DEPENDS += "qtbase qtdeclarative qtdeclarative-native qmlcompilerplus-native"

BBCLASSEXTEND = "native nativesdk"
