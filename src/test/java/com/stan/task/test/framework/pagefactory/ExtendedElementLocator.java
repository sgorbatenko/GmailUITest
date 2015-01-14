
package com.stan.task.test.framework.pagefactory;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultElementLocator;

public class ExtendedElementLocator extends DefaultElementLocator
{

    private final boolean _isDisplayed;

    public ExtendedElementLocator(WebDriver driver, Field field)
    {
        super(driver, field);

        _isDisplayed = field.getAnnotation(Displayed.class) != null;
    }

    @Override
    public WebElement findElement()
    {
        if (!_isDisplayed)
        {
            try
            {
                return super.findElement();
            }
            catch (Throwable e)
            {
                throw new NoSuchElementException(
                    "Cannot locate a displayed element using " + toString() + " - Error: " + e.getMessage());
            }
        }

        List<WebElement> allElements = this.findElements();
        if (allElements == null || allElements.isEmpty())
        {
            throw new NoSuchElementException(
                "Cannot locate a displayed element using " + toString());
        }
        return allElements.get(0);
    }

    @Override
    public List<WebElement> findElements()
    {
        if (!_isDisplayed)
        {
            return super.findElements();
        }

        List<WebElement> elements = super.findElements();

        Iterator<WebElement> itr = elements.iterator();

        while (itr.hasNext())
        {
            WebElement element = itr.next();

            if (!element.isDisplayed())
            {
                itr.remove();
            }
        }

        return elements;
    }

}
