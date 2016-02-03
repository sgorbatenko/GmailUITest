
package com.stan.task.framework.exception;

public class NoSuchControlException extends TestAutomationException
{
    private static final long serialVersionUID = -3131619556441132252L;

    public NoSuchControlException()
    {
        super();
    }

    public NoSuchControlException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public NoSuchControlException(String message)
    {
        super(message);
    }

    public NoSuchControlException(Throwable cause)
    {
        super(cause);
    }

}
