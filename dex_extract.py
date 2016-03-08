import os 
import zipfile       
path="/home/hit_alan/zhoufandi/somefilebetweenlinuxandwindows/"  # this is apk files' store path
dex_path="/home/hit_alan/zhoufandi/somefilebetweenlinuxandwindows/" # a directory  store dex files 
 
 
apklist = os.listdir(path) # get all the names of apps
 
if not os.path.exists(dex_path):
    os.makedirs(dex_path)
 
for APK in apklist:
    portion = os.path.splitext(APK)
 
    if portion[1] =='.apk':
        newname = portion[0] + '.zip' # change them into zip file to extract dex files
        os.rename(APK,newname)
 
# if APK.endswith('.zip'):
apklists = os.listdir(path)
for Zip in apklists:
    deal = os.path.splitext(Zip)
    if Zip.endswith('.zip'):
       print 'the apkname is' + deal[0]
       zip_apk_path = os.path.join(path,Zip) # get the zip files
       z = zipfile.ZipFile(zip_apk_path, 'r') # read zip files
       for filename in z.namelist() :
	if filename.endswith('.dex'):
                dexfilename = deal[0] + '.dex'
                dexfilepath = os.path.join(dex_path, dexfilename)
                f = open(dexfilepath, 'w+') # eq: cp classes.dex dexfilepath
                f.write(z.read(filename))
 
