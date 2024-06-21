# use systemd
INIT_MANAGER:forcevariable = "systemd"

INHERIT += "buildstats buildstats-summary"
INHERIT += "rm_work"

# default to commercial Qt
QT_EDITION = "commercial"

LICENSE_FLAGS_ACCEPTED = "commercial_ffmpeg"

# include commercial addons and enable Qt QML Compiler
QT_COMMERCIAL_MODULES = "1"

# fast compression of SDK tar package
SDK_XZ_COMPRESSION_LEVEL = "-1"

# be more strict with QA warnings, turn them all to errors:
EXTRA_QA = "\
    ldflags useless-rpaths rpaths staticdev libdir xorg-driver-abi \
    textrel already-stripped incompatible-license files-invalid \
    installed-vs-shipped compile-host-path install-host-path \
    pn-overrides infodir build-deps \
    unknown-configure-option symlink-to-sysroot multilib \
    invalid-packageconfig host-user-contaminated uppercase-pn \
"
ERROR_QA:append = "${EXTRA_QA}"
WARN_QA:remove = "${EXTRA_QA}"

# workaround for qemu
# nativesdk-qemu-user-mips rdepends on nativesdk-bash, but it isn't a build dependency
INSANE_SKIP:nativesdk-qemu-user-mips = "build-deps"

# enable thumb for broader test coverage (oe-core autobuilder doesn't have thumb enabled)
PREFERRED_ARM_INSTRUCTION_SET              ?= "thumb"
ARM_INSTRUCTION_SET = "${PREFERRED_ARM_INSTRUCTION_SET}"

RDEPENDS:${PN}:append:pn-packagegroup-qt6-addons = " squish"

# enable webengine with commercial/proprietary features
DISTRO_FEATURES:append = " webengine"
PACKAGECONFIG:append:pn-qtwebengine = " proprietary-codecs"

PACKAGECONFIG:append:pn-qtbase = "\
    tslib kms gbm zstd libproxy gssapi vulkan mtdev cups \
    sql-mysql sql-odbc sql-psql sql-sqlite journald brotli \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'gtk', '', d)} \
    use-gold-linker \
"

# imageformats mng FIXME
PACKAGECONFIG:append:pn-qtimageformats = " jasper"

# enable examples for all Qt modules
PACKAGECONFIG:append:pn-qmlcompilerplus = " examples"
PACKAGECONFIG:append:pn-qt3d = " examples"
PACKAGECONFIG:append:pn-qt5compat = " examples"
PACKAGECONFIG:append:pn-qtapplicationmanager = " examples"
PACKAGECONFIG:append:pn-qtbase = " examples"
PACKAGECONFIG:append:pn-qtcharts = " examples"
PACKAGECONFIG:append:pn-qtcoap = " examples"
PACKAGECONFIG:append:pn-qtconnectivity = " examples"
PACKAGECONFIG:append:pn-qtdatavis3d = " examples"
PACKAGECONFIG:append:pn-qtdeclarative = " examples"
PACKAGECONFIG:append:pn-qtdeviceutilities = " examples"
PACKAGECONFIG:append:pn-qtgraphs = " examples"
PACKAGECONFIG:append:pn-qtgrpc = " examples"
PACKAGECONFIG:append:pn-qthttpserver = " examples"
PACKAGECONFIG:append:pn-qtimageformats = " examples"
PACKAGECONFIG:append:pn-qtinsighttracker = " examples"
PACKAGECONFIG:append:pn-qtinterfaceframework = " examples"
PACKAGECONFIG:append:pn-qtlanguageserver = " examples"
PACKAGECONFIG:append:pn-qtlocation = " examples"
PACKAGECONFIG:append:pn-qtlottie = " examples"
PACKAGECONFIG:append:pn-qtmqtt = " examples"
PACKAGECONFIG:append:pn-qtmultimedia = " examples"
PACKAGECONFIG:append:pn-qtnetworkauth = " examples"
PACKAGECONFIG:append:pn-qtopcua = " examples"
PACKAGECONFIG:append:pn-qtpdf = " examples"
PACKAGECONFIG:append:pn-qtpositioning = " examples"
PACKAGECONFIG:append:pn-qtquick3d = " examples"
PACKAGECONFIG:append:pn-qtquick3dphysics = " examples"
PACKAGECONFIG:append:pn-qtquickdesigner-components = " examples"
PACKAGECONFIG:append:pn-qtquicktimeline = " examples"
PACKAGECONFIG:append:pn-qtremoteobjects = " examples"
PACKAGECONFIG:append:pn-qtscxml = " examples"
PACKAGECONFIG:append:pn-qtsensors = " examples"
PACKAGECONFIG:append:pn-qtserialbus = " examples"
PACKAGECONFIG:append:pn-qtserialport = " examples"
PACKAGECONFIG:append:pn-qtshadertools = " examples"
PACKAGECONFIG:append:pn-qtspeech = " examples"
PACKAGECONFIG:append:pn-qtsvg = " examples"
PACKAGECONFIG:append:pn-qttools = " examples"
PACKAGECONFIG:append:pn-qttranslations = " examples"
PACKAGECONFIG:append:pn-qtvirtualkeyboard = " examples"
PACKAGECONFIG:append:pn-qtvncserver = " examples"
PACKAGECONFIG:append:pn-qtwayland = " examples"
PACKAGECONFIG:append:pn-qtwebchannel = " examples"
PACKAGECONFIG:append:pn-qtwebengine = " examples"
PACKAGECONFIG:append:pn-qtwebsockets = " examples"
PACKAGECONFIG:append:pn-qtwebview = " examples"
