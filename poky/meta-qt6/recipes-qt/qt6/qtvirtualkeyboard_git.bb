LICENSE = "(The-Qt-Company-Commercial | (GPL-3.0-only & Qt-GPL-exception-1.0) & GFDL-1.3-no-invariants-only) & Apache-2.0 & BSD-3-Clause"
LIC_FILES_CHKSUM = " \
    file://LICENSES/GFDL-1.3-no-invariants-only.txt;md5=a22d0be1ce2284b67950a4d1673dd1b0 \
    file://LICENSES/GPL-3.0-only.txt;md5=d32239bcb673463ab874e80d47fae504 \
    file://LICENSES/LicenseRef-Qt-Commercial.txt;md5=40a1036f91cefc0e3fabad241fb5f187 \
    file://LICENSES/Qt-GPL-exception-1.0.txt;md5=9a13522cd91a88fba784baf16ea66af8 \
    file://src/plugins/openwnn/3rdparty/openwnn/NOTICE;md5=50e3e853eb9dd5ccdf6192678106b3da \
    file://src/plugins/pinyin/3rdparty/pinyin/NOTICE;md5=506fbea94b9d051d6478776c50a4c66b \
    file://src/plugins/tcime/3rdparty/tcime/COPYING;md5=1474257e03071e0ffb9ed0db6dac8954 \
"

inherit qt6-cmake

include recipes-qt/qt6/qt6-git.inc
include recipes-qt/qt6/qt6.inc

# To enabled Nuance T9 Write support, you need to provide the licensed components
# and enable "t9write" in PACKAGECONFIG. This can be done in a separate .bbappend file.
# for example:
#T9WRITEPACKAGE = "${HOME}/Downloads/zzEval_QT_T9Write_Alpha_v750_20150916.zip"
#SRC_URI += "file://${T9WRITEPACKAGE};subdir=git/src/virtualkeyboard/3rdparty/t9write"
#PACKAGECONFIG = "t9write"

VKB_LANGUAGES ?= "\
    lang-ar_AR \
    lang-bg_BG \
    lang-cs_CZ \
    lang-da_DK \
    lang-de_DE \
    lang-el_GR \
    lang-en_GB \
    lang-en_US \
    lang-es_ES \
    lang-es_MX \
    lang-et_EE \
    lang-fa_FA \
    lang-fi_FI \
    lang-fr_CA \
    lang-fr_FR \
    lang-he_IL \
    lang-hi_IN \
    lang-hr_HR \
    lang-hu_HU \
    lang-id_ID \
    lang-it_IT \
    lang-ja_JP \
    lang-ko_KR \
    lang-ms_MY \
    lang-nb_NO \
    lang-nl_NL \
    lang-pl_PL \
    lang-pt_BR \
    lang-pt_PT \
    lang-ro_RO \
    lang-ru_RU \
    lang-sk_SK \
    lang-sl_SI \
    lang-sq_AL \
    lang-sr_SP \
    lang-sv_SE \
    lang-th_TH \
    lang-tr_TR \
    lang-uk_UA \
    lang-vi_VN \
    lang-zh_CN \
    lang-zh_TW \
"

PACKAGECONFIG ?= "${VKB_LANGUAGES} ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'desktop', '', d)}"

