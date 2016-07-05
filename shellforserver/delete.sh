#$1 means the dir of the analyzer
#$2 means the type of the analyzer which determine whether we need to delete the secondapk dir
#$3 means the detail dir of the order
cd $1/clientapk/
rm -r $3
cd $1/channelapk/
rm -r $3
echo "delete the clientapk and channelapk thing"
if [ "$2" != "0" ]
     then
     cd $1/secondclientapk/
     rm -r $3
     echo "delete the secondclientapk thing"
fi

