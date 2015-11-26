
package com.stan.task.test.framework.control;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.stan.task.test.framework.page.Page;

public class TextArea extends Element // AbstractTextField
{
    private static final String LABEL_TAG_NAME = "label";
    private static final String TEXTAREA_TAG_NAME = "textarea";
    public static final String ID_ATTRIBUTE = "id";
    public static final String CLASS_ATTRIBUTE = "class";
    private static final String INVALID_CLASS_SUBSTRING = "x-form-invalid-field";
    private static final String REQUIRED_CLASS_SUBSTRING = "x-form-required-field";

    public TextArea(Page parentBrowserItem,
        ElementLocator elementLocator, String fieldControlName)
    {
        super(parentBrowserItem, elementLocator, fieldControlName);
    }

    public TextArea(Page parentBrowserItem,
        List<ElementLocator> elementLocators, String fieldControlName)
    {
        super(parentBrowserItem, elementLocators, fieldControlName);
    }

    public TextArea(Page parentBrowserItem, WebElement element,
        String controlName)
    {
        super(parentBrowserItem, element, controlName);
    }

    public boolean isRequired()
    {
        WebElement inputElement = getSeleniumWebElement(true);  // getInputElement();

        String classValue = inputElement.getAttribute(CLASS_ATTRIBUTE);

        return classValue.contains(REQUIRED_CLASS_SUBSTRING);
    }

    public boolean isInvalid()
    {
        WebElement inputElement = getSeleniumWebElement(true); // getInputElement();

        String classValue = inputElement.getAttribute(CLASS_ATTRIBUTE);

        return classValue.contains(INVALID_CLASS_SUBSTRING);
    }

    @Override
    public boolean isEnabled()
    {
        // We use different locators and we cannot always call
        // Ext.getCmp(arguments[0]) as it may fail
        // Method will try to find parent element first
        String elementID = getElementID();
        if (elementID.contains("-input")) // it can be -inputRow or inputEl
        {
            String parentID = elementID.substring(0,
                elementID.indexOf("-input"));
            String scriptGetDisabled = String.format(
                "return Ext.getCmp('%s').disabled", parentID);
            return !(Boolean) executeScript(scriptGetDisabled);
        }
        return !(Boolean) executeScript(
            "return Ext.getCmp(arguments[0]).disabled", elementID);
    }

    public String getText()
    {
        String text = getTextAreaElement().getAttribute(VALUE_ATTRIBUTE);
        return text;
    }

    public void clear()
    {
        // TestLogger.writeStep("Clear " + getDescription());

        getTextAreaElement().clear();
    }

    protected WebElement getTextAreaElement()
    {
        WebElement currentElement = getSeleniumWebElement(true);
        WebElement inputElement = null;
        if (TEXTAREA_TAG_NAME.equals(currentElement.getTagName()))
        {
            inputElement = currentElement;
        }
        else
        {
            inputElement = getSeleniumWebElement(true).findElement(By.tagName(TEXTAREA_TAG_NAME));
        }
        return inputElement;
    }

    public void append(String text)
    {
        // TestLogger.writeStep("Type text '" + text + "' into " + getDescription());

        // getParentClientBrowser().confirmFocus();

        getTextAreaElement().sendKeys(text);
    }

    public void replace(String text)
    {
        clear();
        append(text);
    }
}