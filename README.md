**It is used to test android N image only.**  
This is used to test if Wifi and Bluetooth functions work well .  

*Download and install the two apk (mainapk and testapk), at [here](http://10.37.132.97:3000/Lord/ScanWifi_Bluetooth/releases).*
* For BT test,it scans for 120 seconds.Remember to place an android device with BT on near to the tested device.**If after 120 seconds,no available BT device is detected,the case will fail.**
* For Wifi test,it scans for two Wifi,"Linksys44296" and "Linksys44296_5GHz" .**Only when both two Wifi are detected,the case can pass .**

**Note :**
* Command to observe log


~~~
logcat -c;logcat -s scanwifibt:v 
~~~

* You can **either** just install the two apks,connect adb and run command

~~~
adb shell am instrument -w -r   -e debug false -e class com.marvell.scan.testAll com.marvell.scan.test/android.support.test.runner.AndroidJUnitRunner
~~~

**Or** install the two apks,get board ip,set variable "boardIp" in the "testBTWifi.bat" file,and run the "testBTWifi.bat" file to run the test along with monkey test for 100 times.
In cmd,

~~~
 .\testWifi.bat
~~~
