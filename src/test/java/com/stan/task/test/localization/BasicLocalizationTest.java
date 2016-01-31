
package com.stan.task.test.localization;

import org.testng.annotations.Test;

import com.stan.task.test.framework.localization.Utils;
import com.stan.task.test.framework.localization.dataprovider.DataProvider;
import com.stan.task.test.framework.localization.dataprovider.DataProviderArguments;
import com.stan.task.test.framework.localization.dataprovider.HomePageProvider;
import com.stan.task.test.framework.localization.dataprovider.TestEntity;

public class BasicLocalizationTest extends AbstractLocalizationTest
{
    @Test(dataProviderClass = DataProvider.class, dataProvider = "getExpectedEntities")
    @DataProviderArguments("filePath=/HomePage.csv")
    public void testLoacalizationOnHomePage(TestEntity entity)
    {
        DataProvider provider = new HomePageProvider(_homeUi);
        Utils.verifyMapsAreEqual(entity.getExpectedData(),
            provider.provideActualResult(entity));
    }
}
