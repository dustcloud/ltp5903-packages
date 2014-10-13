require stunnel.inc

PR = "r1"

SRC_URI = "ftp://ftp.stunnel.org/stunnel/archive/4.x/stunnel-${PV}.tar.gz \
	   file://automake.patch;patch=1 \
	   file://stunnel_monitor \
	   file://init"

do_install() {
	autotools_do_install
	install -d ${D}${sysconfdir}/stunnel ${D}${sysconfdir}/init.d
	install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/stunnel
	install -m 755 ${WORKDIR}/stunnel_monitor ${D}${bindir}/stunnel_monitor
}
