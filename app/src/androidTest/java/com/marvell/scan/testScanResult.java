package com.marvell.scan;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Zishang on 2017/2/7.
 */

@RunWith(AndroidJUnit4.class)
public class testScanResult{

    Instrumentation instrumentation;
    UiDevice myUiDevice;
    String scanBluetooth = "am start -W com.android.tv.settings/.accessories.AddAccessoryActivity";
    String enterWifiSetting = "am start -W com.android.tv.settings/.connectivity.NetworkActivity";
    int waitTime_Bluetooth = 60000;
    int short_wait = 2000;
    int long_wait = 6000;
    //String wifiProperty = "WPA/WPA2 PSK";
    String TAG_scan = "scan";

    @Before
    public void setUp() throws Exception {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        myUiDevice = UiDevice.getInstance(instrumentation);
        myUiDevice.pressHome();
    }

    @Test
    public void testBluetooth() throws Exception {
        myUiDevice.executeShellCommand(scanBluetooth);
        //wait,'cause scan takes time
        myUiDevice.wait(Until.hasObject(By.res("com.android.tv.settings:id/action_title")),waitTime_Bluetooth);
        Thread.sleep(3000);
        assert myUiDevice.hasObject(By.res("com.android.tv.settings:id/action_title"));
    }

    @Test
    public void testWifi() throws Exception {
        myUiDevice.executeShellCommand(enterWifiSetting);
        myUiDevice.pressEnter();
        myUiDevice.pressEnter();
        myUiDevice.pressEnter();
        Thread.sleep(long_wait);
        UiObject2 seeAll = myUiDevice.findObject(By.hasChild(By.text("See all"))).getParent();
        UiScrollable listAll = new UiScrollable(new UiSelector().scrollable(true));
        listAll.setAsVerticalList();
        listAll.scrollIntoView(new UiSelector().text("See all"));
        if (seeAll.isClickable()){
            seeAll.click();
        }else {
            Log.w(TAG_scan, "testWifi: The button 'seeAll' is not clickable,try to locate the correct button");
        }
        Thread.sleep(short_wait); //
        UiScrollable wifiList = new UiScrollable(new UiSelector().scrollable(true));
        wifiList.setAsVerticalList();
        assert wifiList.scrollIntoView(new UiSelector().text("Linksys44296_5GHz"));
        assert wifiList.scrollIntoView(new UiSelector().text("Linksys44296"));
    }

    @After
    public void tearDown() throws Exception {


    }

}
