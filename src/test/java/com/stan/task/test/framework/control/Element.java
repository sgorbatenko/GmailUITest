
package com.stan.task.test.framework.control;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.seleniumhq.jetty7.util.log.Log;

import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.exception.NoSuchControlException;
import com.stan.task.test.framework.page.AbstractPage;

/**
 * Base implementation of the web element or web control, with low level internal methods to find the control in the web browser and exposed set of high level
 * generic methods to operate on the control
 */
public class Element // extends AbstractPageModelItem implements ElementCommon
{
    public static final String ID_ATTRIBUTE = "id";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String STYLE_ATTRIBUTE = "style";

    private static final String SET_ATTRIBUTE_SCRIPT = "arguments[0].setAttribute('%s',arguments[1]);";
    private static final String FIRE_EVENT_SCRIPT = "arguments[0].fireEvent(arguments[1])";
    private static final String CHECKBOX_SUBCLASS_NAME = "x-form-checkbox";

    private List<ElementLocator> _locators = new ArrayList<ElementLocator>();

    // private static ClientBrowser _browser = new

    private WebElement _seleniumWebElement;

    private String _internalTestName;

    private AbstractPage _parentPage;

    private boolean _visibleMethodMustFindElement;

    /**
     * Implicit Selenium wait (1 second)
     */
    private static final long IMPLICIT_WAIT = 1;

    /**
     * Flag which indicates the element was created using page factory annotations in the original page class, or the control
     * was originally instantiated with a selenium web element
     */
    private boolean _pagefactoryInitializationFlag;

    public Element(AbstractPage parentPage,
        ElementLocator elementLocator, String fieldControlName)
    {
        _parentPage = parentPage;
        this.addLocator(elementLocator);
        setInternalTestName(fieldControlName);
        _pagefactoryInitializationFlag = false;
    }

    public Element(AbstractPage parentPage,
        List<ElementLocator> elementLocators, String controlName)
    {
        _parentPage = parentPage;
        this.addLocators(elementLocators);
        setInternalTestName(controlName);
        _pagefactoryInitializationFlag = false;
    }

    public Element(AbstractPage parentPage, WebElement element,
        String controlName)
    {
        _parentPage = parentPage;
        _seleniumWebElement = element;
        setInternalTestName(controlName);
        _pagefactoryInitializationFlag = true;
    }

    // public Element(Element parentBrowserItem,
    // ElementLocator elementLocator, String fieldControlName)
    // {
    // this.addLocator(elementLocator);
    // setInternalTestName(fieldControlName);
    // _pagefactoryInitializationFlag = false;
    // }
    //
    // public Element(Element parentBrowserItem,
    // List<ElementLocator> elementLocators, String controlName)
    // {
    // this.addLocators(elementLocators);
    // setInternalTestName(controlName);
    // _pagefactoryInitializationFlag = false;
    // }
    //
    // public Element(Element parentBrowserItem, WebElement element,
    // String controlName)
    // {
    //
    // _seleniumWebElement = element;
    // setInternalTestName(controlName);
    // _pagefactoryInitializationFlag = true;
    // }


    public List<ElementLocator> getLocatorsList()
    {
        return _locators;
    }


    public void addLocator(ElementLocator locator)
    {
        _locators.add(locator);
    }


    public void addLocators(List<ElementLocator> locatorsList)
    {
        for (ElementLocator locator : locatorsList)
        {
            addLocator(locator);
        }
    }

    protected WebDriver getSeleniumWebDriver()
    {
        return getParentClientBrowser().getSeleniumWebDriver();
    }


    private ClientBrowser getParentClientBrowser()
    {
        // TODO-SG Auto-generated method stub
        return null;
    }

    /**
     * For elements which have a specific query which includes the visible/exists state, set this to true which forces the visible method to always query for
     * the element
     *
     * @param value
     */
    public void setVisibleMethodMustFindElement(boolean value)
    {
        _visibleMethodMustFindElement = value;
    }

