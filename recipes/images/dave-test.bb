# Dave's test image

IMAGE_PREPROCESS_COMMAND = "ls -l ${IMAGE_ROOTFS}/../../srd_patch/rootfs;"

export IMAGE_BASENAME = "dave-test-image"
IMAGE_LINGUAS = ""

IMAGE_INSTALL = "task-base"

inherit image
