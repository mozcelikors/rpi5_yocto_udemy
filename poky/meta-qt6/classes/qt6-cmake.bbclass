inherit cmake python3native perlnative pkgconfig qt6-paths

DEPENDS:prepend = "qtbase-native "

QT_MESSAGE_LOG_LEVEL ?= "STATUS"
EXTRA_OECMAKE += "\
    -DQT_CMAKE_DEBUG_EXTEND_TARGET=ON \
    -DQT_BUILD_INTERNALS_NO_FORCE_SET_INSTALL_PREFIX=ON \
    -DCMAKE_MESSAGE_LOG_LEVEL=${QT_MESSAGE_LOG_LEVEL} \
"
QT_FORCE_BUILD_TOOLS ?= "OFF"
EXTRA_OECMAKE:append:class-target = "\
    -DQT_HOST_PATH:PATH=${RECIPE_SYSROOT_NATIVE}${prefix_native}/ \
    -DQT_FORCE_BUILD_TOOLS=${QT_FORCE_BUILD_TOOLS} \
    -D__harfbuzz_broken_config_file=TRUE \
"
EXTRA_OECMAKE:append:class-nativesdk = "\
    -DQT_HOST_PATH:PATH=${RECIPE_SYSROOT_NATIVE}${prefix_native}/ \
    -DQT_FORCE_BUILD_TOOLS=ON \
"

EXTRA_OECMAKE += "\
    -DINSTALL_ARCHDATADIR:PATH=${@os.path.relpath(d.getVar('QT6_INSTALL_ARCHDATADIR'), d.getVar('prefix') + '/')} \
    -DINSTALL_BINDIR:PATH=${@os.path.relpath(d.getVar('QT6_INSTALL_BINDIR'), d.getVar('prefix') + '/')} \
    -DINSTALL_DATADIR:PATH=${@os.path.relpath(d.getVar('QT6_INSTALL_DATADIR'), d.getVar('prefix') + '/')} \
    -DINSTALL_DESCRIPTIONSDIR:PATH=${@os.path.relpath(d.getVar('QT6_INSTALL_DESCRIPTIONSDIR'), d.getVar('prefix') + '/')} \
    -DINSTALL_DOCDIR:PATH=${@os.path.relpath(d.getVar('QT6_INSTALL_DOCDIR'), d.getVar('prefix') + '/')} \
    -DINSTALL_EXAMPLESDIR:PATH=${@os.path.relpath(d.getVar('QT6_INSTALL_EXAMPLESDIR'), d.getVar('prefix') + '/')} \
    -DINSTALL_INCLUDEDIR:PATH=${@os.path.relpath(d.getVar('QT6_INSTALL_INCLUDEDIR'), d.getVar('prefix') + '/')} \
    -DINSTALL_LIBDIR:PATH=${@os.path.relpath(d.getVar('QT6_INSTALL_LIBDIR'), d.getVar('prefix') + '/')} \
    -DINSTALL_LIBEXECDIR:PATH=${@os.path.relpath(d.getVar('QT6_INSTALL_LIBEXECDIR'), d.getVar('prefix') + '/')} \
    -DINSTALL_PLUGINSDIR:PATH=${@os.path.relpath(d.getVar('QT6_INSTALL_PLUGINSDIR'), d.getVar('prefix') + '/')} \
    -DINSTALL_QMLDIR:PATH=${@os.path.relpath(d.getVar('QT6_INSTALL_QMLDIR'), d.getVar('prefix') + '/')} \
    -DINSTALL_SYSCONFDIR:PATH=${@os.path.relpath(d.getVar('QT6_INSTALL_SYSCONFDIR'), d.getVar('prefix') + '/')} \
    -DINSTALL_TESTSDIR:PATH=${@os.path.relpath(d.getVar('QT6_INSTALL_TESTSDIR'), d.getVar('prefix') + '/')} \
    -DINSTALL_TRANSLATIONSDIR:PATH=${@os.path.relpath(d.getVar('QT6_INSTALL_TRANSLATIONSDIR'), d.getVar('prefix') + '/')} \
    -DINSTALL_MKSPECSDIR:PATH=${@os.path.relpath(d.getVar('QT6_INSTALL_MKSPECSDIR'), d.getVar('prefix') + '/')} \
"

do_install:append() {
    # Replace host paths with qmake built-in properties QTBUG-84725
    find ${D} \( -name "*.pri" -or -name "*.prl" \) -exec \
        sed -i -e 's|${STAGING_DIR_NATIVE}|$$[QT_HOST_PREFIX/get]|g' \
               -e 's|${STAGING_DIR_HOST}|$$[QT_SYSROOT]|g' \
               -e '/QMAKE_PRL_BUILD_DIR/d' {} \;
}

export QT_DISABLE_SHADER_DISK_CACHE = "1"
