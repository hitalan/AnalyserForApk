#$1 means the right apk name
#$2 means the bad apk name
./androaxml.py -i $1 >a.txt
./androaxml.py -i $2 >b.txt
python diff.py
