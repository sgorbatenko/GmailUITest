
package com.stan.task.test.framework;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.stan.task.test.framework.control.Element;
import com.stan.task.test.framework.utils.Wait;



public final class BrowserExpectedConditions
{
    private BrowserExpectedConditions()
    {
    }

    /**
     * The ElementPresent Class.
     *
     * @param condition
     * @param driver
     */
    @SuppressWarnings("unchecked")
    public static void waitFor(ExpectedCondition condition, WebDriver driver)
    {
        WebDriverWait w = new Wait(driver, Wait.Length.Long);
        w.until(condition);
    }

    public static class ElementPresent implements ExpectedCondition<WebElement>
    {
        private final By _locator;

        /**
         * Creates a new ElementPresent instance.
         *
         * @param locator
         *        the locator
         */
        public ElementPresent(By locator)
        {
            this._locator = locator;

        }

        public WebElement apply(WebDriver driver)
        {

            return driver.findElement(_locator);
        }
    }

    public static class ElementNotPresent implements ExpectedCondition<Boolean>
    {

        private final By _locator;

        /**
         * Creates a new ElementPresent instance.
         *
         * @param locator
         *        the locator
         */
        public ElementNotPresent(By locator)
        {
            this._locator = locator;

        }


        public Boolean apply(WebDriver driver)
        {
            try
            {
                driver.findElement(_locator);
                return false;
            }
            catch (NoSuchElementException e)
            {
                return true;
            }
        }
    }

    public static class ElementIsDisabled implements ExpectedCondition<Boolean>
    {

        private final By _locator;

        /**
         * Creates a new ElementPresent instance.
         *
         * @param locator
         *        the locator
         */
        public ElementIsDisabled(By locator)
        {
            this._locator = locator;

        }

        public Boolean apply(WebDriver driver)
        {
            WebElement el = driver.findElement(_locator);
            if (el.getAttribute(Element.CLASS_ATTRIBUTE).contains("x-btn-disabled"))
            {
                return true;
            }
            return false;
        }
    }

    public static class ElementIsEnabled implements ExpectedCondition<Boolean>
    {

        private final By _locator;

        /**
         * Creates a new ElementPresent instance.
         *
         * @param locator
         *        the locator
         */
        public ElementIsEnabled(By locator)
        {
            this._locator = locator;

        }

        public Boolean apply(WebDriver driver)
        {
            WebElement el = driver.findElement(_locator);
            if (el.getAttribute(Element.CLASS_ATTRIBUTE).contains("x-btn-disabled"))
            {
                return false;
            }
            return true;
        }
    }

    public static class JavaScriptObjectLoaded implements ExpectedCondition<Boolean>
    {
        private final String _javaScript;
        private final Object[] _args;

        public JavaScriptObjectLoaded(String javaScript, Object... args)
        {
            this._javaScript = javaScript;
            this._args = args;
        }


        public Boolean apply(WebDriver driver)
        {
            String result = ((JavascriptExecutor) driver).executeScript(this._javaScript.toString(), _args).toString();
            return !("null".equals(result) | "undefined".equals(result));
        }
    }

    /**
     * Waits while number of windows not equals to number. Function uses
     * driver.getWindowHandles() method
     */

    public static class NumberOfWindowsEquals implements ExpectedCondition<Boolean>
    {
        private int _number;

        public NumberOfWindowsEquals(int number)
        {
            _number = number;
        }

        public Boolean apply(WebDriver driver)
        {
            return driver.getWindowHandles().size() == _number;
        }
    }
}