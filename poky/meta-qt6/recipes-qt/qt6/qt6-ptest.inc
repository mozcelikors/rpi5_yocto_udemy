FILESEXTRAPATHS:append := ":${THISDIR}/ptest"
SRC_URI += "file://run-ptest"

inherit ptest

QT_PTEST_ENABLED ?= "${@bb.utils.contains('DISTRO_FEATURES', 'ptest', '1', '0', d)}"
PTEST_ENABLED = "${QT_PTEST_ENABLED}"

DEBUG_PREFIX_MAP += "\
 -fmacro-prefix-map=${D}= \
"

do_install_ptest_base[progress] = "${@d.getVarFlag('do_compile', 'progress')}"

B_PTEST = "${WORKDIR}/build-ptest"

fakeroot do_install_ptest() {
    cat >${WORKDIR}/toolchain-ptest.cmake <<EOF
include(${WORKDIR}/toolchain.cmake)
list(PREPEND CMAKE_FIND_ROOT_PATH ${D})
EOF

    mkdir -p ${B_PTEST}
    cd ${B_PTEST}
    cmake \
        ${OECMAKE_GENERATOR_ARGS} \
        -DCMAKE_TOOLCHAIN_FILE=${WORKDIR}/toolchain-ptest.cmake \
        -DPython3_EXECUTABLE=${PYTHON} \
        ${EXTRA_OECMAKE} \
        -DQT_BUILD_STANDALONE_TESTS=ON \
        -DQT_ADDITIONAL_PACKAGES_PREFIX_PATH=${D}${QT6_INSTALL_LIBDIR}/cmake \
        -DCMAKE_STAGING_PREFIX=${D}${prefix} \
        -DCMAKE_SKIP_RPATH=ON \
        -DQT_DISABLE_NO_DEFAULT_PATH_IN_QT_PACKAGES=ON \
        ${S} \
        -Wno-dev
    ${CMAKE_VERBOSE} cmake --build ${B_PTEST} --target all

    for tests in auto baseline
    do
        if [ -e "${B_PTEST}/tests/$tests" ]; then
            install -d ${D}${PTEST_PATH}/tests
            cp -r ${B_PTEST}/tests/$tests ${D}${PTEST_PATH}/tests

            # remove build files
            find ${D}${PTEST_PATH}/tests/$tests \
                \( -wholename "*/.*" -o -wholename "*autogen*" -o -iwholename "*cmake*" -o -name Makefile \) \
                -delete

            # tests may depend on files from sources
            cp -r ${S}/tests/$tests ${D}${PTEST_PATH}/tests
        fi
    done
}

fakeroot python do_create_ptest_list() {
    import json, os, subprocess

    builddir = d.getVar('B_PTEST')
    ptest_path = d.getVar('PTEST_PATH')
    try:
        command_output = subprocess.check_output(['ctest', '--show-only=json-v1'], cwd=builddir, text=True)
    except subprocess.CalledProcessError as e:
        bb.fatal('Could not get list of tests: {e.output}')

    json_data = json.loads(command_output)
    tests_data = json_data.get('tests', [])

    output = d.getVar('D') + os.path.join(ptest_path, 'tst_list')
    file = open(output, 'w')

    for test in tests_data:
        test_name = test.get('name')
        working_directory = next((prop['value'] for prop in test.get('properties', []) if prop['name'] == 'WORKING_DIRECTORY'), None)

        test_executable = os.path.normpath(os.path.join(working_directory,test_name))
        if test_executable.startswith(builddir) and os.path.isfile(test_executable):
           test_executable = test_executable.replace(builddir,ptest_path)
           file.write(f'{test_executable}\n')

    file.close()
}

addtask create_ptest_list after do_install_ptest_base before do_package
python () {
    if not(d.getVar('PTEST_ENABLED') == "1"):
        bb.build.deltask('do_create_ptest_list', d)
}

INSANE_SKIP:${PN}-ptest += "file-rdeps"

PACKAGESPLITFUNCS =+ "remove_ptest_debug"
remove_ptest_debug() {
    if [ -e ${PKGD}${QT6_INSTALL_LIBDIR}/${BPN}/ptest/tests ]; then
        find ${PKGD}${QT6_INSTALL_LIBDIR}/${BPN}/ptest/tests -depth -type d -name .debug -exec rm -rf '{}' \;
    fi
}
