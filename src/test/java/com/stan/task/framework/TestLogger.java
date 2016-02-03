
package com.stan.task.framework;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.stan.task.framework.exception.TestFailedException;

//Class to provide global logging for test cases
public class TestLogger
{
    private Logger _logger = Logger.getLogger(Thread.currentThread().getName());
    private long _currentTestStepIndex;
    private boolean _warnWasCalled;
    private boolean _failAndContinueWasCalled;
    private boolean _inDebugMode;

    private static Map<Thread, TestLogger> _TESTLOGGERBYTHREAD = new HashMap<Thread, TestLogger>();

    // Declare private constructor
    public TestLogger()
    {
    }

    public Logger getLogger()
    {
        return _logger;
    }

    public long getCurrentTestStepIndex()
    {
        return _currentTestStepIndex;
    }

    public void setCurrentTestStepIndex(long testStepIndex)
    {
        _currentTestStepIndex = testStepIndex;
    }

    public boolean getWarnWasCalled()
    {
        return _warnWasCalled;
    }

    public void setWarnWasCalled(boolean warnWasCalled)
    {
        _warnWasCalled = warnWasCalled;
    }

    public boolean getFailAndContinueWasCalled()
    {
        return _failAndContinueWasCalled;
    }

    public void setFailAndContinueWasCalled(boolean failAndContinueWasCalled)
    {
        _failAndContinueWasCalled = failAndContinueWasCalled;
    }

    public void setInDebugModeState(boolean inDebugMode)
    {
        _inDebugMode = inDebugMode;
    }

    public boolean getInDebugModeState()
    {
        return _inDebugMode;
    }

    private static TestLogger getCurrentThreadTestLogger()
    {
        Thread currentThread = Thread.currentThread();

        if (_TESTLOGGERBYTHREAD.containsKey(currentThread))
        {
            return _TESTLOGGERBYTHREAD.get(currentThread);
        }

        TestLogger testLogger = new TestLogger();


        _TESTLOGGERBYTHREAD.put(currentThread, testLogger);

        return testLogger;
    }

    /**
     * Returns true if warn was called from current thread since reset
     * 
     * @return true if warn was called from current thread since reset
     */
    public static boolean getWasWarned()
    {
        return getCurrentThreadTestLogger().getWarnWasCalled();
    }

    /**
     * Returns true if fail and continue was called from current thread since
     * reset
     * 
     * @return true if fail and continue was called from current thread since
     *         reset
     */
    public static boolean getWasFailedAndContinued()
    {
        return getCurrentThreadTestLogger().getFailAndContinueWasCalled();
    }

    /**
     * Resets log settings for current thread logger
     */
    public static void resetLogs()
    {
        TestLogger currentThreadTestLogger = getCurrentThreadTestLogger();

        currentThreadTestLogger.setCurrentTestStepIndex(0);
        currentThreadTestLogger.setWarnWasCalled(false);
        currentThreadTestLogger.setFailAndContinueWasCalled(false);
    }

    public static void setIsInDebugMode(boolean inDebugMode)
    {
        getCurrentThreadTestLogger().setInDebugModeState(inDebugMode);
    }

    public static boolean getIsInDebugMode()
    {
        return getCurrentThreadTestLogger().getInDebugModeState();
    }

    /**
     * Write a simple non-numbered log entry to the log
     * 
     * @param text
     */
    public static void log(String text)
    {
        getCurrentThreadTestLogger().getLogger().info(text);
    }

    public static void debug(String text)
    {
        if (getIsInDebugMode())
        {
            TestLogger.log(text);
        }
    }

    /**
     * Write a numbered test step to the log
     * 
     * @param text
     */
    public static void writeStep(String text)
    {
        TestLogger currentThreadTestLogger = getCurrentThreadTestLogger();

        currentThreadTestLogger.setCurrentTestStepIndex(currentThreadTestLogger.getCurrentTestStepIndex() + 1);

        currentThreadTestLogger.getLogger()
            .info(Long.toString(currentThreadTestLogger.getCurrentTestStepIndex()) + ". " + text);
    }

    /**
     * Write a debug info line to the log
     * 
     * @param text
     */
    public static void writeDebug(String text)
    {
        TestLogger currentThreadTestLogger = getCurrentThreadTestLogger();

        currentThreadTestLogger.getLogger().debug(text);
    }

