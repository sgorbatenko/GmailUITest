
package com.stan.task.test.framework.page;

import org.openqa.selenium.support.FindBy;

import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.control.Element;
import com.stan.task.test.framework.pagefactory.ExtendedPageFactory;
import com.stan.task.test.framework.pagefactory.TestName;

public class ViewEmailPage extends AbstractPage
{
    // @TestName(testName = "To textbox")
    // @FindBy(name = "to")
    // private TextArea _toTxt;

    @TestName(testName = "Subject area")
    @FindBy(css = "h2.hP")
    private Element _subject;

    @TestName(testName = "Body area")
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
