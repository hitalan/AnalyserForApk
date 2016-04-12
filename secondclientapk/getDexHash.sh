filepath=`pwd`
python getDexHash.py $filepath $filepath
./change.sh
result=`python md5.py $filepath`
echo $result 


