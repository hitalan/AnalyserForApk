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
  filesignature[$c]=$(java -cp ../../getSignature.jar com.wandoujia.tools.ApkSignatureToolsMain $file |grep 'signatureMd5')
  c=`expr $c + 1`
done
echo "${filesignature[*]}"
c=0
for file in ${filelist[*]}
do
  filepackage[$c]=$(aapt dump badging $file | grep 'package:')
  c=`expr $c + 1`
done
echo "${filepackage[*]}"

