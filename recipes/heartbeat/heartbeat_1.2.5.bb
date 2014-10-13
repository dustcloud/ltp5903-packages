DESCRIPTION = "A fault-tolerant redundancy framework"
HOMEPAGE = "http://www.linux-ha.org"
LICENSE = "GPL"
SECTION = "libs"
DEPENDS = "libnet glib-1.2"
PR = "r2"

# note: the SRC_URI is a dummy since the original source is too old to be
# found at http://linux-ha.org, we use our archived source
SRC_URI = "http://linux-ha.org/download/${PN}-${PV}.tar.gz \
	file://time-rollover.patch;patch=1 \
	file://api-dealloc.patch;patch=1 \
	file://authkeys \
	file://ha.cf \
	file://haresources \
"

# TODO: other patches ?

inherit autotools

# extra parameters to configure
EXTRA_OECONF = "--enable-static=no --without-libxml2"

# by default, PACKAGES contains -dbg, -doc, -dev, -locale
PACKAGES += "${PN}-extra"

LEAD_SONAME = "libhbclient.so"

# note: -dev, -dbg and -doc packages have reasonable default contents

FILES_${PN}-dev = "/usr/include /usr/lib/lib*.so /usr/lib/lib*.a /usr/lib/lib*.la \
	/usr/lib/stonith/plugins/stonith/*.a /usr/lib/stonith/plugins/stonith/*.la \
	/usr/lib/heartbeat/plugins/*/*.a /usr/lib/heartbeat/plugins/*/*.la "

FILES_${PN}-dbg += "/usr/lib/heartbeat/.debug /usr/lib/heartbeat/plugins/*/.debug \
	/usr/lib/stonith/plugins/*/.debug /usr/lib/pils/plugins/*/.debug "

# the base package only needs a subset of the full install
FILES_${PN} = "/etc /var /usr/bin /usr/lib/lib*.so.* \
	/usr/lib/heartbeat /usr/lib/pils /usr/lib/stonith "

CONFFILES_${PN} = "/etc/ha.d/authkeys /etc/ha.d/ha.cf /etc/ha.d/haresources"

FILES_${PN}-extra = "/usr/sbin"


do_configure() {
	oe_runconf

}

do_install_append() {
	find ${D}/${libdir} -name \*.a -exec rm -f {} \;
	find ${D}/${libdir} -name \*.la -exec rm -f {} \;
# Install configuration files	
	install	-d ${D}${sysconfdir}/ha.d
	install -m 0600 ${WORKDIR}/authkeys ${D}${sysconfdir}/ha.d/
	install -m 0644 ${WORKDIR}/ha.cf ${D}${sysconfdir}/ha.d/
	install -m 0644 ${WORKDIR}/haresources ${D}${sysconfdir}/ha.d/
}

do_stage() {
	autotools_stage_all

}

# TODO: set haclient, hacluster ownership .. on what?

pkg_postinst_heartbeat() {
# make sure the haclient group exists
	grep ^haclient: /etc/group > /dev/null
	if [ $? -ne 0 ]; then
	    echo "haclient:*:102:" >> /etc/group
	fi
# cl_status should be setgid to haclient
	chgrp haclient /usr/bin/cl_status
	chmod 2755 /usr/bin/cl_status
# note: startup script is enabled through config-redundancy
}
