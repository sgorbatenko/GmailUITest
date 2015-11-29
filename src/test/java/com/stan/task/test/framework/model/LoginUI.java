
package com.stan.task.test.framework.model;

import org.testng.Assert;

import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.data.User;
import com.stan.task.test.framework.page.HomePage;
import com.stan.task.test.framework.page.LoginPage;

public class LoginUI implements UiObject // extends ApplicationUI
{
    private final ClientBrowser _parentClientBrowser;

    private LoginPage _loginPage;

    /**
     * Creates a new ApplicationUI instance.
     * 
     * @param parentClientBrowser
     *        the parent login ui
     */
    public LoginUI(ClientBrowser parentClientBrowser)
    {
        // super(parentClientBrowser);
        _parentClientBrowser = parentClientBrowser;
    }

    public LoginPage getPage()
    {
        if (_loginPage == null)
        {
            _loginPage = new LoginPage(_parentClientBrowser);
        }
        return _loginPage;
    }

    public void setEmailname(String userName)
    {
        _parentClientBrowser.waitForElementPresent(getPage().getEmailTextBox());
        getPage().getEmailTextBox().click();
        getPage().getEmailTextBox().append(userName);
    }

    public void setPassword(String pwd)
    {
        _parentClientBrowser.waitForElementPresent(getPage().getPasswordTextBox());
        getPage().getPasswordTextBox().click();
        getPage().getPasswordTextBox().append(pwd);
    }

    /**
     * Performs the action in the LoginUI that logs in, i. e. wait for and click
     * the login button
     */
    public void clickLoginButton()
    {
        getPage().getLoginButton().click();
    }

    public void clickNextButton()
    {
        getPage().getNextButton().click();
    }

    public void login(User user)
    {
        setEmailname(user.getEmailAddress());
        clickNextButton();
        setPassword(user.getPassword());
        clickLoginButton();
    }

    public void verifyUserIsLoggedIn()
    {
        Assert.assertTrue(
            getApplicationHomePage().getGoogleAccountMenu().isDisplayed(),
            "User Is Logged In");
    }

    public HomePage getApplicationHomePage()
    {
        return _parentClientBrowser.getApplicationUI().getPage();
    }

    public LoginUI logout()
    {
        getApplicationHomePage().getGoogleAccountMenu().click();
        getApplicationHomePage().getSignOutBtn().click();
        return this;
    }

    public void verifyIsUserLogedOut()
    {
        Assert.assertTrue(
            _loginPage.getPasswordTextBox().isVisible(),
            "User is logged out by Password TextBox being visible");
    }

}
