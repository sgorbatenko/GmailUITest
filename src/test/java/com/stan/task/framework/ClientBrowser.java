
package com.stan.task.framework;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.stan.task.framework.control.Element;
import com.stan.task.framework.control.ElementLocator;
import com.stan.task.framework.exception.EnvironmentConfigurationException;
import com.stan.task.framework.model.HomeUI;
import com.stan.task.framework.model.LoginUI;
import com.stan.task.framework.utils.Wait;

/**
 * Client Web Browser, the parent object of all web applications and web controls
 */
public class ClientBrowser // extends AbstractClientBrowserChild
{
    /**
     * Web Browser's browser type
     */
    private final BrowserType _browserType;
    private HomeUI _applicationUI;
    private LoginUI _loginUI;

    /**
     * Optional browser application. If this is set, the browser application must implement BrowserApplicationUI interface
     */
    // private BrowserApplicationUI _browserApplicationUI;

    /**
     * Low-level selenium driver for this browser instance
     */
    private WebDriver _driver;

    /**
     * Constructs new instance of ClientBrowser.
     *
     * @param type
     *        - browser type from @link{BrowserType} enumeration.
     */
    public ClientBrowser(BrowserType type)
    {
        _browserType = type;
    }

    /**
     * Gets the main ui.
     *
     * @return the mainUI
     */
    public HomeUI getApplicationUI()
    {
        if (_applicationUI == null)
        {
            _applicationUI = new HomeUI(this);
        }
        return _applicationUI;
    }

    public LoginUI getLoginUI()
    {
        if (_loginUI == null)
        {
            _loginUI = new LoginUI(this);
        }
        return _loginUI;
    }

