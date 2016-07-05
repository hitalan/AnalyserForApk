#$1 is the type of the whether we shall makedir in the secondclientapk
#$2 is the dir of the analyser
#$3 is the detail dir generate by the time
if [ "$1" = "1" ]
 then
 cd $2/secondclientapk/
 mkdir $3
 echo "already mkdir in the second dir" 
else
 cd $2/clientapk/
 mkdir $3
 cd ../channelapk
 mkdir $3
 echo "already mkdir in the right"
fi
