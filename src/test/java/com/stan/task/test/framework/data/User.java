
package com.stan.task.test.framework.data;

public class User
{
    private String _emailAddress;
    private String _password;

    public User(String emailAddress, String password)
    {
        _emailAddress = emailAddress;
        _password = password;
    }

    public String getEmailAddress()
    {
        return _emailAddress;
    }

    public String getPassword()
    {
        return _password;
    }
}
