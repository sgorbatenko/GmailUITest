
package com.stan.task.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.stan.task.test.framework.model.LoginUI;

public class LoginTest extends AbstractSeleniumTest
{
    protected static final int BUY_COLUMN_INDEX = 1;
    protected static final int SELL_COLUMN_INDEX = 2;
    private LoginUI _loginUI;

    @BeforeClass
    public void setup()
    {
        _loginUI = getClientBrowser().getLoginUI();
        // _ui = new LoginUI(getClientBrowser());
        // _ui.open();
        // _ui.selectCurrencyInGrid(Currency.EUR);
        // _currencyTable = _ui.getPage().getMainGrid().getCurrencyDataTable();
        // _sumTable = _ui.getPage().getMainGrid().getSumDataTable();
    }

    @Test
    void testLogin()
    {
        _loginUI.login();

        // TODO: Verification should be added
    }
}
