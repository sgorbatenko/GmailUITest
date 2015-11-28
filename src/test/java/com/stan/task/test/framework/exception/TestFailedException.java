
package com.stan.task.test.framework.exception;

public class TestFailedException extends TestAutomationException
{
    /**
     * 
     */
    private static final long serialVersionUID = 4483273269873810583L;

    public TestFailedException(String message)
    {
        super(message);
    }

    public TestFailedException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
