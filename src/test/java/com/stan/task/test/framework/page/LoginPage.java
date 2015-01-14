
package com.stan.task.test.framework.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.pagefactory.ExtendedPageFactory;
import com.stan.task.test.framework.pagefactory.TestName;

/**
 * The LoginPage Class.
 */
public final class LoginPage implements AbstractPage
{
    // @TestName(testName = "Logo image")
    // @FindBy(css = "#fp-login-form #fp-login-form-body label.x-component")
    // private Image _logoImage;

    @TestName(testName = "Email")
    @FindBy(name = "Email")
    private WebElement _emailTextBox;

    @TestName(testName = "Password")
    @FindBy(name = "Passwd")
    private WebElement _passwordTextBox;

    @TestName(testName = "Login")
    @FindBy(name = "signIn")
    private WebElement _loginButton;

    // @TestName(testName = "Create new account")
    // @FindBy(linkText = "Create new account")
    // private Link _createAccountLink;
    //
    // @TestName(testName = "Forgot your password")
    // @FindBy(linkText = "Forgot your password?")
    // private Link _forgotPasswordLink;

    /**
     * Creates a new LoginPage instance.
     * 
     * @param clientBrowser
     *        the client browser
     */
    public LoginPage(ClientBrowser clientBrowser)
    {
        ExtendedPageFactory.initElements(clientBrowser, this);
    }

    /**
     * Gets the username text box.
     * 
     * @return the textFieldUsername
     */
    public WebElement getEmailTextBox()
    {
        return _emailTextBox;
    }

    /**
     * Gets the password text box.
     * 
     * @return the textFieldPassword
     */
    public WebElement getPasswordTextBox()
    {
        return _passwordTextBox;
    }

    /**
     * Gets the login button.
     * 
     * @return the btnLogin
     */
    public WebElement getLoginButton()
    {
        return _loginButton;
    }
    //
    // /**
    // * Gets the creates the account link.
    // *
    // * @return the lnkCreateAccount
    // */
    // public Link getCreateAccountLink() {
    // return _createAccountLink;
    // }
    //
    // /**
    // * Gets the forgot password link.
    // *
    // * @return the lnkForgotPassword
    // */
    // public Link getForgotPasswordLink() {
    // return _forgotPasswordLink;
    // }

}