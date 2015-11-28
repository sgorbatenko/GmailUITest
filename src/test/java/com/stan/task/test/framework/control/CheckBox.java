
package com.stan.task.test.framework.control;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.stan.task.test.framework.page.Page;

public class CheckBox extends Element
{
    public static final String CHECKED_ATTRIBUTE = "aria-checked";
    private static final String TRUE = "true";

    public CheckBox(Page parentBrowserItem,
        ElementLocator elementLocator, String fieldControlName)
    {
        super(parentBrowserItem, elementLocator, fieldControlName);
    }

    public CheckBox(Page parentBrowserItem,
        List<ElementLocator> elementLocators, String fieldControlName)
    {
        super(parentBrowserItem, elementLocators, fieldControlName);
    }

    public CheckBox(WebElement element, String controlName)
    {
        super(element, controlName);
    }

    public boolean isChecked()
    {
        WebElement inputElement = getSeleniumWebElement(true);
        String classValue = inputElement.getAttribute(CHECKED_ATTRIBUTE);
        return classValue.contains(TRUE);
    }
}