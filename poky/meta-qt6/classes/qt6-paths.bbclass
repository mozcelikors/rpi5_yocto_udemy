# Install locations:

# Executables [PREFIX/bin]
QT6_INSTALL_BINDIR ?= "${bindir}"
# Header files [PREFIX/include]
QT6_INSTALL_INCLUDEDIR ?= "${includedir}"
# Libraries [PREFIX/lib]
QT6_INSTALL_LIBDIR ?= "${libdir}"
# Arch-dependent data [PREFIX]
QT6_INSTALL_ARCHDATADIR ?= "${libdir}"
# Plugins [ARCHDATADIR/plugins]
QT6_INSTALL_PLUGINSDIR ?= "${libdir}/plugins"
# Helper programs [ARCHDATADIR/bin on Windows, ARCHDATADIR/libexec otherwise]
QT6_INSTALL_LIBEXECDIR ?= "${libexecdir}"
QT6_INSTALL_LIBEXECDIR:mingw32 ?= "${bindir}"
# QML2 imports [ARCHDATADIR/qml]
QT6_INSTALL_QMLDIR ?= "${libdir}/qml"
# "Arch-independent data [PREFIX]
QT6_INSTALL_DATADIR ?= "${datadir}"
# Documentation [DATADIR/doc]
QT6_INSTALL_DOCDIR ?= "${docdir}"
# Translations [DATADIR/translations]
QT6_INSTALL_TRANSLATIONSDIR ?= "${datadir}/translations"
# Settings used by Qt programs [PREFIX/etc/xdg]
QT6_INSTALL_SYSCONFDIR ?= "${sysconfdir}/xdg"
# Examples [PREFIX/examples]
QT6_INSTALL_EXAMPLESDIR ?= "${datadir}/examples"
# Tests [PREFIX/tests]
QT6_INSTALL_TESTSDIR ?= "${prefix}/tests"
# Module description files directory [DATADIR/modules]
QT6_INSTALL_DESCRIPTIONSDIR ?= "${datadir}/modules"
# Mkspecs files [PREFIX/mkspecs]
QT6_INSTALL_MKSPECSDIR ?= "${libdir}/mkspecs"
