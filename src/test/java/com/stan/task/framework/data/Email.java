
package com.stan.task.framework.data;

public class Email
{
    private String _toAddress;
    private String _subject;
    private String _content;

    public Email(String toAddress, String subject, String content)
    {
        _toAddress = toAddress;
        _subject = subject;
        _content = content;
    }

    public String getToAddress()
    {
        return _toAddress;
    }

    public String getSubject()
    {
        return _subject;
    }

    public String getBody()
    {
        return _content;
    }
}
