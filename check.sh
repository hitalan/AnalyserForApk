#!/bin/bash
python dex_extract.py
echo "=====================开始解压文件并获取dex信息================================"
value=$1
s=${value%%.*}".dex"
value2=$2
s2=${value2%%.*}".dex"
echo "=====================开始检测dexHash值是否相同================================"
the_compare_result=$(python testforFile.py $s $s2)
if [ "$the_compare_result" == "yes"  ]
 then 
  echo "Hash值相同是正版应用!"
else
echo "====================Hash值不同开始进行包名和版本名检测========================"
 	./change.sh
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
                echo "===================包名相同===================="
  		if [ "$versionName" == "$second_versionName" ]
  		then
                echo "===================版本号相同=================="
  		echo "软件包名相同版本号相同，但是hash值不同可以判定为二次打包应用"
  		else
  		echo "===================版本号不同=================="
                echo "软件需要结合对应该版本的白名单进行hash值分析，若对应白名单存在该版本hash值并相同则正版其他版本应用，若不同则是二次打包应用"
                echo "白名单不存在对应版本的软件则判定为疑似二次打包应用，可能是二次打包，可能是渠道中不存在的版本的正版应用"
 		 fi
	else
  	echo "=============应用包名不相同判定应用是否是白名单 ==================="
        echo "==================若是白名单则为不相关应用========================="
        echo "=================不是白名单 开始进行androguard相似度分析==========="
        cd androguard-2.0
        ./androsim.py -i  ../$1 ../$2 |grep 'methods' >temp.txt
        read simtemp<temp.txt
        simtmp=${simtemp:13}
        sim=${simtmp%% *}
        rm temp.txt
        echo $sim
        echo "=================不是白名单 开始进行stigmata相似度分析============="
        cd ../dex2jar-2.0
        ./d2j-dex2jar.sh ../$s
        ./d2j-dex2jar.sh ../$s2
        ./changename.sh
        cd ../target
        java -jar stigmata-5.0-SNAPSHOT.jar   -f xml  -b   cvfv  compare ../dex2jar-2.0/classes1-dex2jar.jar  ../dex2jar-2.0/classes2-dex2jar.jar        
        rm ../dex2jar-2.0/*-dex2jar.jar
        fi
fi

