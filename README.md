**It is used to test android N image only.**  
This is used to test if Wifi and Bluetooth functions work well .  
* For BT test,it scans for 120 seconds.Remember to place an android device with BT on near to the tested device.**If after 120 seconds,no available BT device is detected,the case will fail.**
* For Wifi test,it scans for two Wifi,"Linksys44296" and "Linksys44296_5GHz" .**Only when both two Wifi are detected,the case can pass .**

**Note :**
* Command to observe log   


~~~
logcat -c;logcat -s scanwifibt:v 
~~~
