
package com.stan.task.test.framework;

import java.net.URL;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ScreenshotRemoteWebdriver extends RemoteWebDriver
{
    // BEGIN: constants


    // END: constants

    // BEGIN: fields, getters and setters

    public ScreenshotRemoteWebdriver(URL url, DesiredCapabilities capabilities)
    {
        super(url, capabilities);
    }

    // END: fields, getters and setters

    // BEGIN: construction


    // END: construction

    // BEGIN: methods


    public <X> X getScreenshotAs(OutputType<X> target)
    {
        if ((Boolean) getCapabilities().getCapability(CapabilityType.TAKES_SCREENSHOT))
        {
            String base64Str =
                execute(DriverCommand.SCREENSHOT).getValue().toString();
            return target.convertFromBase64Png(base64Str);
        }
        return null;

    }


    // END: methods
}
