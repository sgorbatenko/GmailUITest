
package com.stan.task.test.framework.pagefactory;

import java.lang.reflect.Field;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

public class ExtendedElementLocatorFactory implements ElementLocatorFactory
{
    private final WebDriver _driver;

    public ExtendedElementLocatorFactory(WebDriver driver)
    {
        this._driver = driver;
    }


    public ElementLocator createLocator(Field field)
    {
        return new ExtendedElementLocator(_driver, field);
    }

}
