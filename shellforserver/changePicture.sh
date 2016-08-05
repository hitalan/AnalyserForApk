#$1 means the folder path  we shall change
cd $1
for dir in $(ls -cr $1)
do
    if [ -d $dir ];
     then
      cd $dir
      echo $dir
	for seconddir in $(ls -cr `pwd`)
	   do
	     if [ -d $seconddir ];
		then
		  cd $seconddir
		  echo $seconddir
		    c=0
		     for png in `ls *.png`
		       do                                    
	         	c=`expr $c + 1`
                           mv $png  $c'.png'
		     done
                  cd .. 
             fi  
       done
       cd .. 
    fi
done                         
