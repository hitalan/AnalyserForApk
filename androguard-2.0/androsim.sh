channelapkfile=`ls ../channelapk/*.apk`
for file in `ls ../clientapk/*.apk`
do
 echo $file
 echo $channelapkfile
 ./androsim.py -i  $file $channelapkfile
#  echo $value
done
