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
dex_path=sys.argv[2] # a directory  store dex files 


apklist = os.listdir(path) # get all the names of apps

if not os.path.exists(dex_path):
    os.makedirs(dex_path)
apkhash = dict()
for APK in apklist:
    portion = os.path.splitext(APK)

    if portion[1] =='.apk':
        apkhash[APK]=md5_file(APK)
        newname = portion[0] + '.zip' # change them into zip file to extract dex files
        os.rename(APK,newname)
apklists = os.listdir(path)
for Zip in apklists:
    deal = os.path.splitext(Zip)
    if Zip.endswith('.zip'):
      # print 'the apkname is' + deal[0]
       zip_apk_path = os.path.join(path,Zip) # get the zip files
       z = zipfile.ZipFile(zip_apk_path, 'r') # read zip files
       for filename in z.namelist() :
        if filename.endswith('.dex'):
                dexfilename = deal[0] + '.dex'
                dexfilepath = os.path.join(dex_path, dexfilename)
                f = open(dexfilepath, 'w+') # eq: cp classes.dex dexfilepath
                f.write(z.read(filename))
dexlists = os.listdir(path)
d = dict()
for Dex in dexlists:
    dex = os.path.splitext(Dex)
    if Dex.endswith('.dex'):
       d[Dex]=md5_file(Dex)
for (k,v) in d.items():
  print k+" "+v+","
for (k,v) in apkhash.items():
  print k+" "+v








   
