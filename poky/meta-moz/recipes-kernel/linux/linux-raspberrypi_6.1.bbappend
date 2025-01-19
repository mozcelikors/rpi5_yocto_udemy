FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

KBUILD_DEFCONFIG:raspberrypi5_custom = "bcm2712_defconfig"

SRC_URI:append = " file://enable_proc_configgz.cfg \
                   file://enable_spidev.cfg \
                   file://0001-Add-debug-print-to-spidev-kernel-driver.patch \
"

LINUX_VERSION = "6.1.63"
LINUX_RPI_BRANCH = "rpi-6.1.y"
LINUX_RPI_KMETA_BRANCH = "yocto-6.1"

SRCREV_machine = "a984fda6b2c24dbf1ca21924f99c8f9418f5765e"
SRCREV_meta = "43d1723dbe0ce7b341cf32feeb35ecbe6b0ce29a"

RPI_KERNEL_DEVICETREE_OVERLAYS:remove = "overlays/hifiberry-amp4pro.dtbo"
RPI_KERNEL_DEVICETREE_OVERLAYS:remove = "overlays/disable-wifi.dtbo"
