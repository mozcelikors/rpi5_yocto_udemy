inherit qt6-paths

SDK_POSTPROCESS_COMMAND:prepend = "create_qt6_sdk_files;"

EXE_EXT = ""
EXE_EXT:sdkmingw32 = ".exe"
PATH_DELIM = ":"
PATH_DELIM:sdkmingw32 = ";"

QT6_INSTALL_HOST_LIBEXECDIR = "${QT6_INSTALL_LIBEXECDIR}"
QT6_INSTALL_HOST_LIBEXECDIR:sdkmingw32 = "${QT6_INSTALL_LIBEXECDIR:mingw32}"

TARGET_SYSROOT ?= "${SDKTARGETSYSROOT}"
NATIVE_SYSROOT ?= "${SDKPATHNATIVE}"

create_qt6_sdk_files () {
    # Generate a qt.conf file to be deployed with the SDK
    qtconf=${WORKDIR}/qt.conf
    echo '[Paths]' > $qtconf
    echo 'Prefix = ${prefix}' >> $qtconf
    echo 'Headers = ${QT6_INSTALL_INCLUDEDIR}' >> $qtconf
    echo 'Libraries = ${QT6_INSTALL_LIBDIR}' >> $qtconf
    echo 'ArchData = ${QT6_INSTALL_ARCHDATADIR}' >> $qtconf
    echo 'Data = ${QT6_INSTALL_DATADIR}' >> $qtconf
    echo 'Binaries = ${QT6_INSTALL_BINDIR}' >> $qtconf
    echo 'LibraryExecutables = ${QT6_INSTALL_LIBEXECDIR}' >> $qtconf
    echo 'Plugins = ${QT6_INSTALL_PLUGINSDIR}' >> $qtconf
    echo 'Qml2Imports = ${QT6_INSTALL_QMLDIR}' >> $qtconf
    echo 'Translations = ${QT6_INSTALL_TRANSLATIONSDIR}' >> $qtconf
    echo 'Documentation = ${QT6_INSTALL_DOCDIR}' >> $qtconf
    echo 'Settings = ${QT6_INSTALL_SYSCONFDIR}' >> $qtconf
    echo 'Examples = ${QT6_INSTALL_EXAMPLESDIR}' >> $qtconf
    echo 'Tests = ${QT6_INSTALL_TESTSDIR}' >> $qtconf
    echo 'HostPrefix = ${@os.path.relpath("${NATIVE_SYSROOT}", "${NATIVE_SYSROOT}${QT6_INSTALL_BINDIR}")}' >> $qtconf
    echo 'HostData = ${@os.path.relpath("${TARGET_SYSROOT}${QT6_INSTALL_ARCHDATADIR}", "${NATIVE_SYSROOT}")}' >> $qtconf
    echo 'HostBinaries = ${@os.path.relpath("${NATIVE_SYSROOT}${QT6_INSTALL_BINDIR}", "${NATIVE_SYSROOT}")}' >> $qtconf
    echo 'HostLibraries = ${@os.path.relpath("${NATIVE_SYSROOT}${QT6_INSTALL_LIBDIR}", "${NATIVE_SYSROOT}")}' >> $qtconf
    echo 'HostLibraryExecutables = ${@os.path.relpath("${NATIVE_SYSROOT}${QT6_INSTALL_HOST_LIBEXECDIR}", "${NATIVE_SYSROOT}")}' >> $qtconf
    echo 'Sysroot = ${@os.path.relpath("${TARGET_SYSROOT}", "${NATIVE_SYSROOT}${QT6_INSTALL_BINDIR}")}' >> $qtconf
    echo 'HostSpec = linux-oe-g++' >> $qtconf
    echo 'TargetSpec = linux-oe-g++' >> $qtconf
    echo 'SysrootifyPrefix = true' >> $qtconf

    # add qt.conf to both bin and libexec dirs
    cp ${WORKDIR}/qt.conf ${SDK_OUTPUT}${NATIVE_SYSROOT}${QT6_INSTALL_BINDIR}/
    cp ${WORKDIR}/qt.conf ${SDK_OUTPUT}${NATIVE_SYSROOT}${QT6_INSTALL_HOST_LIBEXECDIR}/
    cp ${WORKDIR}/qt.conf ${SDK_OUTPUT}${NATIVE_SYSROOT}${QT6_INSTALL_BINDIR}/target_qt.conf

    install -d ${SDK_OUTPUT}${NATIVE_SYSROOT}/environment-setup.d
    script=${SDK_OUTPUT}${NATIVE_SYSROOT}/environment-setup.d/qt6.sh
    touch $script
    echo 'export OE_QMAKE_CFLAGS="$CFLAGS"' >> $script
    echo 'export OE_QMAKE_CXXFLAGS="$CXXFLAGS"' >> $script
    echo 'export OE_QMAKE_LDFLAGS="$LDFLAGS"' >> $script
    echo 'export OE_QMAKE_CC="$CC"' >> $script
    echo 'export OE_QMAKE_CXX="$CXX"' >> $script
    echo 'export OE_QMAKE_LINK="$CXX"' >> $script
    echo 'export OE_QMAKE_AR="$AR"' >> $script
    echo 'export OE_QMAKE_STRIP="$STRIP"' >> $script
    echo 'export OE_QMAKE_OBJCOPY="$OBJCOPY"' >> $script
    echo 'export OE_QMAKE_AR_LTCG="${HOST_PREFIX}gcc-ar"' >> $script

    # Generate a toolchain file for using Qt without running setup-environment script
    cat > ${SDK_OUTPUT}${NATIVE_SYSROOT}/usr/share/cmake/Qt6Toolchain.cmake <<EOF
cmake_minimum_required(VERSION 3.11)
include_guard(GLOBAL)

get_filename_component(SYSROOTS \${CMAKE_CURRENT_LIST_DIR}/../../../.. ABSOLUTE)

set(ENV{PATH} "${NATIVE_SYSROOT}${bindir}${PATH_DELIM}\$ENV{PATH}")
set(ENV{PKG_CONFIG_SYSROOT_DIR} "${TARGET_SYSROOT}")
set(ENV{PKG_CONFIG_PATH} "${TARGET_SYSROOT}${libdir}/pkgconfig")

set(CMAKE_SYSTEM_NAME Linux)
set(CMAKE_SYSROOT ${TARGET_SYSROOT})

set(CMAKE_FIND_ROOT_PATH ${TARGET_SYSROOT})
set(CMAKE_FIND_ROOT_PATH_MODE_PROGRAM NEVER)
set(CMAKE_FIND_ROOT_PATH_MODE_LIBRARY ONLY)
set(CMAKE_FIND_ROOT_PATH_MODE_INCLUDE ONLY)
set(CMAKE_FIND_ROOT_PATH_MODE_PACKAGE ONLY)

set(CMAKE_SYSTEM_PROCESSOR ${TUNE_PKGARCH})

set(CMAKE_C_COMPILER "${NATIVE_SYSROOT}${bindir}/${TARGET_SYS}/${TARGET_PREFIX}gcc${EXE_EXT}")
set(CMAKE_CXX_COMPILER "${NATIVE_SYSROOT}${bindir}/${TARGET_SYS}/${TARGET_PREFIX}g++${EXE_EXT}")

set(TARGET_COMPILER_FLAGS "${TARGET_CC_ARCH} --sysroot=${TARGET_SYSROOT}")
set(TARGET_LINKER_FLAGS "${TARGET_LDFLAGS}")

include(CMakeInitializeConfigs)

function(cmake_initialize_per_config_variable _PREFIX _DOCSTRING)
  if (_PREFIX MATCHES "CMAKE_(C|CXX|ASM)_FLAGS")
    set(CMAKE_\${CMAKE_MATCH_1}_FLAGS_INIT "\${TARGET_COMPILER_FLAGS}")

    foreach (config DEBUG RELEASE MINSIZEREL RELWITHDEBINFO)
      if (DEFINED TARGET_COMPILER_FLAGS_\${config})
        set(CMAKE_\${CMAKE_MATCH_1}_FLAGS_\${config}_INIT "\${TARGET_COMPILER_FLAGS_\${config}}")
      endif()
    endforeach()
  endif()

  if (_PREFIX MATCHES "CMAKE_(SHARED|MODULE|EXE)_LINKER_FLAGS")
    foreach (config SHARED MODULE EXE)
      set(CMAKE_\${config}_LINKER_FLAGS_INIT "\${TARGET_LINKER_FLAGS}")
    endforeach()
  endif()

  _cmake_initialize_per_config_variable(\${ARGV})
endfunction()

if(CMAKE_HOST_WIN32)
  set(CMAKE_BUILD_WITH_INSTALL_RPATH ON)
endif()

if(NOT DEFINED CMAKE_INSTALL_PREFIX)
  set(CMAKE_INSTALL_PREFIX_INITIALIZED_TO_DEFAULT 1)
endif()
set(CMAKE_INSTALL_PREFIX "${prefix}" CACHE PATH "Install path prefix")

# Include the toolchain configuration subscripts
file( GLOB toolchain_config_files "\${CMAKE_CURRENT_LIST_DIR}/OEToolchainConfig.cmake.d/*.cmake" )
foreach(config \${toolchain_config_files})
    include(\${config})
endforeach()
EOF

    # resolve absolute paths at runtime
    sed -i -e 's|${SDKPATH}/sysroots|\${SYSROOTS}|g' \
        ${SDK_OUTPUT}${NATIVE_SYSROOT}/usr/share/cmake/Qt6Toolchain.cmake

    # Conan profile
    mkdir -p ${SDK_OUTPUT}${NATIVE_SYSROOT}/usr/share/conan
    cat > ${SDK_OUTPUT}${NATIVE_SYSROOT}/usr/share/conan/profile <<EOF
include(${TARGET_SYSROOT}${datadir}/conan/profile)
[env]
QT_CONFIGURE_MODULE=${NATIVE_SYSROOT}${QT6_INSTALL_BINDIR}/qt-configure-module
EOF
}

# default debug prefix map isn't valid in the SDK
DEBUG_PREFIX_MAP = ""
lcl_maybe_fortify = ""
