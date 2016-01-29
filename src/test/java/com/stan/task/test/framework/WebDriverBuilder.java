
package com.stan.task.test.framework;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

import com.stan.task.test.framework.configuration.Environment;
import com.stan.task.test.framework.exception.EnvironmentConfigurationException;



public final class WebDriverBuilder
{
    // private static final Logger LOGGER = Logger.getLogger(WebDriverBuilder.class);
    private static final String SYSTEM_PROPERTY_FIREFOX_BIN = "firefoxBin";
    private static final String SYSTEM_PROPERTY_CHROME_BIN = "chromeBin";
    private static final String SYSTEM_PROPERTY_FIREBUG_ENABLED = "fbug";
    private static final String SYSTEM_PROPERTY_SELENIUM_IDE_ENABLED = "side";
    private static final String PLUGINS_DIR = "plugins";


    private WebDriverBuilder()
    {
    }

    static WebDriver getLocalDriver(BrowserType browser) throws EnvironmentConfigurationException
    {
        // LOGGER.info("Getting local driver for browser: " + browser);
        WebDriver driver;
        // if (!new File("./res").exists())
        // {
        // // copy 'res' dir from jar to current directory
        // try
        // {
        // ResourcesUtils ru = new ResourcesUtils();
        // ru.copy(WebDriverBuilder.class, "res/",
        // new File("./res"));
        // }
        // catch (IOException ex)
        // {
        // throw new EnvironmentConfigurationException(ex);
        // }
        // }

        switch (browser.getBrowserName())
        {
            case FIREFOX:
                driver = getFirefoxDriver();
                break;

            case IE:
                driver = getInternetExplorerDriver();
                break;

            case CHROME:
                driver = getChromeDriver();
                break;

            default:
                throw new EnvironmentConfigurationException(browser + " isn't supported");
        }

        return driver;
    }



    /**
     * Return a new Firefox profile with settings appropriate for the framework
     *
     * @param isRemoteDriver
     *        true if this is to be used as a profile for a remote driver
     * @return a new Firefox profile with settings appropriate for the framework
     */
    private static FirefoxProfile newFireFoxProfile()
    {
        FirefoxProfile profile = new FirefoxProfile();

        profile.setEnableNativeEvents(true);

        profile.setPreference("dom.max_chrome_script_run_time", 0);
        profile.setPreference("dom.max_script_run_time", 0);

        // Configuring default folder for downloading files
        File downloadsPath = new File(Environment.getDefaultBrowserDownloadsPath());

        profile.setPreference("browser.download.dir", downloadsPath.getAbsolutePath());
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream");

        return profile;
    }

    private static WebDriver getFirefoxDriver() throws EnvironmentConfigurationException
    {
        String firefoxBin = null;
        if (Environment.getFirefoxBinary() != null && Environment.getFirefoxBinary().isEmpty())
        {
            firefoxBin = System.getProperty(SYSTEM_PROPERTY_FIREFOX_BIN);
        }
        else
        {
            firefoxBin = Environment.getFirefoxBinary();
        }
        final Boolean firebugEnabled = Boolean.valueOf(System.getProperty(SYSTEM_PROPERTY_FIREBUG_ENABLED, "false"));
        final Boolean seleniumIDEEnabled = Boolean.valueOf(System.getProperty(SYSTEM_PROPERTY_SELENIUM_IDE_ENABLED, "false"));

        FirefoxProfile profile = newFireFoxProfile();

        if (firebugEnabled)
        {
            try
            {
                String path = WebDriverBuilder.class.getClassLoader()
                    .getResource(PLUGINS_DIR + "/firebug-1.9.2-fx.xpi")
                    .getPath();
                profile.addExtension(new File(path));
                profile.setPreference("extensions.firebug.showFirstRunPage", false);

                path = WebDriverBuilder.class.getClassLoader()
                    .getResource(PLUGINS_DIR + "/firefinder_for_firebug-1.2.2-fx.xpi")
                    .getPath();
                profile.addExtension(new File(path));
            }
            catch (IOException ex)
            {
                throw new EnvironmentConfigurationException(ex);
            }
        }

        if (seleniumIDEEnabled)
        {
            try
            {
                String path = WebDriverBuilder.class.getClassLoader()
                    .getResource(PLUGINS_DIR + "/selenium_ide-1.7.3-fx.xpi")
                    .getPath();
                profile.addExtension(new File(path));
            }
            catch (IOException ex)
            {
                throw new EnvironmentConfigurationException(ex);
            }
        }

        // ZG sometimes Firefox is started but webdriver cannot connect to it and throws the exception
        // it causes failures for all subsequent tests execution.
        // so here there are several attempts to start firefox and establish the connection to it.
        // It's also necessary to clean unmanaged firefoxes after all tests completed on the test environment
        WebDriver firefoxDriver = null;
        int attemptCount = 3;

        while (null == firefoxDriver && attemptCount > 0)
        {

            // LOGGER.info("Starting Firefox and refreshing the empty page, attempt_count = " + attemptCount);

            try
            {
                if (firefoxBin != null)
                {
                    firefoxDriver = new FirefoxDriver(new FirefoxBinary(new File(firefoxBin)), profile);
                }
                else
                {
                    firefoxDriver = new FirefoxDriver(profile);
                }

                firefoxDriver.navigate().refresh();
            }
            catch (RuntimeException ex)
            {
                firefoxDriver = null;

                // LOGGER.info("Failed Firefox start");
                // LOGGER.info(ex.getStackTrace());
            }

            attemptCount--;
        }

        return firefoxDriver;
    }

