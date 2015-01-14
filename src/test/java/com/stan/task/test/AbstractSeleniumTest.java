
package com.stan.task.test;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.stan.task.test.framework.BrowserName;
import com.stan.task.test.framework.BrowserType;
import com.stan.task.test.framework.ClientBrowser;
import com.stan.task.test.framework.model.ApplicationUI;
import com.stan.task.test.framework.pagefactory.TestEnvironment;

/**
 * The AbstractSeleniumTest Class, base class for nearly all tests with common
 * setup and cleanup methods
 */
public abstract class AbstractSeleniumTest
{
    private ClientBrowser _browser;
    private ApplicationUI _applicationUI;

    private TestEnvironment _testEnvironment;


    @BeforeClass
    public void seleniumTestBeforeClass() throws Exception
    {
        // _applicationUI = newApplicationUI(BrowserName.fromString(browser),
        // version, screenWidth, screenHeight);
        _browser = getNewBrowserInstance(BrowserName.fromString("firefox"),
            "34", 200, 200);
        // _applicationUI = newApplicationUI(BrowserName.fromString("chrome"),
        // "26", screenWidth, screenHeight);
        // _applicationUI = newApplicationUI(BrowserName.fromString("ie"), "9",
        // screenWidth, screenHeight);

        _applicationUI = getClientBrowser().getApplicationUI();
    }

    /**
     * Gets the client browser.
     * 
     * @return the client browser.
     */
    public ClientBrowser getClientBrowser()
    {
        return _browser;
    }

    /**
     * Gets the test environment.
     * 
     * @return the test environment.
     */
    public TestEnvironment getTestEnvironment()
    {
        if (_testEnvironment == null)
        {
            _testEnvironment = TestEnvironment.getInstance();
        }
        return _testEnvironment;
    }



    /**
     * Gets the Application ui
     * 
     * @return the Application UI
     */
    protected ApplicationUI getApplicationUI()
    {
        return _applicationUI;
    }


    protected ClientBrowser getNewBrowserInstance(BrowserName browserName, String browserVersion)
    {
        ClientBrowser newClientBrowser = getNewBrowserInstance(browserName, browserVersion, 0, 0);
        return newClientBrowser;
    }

    protected ClientBrowser getNewBrowserInstance(BrowserName browserName, String browserVersion,
        int screenWidth, int screenHeight)
    {
        ClientBrowser newClientBrowser = getTestEnvironment().newClientBrowser(new BrowserType(browserName, browserVersion));
        newClientBrowser.gotoUrl(TestEnvironment.getAppUrl());
        newClientBrowser.setWindowPosition(0, 0);
        if (screenWidth == 0 || screenHeight == 0)
        {
            newClientBrowser.maximize();
        }
        else
        {
            newClientBrowser.setWindowSize(screenWidth, screenHeight);
        }
        return newClientBrowser;
    }


    @AfterClass
    public void teardown()
    {
        _browser.close();
    }

}