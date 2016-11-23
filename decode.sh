#!/bin/bash

for f in $(find ./ -name '*.js' -or -name '*.html' -or -name '*.css'); 
do java -jar shitDecryptor.jar $f 
done
