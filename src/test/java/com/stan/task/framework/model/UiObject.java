
package com.stan.task.framework.model;

import com.stan.task.framework.page.AbstractPage;

public interface UiObject
{
    public AbstractPage getPage();

    boolean isOpened();

    UiObject open();

    public default String getUIFieldValue(String property) throws Exception
    {
        return getPage().getUiControl(property).getText();
    }
}
