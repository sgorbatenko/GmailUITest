
package com.stan.task.test.framework.page;

import org.openqa.selenium.support.FindBy;

import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.control.grid.MainCurrencyGrid;
import com.stan.task.test.framework.pagefactory.ExtendedPageFactory;
import com.stan.task.test.framework.pagefactory.TestName;

public class HomePage implements AbstractPage
{
    @TestName(testName = "Currency Main Grid")
    @FindBy(css = "div#feMain2 .local_table")
    private MainCurrencyGrid _mainGrid;

    public HomePage(ClientBrowser clientBrowser)
    {
        ExtendedPageFactory.initElements(clientBrowser, this);
    }


    public MainCurrencyGrid getMainGrid()
    {
        return _mainGrid;
    }


}
