OE_QMAKE_PATH_PREFIX = "${prefix}"
OE_QMAKE_PATH_HEADERS = "${QT6_INSTALL_INCLUDEDIR}"
OE_QMAKE_PATH_LIBS = "${QT6_INSTALL_LIBDIR}"
OE_QMAKE_PATH_ARCHDATA = "${QT6_INSTALL_ARCHDATADIR}"
OE_QMAKE_PATH_DATA = "${QT6_INSTALL_DATADIR}"
OE_QMAKE_PATH_BINS = "${QT6_INSTALL_BINDIR}"
OE_QMAKE_PATH_LIBEXECS = "${QT6_INSTALL_LIBEXECDIR}"
OE_QMAKE_PATH_PLUGINS = "${QT6_INSTALL_PLUGINSDIR}"
OE_QMAKE_PATH_QML = "${QT6_INSTALL_QMLDIR}"
OE_QMAKE_PATH_TRANSLATIONS = "${QT6_INSTALL_TRANSLATIONSDIR}"
OE_QMAKE_PATH_DOCS = "${QT6_INSTALL_DOCDIR}"
OE_QMAKE_PATH_SETTINGS = "${sysconfdir}"
OE_QMAKE_PATH_EXAMPLES = "${QT6_INSTALL_EXAMPLESDIR}"
OE_QMAKE_PATH_TESTS = "${QT6_INSTALL_TESTSDIR}"
OE_QMAKE_PATH_HOST_PREFIX = ""
OE_QMAKE_PATH_HOST_PREFIX:class-target = "${STAGING_DIR_NATIVE}"
OE_QMAKE_PATH_HOST_BINS = "${QT6_INSTALL_BINDIR}"
OE_QMAKE_PATH_HOST_DATA = "${QMAKE_MKSPEC_PATH_TARGET}"
OE_QMAKE_PATH_HOST_LIBS = "${STAGING_LIBDIR}"
OE_QMAKE_PATH_HOST_LIBEXECS = "${QT6_INSTALL_LIBEXECDIR}"
OE_QMAKE_PATH_EXTERNAL_HOST_BINS = "${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_HOST_BINS}"

# This is useful for target recipes to reference native mkspecs
QMAKE_MKSPEC_PATH_NATIVE = "${STAGING_LIBDIR_NATIVE}"
QMAKE_MKSPEC_PATH_TARGET = "${STAGING_LIBDIR}"

QMAKE_MKSPEC_PATH = "${QMAKE_MKSPEC_PATH_TARGET}"
QMAKE_MKSPEC_PATH:class-native = "${QMAKE_MKSPEC_PATH_NATIVE}"
QMAKE_MKSPEC_PATH:class-nativesdk = "${QMAKE_MKSPEC_PATH_NATIVE}"

DEPENDS:prepend = "qtbase-native "

# hardcode linux, because that's what 0001-Add-linux-oe-g-platform.patch adds
XPLATFORM:toolchain-clang = "linux-oe-clang"
XPLATFORM ?= "linux-oe-g++"

OE_QMAKE_PLATFORM_NATIVE = "${XPLATFORM}"
OE_QMAKE_PLATFORM = "${XPLATFORM}"

# Add -d to show debug output from every qmake call, but it prints *a lot*, better to add it only to debugged recipe
OE_QMAKE_DEBUG_OUTPUT ?= ""

# Don't look through supplied directories recursively by default
OE_QMAKE_RECURSIVE ?= ""

# Paths in .prl files contain SYSROOT value
SSTATE_SCAN_FILES += "*.pri *.prl *.prf"

