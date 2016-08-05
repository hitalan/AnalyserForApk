# $1 mean the method of -c or -h $2 mean the type of the bad $3 mean the apkname and it also is the floder name $4 mean the number of the png
#!/bin/bash
if [ $# -eq 0 ];
    then
    NAME=$(date +%Y%m%d_%H%M%S).png
elif [ $1 = "-c" ]                 # colon
    then
    NAME=$(date +%Y%m%d_%H:%M:%S).png
elif [ $1 = "-h" ]                 # hyphen
    then
    NAME=$4'.png'
else
    echo -e "\033[31m *Invalid argument.\n *Usage '$0 -[ch]' \033[0m"
    exit 1
fi    
adb shell /system/bin/screencap -p /sdcard/$NAME
adb pull /sdcard/$NAME ~/Pictures/$2/$3/

# the '[' and ']' should have space with left and right
if [ $? -eq 0 ]
	then
	echo -e "\033[32m *Screenshot save successfully.\n *The Path is ~/Pictures/$2/$3/${NAME} \033[0m"
	else
	echo -e "\033[31m *Screenshot save failed \033[0m"
fi
