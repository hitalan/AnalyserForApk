filepath=`pwd`
result=`python getDexHash.py $filepath $filepath` 
echo $result
./change.sh

