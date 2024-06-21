Qt6 OpenEmbedded/Yocto Project layer
====================================

This layer depends on:

URI: https://git.openembedded.org/openembedded-core/

URI: https://git.openembedded.org/meta-openembedded/

Yocto version support
---------------------

The branching of meta-qt6 layer follows [Qt branching scheme](https://wiki.qt.io/Branch_Guidelines),
that is `dev` for the development branch, `6.x` for minor stabilization branches,
`6.x.y` for the release branches and `lts-6.x.y` for commercial LTS releases.

Each Qt release is tagged either as `v6.x.y` or `v6.x.y-lts`

Following table shows the Yocto branches which are used to test each
Qt version (x) and any additional Yocto releases that are stated in
the LAYERSERIES_COMPAT (c).

| Yocto \ Qt | dev | 6.7  | 6.6  | 6.5 | 6.4 | 6.3 | 6.2 |
|:---------- |:---:|:----:|:----:|:---:|:---:|:---:|:---:|
| scarthgap  |  x  |  x   |  x   |  x  |     |     |     |
| nanbield   |  x  |  x   |  x   |  x  |     |     |     |
| mickledore |  c  |  c   |  c   |  c  |  c  |     |     |
| langdale   |  c  |  c   |  c   |  c  |  c  |     |     |
| kirkstone  |  x  |  x   |  x   |  x  |  c  |  c  |  x  |
| honister   |     |      |  c   |  c  |  c  |  c  |  c  |
| hardknott  |     |      |  c   |  c  |  c  |  c  |  c  |
| gatesgarth |     |      |  c   |  c  |  c  |  c  |  c  |
| dunfell    |     |      |  x   |  x  |  c  |  c  |  x  |
|            |     |      |      |     |     |     |     |
|            | dev |stable|stable| LTS | EOL | EOL | LTS |

Detailed status of supported Qt versions can be found from
https://doc.qt.io/qt-6/supported-platforms.html#supported-qt-versions

Commercial Qt
-------------

Qt is dual-licensed under commercial and open source licenses.
The license can be selected using the `QT_EDITION` variable. `commercial` and
`opensource` are valid values. The default value is `opensource`.

For commercial Qt users, the layer provides additional support with LTS
(Long Term Support) releases for selected Qt versions and additional
Qt modules licensed as commercial-only.

The LTS releases are available in branches named `lts-6.x`. The source code
for the LTS releases and the commercial Qt modules are only available for
commercial Qt license holders. They can only be built and used if you have
a commercial Qt license and you have set up SSH access to Qt Gerrit (see links below).

The commercial Qt modules are included in the build if the `QT_COMMERCIAL_MODULES`
variable is set to `1` and you are using a commercial edition of Qt.

QtMultimedia
------------

Qt Multimedia now prefers [FFmpeg][1] as the multimedia backend instead of GStreamer.
FFmpeg recipe, however, is flagged with LICENSE_FLAGS = "commercial", which means
that user must accept the license before FFmpeg can be used in the build. If user
accepts the license using LICENSE_FLAGS_ACCEPTED = 'commercial_ffmpeg', the FFmpeg
support is enabled in Qt Multimedia. If user doesn't accept the license,
Qt Multimedia only uses GStreamer.

[1]: https://doc.qt.io/qt-6/qtmultimedia-index.html#ffmpeg-as-the-default-backend

Contributing
------------

To contribute to this layer submit the patches for review using
[Qt Gerrit](https://codereview.qt-project.org).

More information about Qt Gerrit and how to use it:
 - [Gerrit_Introduction](https://wiki.qt.io/Gerrit_Introduction)
 - [Setting_up_Gerrit](https://wiki.qt.io/Setting_up_Gerrit)

Report bugs on [Qt Bug Tracker](https://bugreports.qt.io) using
`Yocto: meta-qt6 layer` component.

Layer maintainers
-----------------

 - Samuli Piippo <samuli.piippo@qt.io>

