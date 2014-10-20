PR = "r4"

SRC_URI = "http://ftp.sudo.ws/sudo/dist/sudo-${PV}.tar.gz \
           file://nonrootinstall.patch;patch=1 \
           file://nostrip.patch;patch=1 \
           file://noexec-link.patch;patch=1 \
           file://configure"

require sudo.inc

do_configure() { 
    cp ${WORKDIR}/configure ${S}/
    oe_runconf
}

