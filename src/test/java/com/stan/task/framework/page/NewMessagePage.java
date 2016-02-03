
package com.stan.task.framework.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.stan.task.framework.ClientBrowser;
import com.stan.task.framework.control.TextArea;
import com.stan.task.framework.control.TextBox;
import com.stan.task.framework.pagefactory.ExtendedPageFactory;
import com.stan.task.framework.pagefactory.ControlName;

public class NewMessagePage extends AbstractPage
{
    @ControlName(name = "To textbox")
    @FindBy(name = "to")
    private TextArea _toTxt;

    @ControlName(name = "Subject textbox")
    @FindBy(name = "subjectbox")
    private TextBox _subjectTxt;

    @ControlName(name = "Body texarea")
    @FindBy(css = "div[aria-label='Message Body']")
    private WebElement _bodyTxt;

    @ControlName(name = "Send button")
    @FindBy(css = "div[aria-label*='Send']")
    private WebElement _sendBtn;

    public TextArea get_toTxt()
    {
        return _toTxt;
    }

    public TextBox get_subjectTxt()
    {
        return _subjectTxt;
    }

    public WebElement get_bodyTxt()
    {
        return _bodyTxt;
    }

    public WebElement get_sendBtn()
    {
        return _sendBtn;
    }

    public NewMessagePage(ClientBrowser clientBrowser)
    {
        ExtendedPageFactory.initElements(clientBrowser, this);
    }

}
