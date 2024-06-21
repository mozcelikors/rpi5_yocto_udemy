LICENSE = "(The-Qt-Company-Commercial | (GPL-3.0-only & Qt-GPL-exception-1.0) & (LGPL-3.0-only | GPL-2.0-only | GPL-3.0-only) & GFDL-1.3-no-invariants-only) & Apache-2.0 & BSD-3-Clause & BSL-1.0 & MIT"
LIC_FILES_CHKSUM = " \
    file://LICENSES/BSD-3-Clause.txt;md5=cb40fa7520502d8c7a3aea47cae1316c \
    file://LICENSES/BSL-1.0.txt;md5=8c92b4c255bdcce2989707d5b8a4d302 \
    file://LICENSES/GFDL-1.3-no-invariants-only.txt;md5=a22d0be1ce2284b67950a4d1673dd1b0 \
    file://LICENSES/GPL-2.0-only.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://LICENSES/GPL-3.0-only.txt;md5=d32239bcb673463ab874e80d47fae504 \
    file://LICENSES/LGPL-3.0-only.txt;md5=e6a600fd5e1d9cbde2d983680233ad02 \
    file://LICENSES/LicenseRef-Qt-Commercial.txt;md5=40a1036f91cefc0e3fabad241fb5f187 \
    file://LICENSES/Qt-GPL-exception-1.0.txt;md5=9a13522cd91a88fba784baf16ea66af8 \
    file://src/assistant/qlitehtml/src/3rdparty/litehtml/LICENSE;md5=55d411204c54bf2524f471635a7d306a \
    file://src/assistant/qlitehtml/src/3rdparty/litehtml/src/gumbo/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
"

inherit qt6-cmake

include recipes-qt/qt6/qt6-git.inc
include recipes-qt/qt6/qt6.inc

SRC_URI += " \
    ${QT_GIT}/playground/qlitehtml.git;name=qttools-qlitehtml;branch=master;protocol=${QT_GIT_PROTOCOL};destsuffix=git/src/assistant/qlitehtml \
    git://github.com/litehtml/litehtml.git;name=qttools-qlitehtml-litehtml;branch=master;destsuffix=git/src/assistant/qlitehtml/src/3rdparty/litehtml;protocol=https \
"

DEPENDS += "qtbase qtdeclarative qttools-native"

PACKAGECONFIG:class-native = "${@bb.utils.contains('BBFILE_COLLECTIONS', 'clang-layer', 'clang', '', d)}"
PACKAGECONFIG:class-nativesdk = "${@bb.utils.contains('BBFILE_COLLECTIONS', 'clang-layer', 'clang', '', d)}"
PACKAGECONFIG:remove:mingw32 = "clang"

PACKAGECONFIG[clang] = "-DFEATURE_clang=ON,-DFEATURE_clang=OFF,clang"

FILES:${PN}-tools += "${QT6_INSTALL_DATADIR}/phrasebooks"

BBCLASSEXTEND = "native nativesdk"

SRCREV_FORMAT = "qttools_qttools-qlitehtml_qttools-qlitehtml-litehtml"
