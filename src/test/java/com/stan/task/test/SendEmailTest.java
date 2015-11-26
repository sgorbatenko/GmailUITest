
package com.stan.task.test;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.stan.task.test.framework.data.Email;
import com.stan.task.test.framework.model.ApplicationUI;
import com.stan.task.test.framework.model.LoginUI;
import com.stan.task.test.framework.utils.Wait;

public class SendEmailTest extends AbstractSeleniumTest
{
    private LoginUI _loginUI;
    private ApplicationUI _mainUI;

    @BeforeClass
    public void setup() throws InterruptedException
    {
        getClientBrowser().getSeleniumWebDriver().manage().timeouts()
            .implicitlyWait(Wait.TIMEOUT_MIN_WAIT, TimeUnit.SECONDS);
        _loginUI = getClientBrowser().getLoginUI();
        _mainUI = getClientBrowser().getApplicationUI();
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
    void testSendEmail()
    {
        _loginUI.login("usr7778899@gmail.com", "testPass456!#");
        Email e = new Email("usr7778899@gmail.com", "Test subject", "Test body");
        _mainUI.getNewMessageUI().sendEmail(e);
    }
}
