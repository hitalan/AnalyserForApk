#!/bin/bash
cd $1
c=0
for file in `ls *.apk`
do
  filelist[$c]=$file
  c=`expr $c + 1`
done
echo "${filelist[*]}"
c=0
for file in ${filelist[*]}
do
  filepackage[$c]=$(aapt dump badging $file | grep 'package:')
  c=`expr $c + 1`
done
echo "${filepackage[*]}"
cd ../channelapk
c=0
for file in `ls *.apk`
do
  channellist[$c]=$file
  c=`expr $c + 1`
done
echo "${channellist[*]}"
for file in ${channellist[*]}
do
 channelpackage[$c]=$(aapt dump  badging $file |grep 'package:')
 c=`expr $c + 1`
done
echo "${channelpackage[*]}"


