
package com.stan.task.framework.model;

import com.stan.task.framework.ClientBrowser;
import com.stan.task.framework.data.Email;
import com.stan.task.framework.page.NewMessagePage;

public class NewMessageUI implements UiObject
{
    private final ClientBrowser _parentClientBrowser;

    private NewMessagePage _newMessagePage;


    public NewMessageUI(ClientBrowser parentClientBrowser)
    {
        // super(parentClientBrowser);
        _parentClientBrowser = parentClientBrowser;
    }

    @Override
    public NewMessagePage getPage()
    {
        if (_newMessagePage == null)
        {
            _newMessagePage = new NewMessagePage(_parentClientBrowser);
        }

        return _newMessagePage;
    }

    private void setToAddress(String toAddress)
    {
        if (toAddress != null)
        {
            _parentClientBrowser.waitForElementPresent(getPage().get_toTxt());
            getPage().get_toTxt().click();
            getPage().get_toTxt().append(toAddress); // replace(userName);
        }
        // getParentClientBrowser().waitForTextPresentInElement(By.name(USERNAME_INPUT_NAME),
    }

    private void setSubject(String subj)
    {
        if (subj != null)
        {
            _parentClientBrowser.waitForElementPresent(getPage().get_subjectTxt());
            getPage().get_subjectTxt().click();
            getPage().get_subjectTxt().append(subj);
        }
    }

    private void setBody(String content)
    {
        if (content != null)
        {
            // _parentClientBrowser.waitForElementPresent(getPage().get_bodyTxt());
            getPage().get_bodyTxt().click();
            getPage().get_bodyTxt().sendKeys(content);
        }
    }

    private void clickSendButton()
    {
        getPage().get_sendBtn().click();
    }

    public void sendEmail(Email e)
    {
        setToAddress(e.getToAddress());
        setSubject(e.getSubject());
        setBody(e.getBody());
        clickSendButton();
    }

    @Override
    public boolean isOpened()
    {
        return getPage().get_sendBtn().isDisplayed();
    }

    @Override
    public UiObject open()
    {
        if (!isOpened())
        {

        }
        return null;
    }
}
