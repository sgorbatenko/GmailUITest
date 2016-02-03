
package com.stan.task.framework.control;

import org.openqa.selenium.WebElement;

/**
 * Element HTML document, to support finding data and controls in controls
 */
public class ElementHtmlDoc extends HtmlDoc
{
    private final String _controlDescription;

    public ElementHtmlDoc(Element element)
    {
        super(element.getSeleniumWebElement(true).getAttribute("innerHTML"));

        _controlDescription = element.getName();
    }

    public ElementHtmlDoc(WebElement seleniumWebelement, String controlDescription)
    {
        super(seleniumWebelement.getAttribute("innerHTML"));

        _controlDescription = controlDescription;
    }

    /**
     * Get the control description
     * 
     * @return the description of the control
     */
    protected String getControlDescription()
    {
        return _controlDescription;
    }
}
