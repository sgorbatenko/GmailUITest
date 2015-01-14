
package com.stan.task.test.framework.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public final class CalcUtils
{
    public static double calcAvg(ArrayList<String> list)
    {
        double sum = 0;
        int count = 0;
        for (int i = 0; i < list.size(); i++)
        {
            if (list.get(i) != null)
            {
                sum += Double.parseDouble(list.get(i));
                count++;
            }

        }
        double avg = sum / count;

        return round(avg, 4);
    }

    public static double round(double value, int places)
    {
        if (places < 0)
        {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
