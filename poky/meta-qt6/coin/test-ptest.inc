IMAGE_CLASSES += "testimage"
TEST_SUITES = "ssh ping parselogs ptest"
IMAGE_FEATURES += "ssh-server-dropbear"
IMAGE_INSTALL:append = "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'qt3d-ptest', '', d)} \
    qt5compat-ptest \
    qtbase-ptest \
    qtcharts-ptest \
    qtcoap-ptest \
    qtconnectivity-ptest \
    ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'qtdatavis3d-ptest', '', d)} \
    qtdeclarative-ptest \
    qtdeviceutilities-ptest \
    qtgraphs-ptest \
    qtgrpc-ptest \
    qthttpserver-ptest \
    qtimageformats-ptest \
    qtinterfaceframework-ptest \
    qtlocation-ptest \
    qtlottie-ptest \
    qtmqtt-ptest \
    qtmultimedia-ptest \
    qtnetworkauth-ptest \
    qtopcua-ptest \
    qtpositioning-ptest \
    qtquick3d-ptest \
    qtquickdesigner-components-ptest \
    qtquicktimeline-ptest \
    qtremoteobjects-ptest \
    qtscxml-ptest \
    qtsensors-ptest \
    qtserialbus-ptest \
    qtserialport-ptest \
    qtshadertools-ptest \
    qtspeech-ptest \
    qtsvg-ptest \
    qttools-ptest \
    qtvirtualkeyboard-ptest \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'qtwayland-ptest', '', d)} \
    qtwebchannel-ptest \
    qtwebsockets-ptest \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webengine', 'qtpdf-ptest', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webengine', 'qtwebengine-ptest', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'webengine', 'qtwebview-ptest', '', d)} \
    ${@bb.utils.contains('QT_COMMERCIAL_MODULES', '1', '\
        qmlcompilerplus-ptest \
        qtinsighttracker-ptest \
        qtvncserver-ptest \
        ', '', d)} \
"
