DESCRIPTION = "Ipkg workaround for dcc.ini permissions"
DEPENDS = ""
LICENSE = "Dust"
#ALLOW_EMPTY = 1

PROVIDES = "dcc-ini-fix"
PN = "dcc-ini-fix"
PV = "1.0"
PR = ""
PACKAGES = "${PN}"

# Just fetch and place the ipkg in the correct place to build the rootfs.

# As used in openembedded/classes/image.bbclass via rootfs_ipk.bbclass
PACKAGE_INSTALL_append = ${DEPLOY_DIR_IPK}/armv5te/${PN}_${PV}_armv5te.ipk

SRC_URI = "http://ipkg-repository/marconi-ipkgs/Marconi-2.2.0-pm2511/${PN}_${PV}_armv5te.ipk"

inherit dust
