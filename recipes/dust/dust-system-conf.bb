DESCRIPTION = "Dust System Conf"
DEPENDS = ""
LICENSE = "Dust"
#ALLOW_EMPTY = 1

PROVIDES = "dust-system-conf"
PN = "dust-system-conf"
PV = "3.0.0.14"
PR = ""
PACKAGES = "${PN}"

# Just fetch and place the ipkg in the correct place to build the rootfs.

# As used in openembedded/classes/image.bbclass via rootfs_ipk.bbclass
PACKAGE_INSTALL_append = ${DEPLOY_DIR_IPK}/armv5te/${PN}_${PV}_armv5te.ipk

SRC_URI = "http://ipkg-repository/brightsource-ipkgs/Manager-3.1.0-devel-pm2511/${PN}_${PV}_armv5te.ipk"

inherit dust
