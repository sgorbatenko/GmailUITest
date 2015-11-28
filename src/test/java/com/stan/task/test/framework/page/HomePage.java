
package com.stan.task.test.framework.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.control.grid.SimpleGrid;
import com.stan.task.test.framework.pagefactory.ExtendedPageFactory;
import com.stan.task.test.framework.pagefactory.TestName;

public class HomePage implements Page
{
    @TestName(testName = "Account menu")
    @FindBy(xpath = "//div/a[contains(@title,\'Google Account\')]")
    private WebElement _googleAccountMenu;

    @TestName(testName = "Account button")
    @FindBy(css = ".gb_8c")
    private WebElement _accountBtn;

    @TestName(testName = "Sign out button")
    @FindBy(xpath = "//div/a[text()=\'Sign out\']")
    private WebElement _signOutBtn;

    @TestName(testName = "Compose button")
    @FindBy(css = "div[role='button'][gh='cm']")
    private WebElement _composeBtn;

    @TestName(testName = "Inbox Link")
    @FindBy(css = "div[role='navigation']  a[title*='Inbox']")
    private WebElement _inboxLink;

    @TestName(testName = "Inbox Grid")
    @FindBy(css = ".BltHke:not([style='display: none;']) table.F")
    private SimpleGrid _inboxGrid;

    public HomePage(ClientBrowser clientBrowser)
    {
        ExtendedPageFactory.initElements(clientBrowser, this);
    }

    public WebElement getGoogleAccountMenu()
    {
        return _googleAccountMenu;
    }

    public WebElement getAccount()
    {
        return _accountBtn;
    }

    public WebElement getSignOutBtn()
    {
        return _signOutBtn;
    }

    public WebElement getComposeBtn()
    {
        return _composeBtn;
    }

    public WebElement getInboxLink()
    {
        return _inboxLink;
    }

    public SimpleGrid getInboxGrid()
    {
        return _inboxGrid;
    }
}
