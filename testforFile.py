#coding=utf-8
from hashlib import md5
import sys 
def md5_file(name):
    m = md5()
    a_file = open(name, 'rb')
    m.update(a_file.read())
    a_file.close()
    return m.hexdigest()
  
first = md5_file(sys.argv[1])
second = md5_file(sys.argv[2])
if first==second:
	print	'yes'
else:
	print	'wrong'

print sys.argv[1]
print sys.argv[2]
print md5_file(sys.argv[1])

print md5_file(sys.argv[2])
