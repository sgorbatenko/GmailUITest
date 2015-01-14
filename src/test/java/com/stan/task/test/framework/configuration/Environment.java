
package com.stan.task.test.framework.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public final class Environment
{
    private static final String URI_JMX_CONNECTION = "service:jmx:rmi:///jndi/rmi://localhost:8083/jmxrmi";

    private static final String DEFAULT_BROWSER_DOWNLOADS_PATH = "test-output" + File.separator + "downloads";

    private static final String LOG_OUTPUT_FOLDER = "test-output" + File.separator + "logs" + File.separator;

    private static final String RESOURCES_FOLDER = "src" + File.separator
                    + "test"
                    + File.separator
                    + "resources"
                    + File.separator;

    // private static final String CHROME_DRIVER_BINARY = "res" + File.separator;

    // Set default value for App URL, can be overridden by setting in resource file on disk
    private static String _ApplicationUrl = "http://finance.i.ua"; //


    private static String _SeleniumUrl = "http://localhost:4444/wd/hub";
    private static String _ChromeServerUrl = "";
    private static Boolean _IsRemote = false;
    private static String _FirefoxBinary = "";

    static
    {
        Properties props = new Properties();
        InputStream inProps = props.getClass().getResourceAsStream("/web-tests-config.properties");
        if (inProps != null)
        {
            // The resource was found, replace defaults
            try
            {
                props.load(inProps);
                inProps.close();
                String host = "127.0.0.3";
                String port = "8080";
                String protocol = "http";
                String path = "footprints";

                if (props.containsKey("web.tests.app.protocol"))
                {
                    protocol = props.getProperty("web.tests.app.protocol");
                }
                if (props.containsKey("web.tests.app.host"))
                {
                    host = props.getProperty("web.tests.app.host");
                }
                if (props.containsKey("web.tests.app.port"))
                {
                    port = props.getProperty("web.tests.app.port");
                }
                if (props.containsKey("web.tests.app.path"))
                {
                    path = props.getProperty("web.tests.app.path");
                }
                _ApplicationUrl = String.format("%s://%s:%s/%s", protocol, host, port, path);
                if (props.containsKey("web.tests.isremote"))
                {
                    if (props.getProperty("web.tests.isremote").equalsIgnoreCase("true"))
                    {
                        _IsRemote = true;
                        _SeleniumUrl = props.getProperty("web.tests.remoteUrl");
                        _ChromeServerUrl = props.getProperty("webdriver.chrome.remote");
                    }
                }
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public static String getResourcesPath()
    {
        return RESOURCES_FOLDER;
    }

    private Environment()
    {
    }

    /**
     * @return Is the Selenium server running on another machine?
     */
    public static Boolean IsRemote()
    {
        return _IsRemote;
    }

    /**
     * Set application URL used for tests
     *
     * @param value
     */
    public static void setAppUrl(String value)
    {
        _ApplicationUrl = value;
    }

    /**
     * @return Application URL
     */
    public static String getAppUrl()
    {
        return _ApplicationUrl;
    }

    public static String getAppLogoutUrl()
    {
        return getAppUrl() + "/logout";
    }

    /**
     * @return remote Selenium server URL
     */
    public static String getSeleniumUrl()
    {
        return _SeleniumUrl;
    }

    /**
     * Gets the uri jmx connection.
     *
     * @return the uri jmx connection.
     */
    public static String getUriJmxConnection()
    {
        return URI_JMX_CONNECTION;
    }

    public static String getDefaultBrowserDownloadsPath()
    {
        return DEFAULT_BROWSER_DOWNLOADS_PATH;
    }

    public static String getFirefoxDriverLogFile(String inUniqueIdentifier)
    {
        File file = new File(Environment.getLogOutputFolder() + "firefoxdriver" + inUniqueIdentifier + ".log");
        return file.getAbsolutePath();
    }

    public static String getChromeDriverLogFile(String inUniqueIdentifier)
    {
        File file = new File(Environment.getLogOutputFolder() + "chromedriver" + inUniqueIdentifier + ".log");
        return file.getAbsolutePath();
    }

    public static String getLogOutputFolder()
    {
        return LOG_OUTPUT_FOLDER;
    }

    public static String getChromeServerUrl()
    {
        return _ChromeServerUrl;
    }

    public static String getFirefoxBinary()
    {
        return _FirefoxBinary;
    }

    public static void setFirefoxBinary(String firefoxBinary)
    {
        _FirefoxBinary = firefoxBinary;
    }



}
