#$1 means the folder of the capture apk
# $2 means the path of the shell
# $package means the package name of this apk to monkey 
# $i means the result text name
cd $1
for file in `ls *.apk`
do
  package=$(aapt dump badging $file|grep 'package')
  package=${package#*"'"}
  package=${package%%"'"*}
  echo $package
  adb install $file
  adb  shell   monkey -p  $package  -v 1
  cd $2
  adb shell < packet.sh
  adb pull /data/local/net.pcap ~/
  i=$file".txt"
  parse-pcap ~/net.pcap>$i
  adb uninstall $package
  cd $1
done
