i=1
for var in *-dex2jar.jar; do mv "$var" "classes${i}-dex2jar.jar"; let i++;done
