
package com.stan.task.test.framework.control.grid;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.stan.task.test.framework.control.ElementLocator;
import com.stan.task.test.framework.datastructures.DataTable;
import com.stan.task.test.framework.page.AbstractPage;

public class MainCurrencyGrid extends SimpleGrid
{

    public static final String QUERY_ALL_ROWS_CSS = "div#feMain2 .local_table tr:not(.sum)";
    public static final String QUERY_SUM_ROWS_CSS = "div#feMain2 .local_table .sum";

    public MainCurrencyGrid(AbstractPage parentBrowserItem, ElementLocator elementLocator, String fieldControlName)
    {
        super(parentBrowserItem, elementLocator, fieldControlName);
    }

    public MainCurrencyGrid(AbstractPage parentBrowserItem, List<ElementLocator> elementLocators, String fieldControlName)
    {
        super(parentBrowserItem, elementLocators, fieldControlName);
    }

    public MainCurrencyGrid(AbstractPage parentBrowserItem, WebElement element, String controlName)
    {
        super(parentBrowserItem, element, controlName);
    }

    public void selectCurrency(Currency cur)
    {
        String locator = cur.getCurrencyXpath();

        WebElement btn = findChildSeleniumWebElement(By.xpath(locator));

        btn.click();
    }

    public DataTable getCurrencyDataTable()
    {
        return getTable(QUERY_ALL_ROWS_CSS);
    }

    public DataTable getSumDataTable()
    {
        return getTable(QUERY_SUM_ROWS_CSS);
    }

    public enum Currency
    {
        USD("USD"),
        EUR("EUR"),
        RUB("RUB");

        private final String name;

        private Currency(String value)
        {
            this.name = value;
        }

        public String getCurrencyName()
        {
            return name;
        }

        public String getCurrencyXpath()
        {
            return "//li/i/a[.='" + getCurrencyName() + "']";
        }
    }
}