# drop default -e and add needed OE_QMAKE vars explicitly
# the problem is that when generated Makefile has:
# CFLAGS = -pipe $(OE_QMAKE_CFLAGS) -O2 -pthread -D_REENTRANT -Wall -W -fPIC $(DEFINES)
# then OE_QMAKE_CFLAGS are exported and used correctly, but then whole CFLAGS is overwritten from env (and -fPIC lost and build fails)
EXTRA_OEMAKE = " \
    MAKEFLAGS='${PARALLEL_MAKE}' \
    OE_QMAKE_CC='${OE_QMAKE_CC}' \
    OE_QMAKE_CXX='${OE_QMAKE_CXX}' \
    OE_QMAKE_CFLAGS='${OE_QMAKE_CFLAGS}' \
    OE_QMAKE_CXXFLAGS='${OE_QMAKE_CXXFLAGS}' \
    OE_QMAKE_LINK='${OE_QMAKE_LINK}' \
    OE_QMAKE_LDFLAGS='${OE_QMAKE_LDFLAGS}' \
    OE_QMAKE_AR='${OE_QMAKE_AR}' \
    OE_QMAKE_STRIP='${OE_QMAKE_STRIP}' \
    OE_QMAKE_INCDIR_QT='${STAGING_DIR_TARGET}/${OE_QMAKE_PATH_HEADERS}' \
"

OE_QMAKE_QMAKE = "${OE_QMAKE_PATH_EXTERNAL_HOST_BINS}/qmake"
export OE_QMAKE_CC = "${CC}"
export OE_QMAKE_CFLAGS = "${CFLAGS}"
export OE_QMAKE_CXX = "${CXX}"
export OE_QMAKE_CXXFLAGS = "${CXXFLAGS}"
export OE_QMAKE_LINK = "${CXX}"
export OE_QMAKE_LDFLAGS = "${LDFLAGS}"
export OE_QMAKE_AR = "${AR}"
export OE_QMAKE_STRIP = "echo"
export OE_QMAKE_OBJCOPY = "${OBJCOPY}"
export OE_QMAKE_AR_LTCG = "${HOST_PREFIX}gcc-ar"

# qmake reads if from shell environment
export OE_QMAKE_QTCONF_PATH = "${WORKDIR}/qt.conf"

inherit qt6-paths remove-libtool

generate_target_qt_config_file() {
    qtconf="$1"
    cat > "${qtconf}" <<EOF
[Paths]
Prefix = ${OE_QMAKE_PATH_PREFIX}
Headers = ${OE_QMAKE_PATH_HEADERS}
Libraries = ${OE_QMAKE_PATH_LIBS}
ArchData = ${OE_QMAKE_PATH_ARCHDATA}
Data = ${OE_QMAKE_PATH_DATA}
Binaries = ${OE_QMAKE_PATH_BINS}
LibraryExecutables = ${OE_QMAKE_PATH_LIBEXECS}
Plugins = ${OE_QMAKE_PATH_PLUGINS}
Qml2Imports = ${OE_QMAKE_PATH_QML}
Translations = ${OE_QMAKE_PATH_TRANSLATIONS}
Documentation = ${OE_QMAKE_PATH_DOCS}
Settings = ${OE_QMAKE_PATH_SETTINGS}
Examples = ${OE_QMAKE_PATH_EXAMPLES}
Tests = ${OE_QMAKE_PATH_TESTS}
HostBinaries = ${OE_QMAKE_PATH_BINS}
HostData = ${OE_QMAKE_PATH_ARCHDATA}
HostLibraries = ${OE_QMAKE_PATH_LIBS}
HostSpec = ${OE_QMAKE_PLATFORM}
TargetSpec = ${OE_QMAKE_PLATFORM}
ExternalHostBinaries = ${OE_QMAKE_PATH_BINS}
Sysroot =
EOF
}

do_generate_qt_config_file() {
    generate_qt_config_file_paths
    generate_qt_config_file_effective_paths
}

