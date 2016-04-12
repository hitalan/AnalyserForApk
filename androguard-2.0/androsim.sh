channelapkfile=`ls ../channelapk/*.apk`
for file in `ls ../clientapk/*.apk`
do
echo $file
value=`./androsim.py -i $channelapkfile $file|grep 'similarity' `
echo $value
done
