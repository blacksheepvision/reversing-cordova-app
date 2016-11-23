#!/bin/bash

if java -jar ../toolz/apktool_2.2.1.jar b .; then
jarsigner -keystore ../.android/debug.keystore ./dist/* androiddebugkey
read -p "Hit enter to deploy"
../Android/Sdk/platform-tools/adb install -r -d ./dist/my.apk
fi
