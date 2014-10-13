#!/bin/sh

# Set this to your working directory
export LOCAL_BUILD_DIR=`pwd`

# The location of the bitbake tool
BB_DIR=/tools/bitbake

# The location of the OpenEmbedded directory
export OPENEMBEDDED_DIR=/tools/openembedded

addpath () {
    if ! echo $PATH | /bin/egrep -q "(^|:)$1($|:)" ; then
	if [ "$2" = "after" ] ; then
	    PATH=$PATH:$1
	else
	    PATH=$1:$PATH
	fi
    fi
}

if [ -n "$USE_SDEV_TOOLCHAIN" ]; then
    export TOOLCHAIN_DIR=/mnt/sdev/tools/toolchain/arm/armv5te
    addpath "$TOOLCHAIN_DIR/bin"
    echo "You'll need to edit local.conf to set TOOLCHAIN_OPTIONS"
fi

addpath "$BB_DIR/bin"

export BBPATH=$LOCAL_BUILD_DIR:$OPENEMBEDDED_DIR
export BB_ENV_EXTRAWHITE="LOCAL_BUILD_DIR OPENEMBEDDED_DIR TOOLCHAIN_DIR MACHINE DUST_EXTRA_INSTALL"

# on first run, make sure there's a staging directory so we don't get errors
# about a missing lock file directory
[ -d "$LOCAL_BUILD_DIR/tmp/staging" ] || mkdir -p "$LOCAL_BUILD_DIR/tmp/staging"
