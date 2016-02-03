
package com.stan.task.framework.exception;



/**
 * Use this Exception or derived exceptions for errors to be specifically
 * identified with the control layer
 */
public class ControlLayerException extends TestAutomationException
{
    private static final long serialVersionUID = -4513001671552434302L;

    public ControlLayerException()
    {
        super();
    }

    public ControlLayerException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ControlLayerException(String message)
    {
        super(message);
    }

    public ControlLayerException(Throwable cause)
    {
        super(cause);
    }
}
