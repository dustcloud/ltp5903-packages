DESCRIPTION = "gdb - GNU debugger"
HOMEPAGE = "http://www.gnu.org/software/gdb/"
LICENSE="GPL"
SECTION = "devel"
PRIORITY = "optional"

inherit autotools

# Edited by SRD
#SRC_URI = "${GNU_MIRROR}/gdb/gdb-${PV}.tar.gz"
SRC_URI = "ftp://sourceware.org/pub/gdb/snapshots/current/gdb-6.8.50.20090518.tar.bz2"

#FILESDIR = "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/gdb-${PV}"
FILESDIR = "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/gdb-6.8.50.20090518"

export CC_FOR_BUILD = "${BUILD_CC}"
export CXX_FOR_BUILD = "${BUILD_CXX}"
export CPP_FOR_BUILD = "${BUILD_CPP}"
export CFLAGS_FOR_BUILD = "${BUILD_CFLAGS}"
export CXXFLAGS_FOR_BUILD = "${BUILD_CXXFLAGS}"
export CPPFLAGS_FOR_BUILD = "${BUILD_CPPFLAGS}"

#S = "${WORKDIR}/gdb-${PV}"
S = "${WORKDIR}/gdb-6.8.50.20090518"
B = "${WORKDIR}/build-${TARGET_SYS}"