    /**
     * Open browser if it has not already been opened
     */
    public void open()
    {
        if (!isBrowserOpen())
        {
            try
            {
                // Constructs webdriver instance using provided options. Currently this is only browser name.
                _driver = WebDriverBuilder.getLocalDriver(_browserType);
                maximize();
            }
            catch (EnvironmentConfigurationException ex)
            {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * return true if browser still open (if it still has a valid driver)
     *
     * @return true if browser still open (if it still has a valid driver)
     */
    public boolean isBrowserOpen()
    {
        return _driver != null;
    }

    /**
     * Close browser
     */
    public void close()
    {
        getSeleniumWebDriver().close();


        getSeleniumWebDriver().quit();

        _driver = null;
    }

    /**
     * Focus the initial or main browser window (in case a popup has caused
     * window to lose focus)
     */
    public void focus()
    {
        try
        {
            _driver.switchTo().window(_driver.getWindowHandle());
        }
        catch (Throwable exception)
        {

        }
    }

    /**
     * Opens specified URL.
     *
     * @param url
     *        - URL to open.
     */
    public void gotoUrl(String url)
    {
        getSeleniumWebDriver().get(url);
    }

    /**
     * Return the current URL from the browser
     *
     * @return the current URL from the browser
     */
    public String getCurrentUrl()
    {
        return getSeleniumWebDriver().getCurrentUrl();
    }

    /**
     * refreshes the current web page
     */
    public void refreshPage()
    {
        getSeleniumWebDriver().navigate().refresh();
    }

    /**
     * Restore window to the current window size, resizing it smaller first to force a redraw
     */
    public void restore()
    {
        Window window = getSeleniumWebDriver().manage().window();

        Point windowPosition = window.getPosition();

        Dimension windowSize = window.getSize();

        Dimension smallerWindowSize = new Dimension(windowSize.width - 10, windowSize.height - 10);

        window.setPosition(windowPosition);

        // Size to slightly smaller size to force a redraw
        window.setSize(smallerWindowSize);

        // Size to expected size
        window.setSize(windowSize);
    }


    /**
     * Maximize the browser window on the screen
     */
    public void maximize()
    {
        getSeleniumWebDriver().manage().window().maximize();
    }

    /**
     * Set the browser window size on the screen
     *
     * @param screenWidth
     * @param screenHeight
     */
    public void setWindowSize(int screenWidth, int screenHeight)
    {
        Dimension screenResolution = new Dimension(screenWidth, screenHeight);
        getSeleniumWebDriver().manage().window().setSize(screenResolution);
    }

    /**
     * Set the browser window position on the screen
     *
     * @param x
     * @param y
     */
    public void setWindowPosition(int x, int y)
    {
        getSeleniumWebDriver().manage().window().setPosition(new Point(x, y));
    }

    /**
     * Send the key sequence to the browser
     *
     * @param keys
     * @param n
     */
    public void sendKeys(Keys keys, int n)
    {
        Actions action = new Actions(getSeleniumWebDriver());

        for (int i = 0; i < n; i++)
        {
            action.sendKeys(keys);
        }
        action.perform();
    }

    /**
     * Type a tab key into the browser
     */
    public void sendTab()
    {
        sendKeys(Keys.TAB, 1);
    }

    /**
     * Type a return key into the browser
     */
    public void sendReturn()
    {
        sendKeys(Keys.RETURN, 1);
    }

    /**
     * Get the low-level Selenium web Driver --- this should not be used for new coding
     *
     * @return the low-level Selenioum web Driver --- this should not be used for new coding
     */
    public WebDriver getSeleniumWebDriver()
    {
        if (isBrowserOpen())
        {
            return _driver;
        }
        open();
        return _driver;
    }

    /**
     * Gets the java script executor
     *
     * @return the java script executor
     */
    public JavascriptExecutor getJavaScriptExecutor()
    {
        return (JavascriptExecutor) getSeleniumWebDriver();
    }

    /**
     * Enum for Browser-based timeouts provided by getTimeout(), in seconds
     */
    public static enum BrowserBasedTime
    {
        /**
         * Wait for a control to exist, be visible, or be enabled in the UI, in seconds
         */
        WAIT_FOR_CONTROL_IN_UI,

        /**
         * Wait for submenu to popup after clicking parent menu item, in seconds
         */
        WAIT_FOR_SUBMENU_TO_APPEAR,

        /**
         * Wait for popup to appear, in seconds
         */
        WAIT_FOR_POPUP_TO_APPEAR,

        /**
         * Wait for popup to vanish, in seconds
         */
        WAIT_FOR_POPUP_TO_VANISH,

        /**
         * Wait for a short-length operation, 10 seconds or so on fastest browser
         */
        WAIT_FOR_SHORT_OPERATION,

        /**
         * Wait for a medium-length operation, 30 seconds or so on fastest browser
         */
        WAIT_FOR_MEDIUM_OPERATION,

        /**
         * Wait for a long program operation, 90 seconds or so on fastest browser
         */
        WAIT_FOR_LONG_OPERATION,

        /**
         * Wait for a very long program operation, 3 minutes or so on fastest browser
         */
        WAIT_FOR_VERY_LONG_OPERATION,

        /**
         * Wait for a extremely long program operation, 5 minutes or so on fastest browser
         */
        WAIT_FOR_EXTREMELY_LONG_OPERATION,

        /**
         * Wait for a extremely long program operation, 30 minutes or so on fastest browser
         */
        WAIT_FOR_INSANELY_LONG_OPERATION,

        /**
         * Wait time for an entity to close while checking for `irmation prompt if an editing entity
         */
        WAIT_FOR_ENTITY_CLOSE,

        /**
         * Tolerance for date comparisons in tests, in seconds
         */
        TEST_DATE_COMPARISON_TOLERANCE;
    }

    /**
     * Get instance based standard browser based time in seconds, which may vary depending on the browser type
     *
     * @param browserBasedTime
     * @return instance based standard browser based timeouts, which may vary depending on the browser type
     */

    public double getTimeInSeconds(BrowserBasedTime browserBasedTime)
    {
        double timeInSeconds;

        switch (browserBasedTime)
        {
            // wait for control to appear in a normal UI
            case WAIT_FOR_CONTROL_IN_UI:
                timeInSeconds = 5;
                break;

            // wait for submenu to pop up after menu clicked
            case WAIT_FOR_SUBMENU_TO_APPEAR:
                timeInSeconds = 5;
                break;

            // wait for dialog or popup message to appear
            case WAIT_FOR_POPUP_TO_APPEAR:
                timeInSeconds = 15;
                break;

            // wait for dialog or popup message to go away
            case WAIT_FOR_POPUP_TO_VANISH:
                timeInSeconds = 10;
                break;

            // Short program operation
            case WAIT_FOR_SHORT_OPERATION:
                timeInSeconds = 10;
                break;

            // Medium program operation
            case WAIT_FOR_MEDIUM_OPERATION:
                timeInSeconds = 30;
                break;

            // Long program operation
            case WAIT_FOR_LONG_OPERATION:
                timeInSeconds = 90;
                break;

            // Very Long program operation
            case WAIT_FOR_VERY_LONG_OPERATION:
                timeInSeconds = 3 * 60;
                break;

            // Extremely Long program operation
            case WAIT_FOR_EXTREMELY_LONG_OPERATION:
                timeInSeconds = 5 * 60;
                break;

            // Isanely Long program operation
            case WAIT_FOR_INSANELY_LONG_OPERATION:
                timeInSeconds = 30 * 60;
                break;

            // Wait for entity to close
            case WAIT_FOR_ENTITY_CLOSE:
                timeInSeconds = 30;
                break;

            // Give IE tests 15 min date comparison tolerance, other browsers 5 min
            case TEST_DATE_COMPARISON_TOLERANCE:
                timeInSeconds = 5 * 60;
                break;

            default:
                timeInSeconds = 5;
        }

        return timeInSeconds;
    }

    public void waitForElementPresent(final Element element, long timeout)
    {
        for (ElementLocator elementLocator : element.getLocatorsList())
        {
            final By locator = elementLocator.toSeleniumByLocator();

            try
            {
                WebDriverWait w = new WebDriverWait(getSeleniumWebDriver(), timeout);
                w.until(new BrowserExpectedConditions.ElementPresent(locator));

                return;
            }
            catch (TimeoutException e)
            {
                // Log.warn("unable to find element using one of the locators. trying next one..");
            }
        }
        throw new TimeoutException("unable to find element using locator");
    }

    /**
     * Wait for element present with default timeout 15 seconds. Low-level Legacy method - do not use for new coding
     *
     * @param element
     *        the element
     */
    public void waitForElementPresent(final Element element)
    {
        waitForElementPresent(element, Wait.TIMEOUT_INTERACTION);
    }


    public void waitForElementNotPresent(final Element element, long timeout)
    {
        for (ElementLocator elementLocator : element.getLocatorsList())
        {
            final By locator = elementLocator.toSeleniumByLocator();
            try
            {
                WebDriverWait w = new WebDriverWait(getSeleniumWebDriver(), timeout);
                w.until(new BrowserExpectedConditions.ElementNotPresent(locator));

                return;
            }
            catch (TimeoutException e)
            {

            }
        }
        throw new TimeoutException("Able to find element using locator");
    }

    public void waitForElementNotPresent(final Element element)
    {
        waitForElementNotPresent(element, Wait.TIMEOUT_INTERACTION);
    }


    /**
     * Wait for element enabled. Low-level Legacy method - do not use for new coding
     *
     * @param element
     *        the element
     * @param timeout
     *        the timeout
     */
    private void waitForElementEnabled(final Element element, long timeout)
    {
        for (ElementLocator elementLocator : element.getLocatorsList())
        {
            final By seleniumByLocator = elementLocator.toSeleniumByLocator();

            // if (LocatorType.XPATH.equals(elementLocator.getType()))
            // {
            // TestLogger.log("Wait for element enabled with xpath: " + elementLocator.getValue().toString());
            // }

            try
            {
                WebDriverWait w = new WebDriverWait(getSeleniumWebDriver(), timeout);
                w.until(new BrowserExpectedConditions.ElementIsEnabled(seleniumByLocator));

                return;
            }
            catch (TimeoutException e)
            {
                // Log.info("unable to find element using one of the locators. trying next one..");
            }
        }
        throw new TimeoutException("unable to find element using locator");
    }

    /**
     * Wait for element enabled with default timeout 15 seconds. Low-level Legacy method - do not use for new coding
     *
     * @param element
     *        the element
     */
    public void waitForElementEnabled(final Element element)
    {
        waitForElementEnabled(element, Wait.TIMEOUT_INTERACTION);
    }

    /**
     * Checks if element present by xpath. Low-level Legacy method - do not use for new coding
     *
     * @param xpath
     *        the target
     * @return true, if element present
     */
    public boolean isElementPresentByXpath(String xpath)
    {
        // TestLogger.log("Is element present by xpath: " + xpath);

        try
        {
            getSeleniumWebDriver().findElement(By.xpath(xpath));
            return true;
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }

    /**
     * Checks if element is present by css selector. Low-level Legacy method - do not use for new coding
     *
     * @param css
     * @return true, if element is present
     */
    public boolean isElementPresentByCss(String css)
    {
        try
        {
            getSeleniumWebDriver().findElement(By.cssSelector(css));
            return true;
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
    }



    /**
     * Low-level Legacy method - do not use for new coding
     *
     * @param target
     * @param timeout
     * @return true if element was found within the timeout
     */
    public boolean waitForElementPresentByCss(final String target, long timeout)
    {
        try
        {
            WebDriverWait w = new WebDriverWait(getSeleniumWebDriver(), timeout);
            w.until(new BrowserExpectedConditions.ElementPresent(By.cssSelector(target)));
            return true;
        }
        catch (TimeoutException e)
        {
            return false;
        }
    }

    /**
     * Low-level Legacy method - do not use for new coding
     *
     * @param target
     * @param timeout
     * @return true if element was found within the timeout
     */
    public boolean waitForElementPresentByXpath(final String target, long timeout)
    {
        try
        {
            WebDriverWait w = new WebDriverWait(getSeleniumWebDriver(), timeout);
            w.until(new BrowserExpectedConditions.ElementPresent(By.xpath(target)));
            return true;
        }
        catch (TimeoutException e)
        {
            return false;
        }
    }

    /**
     * Low-level Legacy method - do not use for new coding
     *
     * @param by
     * @param text
     */
    public void waitForTextPresentInElement(By by, String text)
    {
        WebDriverWait w = new Wait(getSeleniumWebDriver());
        w.until(org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementValue(by, text));
    }
}