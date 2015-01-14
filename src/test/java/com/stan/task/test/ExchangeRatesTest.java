
package com.stan.task.test;


import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.stan.task.test.framework.control.grid.MainCurrencyGrid.Currency;
import com.stan.task.test.framework.datastructures.DataTable;
import com.stan.task.test.framework.model.HomeUI;
import com.stan.task.test.framework.utils.CalcUtils;

public class ExchangeRatesTest extends AbstractSeleniumTest
{
    protected static final int BUY_COLUMN_INDEX = 1;
    protected static final int SELL_COLUMN_INDEX = 2;
    private HomeUI _ui;
    private DataTable _currencyTable;
    private DataTable _sumTable;

    @BeforeClass
    public void setup()
    {
        _ui = new HomeUI(getClientBrowser());
        // _ui.open();
        _ui.selectCurrencyInGrid(Currency.EUR);
        _currencyTable = _ui.getPage().getMainGrid().getCurrencyDataTable();
        _sumTable = _ui.getPage().getMainGrid().getSumDataTable();
    }

    @Test
    void testAvgEuroCalc()
    {
        ArrayList buyList = _currencyTable.getColumnValues(1);
        ArrayList sellList = _currencyTable.getColumnValues(2);

        Assert.assertEquals(Double.parseDouble((String) _sumTable.getCellValue(2, "1")),
            CalcUtils.calcAvg(buyList), "Verifying average Sell EUR values. ");

        Assert.assertEquals(Double.parseDouble((String) _sumTable.getCellValue(2, "2")),
            CalcUtils.calcAvg(sellList), "Verifying average Buy EUR values. ");
    }
}
