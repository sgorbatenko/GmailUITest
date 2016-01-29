
package com.stan.task.test.framework.localization.dataprovider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.stan.task.test.framework.localization.CsvParser;
import com.stan.task.test.framework.model.UiObject;

public abstract class DataProvider
{
    public static final String TESTDATA_CATALOG = "";// "./src/test/resources/localization/";

    private UiObject _ui;

    public abstract UiObject getUiObject();

    protected DataProvider()
    {
        super();
    }

    protected DataProvider(UiObject ui)
    {
        _ui = ui;
    }

    protected UiObject getUi()
    {
        return _ui;
    }

    protected void setUi(UiObject ui)
    {
        _ui = ui;
    }

    public static List<TestEntity> provideTestEntities(String filePath) throws IOException
    {
        CsvParser csvParser = new CsvParser();
        return csvParser.getTestEntites(DataProvider.TESTDATA_CATALOG + filePath);
    }

    public Map<String, String> provideActualResult(TestEntity entity)
    {
        UiObject ui = getUiObject();// (entity.getData());
        Map<String, String> actualValues = new LinkedHashMap<>();
        for (String property : entity.getExpectedData().keySet())
        {
            actualValues.put(property, getValueFromUI(ui, property));
        }

        return actualValues;
    }

    protected String getValueFromUI(UiObject ui, String property)
    {
        String value;
        try
        {
            value = ui.getUIFieldValue(property).toString().trim();
        }
        catch (Exception exception)
        {
            value = "Value was not obtained from UI. See logs for details.";
        }

        return value;
    }

    @org.testng.annotations.DataProvider(name = "getExpectedEntities")
    public static Object[][] getExpectedEntities(Method testMethod) throws Exception
    {
        Map<String, String> arguments = DataProviderUtils.resolveDataProviderArguments(testMethod);

        List<TestEntity> importedEntities = provideTestEntities(arguments.get("filePath"));

        Object[][] objects = new Object[importedEntities.size()][1];
        for (int i = 0; i < importedEntities.size(); i++)
        {
            objects[i][0] = importedEntities.get(i);
        }
        return objects;
    }
}