
package com.stan.task.test.framework.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.page.Page;



public class ExtendedPageFactory extends PageFactory
{

    public static void initElements(ClientBrowser browser, Page page)
    {
        final WebDriver driverRef = browser.getSeleniumWebDriver();

        ElementLocatorFactory factory;

        factory = new ExtendedElementLocatorFactory(driverRef);

        initElements(browser, factory, page);
    }

    public static void initElements(ClientBrowser browser, ElementLocatorFactory factory, Page page)
    {
        ExtendedPageFactory.initElements(new ExtendedFieldDecorator(browser, factory, page), page);
    }

}
