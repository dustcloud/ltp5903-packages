DESCRIPTION = "Configuration files for Dust package repositories"

RRECOMMENDS_${PN} += "opkg-nogpg"

PV = "1.0"
PR = "r1"
#PACKAGE_ARCH = "${MACHINE_ARCH}"


do_compile() {
	mkdir -p ${S}/${sysconfdir}/opkg

	rm ${S}/${sysconfdir}/opkg/arch.conf || true
	ipkgarchs="${PACKAGE_ARCHS}"
	priority=1
	for arch in $ipkgarchs; do 
		echo "arch $arch $priority" >> ${S}/${sysconfdir}/opkg/arch.conf
		priority=$(expr $priority + 5)
	done

	echo "src root http://ipkg-repository.dusthq.dust-inc.com/pm2511-root" > ${S}/${sysconfdir}/opkg/root-feed.conf
	echo "src dust http://ipkg-repository.dusthq.dust-inc.com/ranger-ipkgs/Ranger-3.0.0-devel-pm2511" > ${S}/${sysconfdir}/opkg/ranger-feed.in
	echo "src dust http://ipkg-repository.dusthq.dust-inc.com/marconi-ipkgs/Marconi-2.2.0-pm2511" > ${S}/${sysconfdir}/opkg/marconi-feed.in
}


do_install () {
	install -d ${D}${sysconfdir}/opkg
	install -m 0644  ${S}/${sysconfdir}/opkg/* ${D}${sysconfdir}/opkg/
}


FILES_${PN} = "${sysconfdir}/opkg/root-feed.conf \
		${sysconfdir}/opkg/ranger-feed.in \
		${sysconfdir}/opkg/marconi-feed.in \
		${sysconfdir}/opkg/arch.conf \
		"

CONFFILES_${PN} += "${sysconfdir}/opkg/root-feed.conf \
			${sysconfdir}/opkg/arch.conf \
			"