    public WebElement getSeleniumWebElement(boolean throwExceptionIfNotFound)
    {
        if (!_pagefactoryInitializationFlag || _seleniumWebElement == null)
        {
            if (_seleniumWebElement == null || isStale(_seleniumWebElement))
            {
                List<ElementLocator> locatorsList = getLocatorsList();

                StringBuilder fullLocator = new StringBuilder();
                for (ElementLocator locator : locatorsList)
                {
                    try
                    {
                        fullLocator.append(locator.getType().name());
                        fullLocator.append('=');
                        fullLocator.append(locator.getValue());

                        // Get selenium web element (no parent on page, throw exception if not found)
                        try
                        {
                            _seleniumWebElement = findSeleniumWebElement(null, locator, true);
                        }
                        catch (Throwable ex)
                        {
                            ex.printStackTrace();
                        }

                        return _seleniumWebElement;
                    }
                    catch (NoSuchElementException e)
                    {
                        _seleniumWebElement = null;
                    }
                }

                if (_seleniumWebElement == null || isStale(_seleniumWebElement))
                {
                    _seleniumWebElement = null;
                    if (throwExceptionIfNotFound)
                    {
                        // fail test with NoSuchControlException
                        throw new NoSuchControlException(
                            "Control was not found by specified locators " + fullLocator);
                    }
                }
            }
        }

        return _seleniumWebElement;
    }

    public boolean exists()
    {
        return getSeleniumWebElement(false) != null;
    }

