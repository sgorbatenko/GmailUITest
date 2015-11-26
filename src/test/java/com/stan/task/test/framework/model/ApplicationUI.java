
package com.stan.task.test.framework.model;

import java.util.Deque;
import java.util.LinkedList;

import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.page.ApplicationPage;

public class ApplicationUI
{
    private final ClientBrowser _parentClientBrowser;

    // Physical pages
    private ApplicationPage _mainPage;

    // UI entities
    private Deque<NewMessageUI> _newMessages = new LinkedList<NewMessageUI>();

    /**
     * Creates a new ApplicationUI instance.
     * 
     * @param parentClientBrowser
     *        the parent login ui
     */
    public ApplicationUI(ClientBrowser parentClientBrowser)
    {
        _parentClientBrowser = parentClientBrowser;
    }

    /**
     * Gets the main page.
     * 
     * @return the main page.
     */
    public ApplicationPage getPage()
    {
        if (_mainPage == null)
        {
            _mainPage = new ApplicationPage(_parentClientBrowser);
        }

        // getParentClientBrowser().confirmFocus();

        return _mainPage;
    }

    /**
     * Checks if is user logged in.
     * 
     * @return true, if is user logged in
     */
    public boolean isUserLoggedIn()
    {
        return getPage().getGoogleAccountMenu().isDisplayed();

    }

    private Deque<NewMessageUI> getNewMessageUIs()
    {
        return _newMessages;
    }

    public NewMessageUI getNewMessageUI()
    {
        getPage().getComposeBtn().click();
        _newMessages.add(new NewMessageUI(_parentClientBrowser));
        return _newMessages.getLast();
    }


}
