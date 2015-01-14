
package com.stan.task.test.framework.control;

import org.openqa.selenium.By;


public class ElementLocator
{
    private LocatorType _locatorType;
    private String _selectorValue;
    private String _searchText;

    /**
     * Constructor for elements except for those with locator types CSS_AND_CONTAINS_TEXT and CSS_AND_TEXT_EQUALS
     *
     * @param locatorType
     * @param value
     */
    public ElementLocator(LocatorType locatorType, String value)
    {
        // if (locatorType != null)
        // {
        // switch (locatorType)
        // {
        // case XPATH:
        // TestLogger.log("Xpath locator: " + value.toString());
        // break;
        // default:
        // break;
        // }
        // }

        setType(locatorType);
        setValue(value);
        setSearchText(null);
    }

    public void setType(LocatorType type)
    {
        _locatorType = type;
    }

    public LocatorType getType()
    {
        return _locatorType;
    }

    public void setValue(String value)
    {
        _selectorValue = value;
    }

    public String getValue()
    {
        return _selectorValue;
    }

    public void setSearchText(String text)
    {
        _searchText = text;
    }

    public String getSearchText()
    {
        return _searchText;
    }

    @Override
    public String toString()
    {
        String text = "";

        if (_locatorType != null)
        {
            text += _locatorType.toString() + "=";
        }

        if (_selectorValue != null)
        {
            text += "'" + _selectorValue + "'";
        }

        if (_searchText != null)
        {
            text += " (searchText='" + _searchText + "')";
        }

        return text;
    }

    public By toSeleniumByLocator()
    {
        switch (getType())
        {
            case ID:
                return By.id(getValue());
            case NAME:
                return By.name(getValue());
            case CLASS_NAME:
                return By.className(getValue());
            case TAG_NAME:
                return By.tagName(getValue());
            case LINK_TEXT:
                return By.linkText(getValue());
            case PARTIAL_LINK_TEXT:
                return By.partialLinkText(getValue());
            case XPATH:
                return By.xpath(getValue());
            case CSS_SELECTOR:
                return By.cssSelector(getValue());
            default:
                return null;
        }
    }
}