    public boolean isVisible()
    {
        try
        {
            // If we were set to always re-query the selenium element on a visible method, clear the selenium element
            if (_visibleMethodMustFindElement)
            {
                _seleniumWebElement = null;
            }

            WebElement webElement = getSeleniumWebElement(false);

            if (webElement == null)
            {
                return false;
            }

            return webElement.isDisplayed()
                && webElement.getLocation().getX() > 0
                && webElement.getLocation().getY() > 0;
        }
        catch (NoSuchControlException e)
        {
            return false;
        }
        catch (StaleElementReferenceException e)
        {
            return false;
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
        catch (WebDriverException e)
        {
            return false;
        }
        catch (Throwable e)
        {
            Log.warn("Unexpected exception in Element.isVisible(), but continuing: " + e.getMessage());

            return false;
        }
    }


    public String getCssValue(String propertyName)
    {
        return getSeleniumWebElement(true).getCssValue(propertyName);
    }


    public boolean hasAttribute(String attributeName)
    {
        return getSeleniumWebElement(true).getAttribute(attributeName) != null;
    }

    public String getText()
    {

        WebElement webElement = getSeleniumWebElement(true);

        String text = webElement.getText();

        return text;
    }


    public String getElementID()
    {
        return getAttributeValue(ID_ATTRIBUTE);
    }

    public String getAttributeValue(String attributeName)
    {
        return getSeleniumWebElement(true).getAttribute(attributeName);
    }

    protected Object executeScript(String script, Object... args)
    {
        return ((JavascriptExecutor) getSeleniumWebDriver()).executeScript(script, args);
    }

    public void click()
    {
        // use getSeleniumWebElement() here
        _seleniumWebElement.click();
    }

    //
    // /**
    // * Implementor method to click the element
    // */
    // protected void clickImpl()
    // {
    // getParentClientBrowser().setActionUrl(getSeleniumWebDriver().getCurrentUrl());
    //
    // getParentClientBrowser().confirmFocus();
    //
    // WebElement seleniumWebElement = null;
    //
    // try
    // {
    // seleniumWebElement = getSeleniumWebElement(true);
    // }
    // catch (Throwable exception)
    // {
    // TestLogger.fail("Can't click " + getDescription() + " - there was an error finding the control in the browser",
    // exception);
    // }
    //
    // if (getElementID().contains("check"))
    // {
    // try
    // {
    // seleniumWebElement.findElement(By.tagName("input")).click();
    //
    // }
    // catch (Throwable e)
    // {
    // try
    // {
    // seleniumWebElement.click();
    // }
    // catch (Throwable exception)
    // {
    // try
    // {
    // Log.info("Error clicking control - waiting ten seconds and trying again in case control was covered by temporary popup status. Error message: "
    // + exception.getMessage());
    //
    // Testing.sleep(10000);
    //
    // seleniumWebElement.click();
    // }
    // catch (Throwable exception2)
    // {
    // TestLogger.fail("Could not click after trying several times control " + getDescription(), exception2);
    // }
    // }
    // }
    // }
    // else
    // {
    // try
    // {
    // WebElement input = seleniumWebElement.findElement(By.tagName("input"));
    //
    // if (input == null)
    // {
    // seleniumWebElement.click();
    // }
    // else
    // {
    //
    // if (input.getAttribute(CLASS_ATTRIBUTE).contains(CHECKBOX_SUBCLASS_NAME))
    // {
    // input.click();
    // }
    // else
    // {
    // seleniumWebElement.click();
    // }
    // }
    // }
    // catch (Throwable e)
    // {
    // try
    // {
    // seleniumWebElement = getSeleniumWebElement(true);
    // }
    // catch (Throwable exception)
    // {
    // TestLogger.fail("Can't click " + getDescription()
    // + " - there was an error finding the control in the browser a second time",
    // exception);
    // }
    //
    // try
    // {
    // seleniumWebElement.click();
    // }
    // catch (Throwable exception)
    // {
    // TestLogger.log("Error clicking control " + getDescription()
    // + " - waiting ten seconds and trying again in case control was covered by temporary popup status. Error message: "
    // + exception.getMessage());
    //
    // try
    // {
    // seleniumWebElement = getSeleniumWebElement(true);
    // }
    // catch (Throwable nestedException)
    // {
    // TestLogger.fail("Can't click " + getDescription()
    // + " - there was an error finding the control in the browser a third time",
    // nestedException);
    // }
    //
    // Testing.sleep(10000);
    //
    // try
    // {
    // seleniumWebElement.click();
    // }
    // catch (Throwable exception2)
    // {
    // TestLogger.fail("Could not click after trying several times control " + getDescription(), exception2);
    // }
    // }
    // }
    // }
    //
    // // logging click time and control description to Client Performance pack
    // getParentClientBrowser().setActionTime();
    // getParentClientBrowser().setControlDescription(getDescription());
    //
    // // Do not confirm focus any more, with newer selenium on IE this handles modal dialogs (which it should not)
    // // getParentClientBrowser().confirmFocus();
    // }

    /**
     * @return the internalTestName
     */
    protected String getInternalTestName()
    {
        return _internalTestName;
    }

    /**
     * @param internalTestName
     *        the internalTestName to set
     */
    protected void setInternalTestName(String internalTestName)
    {
        _internalTestName = internalTestName;
    }

    private boolean isStale(WebElement element)
    {
        if (element == null)
        {
            return false;
        }
        try
        {
            // We are setting timeout to 0 to get fast response on our
            // StaleElementReferenceException check.
            getSeleniumWebDriver().manage().timeouts()
                .implicitlyWait(0, TimeUnit.SECONDS);
            element.getTagName();

        }
        catch (StaleElementReferenceException e)
        {
            return true;
        }
        finally
        {
            getSeleniumWebDriver()
                .manage()
                .timeouts()
                .implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
        }
        return false;
    }



    public boolean isEnabled()
    {
        return getSeleniumWebElement(true).isEnabled();
    }


    public boolean isElementPresent(String xpath)
    {
        return isElementPresentBy(By.xpath(xpath));
    }

    public boolean isElementPresentBy(By locator)
    {
        try
        {
            getSeleniumWebElement(true).findElement(locator);
            return true;
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }


    /**
     * Find child Selenium web Element of the control
     *
     * @param seleniumByLocator
     *        the locator for the Child Selenium web Element
     * @return a Selenium WebElement, or null if control element or child
     *         element not found
     */
    protected WebElement findChildSeleniumWebElement(By seleniumByLocator)
    {
        // if (ByXPath.class.isInstance(seleniumByLocator))
        // {
        // TestLogger.log("Control findChildSeleniumWebElement() called with xpath: " + seleniumByLocator.toString());
        // }

        try
        {
            WebElement webElement = getSeleniumWebElement(false);

            if (webElement == null)
            {
                return null;
            }

            return webElement.findElement(seleniumByLocator);
        }
        catch (NoSuchElementException e)
        {
            return null;
        }
    }

    /**
     * Find list of child elements.
     *
     * @param seleniumByLocator
     *        the Selenium By Locator
     * @return a List of Selenium Web Elements, or null if control element or
     *         child elements not found
     */
    protected List<WebElement> findChildSeleniumWebElements(By seleniumByLocator)
    {
        // if (ByXPath.class.isInstance(seleniumByLocator))
        // {
        // TestLogger.log("Control findChildSeleniumWebElements() called with xpath: " + seleniumByLocator.toString());
        // }

        try
        {
            WebElement webElement = getSeleniumWebElement(false);

            if (webElement == null)
            {
                return null;
            }

            return webElement.findElements(seleniumByLocator);
        }
        catch (NoSuchElementException e)
        {
            return new ArrayList<WebElement>();
        }
    }

    public List<WebElement> GetElementsByCSS(String css)
    {
        List<WebElement> elements = findChildSeleniumWebElements(By.cssSelector(css));
        return elements;
    }

    /**
     * Perform low-level selenium search for a single matching element
     *
     * @param parentSeleniumWebElements
     *        optional list of parent elements to search in, if none provided the search includes all browser elements
     * @param locator
     *        Selenium By Locator to use in the search
     * @param throwExceptionIfNotFound
     *        If true, throw an exception if element is not found, if false simply return null if element is not found
     * @return the matching selenium web element, or null if throwExceptionIfNotFound = false
     * @throws Throwable
     */
    private WebElement findSeleniumWebElement(
        List<WebElement> parentSeleniumWebElements,
        ElementLocator locator,
        boolean throwExceptionIfNotFound) throws Throwable
    {
        // if (seleniumByLocator instanceof ByXPath)
        // {
        // TestLogger.log("Xpath locator used in ClientBrowser.findSeleniumWebElement(): " + seleniumByLocator);
        // }

        if (parentSeleniumWebElements.size() == 0)
        {
            try
            {
                return getSeleniumWebDriver().findElement(locator.toSeleniumByLocator());
            }
            catch (Throwable exception)
            {
                if (throwExceptionIfNotFound)
                {
                    throw exception;
                }
            }

            return null;
        }

        WebElement foundElement;

        for (WebElement parentSeleniumWebElement : parentSeleniumWebElements)
        {
            try
            {
                foundElement = parentSeleniumWebElement.findElement(locator.toSeleniumByLocator());

                if (foundElement != null)
                {
                    return foundElement;
                }
            }
            catch (Throwable exception)
            {
                if (throwExceptionIfNotFound && parentSeleniumWebElements.size() == 1)
                {
                    throw exception;
                }
            }
        }

        if (throwExceptionIfNotFound)
        {
            throw new NoSuchElementException("No elements were found for query '" + locator + "'");
        }

        return null;
    }

    /**
     * Find child Selenium web Element of the control that is displayed
     *
     * @param seleniumByLocator
     *        the locator for the Child Selenium web Element
     * @return a Selenium WebElement, or null if control element not found,
     *         child element not found, or child element not displayed
     */
    protected WebElement findDisplayedChildSeleniumWebElement(By seleniumByLocator)
    {
        try
        {
            WebElement childElement = findChildSeleniumWebElement(seleniumByLocator);

            if (childElement == null)
            {
                return null;
            }

            if (!Boolean.valueOf(childElement.isDisplayed()))
            {
                return null;
            }

            return childElement;
        }
        catch (Throwable exception)
        {
            return null;
        }
    }


    /**
     * Return True if this control has a child HTML element with the exact text specified
     *
     * @param tag
     * @param exactText
     * @return True if this control has a child HTML element with the exact text specified
     */
    public boolean hasChildElementWithExactText(String tag, String exactText)
    {
        String xpath = String.format(".//%s[text()='%s']", tag, exactText);

        return findChildSeleniumWebElement(By.xpath(xpath)) != null;
    }


    /**
     * Moves the mouse over the web element without logging or waiting for it to exist
     */
    public void mouseOverNoWait()
    {
        WebElement element = getSeleniumWebElement(true);

        new Actions(getParentClientBrowser().getSeleniumWebDriver()).moveToElement(element).perform();
    }
}
