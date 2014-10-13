#!/bin/sh

host=${1:-i686}

if [ "$1" = "--help" ]; then
    echo "usage: compile_gdb_for_host.sh [host]"
    exit
fi

mkdir gdb_${host}_arm
cd gdb_${host}_arm

# Configure
echo "Configuring gdb host ${host} for target arm ..."

gdb_dir=$BASE_DIR/tmp/work/armv5te-kaeilos-linux-gnueabi/gdb-6.8-r0/gdb-6.8.50.20090518

if [ ! -d "$gdb_dir" ]; then
    echo "error: can't find gdb directory."
    echo "Try 'bitbake -c unpack gdb'"
    exit 1
fi 

$gdb_dir/configure --host=${host}-linux --target=arm-kaeilos-linux-gnueabi

# TODO: make sure the cross compiler is in the PATH

# Make
echo "Building gdb host ${host} ..."
make 

if [ $? -eq 0 ]; then
    echo "Finished builing gdb host ${host}."
fi
