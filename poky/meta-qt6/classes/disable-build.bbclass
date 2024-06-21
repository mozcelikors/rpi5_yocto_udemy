# Disable compilation steps for the recipe
# Useful for passing CI builds when a Qt module does not compile with updated qtbase or qtdeclarative
# Should be used only with the addon modules not officially supported in the Qt releases

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_install[noexec] = "1"

python () {
  bb.warn("%s is currenly not supported, build produces empty packages!" % d.getVar("BPN"))
}
