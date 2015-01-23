
package com.stan.task.test.framework.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.pagefactory.ExtendedPageFactory;
import com.stan.task.test.framework.pagefactory.TestName;

public class ApplicationPage implements AbstractPage
{
    @TestName(testName = "Account button")
    @FindBy(css = ".gb_aa")
    private WebElement _accountBtn;

    @TestName(testName = "Sign out button")
    @FindBy(xpath = "//div/a[text()=\'Sign out\']")
    private WebElement _signOutBtn;

    public ApplicationPage(ClientBrowser clientBrowser)
    {
        ExtendedPageFactory.initElements(clientBrowser, this);
    }

    public WebElement getAccount()
    {
        return _accountBtn;
    }

    public WebElement getSignOutBtn()
    {
        return _signOutBtn;
    }
}
