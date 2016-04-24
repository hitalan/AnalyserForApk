if [ "$1" = "1" ]
 then
 cp ./secondclientapk/*.py ./secondclientapk/$2
 cp ./secondclientapk/*.sh ./secondclientapk/$2
else
 cp ./clientapk/*.py ./clientapk/$2
 cp ./clientapk/*.sh ./clientapk/$2
fi
 cp ./channelapk/*.py ./channelapk/$2
 cp ./channelapk/*.sh ./channelapk/$2


