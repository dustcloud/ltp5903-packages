# Building PM2511 packages

In order to build packages for the PM2511, you will need a Virtual Machine
(VM) with the bitbake and OpenEmbedded build components.

The VM can be downloaded from TODO 
http://imagehost/vms/pm2511-toolchain-2014-10.7z

You will need 7z http://www.7-zip.org/ to unarchive the VirtualBox
VM. Unarchive the VM to a local disk and "Add" the VM in VirtualBox.

Once you have the VM up and running, connect to the VM using SSH (details
below) and follow the steps below to build a package.

On the PM2511 Toolchain VM, clone this repository. It is important to build
from the branch that matches your Manager version, otherwise you may introduce
incompatible packages.

    $ git clone https://github.com/dustcloud/pm2511-packages.git

Setup the build environment.

    $ cd pm2511-packages
    $ git checkout 4.x-stable
    $ . oe-setup.sh

Use bitbake to build package(s).

    $ bitbake bash

Find .ipk file(s) in _tmp/deploy/glibc/ipk/armv5te_. You may wish to create a
symlink to the ipkg directory.

    $ ln -s tmp/deploy/glibc/ipk/armv5te ipkgs

Often the recipe will build multiple packages to separate libraries, tools or
documentation. It conserves space to only install the packages that are
necessary.

The resulting packages can be copied to the PM2511 Manager and installed.

    dust@manager$ sudo ipkg install foo.ipk

- See the "Using bitbake" document for more details on bitbake and OpenEmbedded.
- See the "Updating package recipes" document for more details on how to 
  update packages to fix security issues. 


# PM2511 Toolchain Virtual Machine details

The PM2511 Toolchain VM is a VirtualBox VM. The system runs Ubuntu 12.04 LTS
(Server) on a single i686 CPU with 384 MB RAM. The VM is configured to use the
NAT networking setup, so that only the host machine can initiate a connection
to the VM. The VM runs SSH, which is exposed to only the host through port
3333. The default login is dust / tcdust. 

The default configuration for this VM prevents other hosts on the network from
connecting. This configuration is designed to keep the VM itself as isolated
as possible for security purposes because the VM may not be online to receive
important security patches.

