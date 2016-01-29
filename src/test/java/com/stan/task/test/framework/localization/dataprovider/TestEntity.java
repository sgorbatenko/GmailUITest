
package com.stan.task.test.framework.localization.dataprovider;

import java.util.HashMap;
import java.util.Map;

public class TestEntity
{
    private Map<String, String> _expectedData = new HashMap<String, String>();
    private Map<String, String> _data = new HashMap<String, String>();

    public Map<String, String> getData()
    {
        return _data;
    }

    public void setData(Map<String, String> data)
    {
        _data = data;
    }

    public Map<String, String> getExpectedData()
    {
        return _expectedData;
    }

    public void setExpectedData(Map<String, String> expectedValueByName)
    {
        _expectedData = expectedValueByName;
    }

    @Override
    public String toString()
    {
        String s = " Entity with fields: ";

        for (String key : _expectedData.keySet())
        {
            s += key + "=" + _expectedData.get(key) + ";";
        }
        return s;
    }

    public String getFromAllDataValue(String property)
    {
        return _data
            .get(property)
            .toString()
            .replace("[", "")
            .replace("]", "");
    }

    public String getFromExpectedDataValue(String property)
    {
        return _data
            .get(property)
            .toString()
            .replace("[", "")
            .replace("]", "");
    }
}
