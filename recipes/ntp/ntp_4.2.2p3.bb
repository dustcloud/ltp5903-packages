require ntp.inc

PR = "r3"

SRC_URI = "http://www.eecis.udel.edu/~ntp/ntp_spool/ntp4/ntp-4.2/${P}.tar.gz \
	file://ipv6only-workaround.patch;patch=1 \
	file://tickadj.c.patch;patch=1 \
	file://ntpd \
	file://ntp.conf \
	file://ntpdate"


# ntp originally includes tickadj. It's split off for inclusion in small
# firmware images on platforms with wonky clocks (e.g. OpenSlug)
# for walnut, tickadj is unnecessary
#RDEPENDS_${PN} = "${PN}-tickadj"

FILES_${PN}-bin = "${bindir}/ntp-wait ${bindir}/ntpdc ${bindir}/ntpq ${bindir}/ntptime ${bindir}/ntptrace"
FILES_${PN} = "${sbindir}/ntpd ${sysconfdir}/ntp.conf ${sysconfdir}/init.d/ntpd ${bindir}/ntpdate ${bindir}/ntpq"
FILES_${PN}-tickadj = "${bindir}/tickadj"
FILES_ntp-utils = "${bindir}/*"

do_install_append() {
	install -d ${D}/${sbindir}
	mv ${D}/${bindir}/ntpd ${D}/${sbindir}/ntpd
	install -d ${D}/${sysconfdir}/init.d
	install -m 644 ${WORKDIR}/ntp.conf ${D}/${sysconfdir}
	install -m 755 ${WORKDIR}/ntpd ${D}/${sysconfdir}/init.d
}

pkg_postinst_ntpdate() {
if test "x$D" != "x"; then
	exit 1
else
	if ! grep -q ntpdate /etc/cron/crontabs/root; then
		echo "adding crontab"
		test -d /etc/cron/crontabs || mkdir -p /etc/cron/crontabs
		echo "30 * * * *    /usr/bin/ntpdate -s -u pool.ntp.org" >> /etc/cron/crontabs/root
	fi
	update-rc.d -s busybox-cron defaults
	update-rc.d -s ntpdate defaults 30
fi
}
