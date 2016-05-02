#coding=utf-8
import os
import zipfile
import sys
from hashlib import md5

def md5_file(name):
    m = md5()
    a_file = open(name, 'rb')
    m.update(a_file.read())
    a_file.close()
    return m.hexdigest()
path=sys.argv[1]  # this is apk files' store path
dexlist = os.listdir(path)
ds = dict()
for Dex in dexlist:
    if Dex.endswith('.dex'):
      ds[Dex]=md5_file(Dex)
for (s,t) in ds.items():
  print s+" "+t+","
apklist = os.listdir(path) # get all the names of apps
apkhash = dict()
for APK in apklist:
    portion = os.path.splitext(APK)
    if portion[1] =='.apk':
        apkhash[APK]=md5_file(APK)
for (k,v) in apkhash.items():
  print k+" "+v+","
