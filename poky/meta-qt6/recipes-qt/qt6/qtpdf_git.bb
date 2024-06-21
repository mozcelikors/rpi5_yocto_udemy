require recipes-qt/qt6/qtwebengine.inc
require recipes-qt/qt6/chromium-gn.inc

DEPENDS += " \
    nodejs-native \
    gperf-native \
    bison-native \
    nss nss-native \
    qtbase qtdeclarative qtdeclarative-native \
    gn-native \
    libxkbcommon \
    python3-html5lib-native \
"

EXTRA_OECMAKE += "\
    -DFEATURE_qtwebengine_build=OFF \
    -DFEATURE_qtpdf_build=ON \
"

PACKAGECONFIG ?= "qml widgets"
PACKAGECONFIG[pdf-v8] = "-DFEATURE_pdf_v8=ON,-DFEATURE_pdf_v8=OFF,qemu-native"
PACKAGECONFIG[pdf-xfa] = "-DFEATURE_pdf_xfa=ON,-DFEATURE_pdf_xfa=OFF"
PACKAGECONFIG[pdf-xfa-bmp] = "-DFEATURE_pdf_xfa_bmp=ON,-DFEATURE_pdf_xfa_bmp=OFF"
PACKAGECONFIG[pdf-xfa-gif] = "-DFEATURE_pdf_xfa_gif=ON,-DFEATURE_pdf_xfa_gif=OFF"
PACKAGECONFIG[pdf-xfa-png] = "-DFEATURE_pdf_xfa_png=ON,-DFEATURE_pdf_xfa_png=OFF"
PACKAGECONFIG[pdf-xfa-tiff] = "-DFEATURE_pdf_xfa_tiff=ON,-DFEATURE_pdf_xfa_tiff=OFF"
PACKAGECONFIG[qml] = "-DFEATURE_qtpdf_quick_build=ON,-DFEATURE_qtpdf_quick_build=OFF"
PACKAGECONFIG[widgets] = "-DFEATURE_qtpdf_widgets_build=ON,-DFEATURE_qtpdf_widgets_build=OFF"

ENABLE_QMLCOMPILER = "0"

do_install:append() {
    # remove conflicting files with QtWebEngine
    rm -f ${D}${libdir}/cmake/Qt6BuildInternals/StandaloneTests/QtWebEngineTestsConfig.cmake
    rm -f ${D}${libdir}/cmake/Qt6/Find*.cmake
}
