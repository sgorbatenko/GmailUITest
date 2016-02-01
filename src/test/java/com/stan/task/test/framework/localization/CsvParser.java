
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
    final String cvsSplitBy = ";";

    public List<TestEntity> getTestEntites(String testDataFileName)
        throws IOException
    {
        List<TestEntity> testEntities = new ArrayList<TestEntity>();
        List<String> lines = parseCsvFile(testDataFileName);
        int numberOfLang = lines.get(0).split(cvsSplitBy).length - 1;

        for (int currentLangIndex = 1; currentLangIndex <= numberOfLang; currentLangIndex++)
        {
            TestEntity langTestEntity = createTestEntityWithLangData(lines, currentLangIndex);
            testEntities.add(langTestEntity);
        }
        Assert.assertFalse(testEntities.isEmpty(), "Expected data is empty.");
        return testEntities;
    }

    private TestEntity createTestEntityWithLangData(List<String> langData, int langIndex)
    {
        TestEntity langTestEntity = new TestEntity();
        Map<String, String> expectedValueByName = new HashMap<String, String>();
        String key;

        for (int lineIndex = 0; lineIndex < langData.size(); lineIndex++)
        {
            String[] strArr = langData.get(lineIndex).split(cvsSplitBy);
            key = strArr[0];
            expectedValueByName.put(key, strArr[langIndex]);
        }
        langTestEntity.setExpectedData(expectedValueByName);
        return langTestEntity;
    }

    private List<String> parseCsvFile(String testDataPath) throws IOException
    {
        return Utils.getTestDataFilesListFromFile(testDataPath);
    }
}
