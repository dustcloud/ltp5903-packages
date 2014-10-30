The LTP5903 packages repository contains recipes for building ipkgs for the
LTP5903 (aka Dust Networks PM2511).

There are several important branches:
- *master* contains the latest "tested" packages. This includes security patches that have not yet been pushed into a release.
- *development* contains the latest "bleeding-edge" packages. This includes security patches and other updates that have not yet been tested internally. 
- *4.x-stable* contains packages included in the latest 4.x Manager release
- *2.x-stable* contains packages included in the latest 2.x Manager release

In order to build packages for the LTP5903, you will need a Virtual Machine
(VM) with the bitbake and OpenEmbedded build components. You can find the VM in the 
[Releases](https://github.com/dustcloud/ltp5903-packages/releases) section of this repository.

Once you have a VM, follow the instructions on the [Building packages
page](https://github.com/dustcloud/ltp5903-packages/wiki/Building-packages).
