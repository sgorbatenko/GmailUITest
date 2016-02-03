
package com.stan.task.framework.pagefactory;

import java.lang.reflect.Field;


public class ControlNameAnnotation
{

    private final Field _field;

    public ControlNameAnnotation(Field field)
    {
        this._field = field;
    }

    public String buildTestName()
    {

        ControlName description = _field.getAnnotation(ControlName.class);

        if (description != null)
        {
            return description.name();
        }

        return null;
    }

}
