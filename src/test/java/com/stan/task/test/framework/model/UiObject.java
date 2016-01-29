
package com.stan.task.test.framework.model;

import com.stan.task.test.framework.page.AbstractPage;

public interface UiObject
{
    public AbstractPage getPage();

    boolean isOpened();

    UiObject open();

    public default String getUIFieldValue(String property)
    {
        return getPage().getUiControl(property).getText();
    }
}
