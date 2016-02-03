
package com.stan.task.framework.page;

import org.openqa.selenium.support.FindBy;

import com.stan.task.framework.ClientBrowser;
import com.stan.task.framework.control.Button;
import com.stan.task.framework.control.Element;
import com.stan.task.framework.pagefactory.ExtendedPageFactory;
import com.stan.task.framework.pagefactory.TestName;

public class SettingsPage extends AbstractPage
{
    @TestName(testName = "Language select")
    @FindBy(css = "select.a5p")
    private Element _langSelect;

    @TestName(testName = "Save button")
    @FindBy(css = "button[guidedhelpid='save_changes_button']")
    private Button _saveBtn;


    public Element getLangSelect()
    {
        return _langSelect;
    }

    public Button getSaveBtn()
    {
        return _saveBtn;
    }

    public SettingsPage(ClientBrowser clientBrowser)
    {
        ExtendedPageFactory.initElements(clientBrowser, this);
    }
}
