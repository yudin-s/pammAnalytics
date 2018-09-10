package org.barsukov.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DatabaseTest.class, DataManagerTest.class, UtilitesTest.class, WebClientFactoryTest.class })
public class AllTests {

}
