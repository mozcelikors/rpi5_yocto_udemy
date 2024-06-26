DESCRIPTION ?= "Qt is a cross-platform application development framework for desktop, embedded and mobile."
HOMEPAGE ?= "https://www.qt.io"

inherit srcrev-update

QT_MODULE ?= "${BPN}"
QT_MODULE_BRANCH ?= "6.7"
QT_MODULE_BRANCH_PARAM ?= "branch=${QT_MODULE_BRANCH};nobranch=1"
QT_MODULE_REPO ?= "${QT_MODULE}.git"

SRC_URI = "${QT_GIT}/${QT_GIT_PROJECT}/${QT_MODULE_REPO};name=${QT_MODULE};${QT_MODULE_BRANCH_PARAM};protocol=${QT_GIT_PROTOCOL}"

CVE_PRODUCT:append = " qt"

S = "${WORKDIR}/git"

PV = "${QT_VERSION}"

SRCREV = "${SRCREV_${QT_MODULE}}"

SRCREV_tqtc-qmlcompilerplus = "6c91930c7f3bce964a9b0aea23af2b62fc4a75b5"
SRCREV_tqtc-qtinsighttracker = "b617375e5a2d9b3d3deada45df5169e4f07c00b4"
SRCREV_tqtc-qtvncserver = "902973f596fb82dd5b647687c9f8dd9dffe57c7a"
SRCREV_qt3d = "584e1f3a3fdd787b2ace87114f787f89c5718ea5"
SRCREV_qt3d-assimp = "c60a259fd35f09b8896b2920d48c436145914aae"
SRCREV_qt5compat = "de8c7e0b43b82a5823015d4c2e267b013d2d2ee6"
SRCREV_qtbase = "98602c26fc97eb41e3dd7548194ca637420a31b9"
SRCREV_qtapplicationmanager = "9d3e2e0600bea28a6fa6623114ead6f837295960"
SRCREV_qtcharts = "f35de24c01a6e9c64276b657407653e893c3004c"
SRCREV_qtcoap = "dcbe6c5277a0c255a4d8c178b6215bf82489a0ef"
SRCREV_qtconnectivity = "d8812bdd39e3927e524b3b38aeb9445dd4eba3a9"
SRCREV_qtdatavis3d = "d5a8908f2d119061f0d920742b1f92ca55c999ab"
SRCREV_qtdeclarative = "e6d14fcdc95713f65367499629f93fb1f1e35ec9"
SRCREV_qtdeviceutilities = "9a49bc7039d03db7f87490f08ecda1529bceb6cf"
SRCREV_qtdoc = "12b560d627d9a801075e61e7ca028556ffd64305"
SRCREV_qtgraphs = "bb5fd92e6163400228876a0995a71e635917c9d5"
SRCREV_qtgrpc = "e3b70ec75f488c1121d92bec18312c377dfd3de7"
SRCREV_qthttpserver = "5336cc98bca36c3243c6ef1fca3547925bfc0604"
SRCREV_qtimageformats = "3eedabd5150383e4345e2786a25bbd185c531785"
SRCREV_qtinterfaceframework = "d87c6f81a84f910c1edd1cdb93f70b736042e4d2"
SRCREV_qtlanguageserver = "f4f5ca2eecb2c48ad3ae98f8506ce7c2aceb623b"
SRCREV_qtlocation = "c9749a81ebf26cb94f83d5569c336a67a6ee81fc"
SRCREV_qtlottie = "5a8235d0a0cba5e2ebeec189dcc7b43c48eaa567"
SRCREV_qtmqtt = "3816ff0913ec76cf60c10782b3023c8a41d18844"
SRCREV_qtmultimedia = "5d2bdf00927771b74c407f7ffa958eb37b1f5e89"
SRCREV_qtnetworkauth = "33a58af35ded52c430b802e191a6faffb8e418c0"
SRCREV_qtopcua = "87e8ad711f7b34443cd1dfca886ab70116159e9c"
SRCREV_qtpositioning = "f473f4b6598d07283feef8541746b9698a79c82a"
SRCREV_qtquick3d = "2f4d3edc8062bac19885a6aec394f335e6e1b5ab"
SRCREV_qtquick3d-assimp = "e21b73ab492fefb0cab229da75e385ba0befbffc"
SRCREV_qtquick3dphysics = "e4ee17465899cb5d7e4f1c7783f043bffc42fe36"
SRCREV_qtquickdesigner-components = "8be09977abbdb638bf483ea417b36091b409c54f"
SRCREV_qtquicktimeline = "fd74687a02ea08ed102b952a645594596a4f53ce"
SRCREV_qtremoteobjects = "6d973e34b5704a2a7c8ebe3db29f95004a1facb1"
SRCREV_qtscxml = "0942dca79e25794bcb0d81cf04fdf74b621c489c"
SRCREV_qtsensors = "3d3ad478b65a26a503e9837784332b52cde9478a"
SRCREV_qtserialbus = "2c1fec48f9a8c075d5bd920f02c58e87322a3952"
SRCREV_qtserialport = "df94dfb4016063614b0daaddb744322280ae14c1"
SRCREV_qtshadertools = "65b015f6a272c8f5eb08f1c39aa8065f7e2379ed"
SRCREV_qtspeech = "d019c418c1ef0e8bcc20c94e9301ddeaf8affd39"
SRCREV_qtsvg = "85eeb5aecff1ad3e16b8ce960a5bfafc05df4461"
SRCREV_qttools = "67a4b353b2ce2e063a61e73619c10865a5655d90"
SRCREV_qttools-qlitehtml = "9c7b5d073bf4713250648cf7ea35580f489f7fd7"
SRCREV_qttools-qlitehtml-litehtml = "6ca1ab0419e770e6d35a1ef690238773a1dafcee"
SRCREV_qttranslations = "d0383e66bbb7fc90f24827cf5a70f926f06503dd"
SRCREV_qtvirtualkeyboard = "6a52fb1c23d2a6cbac8c8634be1386b0b166e33e"
SRCREV_qtwayland = "b71592631fa653985bf6f0e3024244460a42e5a6"
SRCREV_qtwebchannel = "d2f8de3fd4d4b4547bca19d415cfc211979bb5e6"
SRCREV_qtwebengine = "1a3e3a233d91037215851ac2821397fc9a846e64"
SRCREV_qtwebengine-chromium = "da62c2f7a445a04b0ad66502c94e6226cd7dea95"
SRCREV_qtwebsockets = "45d5cdde4c187a499745b5bb5fc14a8cef149679"
SRCREV_qtwebview = "7548ee6677e7e9c02f51edff8676c1c29e2d108f"
