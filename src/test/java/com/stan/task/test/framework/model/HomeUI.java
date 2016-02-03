
package com.stan.task.test.framework.model;

import java.util.Deque;
import java.util.LinkedList;

import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.data.Email;
import com.stan.task.test.framework.page.HomePage;

public class HomeUI implements UiObject
{
    private final ClientBrowser _parentClientBrowser;

    // Physical pages
    private HomePage _homePage;

    // UI entities
    private Deque<NewMessageUI> _newMessages = new LinkedList<NewMessageUI>();
    private EmailViewUI _emailViewUI;
    private SettingsUi _settingsUi;

    public HomeUI(ClientBrowser parentClientBrowser)
    {
        _parentClientBrowser = parentClientBrowser;
    }

    @Override
    public HomePage getPage()
    {
        if (_homePage == null)
        {
            _homePage = new HomePage(_parentClientBrowser);
        }

        return _homePage;
    }

    public EmailViewUI getEmailViewUI(Email e)
    {
        clickRowWithText(e.getSubject());
        _emailViewUI = new EmailViewUI(_parentClientBrowser);
        return _emailViewUI;
    }

    public SettingsUi getSettingsUi()
    {
        if (_settingsUi == null)
        {
            _settingsUi = new SettingsUi(_parentClientBrowser);
        }
        return _settingsUi;
    }

    public boolean isUserLoggedIn()
    {
        return getPage().getGoogleAccountMenu().isDisplayed();
    }

    public NewMessageUI getNewMessageUI()
    {
        getPage().getComposeBtn().click();
        _newMessages.add(new NewMessageUI(_parentClientBrowser));
        return _newMessages.getLast();
    }

    protected void clickRowWithText(String text)
    {
        getPage().getInboxLink().click();
        getPage()
            .getInboxGrid()
            .getRowContainingCellWithExactText(text)
            .click();
    }

    @Override
    public boolean isOpened()
    {
        return isUserLoggedIn();
    }

    @Override
    public UiObject open()
    {
        if (isOpened())
        {
            return this;
        }
        return null;
    }

    // private Deque<NewMessageUI> getNewMessageUIs()
    // {
    // return _newMessages;
    // }

}
