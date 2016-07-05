#$1 means the root folder path 
cd $1
for dir in $(ls -cr $1)
do
    [ -d $dir ] && echo $dir
done     
