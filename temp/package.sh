package=$(aapt dump badging $1 | grep 'package:')
packagetemp=${package:14}
second_package=$(aapt dump badging $2 | grep 'package:')
second_packagetemp=${second_package:14}
packagename=${packagetemp%% *}
second_packagename=${second_packagetemp%% *}
version=$(aapt dump badging $1 | grep 'versionName')
versionName=${version##* }
second_version=$(aapt dump badging $2 | grep 'versionName')
second_versionName=${version##* }

if [ "$packagename" == "$second_packagename" ]
then
  if [ "$versionName" == "$second_versionName" ]
  then 
  echo "it is  same package and the same version but not the same dex so i have to see you are the ercidabao"
  else
  echo "it shall be check in details  whether is a erci  or other banben"
  fi
else
  echo "it is different package do the next"
fi

