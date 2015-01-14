
package com.stan.task.test.framework.pagefactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.control.Element;
import com.stan.task.test.framework.page.AbstractPage;

public class ExtendedFieldDecorator extends DefaultFieldDecorator
{

    private final WebDriver _driver;
    private final AbstractPage _parentPage;

    public ExtendedFieldDecorator(ClientBrowser browser, ElementLocatorFactory locatorFactory, AbstractPage parentPage)
    {
        super(locatorFactory);

        _driver = browser.getSeleniumWebDriver();
        _parentPage = parentPage;
    }

    @Override
    public Object decorate(ClassLoader loader, Field field)
    {
        Object result = super.decorate(loader, field);

        if (result == null)
        {
            ElementLocator locator = factory.createLocator(field);

            if (locator == null)
            {
                return null;
            }

            TestNameAnnotation testAnnotation = new TestNameAnnotation(field);
            String testName = testAnnotation.buildTestName();

            WebElement element = proxyForLocator(loader, locator);
            result = instantiateElement(element, field.getType(), testName);
        }
        if (result instanceof Element)
        {
            Annotations annotations = new Annotations(field);

            ((Element) result).addLocator(new com.stan.task.test.framework.control.ElementLocator(annotations.getLocatorType(),
                annotations.getValue()));
        }

        return result;
    }

    private Object instantiateElement(WebElement element, Class elementClass, String testName)
    {
        try
        {
            Constructor<?>[] constructors = elementClass.getConstructors();

            for (Constructor<?> constructor : constructors)
            {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                if (parameterTypes.length == 3 && parameterTypes[0].isAssignableFrom(AbstractPage.class)
                                && parameterTypes[1].isAssignableFrom(WebElement.class) && parameterTypes[2].isAssignableFrom(String.class))
                {
                    return constructor.newInstance(_parentPage, element, testName);
                }

            }

            return null;
        }
        catch (InstantiationException e)
        {
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e)
        {
            throw new RuntimeException(e);
        }
    }
}