generate_qt_config_file_paths() {
    cat > ${OE_QMAKE_QTCONF_PATH} <<EOF
[Paths]
Prefix = ${OE_QMAKE_PATH_PREFIX}
Headers = ${OE_QMAKE_PATH_HEADERS}
Libraries = ${OE_QMAKE_PATH_LIBS}
ArchData = ${OE_QMAKE_PATH_ARCHDATA}
Data = ${OE_QMAKE_PATH_DATA}
Binaries = ${OE_QMAKE_PATH_BINS}
LibraryExecutables = ${OE_QMAKE_PATH_LIBEXECS}
Plugins = ${OE_QMAKE_PATH_PLUGINS}
Qml2Imports = ${OE_QMAKE_PATH_QML}
Translations = ${OE_QMAKE_PATH_TRANSLATIONS}
Documentation = ${OE_QMAKE_PATH_DOCS}
Settings = ${OE_QMAKE_PATH_SETTINGS}
Examples = ${OE_QMAKE_PATH_EXAMPLES}
Tests = ${OE_QMAKE_PATH_TESTS}
HostBinaries = ${OE_QMAKE_PATH_HOST_BINS}
HostData = ${OE_QMAKE_PATH_HOST_DATA}
HostLibraries = ${OE_QMAKE_PATH_HOST_LIBS}
HostLibraryExecutables = ${OE_QMAKE_PATH_HOST_LIBEXECS}
HostSpec = ${OE_QMAKE_PLATFORM_NATIVE}
TargetSpec = ${OE_QMAKE_PLATFORM}
ExternalHostBinaries = ${OE_QMAKE_PATH_EXTERNAL_HOST_BINS}
Sysroot = ${STAGING_DIR_TARGET}
SysrootifyPrefix=true
EOF
}

generate_qt_config_file_effective_paths() {
    cat >> ${OE_QMAKE_QTCONF_PATH} <<EOF
[EffectivePaths]
HostBinaries = ${OE_QMAKE_PATH_EXTERNAL_HOST_BINS}
HostLibraries = ${STAGING_LIBDIR_NATIVE}
HostData = ${OE_QMAKE_PATH_HOST_DATA}
HostPrefix = ${STAGING_DIR_NATIVE}
HostLibraryExecutables = ${STAGING_DIR_NATIVE}${OE_QMAKE_PATH_HOST_LIBEXECS}
EOF
}
#
# Allows to override following values (as in version 5.0.1)
# Prefix The default prefix for all paths.
# Documentation The location for documentation upon install.
# Headers The location for all headers.
# Libraries The location of installed libraries.
# LibraryExecutables The location of installed executables required by libraries at runtime.
# Binaries The location of installed Qt binaries (tools and applications).
# Plugins The location of installed Qt plugins.
# Imports The location of installed QML extensions to import (QML 1.x).
# Qml2Imports The location of installed QML extensions to import (QML 2.x).
# ArchData The location of general architecture-dependent Qt data.
# Data The location of general architecture-independent Qt data.
# Translations The location of translation information for Qt strings.
# Examples The location for examples upon install.
# Tests The location of installed Qt testcases.
# Settings The location for Qt settings. Not applicable on Windows.

# For bootstrapped
# Sysroot The location of target sysroot
# HostPrefix The prefix for host tools when cross compiling (building tools for both systems)
# HostBinaries The location where to install host tools
# HostData The location where to install host data
# ExternalHostBinaries The location where we already have host tools (when cross compiling, but reusing existing tools)
# TargetSpec The location where to install target mkspec
# HostSpec The location where to install host mkspec

# qmake works fine with separate B, use it by default
SEPB = "${WORKDIR}/build"
B = "${SEPB}"

CONFIGURESTAMPFILE = "${WORKDIR}/qmake5_base_configure.sstate"

qmake5_base_preconfigure() {
        if [ -n "${CONFIGURESTAMPFILE}" -a -e "${CONFIGURESTAMPFILE}" ]; then
                if [ "`cat ${CONFIGURESTAMPFILE}`" != "${BB_TASKHASH}" -a "${S}" != "${B}" ]; then
                        echo "Previously configured separate build directory detected, cleaning ${B}"
                        rm -rf ${B}
                        mkdir ${B}
                fi
        fi
}

qmake5_base_postconfigure(){
        if [ -n "${CONFIGURESTAMPFILE}" ]; then
                echo ${BB_TASKHASH} > ${CONFIGURESTAMPFILE}
        fi
}

EXTRAQCONFFUNCS ??= ""

do_configure[prefuncs] += "qmake5_base_preconfigure ${EXTRAQCONFFUNCS}"
do_configure[postfuncs] += "qmake5_base_postconfigure"

