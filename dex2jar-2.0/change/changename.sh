i=1
for var in `ls *-dex2jar.jar`;
do
mv "$var" "classes${i}-dex2jar.jar";
i=`expr $i + 1`
done
