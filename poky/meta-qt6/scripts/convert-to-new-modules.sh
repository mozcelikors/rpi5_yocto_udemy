#!/bin/sh
#
# meta-qt6 is planned to be as much as possible a drop-in replacement
# for meta-qt5 layer. All the Qt module recipes use the same names as
# the ones in meta-qt5. This means that both meta-qt5 and meta-qt6
# layers can not be used at the same and adding both Qt5 and Qt6 to
# the same image is not possible.
#
# This script does a brute force rename for all the module names to make
# it possible to use both layers at the same time. The new module names
# get '6' postfix e.g. qtbase6, qtdeclarative6, qtdeclarative6-native.
#
# Since both Qt versions deploy files with same names, the install paths
# for either meta-qt5 or meta-qt6 must be changed.
#
# For meta-qt5:
#
#   QT_DIR_NAME  = "/qt5"
#
# For meta-qt6:
#
#   QT6_INSTALL_BINDIR:append = "/qt6"
#   QT6_INSTALL_DATADIR:append = "/qt6"
#   QT6_INSTALL_PLUGINSDIR:append = "/qt6"
#   QT6_INSTALL_QMLDIR:append = "/qt6"
#   QT6_INSTALL_TRANSLATIONSDIR:append = "/qt6"
#   EXTRANATIVEPATH:append = "/qt6"
#
# configured in a suitable global configuration file.
#

MODULES="\
  qt3d qt5compat qtapplicationmanager qtbase qtcharts qtcoap qtconnectivity qtdatavis3d qtdeclarative \
  qtdeviceutilities qtgrpc qthttpserver qtimageformats qtinsighttracker qtinterfaceframework qtlanguageserver \
  qtlocation qtlottie qtmqtt qtmultimedia qtnetworkauth qtopcua qtpdf qtpositioning qtquick3d qtquick3dphysics \
  qtquickdesigner-components qtquicktimeline qtremoteobjects qtscxml qtsensors qtserialbus qtserialport \
  qtshadertools qtspeech qtsvg qttools qttranslations qtvirtualkeyboard qtvncserver qtwayland qtwebchannel \
  qtwebengine qtwebsockets qtwebview"

for module in ${MODULES}; do
  newmodule=${module}6
  find * -type f ! -name *.patch ! -name *.sh -exec sed -i {} \
    -e "s/\([\[\"'=, \-]\)${module}/\1${newmodule}/g" \
    -e "/git/!s/\([\/]\)${module}/\1${newmodule}/g" \
    -e "/SRCREV/s/_${module}/_${newmodule}/g" \
    -e "s/QT_GIT/QT6_GIT/g" \
    -e "s/'\${PN}-%s'/'\${PN}-%s6'/" \
    -e "s/\${QT_MODULE}.git/\${@d.getVar(\'QT_MODULE\').replace(\'6\',\'\')}.git/" \;
  find * -depth -name *${module}* -execdir rename "s/${module}/${newmodule}/" '{}' \;
done