addtask generate_qt_config_file after do_patch before do_configure

do_configure () {
    if [ -z "${QMAKE_PROFILES}" ]; then
        PROFILES="`ls ${S}/*.pro`"
    else
        PROFILES="${QMAKE_PROFILES}"
        bbnote "qmake using profiles: '${QMAKE_PROFILES}'"
    fi

    if [ ! -z "${EXTRA_QMAKEVARS_POST}" ]; then
        AFTER="-after"
        QMAKE_VARSUBST_POST="${EXTRA_QMAKEVARS_POST}"
        bbnote "qmake postvar substitution: '${EXTRA_QMAKEVARS_POST}'"
    fi

    if [ ! -z "${EXTRA_QMAKEVARS_PRE}" ]; then
        QMAKE_VARSUBST_PRE="${EXTRA_QMAKEVARS_PRE}"
        bbnote "qmake prevar substitution: '${EXTRA_QMAKEVARS_PRE}'"
    fi

    if [ ! -z "${EXTRA_QMAKEVARS_CONFIGURE}" ]; then
        QMAKE_VARSUBST_CONFIGURE="${EXTRA_QMAKEVARS_CONFIGURE}"
        bbnote "qmake configure substitution: '${EXTRA_QMAKEVARS_CONFIGURE}'"
    fi

    # for config.tests to read this
    export QMAKE_MAKE_ARGS="${EXTRA_OEMAKE}"

    CMD="${OE_QMAKE_QMAKE} -makefile -o Makefile ${OE_QMAKE_DEBUG_OUTPUT} ${OE_QMAKE_RECURSIVE} $QMAKE_VARSUBST_PRE $AFTER $PROFILES $QMAKE_VARSUBST_POST -- $QMAKE_VARSUBST_CONFIGURE"
    ${OE_QMAKE_QMAKE} -makefile -o Makefile ${OE_QMAKE_DEBUG_OUTPUT} ${OE_QMAKE_RECURSIVE} $QMAKE_VARSUBST_PRE $AFTER $PROFILES $QMAKE_VARSUBST_POST -- $QMAKE_VARSUBST_CONFIGURE || die "Error calling $CMD"
}

do_install:class-native() {
    oe_runmake install INSTALL_ROOT=${D}
    find "${D}" -ignore_readdir_race -name "*.la" -delete
    if ls ${D}${libdir}/pkgconfig/Qt5*.pc >/dev/null 2>/dev/null; then
        sed -i "s@-L${STAGING_LIBDIR}@-L\${libdir}@g" ${D}${libdir}/pkgconfig/Qt5*.pc
    fi
}

do_install() {
    # Fix install paths for all
    find . -name "Makefile*" | xargs -r sed -i "s,(INSTALL_ROOT)${STAGING_DIR_TARGET},(INSTALL_ROOT),g"
    find . -name "Makefile*" | xargs -r sed -i "s,(INSTALL_ROOT)${STAGING_DIR_HOST},(INSTALL_ROOT),g"
    find . -name "Makefile*" | xargs -r sed -i "s,(INSTALL_ROOT)${STAGING_DIR_NATIVE},(INSTALL_ROOT),g"

    oe_runmake install INSTALL_ROOT=${D}

    # Replace host paths with qmake built-in properties
    find ${D} \( -name "*.pri" -or -name "*.prl" \) -exec \
        sed -i -e 's|${STAGING_DIR_NATIVE}|$$[QT_HOST_PREFIX/get]|g' \
            -e 's|${STAGING_DIR_HOST}|$$[QT_SYSROOT]|g' {} \;

    # Replace host paths with pkg-config built-in variable
    find ${D} -name "*.pc" -exec \
        sed -i -e 's|prefix=${STAGING_DIR_HOST}|prefix=|g' \
            -e 's|${STAGING_DIR_HOST}|${pc_sysrootdir}|g' {} \;

    # Replace resolved lib path with the lib name
    find ${D} -name "*.cmake" -exec \
        sed -i -e 's@/[^;]*/lib\([^;]*\)\.\(so\|a\)@\1@g' {} \;

}
