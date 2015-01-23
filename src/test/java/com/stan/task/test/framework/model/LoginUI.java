
package com.stan.task.test.framework.model;

import org.testng.Assert;

import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.page.LoginPage;

public class LoginUI // extends ApplicationUI
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
        // getParentClientBrowser().waitForElementPresentByCss("input[name='" +
        // USERNAME_INPUT_NAME + "']", Wait.TIMEOUT_MIN_WAIT);
        getPage().getEmailTextBox().click();
        getPage().getEmailTextBox().sendKeys(userName); // replace(userName);
        // getParentClientBrowser().waitForTextPresentInElement(By.name(USERNAME_INPUT_NAME),
    }

    public void setPassword(String pwd)
    {
        // getParentClientBrowser().waitForElementPresentByCss("input[name='" +
        // PASSWORD_INPUT_NAME + "']", Wait.TIMEOUT_MIN_WAIT);
        getPage().getPasswordTextBox().click();
        getPage().getPasswordTextBox().sendKeys(pwd); // replace(pwd);
        // getParentClientBrowser().waitForTextPresentInElement(By.name(PASSWORD_INPUT_NAME),
        // pwd);
        // clickOnLogo();
        // Testing.sleep(200);
    }

    /**
     * Performs the action in the LoginUI that logs in, i. e. wait for and click
     * the login button
     */
    public void clickLoginButton()
    {
        // wait for it to be enabled
        // getPage().getLoginButton().waitUntilHasAttributeState("disabled",
        // false, 10, TestSettings.newFeature());

        getPage().getLoginButton().click();
    }

    public void login(String email, String password)
    {
        setEmailname(email);
        setPassword(password);
        clickLoginButton();
    }

    public void verifyUserIsLoggedIn()
    {
        Assert.assertTrue(
            _parentClientBrowser.getApplicationUI().getPage().getAccount().isDisplayed(),
            "User Is Logged In");
    }

    public LoginUI logout()
    {
        _parentClientBrowser.getApplicationUI().getPage().getAccount().click();
        _parentClientBrowser.getApplicationUI().getPage().getSignOutBtn().click();
        return this;
    }

    public void verifyIsUserLogedOut()
    {
        Assert.assertTrue(
            _loginPage.getPasswordTextBox().isDisplayed(),
            "User is logged out by Password TextBox being visible");
    }

}
