package com.marvell.scan;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Zishang on 2017/2/9.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        testBluetooth.class,
        testWifi.class
}
)
public class testAll {
}
