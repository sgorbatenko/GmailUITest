
package com.stan.task.test.framework;


public enum BrowserName
{
    IE("ie"),
    FIREFOX("firefox"),
    CHROME("chrome");

    private String _name;

    private BrowserName(String name)
    {
        _name = name;
    }

    @Override
    public String toString()
    {
        return _name;
    }

    public static BrowserName fromString(String name) throws Exception
    {
        if (name.toLowerCase().contains("chrome"))
        {
            return BrowserName.CHROME;
        }

        if (name.toLowerCase().contains("firefox"))
        {
            return BrowserName.FIREFOX;
        }

        if (name.toLowerCase().contains("ie"))
        {
            return BrowserName.IE;
        }

        throw new Exception("Unsupported Browser Type: " + name);
    }
}
