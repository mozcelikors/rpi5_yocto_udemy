LICENSE = "The-Qt-Company-Commercial | (GPL-3.0-only & Qt-GPL-exception-1.0)"
LIC_FILES_CHKSUM = " \
    file://LICENSES/GPL-3.0-only.txt;md5=d32239bcb673463ab874e80d47fae504 \
    file://LICENSES/LicenseRef-Qt-Commercial.txt;md5=40a1036f91cefc0e3fabad241fb5f187 \
    file://LICENSES/Qt-GPL-exception-1.0.txt;md5=9a13522cd91a88fba784baf16ea66af8 \
"

inherit qt6-cmake

include recipes-qt/qt6/qt6-git.inc
include recipes-qt/qt6/qt6.inc

DEPENDS += "qtbase qttools qttools-native"

PACKAGES = "${PN} ${PN}-dev"
PACKAGES_DYNAMIC = "${PN}-*"
PACKAGESPLITFUNCS:prepend = "split_translation_packages "

python split_translation_packages () {
    do_split_packages(d, d.expand('${QT6_INSTALL_TRANSLATIONSDIR}'),
                      r'^(.*?)(?:_..)+\.qm$', d.expand('${PN}-%s'),
                      'Qt translations for %s', extra_depends='')

    # Add dynamic packages to the rrecommends of the main packages
    pn = d.getVar('PN')
    pkgs = oe.utils.packages_filter_out_system(d)
    d.setVar('RRECOMMENDS:' + pn, ' '.join(pkgs))
}

FILES:${PN}-dev = "${QT6_INSTALL_TRANSLATIONSDIR}/catalogs.json"
