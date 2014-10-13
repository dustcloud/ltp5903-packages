DESCRIPTION = "A simple, small, portable, fast, and secure HTTP server."
LICENSE = "BSD"
HOMEPAGE = "http://www.acme.com/software/thttpd/"
PR ="r10"

SRC_URI = "http://www.acme.com/software/thttpd/thttpd-2.25b.tar.gz \
	   file://install.patch;patch=1 \
	   file://acinclude.m4 \
	   file://init \
           file://thttpd_wrapper \
	   file://htpasswd_shared.diff;patch=1 \
	   file://config-defaults.patch;patch=1"
S = "${WORKDIR}/thttpd-${PV}"

PARALLEL_MAKE = ""

INITSCRIPT_NAME = "thttpd"
INITSCRIPT_PARAMS = "defaults 30"

inherit autotools update-rc.d

EXTRA_OEMAKE += "'WEBDIR=${servicedir}/www'"
FILES_${PN}-dbg_append = " ${servicedir}/www/cgi-bin/.debug"
FILES_${PN}_append = " ${servicedir}"

do_configure () {
	install -m 0644 ${WORKDIR}/acinclude.m4 ${S}/
	autotools_do_configure
}

do_install_append () {
	install -d "${D}${sysconfdir}/init.d"
	cat ${WORKDIR}/init | sed -e 's,@@SRVDIR,${servicedir}/www,g' > ${WORKDIR}/thttpd
	install -c -m 755 ${WORKDIR}/thttpd ${D}${sysconfdir}/init.d/thttpd
	install -c -m 755 ${WORKDIR}/thttpd_wrapper ${D}${sbindir}/thttpd_wrapper
}

pkg_postinst_${PN} () {
    THTTPD_DATA=/home/httpd
    [ -d $THTTPD_DATA ] || mkdir $THTTPD_DATA
    chown dust:dust $THTTPD_DATA
}
