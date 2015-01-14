
package com.stan.task.test.framework.exception;

public abstract class TestAutomationException extends RuntimeException
{
    private static final long serialVersionUID = -9205060342077719460L;

    public TestAutomationException()
    {
        super();
    }

    public TestAutomationException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public TestAutomationException(String message)
    {
        super(message);
    }

    public TestAutomationException(Throwable cause)
    {
        super(cause);
    }

}
