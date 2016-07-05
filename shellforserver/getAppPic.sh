#$1 means the floder of the apk which is the same bad type and have the same packagename
#$2 means the package name of the apk
cd $1
for file in `ls *.apk`
do
 adb install $file
  echo "finished the install"
  if [ -d ~/Pictures/$2 ];then
    echo "文件夹 exist"
  else
    mkdir ~/Pictures/$2
  fi
  if [ -d ~/Pictures/$2/$file ];then
    echo "filefloder exist"
  else
    mkdir ~/Pictures/$2/$file
  fi
# datedir=$(date +%Y%m%d_%H-%M-%S)
# mkdir ~/Pictures/$2/$datedir
 for i in $(seq 10); do
   echo $i times try to monkey ;
   adb shell monkey -p $2 20 
   echo $i times try to capture ;
   cd ~/shellforserver/
   sh screenshot.sh -h $2 $file
 done;
 echo "finished the monkey and capture testing"
 adb uninstall $2
 echo "uninstall the apk"
 cd $1 
done
