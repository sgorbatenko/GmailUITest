
package com.stan.task.framework.pagefactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.stan.task.framework.BrowserName;
import com.stan.task.framework.BrowserType;
import com.stan.task.framework.ClientBrowser;

/**
 * The Test Environment class is responsible for providing test cases and other
 * classes with external environment data and global data
 * 
 */
public final class TestEnvironment // implements Testable
{
    private final List<ClientBrowser> _openClientBrowsers = Collections.synchronizedList(new ArrayList<ClientBrowser>());
    private static TestEnvironment _INSTANCE;

    private static String _AppUrl = "http://gmail.com";

//    // Container providers
//    private AddressBookProvider _addressBookProvider;
//    private CmdbProvider _cmdbProvider;
//    private KnowledgeBaseProvider _knowledgeBaseProvider;


    /**
     * Constructs instance of TestEnvironment.
     * 
     * @param parent
     *        - parent object
     */
    private TestEnvironment()
    {
    }

    /**
     * Static method for constructing TestEnvironment object.
     * 
     * @return TestEnvironment instance.
     */
    public static TestEnvironment getInstance()
    {
        if (_INSTANCE == null)
        {
            _INSTANCE = new TestEnvironment();
        }
        return _INSTANCE;
    }

    /**
     * Constructs default browser and add it to browser collection. If default
     * browser were already initialized - just return it without creating new
     * instance.
     * 
     * @return - default browser.
     */
    public ClientBrowser getDefaultClientBrowser()
    {
        return newClientBrowser(new BrowserType(BrowserName.FIREFOX, "10.0"));
    }

    /**
     * Creates new browser of specified type and puts it into browser's
     * collection.
     * 
     * @param type
     *        - browser type, that should be created.
     * @return - client browser instance.
     */
    public ClientBrowser newClientBrowser(BrowserType type)
    {
        ClientBrowser browser = new ClientBrowser(type);
        browser.open();
        browser.getSeleniumWebDriver().switchTo().window(browser.getSeleniumWebDriver().getWindowHandle());
        getOpenClientBrowsers().add(browser);
        return browser;
    }

    /**
     * Getter for opened browser's collection.
     * 
     * @return - opened browsers.
     */
    public List<ClientBrowser> getOpenClientBrowsers()
    {
        // We don't checking actual state of browsers as there is no webdriver's internal method to do so.
        return _openClientBrowsers;
    }

    /**
     * Closes all opened browsers.
     */
    public void closeAllBrowsers()
    {
        for (int i = 0; i < _openClientBrowsers.size(); i++)
        {
            if (_openClientBrowsers.get(i).isBrowserOpen())
            {
                _openClientBrowsers.get(i).close();
            }
        }
    }

    /**
     * @return Footprints application URL
     */
    public static String getAppUrl()
    {
        return _AppUrl;
    }

    /*
     * public LinkProvider getLinkProvider()
     * {
     * if (_linkProvider == null)
     * {
     * _linkProvider = new LinkProvider();
     * }
     * return _linkProvider;
     * }
     * }
     */
}