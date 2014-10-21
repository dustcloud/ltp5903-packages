inherit pkgconfig

require openssl.inc

# TODO: these settings do not seem to force failure if wrong
#SRC_URI[src.md5sum] = "40b6ea380cc8a5bf9734c2f8bf7e701e"
#SRC_URI[src.sha256sum] = "92511d1f0caaa298dba250426f8e7d5d00b271847886d1adc62422778d6320db"

# Values for openssl 1.0.0m output from fetch
#md5=363362e5c55c95277c2ebf03877a83a1
#sha256=224dbbfaee3ad7337665e24eab516c67446d5081379a40b2f623cf7801e672de

PR = "${INC_PR}.0"

DEFAULT_PREFERENCE = "-1"

export DIRS = "crypto ssl apps engines"
export OE_LDFLAGS="${LDFLAGS}"

SRC_URI += "file://configure-targets.patch;patch=1 \
            file://shared-libs.patch;patch=1 \
            file://debian.patch;patch=1 \
            file://oe-ldflags.patch;patch=1 \
	    file://libdeps-first.patch;patch=1 \
	    file://engines-install-in-libdir-ssl.patch;patch=1 \
	    file://find.pl \
	   "

PARALLEL_MAKE = ""

PACKAGES += " \
	${PN}-engines \
	${PN}-engines-dbg \
	"

FILES_${PN}-engines = "${libdir}/ssl/engines/*.so"
FILES_${PN}-engines-dbg = "${libdir}/ssl/engines/.debug"

do_configure_prepend() {
  cp ${WORKDIR}/find.pl ${S}/util/find.pl
}