PACKAGECONFIG[desktop] = "-DFEATURE_vkb_desktop=ON,-DFEATURE_vkb_desktop=OFF"
PACKAGECONFIG[arrow-keynavigation] = "-DFEATURE_vkb_arrow_keynavigation=ON,-DFEATURE_vkb_arrow_keynavigation=OFF"
PACKAGECONFIG[hunspell] = "-DFEATURE_hunspell=ON,-DFEATURE_hunspell=OFF,hunspell"
PACKAGECONFIG[t9write] = "-DFEATURE_t9write=ON,-DFEATURE_t9write=OFF"
PACKAGECONFIG[lang-ar_AR] = "-DFEATURE_vkb_lang_ar_AR=ON,-DFEATURE_vkb_lang_ar_AR=OFF"
PACKAGECONFIG[lang-bg_BG] = "-DFEATURE_vkb_lang_bg_BG=ON,-DFEATURE_vkb_lang_bg_BG=OFF"
PACKAGECONFIG[lang-cs_CZ] = "-DFEATURE_vkb_lang_cs_CZ=ON,-DFEATURE_vkb_lang_cs_CZ=OFF"
PACKAGECONFIG[lang-da_DK] = "-DFEATURE_vkb_lang_da_DK=ON,-DFEATURE_vkb_lang_da_DK=OFF"
PACKAGECONFIG[lang-de_DE] = "-DFEATURE_vkb_lang_de_DE=ON,-DFEATURE_vkb_lang_de_DE=OFF"
PACKAGECONFIG[lang-el_GR] = "-DFEATURE_vkb_lang_el_GR=ON,-DFEATURE_vkb_lang_el_GR=OFF"
PACKAGECONFIG[lang-en_GB] = "-DFEATURE_vkb_lang_en_GB=ON,-DFEATURE_vkb_lang_en_GB=OFF"
PACKAGECONFIG[lang-en_US] = "-DFEATURE_vkb_lang_en_US=ON,-DFEATURE_vkb_lang_en_US=OFF"
PACKAGECONFIG[lang-es_ES] = "-DFEATURE_vkb_lang_es_ES=ON,-DFEATURE_vkb_lang_es_ES=OFF"
PACKAGECONFIG[lang-es_MX] = "-DFEATURE_vkb_lang_es_MX=ON,-DFEATURE_vkb_lang_es_MX=OFF"
PACKAGECONFIG[lang-et_EE] = "-DFEATURE_vkb_lang_et_EE=ON,-DFEATURE_vkb_lang_et_EE=OFF"
PACKAGECONFIG[lang-fa_FA] = "-DFEATURE_vkb_lang_fa_FA=ON,-DFEATURE_vkb_lang_fa_FA=OFF"
PACKAGECONFIG[lang-fi_FI] = "-DFEATURE_vkb_lang_fi_FI=ON,-DFEATURE_vkb_lang_fi_FI=OFF"
PACKAGECONFIG[lang-fr_CA] = "-DFEATURE_vkb_lang_fr_CA=ON,-DFEATURE_vkb_lang_fr_CA=OFF"
PACKAGECONFIG[lang-fr_FR] = "-DFEATURE_vkb_lang_fr_FR=ON,-DFEATURE_vkb_lang_fr_FR=OFF"
PACKAGECONFIG[lang-he_IL] = "-DFEATURE_vkb_lang_he_IL=ON,-DFEATURE_vkb_lang_he_IL=OFF"
PACKAGECONFIG[lang-hi_IN] = "-DFEATURE_vkb_lang_hi_IN=ON,-DFEATURE_vkb_lang_hi_IN=OFF"
PACKAGECONFIG[lang-hr_HR] = "-DFEATURE_vkb_lang_hr_HR=ON,-DFEATURE_vkb_lang_hr_HR=OFF"
PACKAGECONFIG[lang-hu_HU] = "-DFEATURE_vkb_lang_hu_HU=ON,-DFEATURE_vkb_lang_hu_HU=OFF"
PACKAGECONFIG[lang-id_ID] = "-DFEATURE_vkb_lang_id_ID=ON,-DFEATURE_vkb_lang_id_ID=OFF"
PACKAGECONFIG[lang-it_IT] = "-DFEATURE_vkb_lang_it_IT=ON,-DFEATURE_vkb_lang_it_IT=OFF"
PACKAGECONFIG[lang-ja_JP] = "-DFEATURE_vkb_lang_ja_JP=ON,-DFEATURE_vkb_lang_ja_JP=OFF"
PACKAGECONFIG[lang-ko_KR] = "-DFEATURE_vkb_lang_ko_KR=ON,-DFEATURE_vkb_lang_ko_KR=OFF"
PACKAGECONFIG[lang-ms_MY] = "-DFEATURE_vkb_lang_ms_MY=ON,-DFEATURE_vkb_lang_ms_MY=OFF"
PACKAGECONFIG[lang-nb_NO] = "-DFEATURE_vkb_lang_nb_NO=ON,-DFEATURE_vkb_lang_nb_NO=OFF"
PACKAGECONFIG[lang-nl_NL] = "-DFEATURE_vkb_lang_nl_NL=ON,-DFEATURE_vkb_lang_nl_NL=OFF"
PACKAGECONFIG[lang-pl_PL] = "-DFEATURE_vkb_lang_pl_PL=ON,-DFEATURE_vkb_lang_pl_PL=OFF"
PACKAGECONFIG[lang-pt_BR] = "-DFEATURE_vkb_lang_pt_BR=ON,-DFEATURE_vkb_lang_pt_BR=OFF"
PACKAGECONFIG[lang-pt_PT] = "-DFEATURE_vkb_lang_pt_PT=ON,-DFEATURE_vkb_lang_pt_PT=OFF"
PACKAGECONFIG[lang-ro_RO] = "-DFEATURE_vkb_lang_ro_RO=ON,-DFEATURE_vkb_lang_ro_RO=OFF"
PACKAGECONFIG[lang-ru_RU] = "-DFEATURE_vkb_lang_ru_RU=ON,-DFEATURE_vkb_lang_ru_RU=OFF"
PACKAGECONFIG[lang-sk_SK] = "-DFEATURE_vkb_lang_sk_SK=ON,-DFEATURE_vkb_lang_sk_SK=OFF"
PACKAGECONFIG[lang-sl_SI] = "-DFEATURE_vkb_lang_sl_SI=ON,-DFEATURE_vkb_lang_sl_SI=OFF"
PACKAGECONFIG[lang-sq_AL] = "-DFEATURE_vkb_lang_sq_AL=ON,-DFEATURE_vkb_lang_sq_AL=OFF"
PACKAGECONFIG[lang-sr_SP] = "-DFEATURE_vkb_lang_sr_SP=ON,-DFEATURE_vkb_lang_sr_SP=OFF"
PACKAGECONFIG[lang-sv_SE] = "-DFEATURE_vkb_lang_sv_SE=ON,-DFEATURE_vkb_lang_sv_SE=OFF"
PACKAGECONFIG[lang-th_TH] = "-DFEATURE_vkb_lang_th_TH=ON,-DFEATURE_vkb_lang_th_TH=OFF"
PACKAGECONFIG[lang-tr_TR] = "-DFEATURE_vkb_lang_tr_TR=ON,-DFEATURE_vkb_lang_tr_TR=OFF"
PACKAGECONFIG[lang-uk_UA] = "-DFEATURE_vkb_lang_uk_UA=ON,-DFEATURE_vkb_lang_uk_UA=OFF"
PACKAGECONFIG[lang-vi_VN] = "-DFEATURE_vkb_lang_vi_VN=ON,-DFEATURE_vkb_lang_vi_VN=OFF"
PACKAGECONFIG[lang-zh_CN] = "-DFEATURE_vkb_lang_zh_CN=ON,-DFEATURE_vkb_lang_zh_CN=OFF"
PACKAGECONFIG[lang-zh_TW] = "-DFEATURE_vkb_lang_zh_TW=ON,-DFEATURE_vkb_lang_zh_TW=OFF"

PACKAGES += "${PN}-dictionaries"
RRECOMMENDS:${PN} += "${PN}-dictionaries"
FILES:${PN}-dictionaries = "${QT6_INSTALL_DATADIR}/qtvirtualkeyboard/*/*.dat"

DEPENDS += "qtbase qtdeclarative qtsvg qtdeclarative-native"

