python do_srcrev_update() {
    import subprocess

    recipe = d.getVar('FILE')
    srcrev_file = os.path.join(d.getVar('QT6BASE'), 'recipes-qt/qt6/qt6-git.inc')

    module = d.getVar('QT_MODULE')

    scms = []
    fetcher = bb.fetch2.Fetch(d.getVar('SRC_URI').split(), d)
    urldata = fetcher.ud
    for u in urldata:
        if urldata[u].method.supports_srcrev():
            scms.append(u)

    if len(scms) == 0:
        return

    d.setVar("__BBSEENSRCREV", "1")
    d.setVar("__BBSRCREV_SEEN", "1")
    for scm in scms:
        ud = urldata[scm]
        for name in ud.names:
            rev = ud.method.latest_revision(ud, d, name)
            srcrev = d.getVar("SRCREV_%s" % name)
            if srcrev is None: srcrev = d.getVar("SRCREV")

            if srcrev == rev:
                bb.plain("%s: %s is already latest" % (name, srcrev))
                continue

            bb.plain("%s: %s -> %s" % (name, srcrev, rev))
            cmd = "sed -E -i %s %s -e '/SRCREV(_%s)? /s/%s/%s/'" %  (recipe, srcrev_file, name, srcrev, rev)
            bb.process.run(cmd, log=None, shell=True, stderr=subprocess.PIPE, cwd=None)
}
do_srcrev_update[nostamp] = "1"
addtask srcrev_update after do_fetch
