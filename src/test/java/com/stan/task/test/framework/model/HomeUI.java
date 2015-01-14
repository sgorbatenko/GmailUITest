
package com.stan.task.test.framework.model;

import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.control.grid.MainCurrencyGrid.Currency;
import com.stan.task.test.framework.page.HomePage;

public class HomeUI  // extends ApplicationUI
{
    private final ClientBrowser _parentClientBrowser;

    private HomePage _homePage;

    /**
     * Creates a new ApplicationUI instance.
     * 
     * @param parentClientBrowser
     *        the parent login ui
     */
    public HomeUI(ClientBrowser parentClientBrowser)
    {
        // super(parentClientBrowser);
        _parentClientBrowser = parentClientBrowser;
    }

    public HomePage getPage()
    {
        if (_homePage == null)
        {
            _homePage = new HomePage(_parentClientBrowser);
        }

        return _homePage;
    }

    public void selectCurrencyInGrid(Currency c)
    {
        getPage().getMainGrid().selectCurrency(c);
    }
}
