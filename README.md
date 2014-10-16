* Building PM2511 packages

In order to build packages for the PM2511, you will need a Virtual Machine
(VM) with the bitbake and OpenEmbedded build components.

The VM can be downloaded from TODO

Once you have the VM, connect to the VM using SSH (details below) and follow
the steps below to build a package.

On the PM2511 Toolchain VM, clone this repository. It is important to clone
the branch that matches your Manager version, otherwise you may introduce
incompatible packages.

  git clone REPO_URL

Source the build environment.

  cd pm2511-packages
  . oe-setup.sh

Use bitbake to build package(s).

  bitbake bash

Find .ipk file(s) in tmp/deploy/...

Often the recipe will build multiple packages so libraries, tools or
documentation can be separated and not installed if not desired.

- See the "Using bitbake" document for more details on bitbake.
- See the "Updating package recipes" document for more details on how to 
  update packages to fix security issues. 


* PM2511 Toolchain Virtual Machine details

The PM2511 Toolchain VM is a VirtualBox VM. The system runs Ubuntu 12.04 LTS
(Server) on a single i686 CPU with 384 MB RAM. The VM is configured to use the
NAT networking setup, so that only the host machine can initiate a connection
to the VM. The VM runs SSH, which is exposed to only the host through port
3333. The default login is dust / tcdust. 

The default configuration for this VM prevents other hosts on the network from
connecting. This configuration is designed to keep the VM itself as isolated
as possible for security purposes because the VM may not be online to receive
important security patches.


* Using bitbake

***INSERT

* Background

** OpenEmbedded

OpenEmbedded is a collection of build configurations and recipes for a large number of open source packages. 


* VM setup

The PM2511 Toolchain Virtual Machine is based on Ubuntu 12.04 LTS. Later
versions of Ubuntu (such as 14.04) contain updated packages that cause
problems with the relatively ancient build tools on which the PM2511
cross-compiler was based.

Here are the steps to create your own Virtual Machine. 

** Packages

Install required build packages (a C compiler and some ).

  sudo apt-get install build-essential
  sudo apt-get install help2man diffstat texi2html cvs

Configure virtual machine for bitbake.

  sudo dpkg-reconfigure dash

Install texinfo 4.x

- Under Ubuntu 12.04, you can install the distribution's texinfo package. 

  sudo apt-get install texinfo

- Under later versions, you should build texinfo from source.

  TODO

** Tools

The bitbake, OpenEmbedded tools, and some missing sources are available. On the PM2511 Toolchain VM, these are installed under /tools.


** Other adjustments made to OpenEmbedded

- patch configure in quilt (Ubuntu 14.04)
- patch unifdef
