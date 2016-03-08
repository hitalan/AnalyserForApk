#!/bin/bash
python dex_extract.py
echo "already unzip and find the dex"
value=$1
s=${value%%.*}".dex"
value2=$2
s2=${value2%%.*}".dex"
echo "begin to check whether the md5 is the same"
the_compare_result=$(python testforFile.py $s $s2)
echo $the_compare_result
