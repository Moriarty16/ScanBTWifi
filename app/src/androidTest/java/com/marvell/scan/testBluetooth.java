package com.marvell.scan;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static junit.framework.Assert.fail;

/**
 * Created by Zishang on 2017/2/9.
 */
@RunWith(JUnit4.class)
public class testBluetooth {
    Instrumentation instrumentation;
    UiDevice myUiDevice;
    String killSettings = "am force-stop com.android.tv.settings";
    String scanBluetooth = "am start -W com.android.tv.settings/.accessories.AddAccessoryActivity";
    String TAG_scan = "scanwifibt";
    int waitTime_Bluetooth = 120000;

    @Before
    public void setUp() throws IOException {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        myUiDevice = UiDevice.getInstance(instrumentation);
        myUiDevice.pressHome();
        myUiDevice.executeShellCommand(killSettings);
        myUiDevice.executeShellCommand(scanBluetooth);
    }

    @Test
    public void testBT() throws AssertionError, InterruptedException {
        myUiDevice.wait(Until.hasObject(By.res("com.android.tv.settings:id/list")),waitTime_Bluetooth);
        Thread.sleep(3000);
        UiObject2 BTList = myUiDevice.findObject(By.res("com.android.tv.settings:id/list"));
        Log.i(TAG_scan, "testWifi: Available BT device number is "+BTList.getChildCount());
        //!!!Already tested,assert() is NOT reliable!!!
        if (!(BTList.getChildCount() >= 1)){
            fail();
        }
    }

    @After
    public void tearDown() throws Exception {
        myUiDevice.pressHome();
    }
}
