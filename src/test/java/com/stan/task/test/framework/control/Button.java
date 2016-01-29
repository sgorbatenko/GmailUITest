
package com.stan.task.test.framework.control;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.stan.task.test.framework.page.AbstractPage;

public class Button extends Element
{
    public Button(AbstractPage parentBrowserItem,
        ElementLocator elementLocator, String fieldControlName)
    {
        super(parentBrowserItem, elementLocator, fieldControlName);
    }

    public Button(AbstractPage parentBrowserItem,
        List<ElementLocator> elementLocators, String fieldControlName)
    {
        super(parentBrowserItem, elementLocators, fieldControlName);
    }

    public Button(WebElement element, String controlName)
    {
        super(element, controlName);
    }
}
