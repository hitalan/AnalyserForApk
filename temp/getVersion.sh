version=$(aapt dump badging $1 | grep 'versionName')
versionName=${version##* }
echo $versionName
