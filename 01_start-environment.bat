@echo off

cd /d %~dp0

start /Min java -jar selenium/jar/selenium-server-standalone-3.14.0.jar -role hub -hubConfig selenium/json/hubconfig.json
start /Min java -Dwebdriver.chrome.driver="selenium//driver//chromedriver-v2.41-win32//chromedriver.exe"^
     -Dwebdriver.ie.driver="selenium//driver//IEDriverServer_x64_3.14.0//IEDriverServer.exe"^
     -Dwebdriver.gecko.driver="selenium//driver//geckodriver-v0.21.0-win64//geckodriver.exe"^
     -jar selenium/jar/selenium-server-standalone-3.14.0.jar -role node -nodeConfig selenium/json/nodeconfig.json

@echo on