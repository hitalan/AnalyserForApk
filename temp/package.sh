package=$(aapt dump badging $1 | grep 'package:')
packagetemp=${package:14}
second_package=$(aapt dump badging $2 | grep 'package:')
second_packagetemp=${second_package:14}
packagename=${packagetemp%% *}
second_packagename=${second_packagetemp%% *}
if [ "$packagename" == "$second_packagename" ]
then
  echo "it is  same"
else
  echo "it is different"
fi



