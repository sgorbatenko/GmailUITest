
package com.stan.task.test.framework.model;

import org.openqa.selenium.support.ui.Select;

import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.page.SettingsPage;

public class SettingsUi implements UiObject
{

    private SettingsPage _settingsPage;
    private ClientBrowser _parentClientBrowser;

    @Override
    public SettingsPage getPage()
    {
        if (_settingsPage == null)
        {
            _settingsPage = new SettingsPage(_parentClientBrowser);
        }

        return _settingsPage;
    }

    public SettingsUi(ClientBrowser parentClientBrowser)
    {
        // super(parentClientBrowser);
        _parentClientBrowser = parentClientBrowser;
    }

    @Override
    public boolean isOpened()
    {
        _parentClientBrowser.waitForElementNotPresent(getPage().getLangSelect());
        if (getPage().getLangSelect().isVisible())
        {
            return true;
        }
        return false;
    }

    @Override
    public SettingsUi open()
    {
        if (!isOpened())
        {
            _parentClientBrowser.gotoUrl("https://mail.google.com/mail/u/0/#settings/general");
        }
        return this;
    }

    public void setLanguage(String lang)
    {
        new Select(getPage().getLangSelect().getSeleniumWebElement(true)).selectByVisibleText(lang);
    }

}
