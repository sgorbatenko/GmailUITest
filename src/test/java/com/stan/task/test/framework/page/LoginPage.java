
package com.stan.task.test.framework.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.control.TextBox;
import com.stan.task.test.framework.pagefactory.ExtendedPageFactory;
import com.stan.task.test.framework.pagefactory.TestName;

/**
 * The LoginPage Class.
 */
public final class LoginPage implements Page
{
    // @TestName(testName = "Logo image")
    // @FindBy(css = "#fp-login-form #fp-login-form-body label.x-component")
    // private Image _logoImage;

    @TestName(testName = "Email")
    @FindBy(name = "Email")
    private TextBox _emailTextBox;

    @TestName(testName = "Password")
    @FindBy(name = "Passwd")
    private TextBox _passwordTextBox;

    @TestName(testName = "Next")
    @FindBy(name = "signIn")
    private WebElement _nextButton;

    @TestName(testName = "Login")
    @FindBy(id = "signIn")
    private WebElement _signInButton;

    public LoginPage(ClientBrowser clientBrowser)
    {
        ExtendedPageFactory.initElements(clientBrowser, this);
    }

    /**
     * Gets the username text box.
     * 
     * @return the textFieldUsername
     */
    public TextBox getEmailTextBox()
    {
        return _emailTextBox;
    }

    /**
     * Gets the password text box.
     * 
     * @return the textFieldPassword
     */
    public TextBox getPasswordTextBox()
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
        return _signInButton;
    }

    public WebElement getNextButton()
    {
        return _nextButton;
    }

//    /**
//     * Gets the login button.
//     * 
//     * @return the btnLogin
//     */
//    public WebElement getNextButton()
//    {
//        return _nextButton;
//    }
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