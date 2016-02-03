
package com.stan.task.test.localization;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeClass;

import com.stan.task.test.AbstractSeleniumTest;
import com.stan.task.test.framework.model.HomeUI;

public abstract class AbstractLocalizationTest extends AbstractSeleniumTest
{
    protected HomeUI _homeUi;

    @BeforeClass
    public void setup()
    {
        getClientBrowser().getSeleniumWebDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        getClientBrowser().getLoginUI().login("usr7778899@gmail.com", "testPass456!##");
        _homeUi = getClientBrowser().getApplicationUI();
    }

    protected void changeLanguage(String lang) throws InterruptedException
    {
        _homeUi.getSettingsUi().open().setLanguage(lang);
        _homeUi.getSettingsUi().getPage().getSaveBtn().click();
        /// TO-DO: Replace with implicit wait
        Thread.sleep(2000);
    }
}