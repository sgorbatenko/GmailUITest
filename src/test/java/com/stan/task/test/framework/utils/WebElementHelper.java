
package com.stan.task.test.framework.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;
import org.seleniumhq.jetty7.util.log.Log;

public final class WebElementHelper
{
    // Scripts
    private static final String GET_ELEMENT_PROPERTY_SCRIPT = "return arguments[0].%s";
    private static final String SET_ELEMENT_PROPERTY_SCRIPT = "arguments[0].%s=%s";
    private static final String INVOKE_METHOD_SCRIPT = "arguments[0].%s()";
    public static final String SCROLL_INTO_VIEW_SCRIPT = "arguments[0].scrollIntoView();";

    // Properties
    private static final String TEXT_CONTENT_PROPERTY = "textContent";
    private static final String CLIENT_HEIGHT_PROPERTY = "clientHeight";
    private static final String CLIENT_WIDTH_PROPERTY = "clientWidth";
    private static final String OFFSET_TOP_PROPERTY = "offsetTop";
    private static final String OFFSET_LEFT_PROPERTY = "offsetLeft";
    private static final String SCROLL_HEIGHT_PROPERTY = "scrollHeight";
    private static final String SCROLL_WIDTH_PROPERTY = "scrollWidth";
    private static final String SCROLL_TOP_PROPERTY = "scrollTop";
    private static final String SCROLL_LEFT_PROPERTY = "scrollLeft";
    private static final String CELL_INDEX_PROPERTY = "cellIndex";

    // Methods
    private static final String CLICK_METHOD = "click";

    private WebElementHelper()
    {
    }

    public static String getProperty(WebElement element, String propertyName)
    {
        String script = String.format(GET_ELEMENT_PROPERTY_SCRIPT, propertyName);
        Object result = executeScript(script, element);
        if (result == null)
        {
            return "";
        }
        return result.toString();
    }

    public static void setProperty(WebElement element, String propertyName, String propertyValue)
    {
        String script = String.format(SET_ELEMENT_PROPERTY_SCRIPT, propertyName, propertyValue);
        executeScript(script, element);
    }

    public static String getText(WebElement element)
    {
        return getProperty(element, TEXT_CONTENT_PROPERTY);
    }

    public static int getClientHeight(WebElement element)
    {
        return Integer.parseInt(getProperty(element, CLIENT_HEIGHT_PROPERTY));
    }

    public static int getClientWidth(WebElement element)
    {
        return Integer.parseInt(getProperty(element, CLIENT_WIDTH_PROPERTY));
    }

    public static int getOffsetTop(WebElement element)
    {
        return Integer.parseInt(getProperty(element, OFFSET_TOP_PROPERTY));
    }

    public static int getOffsetLeft(WebElement element)
    {
        return Integer.parseInt(getProperty(element, OFFSET_LEFT_PROPERTY));
    }

    public static int getScrollHeight(WebElement element)
    {
        return Integer.parseInt(getProperty(element, SCROLL_HEIGHT_PROPERTY));
    }

    public static int getScrollWidth(WebElement element)
    {
        return Integer.parseInt(getProperty(element, SCROLL_WIDTH_PROPERTY));
    }

    public static int getScrollTop(WebElement element)
    {
        return Integer.parseInt(getProperty(element, SCROLL_TOP_PROPERTY));
    }

    public static void setScrollTop(WebElement element, String scrollTop)
    {
        setProperty(element, SCROLL_TOP_PROPERTY, scrollTop);
    }

    public static int getScrollLeft(WebElement element)
    {
        return Integer.parseInt(getProperty(element, SCROLL_LEFT_PROPERTY));
    }

    public static void setScrollLeft(WebElement element, String scrollTop)
    {
        setProperty(element, SCROLL_LEFT_PROPERTY, scrollTop);
    }

    public static int getCellIndex(WebElement element)
    {
        return Integer.parseInt(getProperty(element, CELL_INDEX_PROPERTY));
    }

    public static boolean isVisible(WebElement element)
    {
        return element.isDisplayed() && element.getLocation().getY() > 0 && element.getLocation().getX() > 0;
    }

    public static void click(WebElement element)
    {
        String script = String.format(INVOKE_METHOD_SCRIPT, CLICK_METHOD);
        executeScript(script, element);
    }



    @SuppressWarnings("deprecation")
    public static void scrollIntoView(WebElement element, String controlName)
    {
        JavascriptExecutor executor = getJavascriptExecutor(element);

        try
        {
            executor.executeScript(SCROLL_INTO_VIEW_SCRIPT, element);
        }
        catch (Throwable exception)
        {
            Log.warn("Exception (Element.java) trying to scroll control '" + controlName
                + "' into view: "
                + exception.getMessage());
        }

    }

    private static Object executeScript(String script, WebElement element)
    {
        JavascriptExecutor executor = getJavascriptExecutor(element);
        Object result = executor.executeScript(script, element);
        return result;
    }

    private static JavascriptExecutor getJavascriptExecutor(WebElement element)
    {
        WebElement el = element;
        if (!(element instanceof WrapsDriver))
        {
            el = ((WrapsElement) element).getWrappedElement();
        }

        WebDriver driver = ((WrapsDriver) el).getWrappedDriver();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return executor;
    }
}
