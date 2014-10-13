# Root filesystem image for the Dust Walnut (PM2511) board

IMAGE_PREPROCESS_COMMAND = "create_etc_timestamp; \
	preprocess_command ; \
	"

# Don't include debug packages in image
DISTRO_TYPE = "release"

PREFERRED_PROVIDER_ifconfig = "netbase"
PREFERRED_PROVIDER_tar = "tar"
PREFERRED_PROVIDER_gzip = "gzip"

# ACK: this isn't sufficient to select minicom 2.1. I had to remove
# minicom-2.3 from the openembedded recipes. -- dbacher 12/18/09
PREFERRED_PROVIDER_minicom = "minicom"
PREFERRED_VERSION_minicom = "2.1"


# FEED_URIS is used by insert_feed_uris in image.bbclass
# These get emitted as src/gz not src
# Make sure there is no trailing line
DUST_FEED_URI = "http://ipkg-repository.dusthq.dust-inc.com"
FEED_URIS = "root##${DUST_FEED_URI}/pm2511-root \
             trunk##${DUST_FEED_URI}/trunk-ipkgs/Manager-4.0.0-devel-pm2511"

# See the note in base-image.bb
# These can be empty since FEED_URIS generates the .conf files
# This doesn't stop these feeds being emitted which is why we remove
# them in IMAGE_PREPROCESS_COMMAND
ANGSTROM_FEED_CONFIGS = ""
DISTRO_FEED_CONFIGS = ""

# Replace the Angstrom ipkg configuration with our own
ANGSTROM_EXTRA_INSTALL += " \
			   bash \
			   ipkg \
			   dosfstools \
			   ethtool \
			   module-init-tools \
			   logrotate \
			   gzip \
			   watchdog \
			   wget \
			   cron \
			   vim \
			   libcrypto \
			   openssl \
			   minicom \
			   ntp \
			   ntp-bin \
			   ppp-modules \
			   ppp \
			   stunnel \
			   sudo \
			   tar \
			   thttpd \
			   python-core \
			   python-modules \
			   python-misc \
			   python-xml \
			   python-xmlrpc \
			   nfs-utils-client \
			   iputils-ping \
			   util-linux-ng-hwclock \
			   lrzsz \
			   heartbeat \
                           libstdc++ \
                           expat \
                           ulxmlrpcpp \
			   netfilter-modules \
			   iptables \
			   at91-gpio \
   			   "

# These are all existing ipkgs, not built from source.
# Be warned that bitbake has to be run in the same top-level directory and
# that the return code from ipkg is not checked, so get the dependencies right
# or the only error is that files are not installed in rootfs.
# Also, if ipkg asks for approval to overwrite a configuration file, it hangs
# and you have to kill the ipkg and bitbake processes manually.
DUST_INSTALL = "dust-dcc dust-system-conf dust-web-config dustmgr"

DUST_EXTRA_INSTALL ?= ""

DEPENDS = "task-base \
           ${@base_contains("MACHINE_FEATURES", "screen", "psplash-zap", "",d)} \
	   "

# See http://bec-systems.com/web/content/view/79/9/ for more information
IMAGE_INSTALL = "task-base \
	    ${ANGSTROM_EXTRA_INSTALL} \
	    ${DUST_INSTALL} \
	    ${DUST_EXTRA_INSTALL} \
	    ${@base_contains("MACHINE_FEATURES", "screen", "psplash-zap", "",d)} \
	   "

export IMAGE_BASENAME = "srd-console-base-image"
IMAGE_LINGUAS = ""

inherit image


preprocess_command() {
	# copy extra python libraries
	local PYTHON_IMAGE_LIB_DIR=${IMAGE_ROOTFS}/../work/armv5te-kaeilos-linux-gnueabi/python-2.6.1-ml4/image/usr/lib/python2.6/
	cp -v ${PYTHON_IMAGE_LIB_DIR}/filecmp.py* ${IMAGE_ROOTFS}/usr/lib/python2.6/
	cp -v ${PYTHON_IMAGE_LIB_DIR}/platform.py* ${IMAGE_ROOTFS}/usr/lib/python2.6/
	ln -sf /bin/bash ${IMAGE_ROOTFS}/bin/sh

	# copy the patch directory
	cp -Rvf ${IMAGE_ROOTFS}/../../srd_patch/rootfs/* ${IMAGE_ROOTFS}/

	# create SD card mount points
	[ ! -d ${IMAGE_ROOTFS}/media/card ] && mkdir ${IMAGE_ROOTFS}/media/card
	[ ! -d ${IMAGE_ROOTFS}/media/card2 ] && mkdir ${IMAGE_ROOTFS}/media/card2
	ln -sf /media/card ${IMAGE_ROOTFS}/mnt/mmc

	# cleanup .svn files
	find ${IMAGE_ROOTFS} -name .svn -type d | xargs rm -rf

	rm -f ${IMAGE_ROOTFS}/etc/issue

	# home directory clean
	mkdir ${IMAGE_ROOTFS}/home/dust
	chown 500:500 ${IMAGE_ROOTFS}/home/dust
	chmod 755 ${IMAGE_ROOTFS}/home/dust
	mkdir ${IMAGE_ROOTFS}/home/dustcli
	chown 501:501 ${IMAGE_ROOTFS}/home/dustcli
	chmod 755 ${IMAGE_ROOTFS}/home/dustcli

	# security
        chmod 644 ${IMAGE_ROOTFS}/etc/passwd
        chmod 600 ${IMAGE_ROOTFS}/etc/shadow
	chmod 440 ${IMAGE_ROOTFS}/etc/sudoers
        touch ${IMAGE_ROOTFS}/etc/securetty

	# clean up ipkg configuration
	local IPKG_CONF_DIR=${IMAGE_ROOTFS}/etc/opkg
	rm ${IMAGE_ROOTFS}/etc/opkg/base-feed.conf
	rm ${IMAGE_ROOTFS}/etc/opkg/debug-feed.conf
	rm ${IMAGE_ROOTFS}/etc/opkg/gstreamer-feed.conf
	rm ${IMAGE_ROOTFS}/etc/opkg/noarch-feed.conf
	rm ${IMAGE_ROOTFS}/etc/opkg/perl-feed.conf
	rm ${IMAGE_ROOTFS}/etc/opkg/python-feed.conf
	rm ${IMAGE_ROOTFS}/etc/opkg/walnut-feed.conf
}

