cd /sdcard/data/
tcpdump -i eth1 -s 0 -w net.pcap -c 200 tcp
exit
