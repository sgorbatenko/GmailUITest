
package com.stan.task.test.framework.model;

import java.util.Deque;
import java.util.LinkedList;

import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.data.Email;
import com.stan.task.test.framework.page.HomePage;

public class HomeUI
{
    private final ClientBrowser _parentClientBrowser;

    // Physical pages
    private HomePage _mainPage;

    // UI entities
    private Deque<NewMessageUI> _newMessages = new LinkedList<NewMessageUI>();
    private EmailViewUI _emailViewUI;

    public HomeUI(ClientBrowser parentClientBrowser)
    {
        _parentClientBrowser = parentClientBrowser;
    }

    public HomePage getPage()
    {
        if (_mainPage == null)
        {
            _mainPage = new HomePage(_parentClientBrowser);
        }

        return _mainPage;
    }

    public EmailViewUI getEmailViewUI(Email e)
    {
        clickRowWithText(e.getSubject());
        _emailViewUI = new EmailViewUI(_parentClientBrowser);
        return _emailViewUI;
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

//    private Deque<NewMessageUI> getNewMessageUIs()
//    {
//        return _newMessages;
//    }

}
