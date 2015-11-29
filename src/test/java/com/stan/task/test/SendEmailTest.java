
package com.stan.task.test;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.stan.task.test.framework.data.Email;
import com.stan.task.test.framework.data.User;
import com.stan.task.test.framework.model.HomeUI;
import com.stan.task.test.framework.model.LoginUI;
import com.stan.task.test.framework.utils.Wait;

public class SendEmailTest extends AbstractSeleniumTest
{
    private LoginUI _loginUI;
    private HomeUI _mainUI;
    private User _user;

    @BeforeClass
    public void setup() throws InterruptedException
    {
        _user = new User("usr7778899@gmail.com", "testPass456!#");
        getClientBrowser().getSeleniumWebDriver().manage().timeouts()
            .implicitlyWait(Wait.TIMEOUT_MIN_WAIT, TimeUnit.SECONDS);
        _loginUI = getClientBrowser().getLoginUI();
        _mainUI = getClientBrowser().getApplicationUI();
    }

    @AfterClass
    public void teardown()
    {
        // / TODO: Move common login to base class
        // if (getClientBrowser().getApplicationUI().isUserLoggedIn())
        // _loginUI.logout();
        super.teardown();
    }

    @Test
    void testSendEmail()
    {
        _loginUI.login(_user);
        Email e = new Email("usr7778899@gmail.com", "Test subject", "Test body");
        _mainUI.getNewMessageUI().sendEmail(e);
        _mainUI.getEmailViewUI(e).verifyEmailSent(e);
    }
}
