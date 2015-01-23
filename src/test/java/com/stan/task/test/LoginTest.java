
package com.stan.task.test;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.stan.task.test.framework.model.LoginUI;
import com.stan.task.test.framework.utils.Wait;

public class LoginTest extends AbstractSeleniumTest
{
    private LoginUI _loginUI;

    @BeforeClass
    public void setup() throws InterruptedException
    {
        getClientBrowser().getSeleniumWebDriver().manage().timeouts()
            .implicitlyWait(Wait.TIMEOUT_MIN_WAIT, TimeUnit.SECONDS);
        _loginUI = getClientBrowser().getLoginUI();
    }

    @AfterClass
    public void teardown()
    {
        // / TODO: Refactor using Element class instead of WebElement
        // if (getClientBrowser().getApplicationUI().isUserLoggedIn())
        // _loginUI.logout();
        super.teardown();
    }

    @Test
    void testLogin()
    {
        _loginUI.login("usr7778899@gmail.com", "testPass456!#");
        _loginUI.verifyUserIsLoggedIn();
    }

    @Test
    void testSignOut()
    {
        if (!getClientBrowser().getApplicationUI().isUserLoggedIn())
            _loginUI.login("usr7778899@gmail.com", "testPass456!#");
        _loginUI.logout();
        _loginUI.verifyIsUserLogedOut();
    }
}
