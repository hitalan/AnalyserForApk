#!/bin/bash
cd androguard-2.0
./androsim.py -i  ../$1  ../$2|grep 'methods' 

