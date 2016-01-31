
package com.stan.task.test.localization;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeClass;

import com.stan.task.test.AbstractSeleniumTest;
import com.stan.task.test.framework.model.HomeUI;
import com.stan.task.test.framework.utils.Wait;

public abstract class AbstractLocalizationTest extends AbstractSeleniumTest
{
    protected HomeUI _homeUi;

    @BeforeClass
    public void setup()
    {
        getClientBrowser().getSeleniumWebDriver().manage().timeouts().implicitlyWait(Wait.TIMEOUT_MIN_WAIT,
            TimeUnit.SECONDS);
        getClientBrowser().getLoginUI().login("usr7778899@gmail.com", "testPass456!##");
        _homeUi = getClientBrowser().getApplicationUI();
    }

}
