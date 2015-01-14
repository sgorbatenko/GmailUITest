
package com.stan.task.test.framework.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Wait extends WebDriverWait
{
    public static final long TIMEOUT_INTERACTION = 60;
    public static final long TIMEOUT_MIN_WAIT = 10;
    public static final long TIMEOUT_LOADING_WAIT = 240;
    public static final long TIMEOUT_VERY_LONG_OPERATION_WAIT = 480;
    public static final long TIMEOUT_ONE_SECOND_WAIT = 1;
    public static final long TIMEOUT_NONE = 0;

    public Wait(WebDriver driver)
    {
        super(driver, TIMEOUT_INTERACTION);
    }

    public Wait(WebDriver driver, Length length)
    {
        super(driver, length.getValue());
    }

    public enum Length
    {
        Long(TIMEOUT_LOADING_WAIT),
        Average(TIMEOUT_INTERACTION),
        Short(TIMEOUT_MIN_WAIT),
        None(TIMEOUT_NONE);

        private long _value;

        Length(long value)
        {
            _value = value;
        }

        public long getValue()
        {
            return _value;
        }
    }

    /*
     * Wait/Sleep statement for use inside timed loops - initially gives processor very short time slice
     * but as time progresses, processor is given longer time slices so application can perform operations
     * This method allows loops to perform very efficiently during performance testing, as the maximum extra wait
     * time is not more than a fraction of the elapsed time up to 10 seconds, and never more than 1/5 second.
     * This has been optimized on the Windows platform in a different automation framework and seems a good balance.
     */
    public static void waitInsideLoop(long startTimeInMillis)
    {
        long elapsedTimeMillis = System.currentTimeMillis() - startTimeInMillis;

        try
        {
            // if less than 1/100 second, sleep 1/1000 second
            if (elapsedTimeMillis < 10)
            {
                Thread.sleep(1);
            }
            // if less than 1/25 second, sleep 1/250 second
            else if (elapsedTimeMillis < 40)
            {
                Thread.sleep(4);
            }
            // if less than 1/10 second, sleep 1/100 second
            else if (elapsedTimeMillis < 100)
            {
                Thread.sleep(10);
            }
            // if less than 1/5 second, sleep 1/50 second
            else if (elapsedTimeMillis < 200)
            {
                Thread.sleep(20);
            }
            // if less than 2 seconds, sleep 1/20 second
            else if (elapsedTimeMillis < 2000)
            {
                Thread.sleep(50);
            }
            // if less than 10 second2, sleep 1/10 second
            else if (elapsedTimeMillis < 10000)
            {
                Thread.sleep(100);
            }
            // if longer than ten seconds, sleep maximum 1/5 second
            else
            {
                Thread.sleep(200);
            }
        }
        catch (InterruptedException interruptedException)
        {

        }
    }
}
