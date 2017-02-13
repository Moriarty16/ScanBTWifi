package com.marvell.scan;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static junit.framework.Assert.fail;

/**
 * Created by Zishang on 2017/2/9.
 */
@RunWith(JUnit4.class)
public class testWifi {
    static Instrumentation instrumentation;
    static UiDevice myUiDevice;
    static String enterWifiSetting = "am start -W com.android.tv.settings/.connectivity.NetworkActivity";
    static String killSettings = "am force-stop com.android.tv.settings";
    static int short_wait = 2000;
    static int maxScrollStep = 100;
    static String TAG_scan = "scanwifibt";

    @BeforeClass
    public static void setUpBeforeClass() throws IOException, UiObjectNotFoundException, InterruptedException {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        myUiDevice = UiDevice.getInstance(instrumentation);
        myUiDevice.executeShellCommand(killSettings);
        myUiDevice.pressHome();
        myUiDevice.executeShellCommand(enterWifiSetting);
        UiObject2 wifiOnOff = myUiDevice.findObject(By.text("Wi-Fi")).getParent().getParent();
        if (myUiDevice.hasObject(By.text("Available networks"))){
            Log.i(TAG_scan, "testWifi: Wifi is already ON");
        }else {
            wifiOnOff.click();
            Log.i(TAG_scan, "testWifi: Wifi is OFF,turn it ON");
        }
        UiScrollable listAll = new UiScrollable(new UiSelector().scrollable(true));
        listAll.scrollToBeginning(maxScrollStep);
        if (myUiDevice.hasObject(By.text("See all"))){
            Log.i(TAG_scan, "testWifi: Find 'See all'");
        } else {
            for (int num = 1;num <= maxScrollStep;num++){
                boolean scrolled = listAll.scrollForward();
                if(myUiDevice.hasObject(By.text("See all"))) {
                    Log.i(TAG_scan, "testWifi: Find 'See all'");
                    break;
                }
                if (!scrolled) {
                    Log.i(TAG_scan, "testWifi: Already scrolled to the end,do not find 'See all'");
                    break;
                }
            }
        }
        UiObject2 seeAll = myUiDevice.findObject(By.text("See all")).getParent().getParent();
        if (seeAll.isClickable()){
            seeAll.click();
            Log.i(TAG_scan, "testWifi: Click 'See all'");
        }else {
            Log.w(TAG_scan, "testWifi: 'See all' cannot be clicked");
        }
        Thread.sleep(short_wait);
    }

    @Test
    public void testWifi_5g() throws UiObjectNotFoundException {
        UiScrollable wifiList = new UiScrollable(new UiSelector().scrollable(true));
        wifiList.setAsVerticalList();
        Boolean flag01 = wifiList.scrollIntoView(new UiSelector().text("Linksys44296_5GHz"));
        Log.i(TAG_scan, "testWifi: 5g Wifi is available,flag01 = "+flag01);
        //!!!Already tested,assert() is NOT reliable!!!
        if (!flag01){
            fail();
        }
    }

    @Test
    public void testWifi_24g() throws UiObjectNotFoundException {
        UiScrollable wifiList = new UiScrollable(new UiSelector().scrollable(true));
        wifiList.setAsVerticalList();
        Boolean flag02 = wifiList.scrollIntoView(new UiSelector().text("Linksys44296"));
        Log.i(TAG_scan, "testWifi: 2.4g Wifi is available,flag02="+flag02);
        if (!flag02){
            fail();
        }
    }

    @AfterClass
    public static void tearDownAfterlass() throws Exception {
        myUiDevice.pressHome();
    }
}
