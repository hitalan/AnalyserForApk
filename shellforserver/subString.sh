var=$(aapt dump badging $1|grep 'package')
var=${var#*"'"}
echo ${var%%"'"*}