    private static ChromeDriverService getChromeDriverService()
    {
        Random random = new Random();
        final String chromeservicelog = Environment.getChromeDriverLogFile(((Long) (random.nextLong())).toString());

        String name = "chromedriver.exe";
        String chromePath = File.separator + name;
        String chromeEclipsePath = Environment.getResourcesPath() + chromePath;
        if (new File(chromePath).exists())
        {
            //Log.debug("Using chromedriver from " + chromePath);
        }
        else if (new File(chromeEclipsePath).exists())
        {
            chromePath = chromeEclipsePath;
            //Log.debug("Using chromedriver from " + chromePath);
        }
        else
        {
            // copy 'res' dir from jar to current directory
            URL url = WebDriverBuilder.class.getResource("/res/" + name);
            Assert.assertNotNull(url, "Unable to find chromedriver");
        }

        if (new File(chromePath).length() == 0)
        {
            //Log.debug("Waiting until unpack the resources from jar file");
            try
            {
                Thread.sleep(5000);
            }
            catch (InterruptedException ex)
            {
                //Log.debug(ex);
            }
        }

        ChromeDriverService service = new ChromeDriverService.Builder()
            .usingDriverExecutable(new File(chromePath))
            .usingAnyFreePort()
            .withLogFile(new File(chromeservicelog))
            .build();

        // chromedriver sometimes hangs on start and not usable any more.
        // in case of excpetion, try to stop it and start a new one
        boolean success = false;
        int attemptCount = 3;
        while (success == false && attemptCount >= 0)
        {

            try
            {
                service.start();
                success = true;
            }
            catch (WebDriverException ex)
            {
                success = false;
                service.stop();
                attemptCount--;
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }

        if (!success)
        {
            throw new WebDriverException("Timed out waiting for ChromeDriver server to start.");
        }

        return service;
    }

    private static WebDriver getChromeDriver()
    {
        ChromeDriverService service = getChromeDriverService();
        DesiredCapabilities chromeCap = getChromeDriverCapabilities();
        try
        {
            return new ChromeDriver(service, chromeCap);
        }
        catch (WebDriverException e)
        {
            service.stop();
            throw e;
        }
    }

    private static DesiredCapabilities getChromeDriverCapabilities()
    {
        DesiredCapabilities chromeCap = DesiredCapabilities.chrome();
        chromeCap.setCapability("chrome.verbose", true);
        chromeCap.setCapability("chrome.switches", Arrays.asList("--disable-translate"));

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_settings.multiple-automatic-downloads", 1);
        ChromeOptions options = new ChromeOptions();

        // options.setExperimentalOptions("prefs", prefs);

        chromeCap.setCapability(ChromeOptions.CAPABILITY, options);

        final String chromeBin = System.getProperty(SYSTEM_PROPERTY_CHROME_BIN);

        if (chromeBin != null)
        {
            chromeCap.setCapability("chrome.binary", chromeBin);
        }

        return chromeCap;
    }

    private static WebDriver getInternetExplorerDriver()
    {

        String name = "IEDriverServer.exe";
        String iePath = "res" + File.separator + name;
        String ieEclipsePath = Environment.getResourcesPath() + iePath;
        if (new File(iePath).exists())
        {
            //Log.debug("Using iedriver from " + iePath);
        }
        else if (new File(ieEclipsePath).exists())
        {
            iePath = ieEclipsePath;
            //Log.debug("Using iedriver from " + iePath);
        }
        else
        {
            // copy 'res' dir from jar to current directory
            URL url = WebDriverBuilder.class.getResource("/res/" + name);
            Assert.assertNotNull(url, "Unable to find iedriver in jar file");
        }

        if (new File(iePath).length() == 0)
        {
            //Log.debug("Waiting until unpack the resources from jar file");
            try
            {
                Thread.sleep(5000);
            }
            catch (InterruptedException ex)
            {
                //Log.debug(ex);
            }
        }
        String ieBinaryPath = new File(iePath).getAbsolutePath();
        System.setProperty("webdriver.ie.driver", ieBinaryPath);
        DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();

        // Do not set this, we need the error that all domains must be set to the same protected mode
        // ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

        WebDriver ie = new InternetExplorerDriver(ieCapabilities);
        ie.manage().deleteAllCookies();
        return ie;
    }
}
