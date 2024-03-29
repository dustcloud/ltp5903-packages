require gdb-common.inc

DEFAULT_PREFERENCE_avr32 = "-99"

DEPENDS = "ncurses readline"

FILES_gdbserver = "${bindir}/gdbserver"

RRECOMMENDS_gdb_append_linux = " glibc-thread-db "
RRECOMMENDS_gdb_append_linux-gnueabi = " glibc-thread-db "

inherit gettext

#SRC_URI += "file://kill_arm_map_symbols.patch;patch=1 \
#            file://gdbserver-cflags-last.diff;patch=1;pnum=0"
##FIXME	   file://uclibc.patch;patch=1 \

EXTRA_OEMAKE = "'SUBDIRS=intl mmalloc libiberty opcodes bfd sim gdb etc utils'"

EXTRA_OECONF = "--enable-threads --enable-shared --disable-gdbtk --disable-tui --disable-x \
                --with-curses --disable-multilib --with-readline --disable-sim \
                --program-prefix=''"

LDFLAGS_append = " -s"
export CFLAGS_append=" -L${STAGING_LIBDIR}"

do_configure () {
    # override this function to avoid the autoconf/automake/aclocal/autoheader
    # calls for now
    (cd ${S}/gdb/gdbserver && gnu-configize) || die "failure in running gnu-configize"
        CPPFLAGS="" oe_rungdbserverconf
}

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${B}/gdbserver ${D}${bindir}
}

oe_rungdbserverconf() {
    if [ -x ${S}/gdb/gdbserver/configure ] ; then
        cfgcmd="${S}/gdb/gdbserver/configure \
            --build=${BUILD_SYS} \
            --host=${HOST_SYS} \
            --target=${TARGET_SYS} \
            --prefix=${prefix} \
            --exec_prefix=${exec_prefix} \
            --bindir=${bindir} \
            --sbindir=${sbindir} \
            --libexecdir=${libexecdir} \
            --datadir=${datadir} \
            --sysconfdir=${sysconfdir} \
            --sharedstatedir=${sharedstatedir} \
            --localstatedir=${localstatedir} \
            --libdir=${libdir} \
            --includedir=${includedir} \
            --oldincludedir=${oldincludedir} \
            --infodir=${infodir} \
            --mandir=${mandir} \
            ${EXTRA_OECONF} \
            $@"
        oenote "Running $cfgcmd..."
        $cfgcmd || oefatal "oe_runconf failed"
    else
        oefatal "no configure script found"
    fi
}
