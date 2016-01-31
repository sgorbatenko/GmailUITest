
package com.stan.task.test.framework.model;

import org.testng.Assert;

import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.data.User;
import com.stan.task.test.framework.page.HomePage;
import com.stan.task.test.framework.page.LoginPage;

public class LoginUI implements UiObject
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

    @Override
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
        login(user.getEmailAddress(), user.getPassword());
    }

    public void login(String email, String password)
    {
        setEmailname(email);
        clickNextButton();
        setPassword(password);
        clickLoginButton();
    }

    public void verifyUserIsLoggedIn()
    {
        Assert.assertTrue(isUserLoggedIn(), "User Is Logged In");
    }

    public boolean isUserLoggedIn()
    {
        return !getPage().getLoginButton().isVisible();
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

    @Override
    public boolean isOpened()
    {
        // TODO-SG Auto-generated method stub
        return false;
    }

    @Override
    public UiObject open()
    {
        // TODO-SG Auto-generated method stub
        return null;
    }

    public void loginIfNotLoggedIn(String email, String password)
    {
//        if (!isUserLoggedIn())
//        {
        login(email, password);
        // }
    }

}
