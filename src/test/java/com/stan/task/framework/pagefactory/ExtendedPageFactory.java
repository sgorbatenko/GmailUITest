
package com.stan.task.framework.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import com.stan.task.framework.ClientBrowser;
import com.stan.task.framework.page.AbstractPage;



public class ExtendedPageFactory extends PageFactory
{

    public static void initElements(ClientBrowser browser, AbstractPage page)
    {
        final WebDriver driverRef = browser.getSeleniumWebDriver();

        ElementLocatorFactory factory;

        factory = new ExtendedElementLocatorFactory(driverRef);

        initElements(browser, factory, page);
    }

    public static void initElements(ClientBrowser browser, ElementLocatorFactory factory, AbstractPage page)
    {
        ExtendedPageFactory.initElements(new ExtendedFieldDecorator(browser, factory, page), page);
    }

}
