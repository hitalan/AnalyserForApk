#!/bin/bash
result=`ls *.zip`
for var in $result
do
    mv $var ${var%.zip}.apk
done
exit 0
