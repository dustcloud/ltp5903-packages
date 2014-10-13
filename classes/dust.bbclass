# Common empty functions for Dust binary packages

do_unpack() {
  echo "Null unpack method"
}

do_patch() {
  echo "Null patch method"
}

do_configure() {
  echo "Null configure method"
}

do_compile() {
  echo "Null compile method"
}

do_stage() {
  echo "Null stage method"
}

do_install() {
  oenote Creating directory ${WORKDIR}/install/${PN}
  # A directory is needed for each member of PACKAGES
  mkdir -p ${WORKDIR}/install/${PN}
}

do_package() {
  echo "Null package method"
}

do_package_stage() {
  echo "Null package_stage method"
}

do_distribute_sources() {
  echo "Null distribute_sources method"
}

do_package_write() {
  oenote Copying the downloaded ipkg package from ${DL_DIR} to ${DEPLOY_DIR_IPK}/armv5te
  install ${DL_DIR}/${PN}_${PV}_armv5te.ipk ${DEPLOY_DIR_IPK}/armv5te
}
