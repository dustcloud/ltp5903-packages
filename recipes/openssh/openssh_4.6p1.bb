DEPENDS = "zlib openssl"

RCONFLICTS_openssh = "dropbear"
RCONFLICTS_openssh-sshd = "dropbear"

SECTION = "console/network"
DESCRIPTION = "Secure rlogin/rsh/rcp/telnet replacement (OpenSSH) \
Ssh (Secure Shell) is a program for logging into a remote machine \
and for executing commands on a remote machine. \
It provides secure encrypted communications between two untrusted \
hosts over an insecure network.  X11 connections and arbitrary TCP/IP \
ports can also be forwarded over the secure channel. \
It is intended as a replacement for rlogin, rsh and rcp, and can be \
used to provide applications with a secure communication channel."
HOMEPAGE = "http://www.openssh.org/"
LICENSE = "BSD"
PR = "r7"

SRC_URI = "ftp://ftp.openbsd.org/pub/OpenBSD/OpenSSH/portable/openssh-${PV}.tar.gz \
           file://sftp-server-nolibcrypto.patch;patch=1 \
           file://sshd_config \
	   file://ssh_config \
           file://init"

inherit autotools

export ASKPASS_PROGRAM = "${bindir}/ssh-askpass"
export LD = "${CC}"
CFLAGS_prepend = "-I${S} "
CFLAGS_append = " -D__FILE_OFFSET_BITS=64"
LDFLAGS_prepend = "-L${S} -L${S}/openbsd-compat "
EXTRA_OECONF = "--disable-suid-ssh --with-ssl=${STAGING_LIBDIR}/ssl \
	        --with-rand-helper=no --without-pam \
	        --without-zlib-version-check \
		--with-privsep-path=/var/run/sshd \
		--sysconfdir=${sysconfdir}/ssh \
		--with-xauth=/usr/bin/xauth"

EXTRA_OEMAKE = "'STRIP_OPT='"

do_configure_prepend () {
	if [ ! -e acinclude.m4 -a -e aclocal.m4 ]; then
		cp aclocal.m4 acinclude.m4
	fi
}

do_compile_append () {
	install -m 0644 ${WORKDIR}/sshd_config ${S}/
	install -m 0644 ${WORKDIR}/ssh_config ${S}/
}

do_install_append() {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/sshd
	mv ${D}${bindir}/scp ${D}${bindir}/scp.openssh
	mv ${D}${bindir}/ssh ${D}${bindir}/ssh.openssh
	rmdir ${D}/var/run/sshd ${D}/var/run ${D}/var
}

PACKAGES =+ " ssh-keygen openssh-scp openssh-ssh openssh-sshd openssh-sftp openssh-misc openssh-sftp-server"
FILES_openssh-dbg +=${bindir}/.debug ${libdir}exec/.debug"
FILES_openssh-scp = "${bindir}/scp.${PN}"
FILES_openssh-ssh = "${bindir}/ssh.${PN} ${bindir}/slogin /${sysconfdir}/ssh/ssh_config"
FILES_openssh-sshd = "${sbindir}/sshd /${sysconfdir}/init.d/sshd"
FILES_openssh-sshd += " /${sysconfdir}/ssh/moduli /${sysconfdir}/ssh/sshd_config"
FILES_openssh-sftp = "${bindir}/sftp"
FILES_openssh-sftp-server = "${libdir}exec/sftp-server"
FILES_openssh-misc = "${bindir}/ssh* ${libdir}exec/ssh*"
FILES_ssh-keygen = "${bindir}/ssh-keygen"

RDEPENDS_openssh += " openssh-scp openssh-ssh openssh-sshd ssh-keygen "
DEPENDS_openssh-sshd += " update-rc.d"
RDEPENDS_openssh-sshd += " update-rc.d ssh-keygen "

pkg_postinst_openssh-sshd() {
if test "x$D" != "x"; then
	exit 1
else
	addgroup sshd
	adduser --system --home /var/run/sshd --no-create-home --disabled-password --ingroup sshd -s /bin/false sshd
	update-rc.d sshd defaults 9
fi
}

pkg_postinst_openssh-scp() {
	update-alternatives --install ${bindir}/scp scp scp.${PN} 90
}

pkg_postinst_openssh-ssh() {
	update-alternatives --install ${bindir}/ssh ssh ssh.${PN} 90
}

pkg_postrm_openssh-ssh() {
        update-alternatives --remove ${bindir}/ssh ssh.${PN}
}

pkg_postrm_openssh-scp() {
        update-alternatives --remove ${bindir}/scp scp.${PN}
}

pkg_postrm_openssh-sshd() {
if test "x$D" != "x"; then
	exit 1
else
	${sysconfdir}/init.d/sshd stop
	deluser sshd
	delgroup sshd
	update-rc.d -f sshd remove
fi
}

CONFFILES_openssh-sshd = "${sysconfdir}/ssh/sshd_config"
CONFFILES_openssh-ssh = "${sysconfdir}/ssh/ssh_config"
