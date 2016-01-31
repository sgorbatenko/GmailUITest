
package com.stan.task.test.framework.localization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.stan.task.test.framework.localization.dataprovider.TestEntity;

public class CsvParser
{
    public List<TestEntity> getTestEntites(String testDataFileName)
        throws IOException
    {
        TestEntity langTestEntity;
        Map<String, String> expectedValueByName;
        String cvsSplitBy = ";";

        List<TestEntity> testEntities = new ArrayList<TestEntity>();
        List<String> lines = parseCsvFile(testDataFileName);
        int numberOfLang = lines.get(0).split(cvsSplitBy).length - 1; // ines.get(0).split(",").length - 2;

        for (int currentLangIndex = 1; currentLangIndex <= numberOfLang; currentLangIndex++)
        {
            langTestEntity = new TestEntity();
            expectedValueByName = new HashMap<String, String>();
            String key;
            for (int lineIndex = 1; lineIndex < lines.size(); lineIndex++)
            {
                String[] strArr = lines.get(lineIndex).split(cvsSplitBy);
                key = strArr[0];
                expectedValueByName.put(key, strArr[currentLangIndex]);
            }
            langTestEntity.setExpectedData(expectedValueByName);
            testEntities.add(langTestEntity);
        }
        Assert.assertFalse(testEntities.isEmpty(), "Expected data is empty.");
        return testEntities;
    }

    private List<String> parseCsvFile(String testDataPath) throws IOException
    {
        return Utils.getTestDataFilesListFromFile(testDataPath);
    }
}