    /**
     * Write a warning to the log
     * 
     * @param text
     */
    public static void warn(String text)
    {
        TestLogger currentThreadTestLogger = getCurrentThreadTestLogger();

        currentThreadTestLogger.setWarnWasCalled(true);

        currentThreadTestLogger.getLogger().warn("WARNED: " + text);
    }

    /*
     * Fails test case with appropriate message
     */
    public static void fail(String failMessage)
    {
        TestLogger currentTestLogger = getCurrentThreadTestLogger();

        currentTestLogger.getLogger().info("FAILED: " + failMessage);

        throw new TestFailedException(failMessage);
    }

    /*
     * Fails test case with appropriate message and exception wrapped
     * in TestFailedException
     */
    public static void fail(String failMessage, Exception exception)
    {
        TestLogger currentTestLogger = getCurrentThreadTestLogger();

        currentTestLogger.getLogger().info("FAILED: " + failMessage);

        throw new TestFailedException(failMessage, exception);
    }

    /*
     * Fails test case but continues execution
     * (not implemented property, for now just fails test case as fail() does
     */
    public static void failAndContinue(String failMessage)
    {
        TestLogger currentTestLogger = getCurrentThreadTestLogger();

        currentTestLogger.setFailAndContinueWasCalled(true);

        currentTestLogger.getLogger().info("FAILED (CONTINUED): " + failMessage);
    }

    /**
     * Get a descriptive string for the passed stringList suitable for logging
     * output
     * 
     * @param stringList
     * @return a descriptive string for the passed stringList suitable for
     *         logging output
     */
    public static String getLogString(List<String> stringList)
    {
        String logString = "";

        for (int index = 0; index < stringList.size(); index++)
        {
            if (index != 0)
            {
                logString += ",";
            }

            logString += getLogString(stringList.get(index));
        }

        return logString;
    }

    /**
     * Get a descriptive string for the passed object suitable for logging
     * output
     * 
     * @param object
     * @return a descriptive string for the passed object suitable for logging
     *         output
     */
    public static String getLogString(Object object)
    {
        String returnText;

        if (object instanceof String)
        {
            returnText = "'" + (String) object + "'";
        }

        else if (object instanceof Exception)
        {
            returnText = getExceptionLogString((Exception) object);
        }

        else if (object instanceof Collection)
        {
            returnText = getCollectionLogString((Collection) object);
        }

        else if (object instanceof Object[])
        {
            returnText = getObjectArrayLogString((Object[]) object);
        }

        else if (object instanceof String[])
        {
            returnText = getStringArrayLogString((String[]) object);
        }

        else if (object == null)
        {
            returnText = "<null>";
        }

        else
        {
            returnText = object.toString();
        }

        return returnText;
    }

    /**
     * Get a descriptive string for the passed exception suitable for logging
     * output
     * 
     * @param exception
     * @return a descriptive string for the passed exception suitable for
     *         logging output
     */
    public static String getExceptionLogString(Exception exception)
    {
        String logMessage = "";

        Throwable currentException = exception;
        int index = -1;

        // get up to 10 levels of nested exception causes
        while (currentException != null && index < 10)
        {
            index++;

            if (index > 0)
            {
                logMessage += "; ";
            }

            if (currentException.getMessage() != null && !currentException.getMessage().isEmpty())
            {
                logMessage += "Exception at Level " + index + ": '" + currentException.getMessage() + "'";
            }

            currentException = currentException.getCause();
        }

        return logMessage;
    }

    private static String getStringArrayLogString(String[] stringArray)
    {
        String logString = "";

        for (int index = 0; index < stringArray.length; index++)
        {
            if (index != 0)
            {
                logString += ",";
            }

            logString += getLogString(stringArray[index]);
        }

        return logString;
    }

    private static String getObjectArrayLogString(Object[] objectArray)
    {
        String logString = "";

        for (int index = 0; index < objectArray.length; index++)
        {
            if (index != 0)
            {
                logString += ",";
            }

            logString += getLogString(objectArray[index]);
        }

        return logString;
    }

    private static String getCollectionLogString(Collection collection)
    {
        String logString = "";
        int index = -1;

        for (Object item : collection)
        {
            index++;

            if (index != 0)
            {
                logString += ",";
            }

            logString += getLogString(item);
        }

        return "{" + logString + "}";
    }
}