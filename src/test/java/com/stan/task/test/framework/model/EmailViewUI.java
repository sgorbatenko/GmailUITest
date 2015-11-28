
package com.stan.task.test.framework.model;

import org.testng.Assert;

import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.data.Email;
import com.stan.task.test.framework.page.ViewEmailPage;

public class EmailViewUI implements UiObject
{
    private final ClientBrowser _parentClientBrowser;

    private ViewEmailPage _viewEmailPage;

    public EmailViewUI(ClientBrowser parentClientBrowser)
    {
        // super(parentClientBrowser);
        _parentClientBrowser = parentClientBrowser;
    }

    public ViewEmailPage getPage()
    {
        if (_viewEmailPage == null)
        {
            _viewEmailPage = new ViewEmailPage(_parentClientBrowser);
        }

        return _viewEmailPage;
    }

    public void verifyEmailSent(Email e)
    {
        Assert.assertTrue(
            getPage().getSubject().getText().equals(e.getSubject()));
        Assert.assertTrue(
            getPage().getBody().getText().equals(e.getBody()));

    }
}
