
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

    @TestName(testName = "Inbox link")
    @FindBy(css = "div[role='navigation']  a[href*='inbox']")
    private Element _inboxLink;

    @TestName(testName = "Inbox grid")
    @FindBy(css = ".BltHke:not([style='display: none;']) table.F")
    private SimpleGrid _inboxGrid;

    @TestName(testName = "Starred link")
    @FindBy(css = "div[role='navigation']  a[href*='starred']")
    private Element _starredLink;

    @TestName(testName = "Important link")
    @FindBy(css = "div[role='navigation']  a[href*='imp']")
    private Element _importantLink;

    @TestName(testName = "Sent Mail link")
    @FindBy(css = "div[role='navigation']  a[href*='sent']")
    private Element _sentMailLink;

    @TestName(testName = "Drafts link")
    @FindBy(css = "div[role='navigation']  a[href*='drafts']")
    private Element _draftsLink;

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

    public Element getStarredLink()
    {
        return _starredLink;
    }

    public Element getImportantLink()
    {
        return _importantLink;
    }

    public Element getSentMailLink()
    {
        return _sentMailLink;
    }

    public Element getDraftsLink()
    {
        return _draftsLink;
    }

    @Override
    protected void populateUiControls()
    {
        addUiControl(getInboxGrid());
        addUiControl(getAccountBtn());
        addUiControl(getComposeBtn());
        addUiControl(getInboxLink());
        addUiControl(getSignOutBtn());
        addUiControl(getStarredLink());
        addUiControl(getImportantLink());
        addUiControl(getSentMailLink());
        addUiControl(getDraftsLink());
    }
}
