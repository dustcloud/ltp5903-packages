inherit pkgconfig

require openssl.inc

SRC_URI[src.md5sum] = "40b6ea380cc8a5bf9734c2f8bf7e701e"
SRC_URI[src.sha256sum] = "92511d1f0caaa298dba250426f8e7d5d00b271847886d1adc62422778d6320db"

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
	   "

PARALLEL_MAKE = ""

PACKAGES += " \
	${PN}-engines \
	${PN}-engines-dbg \
	"

FILES_${PN}-engines = "${libdir}/ssl/engines/*.so"
FILES_${PN}-engines-dbg = "${libdir}/ssl/engines/.debug"
