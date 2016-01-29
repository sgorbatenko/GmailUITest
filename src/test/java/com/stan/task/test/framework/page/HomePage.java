
package com.stan.task.test.framework.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.control.Button;
import com.stan.task.test.framework.control.Element;
import com.stan.task.test.framework.control.grid.SimpleGrid;
import com.stan.task.test.framework.pagefactory.ExtendedPageFactory;
import com.stan.task.test.framework.pagefactory.TestName;

public class HomePage extends AbstractPage
{
    @TestName(testName = "Account menu")
    @FindBy(xpath = "//div/a[contains(@title,\'Google Account\')]")
    private WebElement _googleAccountMenu;

    @TestName(testName = "Account button")
    @FindBy(css = ".gb_8c")
    private Button _accountBtn;

    @TestName(testName = "Sign out button")
    @FindBy(css = "div a[href*='logout']")
    private Button _signOutBtn;

    @TestName(testName = "Compose button")
    @FindBy(css = "div[role='button'][gh='cm']")
    private Button _composeBtn;

    @TestName(testName = "Inbox Link")
    @FindBy(css = "div[role='navigation']  a[href*='inbox']")
    private Element _inboxLink;

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

    public Button getAccountBtn()
    {
        return _accountBtn;
    }

    public Button getSignOutBtn()
    {
        return _signOutBtn;
    }

    public Button getComposeBtn()
    {
        return _composeBtn;
    }

    public Element getInboxLink()
    {
        return _inboxLink;
    }

    public SimpleGrid getInboxGrid()
    {
        return _inboxGrid;
    }

    @Override
    void populateUiControls()
    {
        addUiControl(getInboxGrid());
        addUiControl(getAccountBtn());
        addUiControl(getComposeBtn());
        addUiControl(getInboxLink());
        addUiControl(getSignOutBtn());
    }
}
