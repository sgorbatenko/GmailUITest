
package com.stan.task.framework;

public class BrowserType
{
    private BrowserName _browserName;
    private String _browserVersion;

    public BrowserType(BrowserName browserName, String version)
    {
        _browserName = browserName;
        _browserVersion = version;
    }

    public BrowserName getBrowserName()
    {
        return _browserName;
    }

    public String getBrowserVersion()
    {
        return _browserVersion;
    }

    @Override
    public String toString()
    {
        return "Type: " + _browserName.toString() + " Version: " + _browserVersion.toString();
    }
}