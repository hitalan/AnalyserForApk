channelapk=`ls ../channelapk/$1/*.dex`
./d2j-dex2jar.sh $channelapk
channel=`ls *-dex2jar.jar`
mkdir $1
mv "$channel" "$1/channeldex2jar.jar";
for clientapk in `ls ../clientapk/$1/*.dex`
do
    ./d2j-dex2jar.sh $clientapk
    echo $clientapk
    client=`ls *-dex2jar.jar`
    mv "$client" "$1/clientdex2jar.jar";
    cd ../target
    result=`java -jar stigmata-5.0-SNAPSHOT.jar   -f xml  -b   cvfv  compare ../dex2jar-2.0/$1/channeldex2jar.jar  ../dex2jar-2.0/$1/clientdex2jar.jar`
    echo $result
    rm ../dex2jar-2.0/$1/clientdex2jar.jar
    cd ../dex2jar-2.0
done
rm -r ./$1
rm *.jar
rm *.zip
