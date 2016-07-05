#$1 means we shall know the already in use task number and it is the channleapk path
cd $1
result=`ls -l |grep "^d"|wc -l`
echo $result

