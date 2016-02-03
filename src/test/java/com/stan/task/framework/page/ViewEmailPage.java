
package com.stan.task.framework.page;

import org.openqa.selenium.support.FindBy;

import com.stan.task.framework.ClientBrowser;
import com.stan.task.framework.control.Element;
import com.stan.task.framework.pagefactory.ExtendedPageFactory;
import com.stan.task.framework.pagefactory.ControlName;

public class ViewEmailPage extends AbstractPage
{
    // @TestName(testName = "To textbox")
    // @FindBy(name = "to")
    // private TextArea _toTxt;

    @ControlName(name = "Subject area")
    @FindBy(css = "h2.hP")
    private Element _subject;

    @ControlName(name = "Body area")
    @FindBy(css = "div.nH.aHU div[dir='ltr']")
    private Element _body;


    // public TextArea get_toTxt()
    // {
    // return _toTxt;
    // }

    public Element getSubject()
    {
        return _subject;
    }

    public Element getBody()
    {
        return _body;
    }

    public ViewEmailPage(ClientBrowser clientBrowser)
    {
        ExtendedPageFactory.initElements(clientBrowser, this);
    }
}
