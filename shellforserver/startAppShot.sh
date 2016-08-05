#$1 means the folder path  it may be the fish or repackage
cd $1
for dir in $(ls -cr $1)
do
    if [ -d $dir ];
     then
      temp=${dir%?}
      package=${temp#?}
      sh ~/shellforserver/getAppPic.sh $1/$dir $package 
    fi
done     
