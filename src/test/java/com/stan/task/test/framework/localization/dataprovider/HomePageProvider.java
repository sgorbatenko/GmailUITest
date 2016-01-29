
package com.stan.task.test.framework.localization.dataprovider;

import com.stan.task.test.framework.model.UiObject;

public class HomePageProvider extends DataProvider
{

    @Override
    public UiObject getUiObject()
    {
        // getUi().getUIFieldValue(property)
        return getUi();
    }
}
