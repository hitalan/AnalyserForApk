#$1 means the root folder path 
cd $1
count=$(ls -l $1| grep "^d"|wc -l)
echo 'the floder is '$count
i=0
for dir in $(ls -cr $1)
do
    if	[ -d $dir ] 
    then
        i=$((i + 1)) 
	if  [ $i -lt $count ]	
	then
	 rm -r $dir
         echo 'rm the floder of '$dir
	fi
    fi
done     
