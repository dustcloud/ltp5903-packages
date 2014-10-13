#!/bin/sh
#Script to check RTC Date and set it to valid Date if RTC date is found Invalid

echo "Running fixtime script"

TIME="2038"
HWCLOCK=$(sudo hwclock | awk '{print $5}')
echo $HWCLOCK

if [ "$HWCLOCK" -ge "$TIME" ]
	then
		sudo date "010100001970"
                sudo hwclock --systohc -utc
	else
		echo "hwclock Y2K38 issue checked "
fi

