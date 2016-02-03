
package com.stan.task.framework.utils;

import org.openqa.selenium.WebElement;

import com.stan.task.framework.exception.CoreLevelException;

public final class ScrollUtils
{

    private ScrollUtils()
    {
    }

    public static boolean hasScroll(WebElement scrollableElement)
    {
        return hasScrollTop(scrollableElement) || hasScrollLeft(scrollableElement);
    }

    public static boolean isScrolledTo(WebElement scrollToElement, WebElement scrollableElement)
    {
        boolean scrolledToTop = true;
        boolean scrolledToLeft = true;
        if (hasScrollTop(scrollableElement))
        {
            scrolledToTop = isScrolledToTop(scrollToElement, scrollableElement);
        }
        if (hasScrollLeft(scrollableElement))
        {
            scrolledToLeft = isScrolledToLeft(scrollToElement, scrollableElement);
        }
        return scrolledToTop && scrolledToLeft;
    }

    public static void scrollTo(WebElement scrollToElement, WebElement scrollableElement)
    {
        if (!hasScroll(scrollableElement))
        {
            throw new CoreLevelException("Scrollable element doesn't have scroll");
        }
        if (isScrolledTo(scrollToElement, scrollableElement))
        {
            return;
        }
        if (hasScrollTop(scrollableElement))
        {
            scrollToTop(scrollToElement, scrollableElement);
        }
        if (hasScrollLeft(scrollableElement))
        {
            scrollToLeft(scrollToElement, scrollableElement);
        }
    }

    private static boolean hasScrollTop(WebElement scrollableElement)
    {
        int scrollableElementClientHeight = WebElementHelper.getClientHeight(scrollableElement);
        int scrollableElementScrollHeight = WebElementHelper.getScrollHeight(scrollableElement);
        return scrollableElementClientHeight < scrollableElementScrollHeight;
    }

    private static boolean hasScrollLeft(WebElement scrollableElement)
    {
        int scrollableElementClientWidth = WebElementHelper.getClientWidth(scrollableElement);
        int scrollableElementScrollWidth = WebElementHelper.getScrollWidth(scrollableElement);
        return scrollableElementClientWidth < scrollableElementScrollWidth - 1;
    }

    private static boolean isScrolledToTop(WebElement scrollToElement, WebElement scrollableElement)
    {
        int scrollToElementClientHeight = WebElementHelper.getClientHeight(scrollToElement);
        int scrollToElementOffsetTop = WebElementHelper.getOffsetTop(scrollToElement);
        int scrollableElementClientHeight = WebElementHelper.getClientHeight(scrollableElement);
        int scrollableElementScrollTop = WebElementHelper.getScrollTop(scrollableElement);
        return scrollableElementScrollTop <= scrollToElementOffsetTop && scrollableElementScrollTop + scrollableElementClientHeight >= scrollToElementClientHeight + scrollToElementOffsetTop;
    }

    private static boolean isScrolledToLeft(WebElement scrollToElement, WebElement scrollableElement)
    {
        int scrollToElementClientWidth = WebElementHelper.getClientWidth(scrollToElement);
        int scrollToElementOffsetLeft = WebElementHelper.getOffsetLeft(scrollToElement);
        int scrollableElementClientWidth = WebElementHelper.getClientWidth(scrollableElement);
        int scrollableElementScrollLeft = WebElementHelper.getScrollLeft(scrollableElement);
        return scrollableElementScrollLeft <= scrollToElementOffsetLeft && scrollableElementScrollLeft + scrollableElementClientWidth >= scrollToElementClientWidth + scrollToElementOffsetLeft;
    }

    private static void scrollToTop(WebElement scrollToElement, WebElement scrollableElement)
    {
        int scrollToElementClientHeight = WebElementHelper.getClientHeight(scrollToElement);
        int scrollToElementOffsetTop = WebElementHelper.getOffsetTop(scrollToElement);
        int scrollableElementClientHeight = WebElementHelper.getClientHeight(scrollableElement);
        String scrollableElementScrollTop = scrollToElementClientHeight + scrollToElementOffsetTop
                        - scrollableElementClientHeight
                        + "";
        WebElementHelper.setScrollTop(scrollableElement, scrollableElementScrollTop);
    }

    private static void scrollToLeft(WebElement scrollToElement, WebElement scrollableElement)
    {
        int scrollToElementClientWidth = WebElementHelper.getClientWidth(scrollToElement);
        int scrollToElementOffsetLeft = WebElementHelper.getOffsetLeft(scrollToElement);
        int scrollableElementClientWidth = WebElementHelper.getClientWidth(scrollableElement);
        String scrollableElementScrollLeft = scrollToElementClientWidth + scrollToElementOffsetLeft
                        - scrollableElementClientWidth
                        + "";
        WebElementHelper.setScrollLeft(scrollableElement, scrollableElementScrollLeft);
    }

    public static int getLeftMaximunScrollValue(WebElement scrollableElement)
    {
        int scrollableElementClientWidth = WebElementHelper.getClientWidth(scrollableElement);
        int scrollableElementScrollWidth = WebElementHelper.getScrollWidth(scrollableElement);

        return scrollableElementScrollWidth - scrollableElementClientWidth;
    }
}
