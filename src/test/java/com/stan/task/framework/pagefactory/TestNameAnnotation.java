
package com.stan.task.framework.pagefactory;

import java.lang.reflect.Field;


public class TestNameAnnotation
{

    private final Field _field;

    public TestNameAnnotation(Field field)
    {
        this._field = field;
    }

    public String buildTestName()
    {

        TestName description = _field.getAnnotation(TestName.class);

        if (description != null)
        {
            return description.testName();
        }

        return null;
    }

}
