
package com.stan.task.framework.localization;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

public final class Utils
{

    private Utils()
    {
    }

    public static List<String> getTestDataFilesListFromFile(String path) throws IOException
    {
        InputStream is = Utils.class.getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<String> lines = new ArrayList<String>();
        String line;
        while ((line = br.readLine()) != null)
        {
            lines.add(line);
        }
        br.close();
        return lines;
    }

    public static void verifyMapsAreEqual(Map<String, String> map, Map<String, String> map2)
    {
        Map<String, String> differencesMap = new LinkedHashMap<String, String>();

        for (String key : map.keySet())
        {
            try
            {
                Assert.assertTrue(map2.get(key).contains(map.get(key)));
            }
            catch (AssertionError e)
            {
                differencesMap.put("\n" + key, "Expected:" + map.get(key).toString()
                    + " But Actual:"
                    + map2.get(key).toString());
            }
        }

        String errorMsg = "\nExpected entity values: "
            + map
            + " \nActual entity values: "
            + map2
            + "\nNext Properties are mismatched: ";
        Assert.assertTrue(differencesMap.isEmpty(),
            errorMsg + differencesMap.toString());
    }

    public static CharSequence fromFile(String filename) throws IOException
    {
        FileInputStream fis = new FileInputStream(filename);
        FileChannel fc = fis.getChannel();

        // Create a read-only CharBuffer on the file
        ByteBuffer bbuf = fc.map(FileChannel.MapMode.READ_ONLY,
            0,
            (int) fc.size());
        CharBuffer cbuf = Charset.forName("8859_1").newDecoder().decode(bbuf);
        fis.close();
        return cbuf;
    }
}
