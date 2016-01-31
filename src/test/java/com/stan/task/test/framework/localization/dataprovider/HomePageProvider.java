
package com.stan.task.test.framework.localization.dataprovider;

import com.stan.task.test.framework.model.UiObject;

public class HomePageProvider extends DataProvider
{

    public HomePageProvider(UiObject ui)
    {
        super(ui);
    }

    @Override
    public UiObject getUiObject()
    {
        // Put logic to navigate to the needed Ui
        // Think about where to put logic to change language
        // getUi().getUIFieldValue(property)
        return getUi();
    }
}
