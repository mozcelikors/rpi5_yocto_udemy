LICENSE = "(The-Qt-Company-Commercial | (GPL-3.0-only & Qt-GPL-exception-1.0) & GFDL-1.3-no-invariants-only) & (Apache-2.0 | MIT) & BSD-3-Clause & BSD-2-Clause & Apache-2.0 & GPL-3-with-bison-exception"
LIC_FILES_CHKSUM = " \
    file://LICENSES/BSD-3-Clause.txt;md5=cb40fa7520502d8c7a3aea47cae1316c \
    file://LICENSES/GFDL-1.3-no-invariants-only.txt;md5=a22d0be1ce2284b67950a4d1673dd1b0 \
    file://LICENSES/GPL-3.0-only.txt;md5=d32239bcb673463ab874e80d47fae504 \
    file://LICENSES/LicenseRef-Qt-Commercial.txt;md5=40a1036f91cefc0e3fabad241fb5f187 \
    file://LICENSES/Qt-GPL-exception-1.0.txt;md5=9a13522cd91a88fba784baf16ea66af8 \
    file://src/3rdparty/SPIRV-Cross/KHRONOS-LICENSE.txt;md5=220cd23564f44ef34996a001e2651a36 \
    file://src/3rdparty/SPIRV-Cross/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57 \
    file://src/3rdparty/glslang/LICENSE.txt;md5=2a2b5acd7bc4844964cfda45fe807dc3 \
"

inherit qt6-cmake

include recipes-qt/qt6/qt6-git.inc
include recipes-qt/qt6/qt6.inc

DEPENDS = "qtbase qtshadertools-native"
DEPENDS:append:class-native = " spirv-tools-native"

RDEPENDS:${PN}-tools = "spirv-tools"
RDEPENDS:${PN}-tools:remove:mingw32 = "spirv-tools"

BBCLASSEXTEND = "native nativesdk"

