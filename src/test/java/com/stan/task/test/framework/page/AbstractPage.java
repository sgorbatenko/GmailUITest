
package com.stan.task.test.framework.page;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.stan.task.test.framework.control.Element;
import com.stan.task.test.framework.exception.NoSuchControlException;

/**
 * The AbstractPage Class.
 */
public abstract class AbstractPage
{
    Map<String, Element> _uiControls = new HashMap();
    private boolean _populatingControls;

    abstract void populateUiControls();

//    public Element getUiControl(String controlName)
//    {
//        return _uiControls.get(controlName);
//    }

    public void addUiControl(Element el)
    {
        _uiControls.put(el.getName(), el);
    }


    public final Map<String, Element> getControls() throws Exception
    {
        if (_uiControls.isEmpty() && !_populatingControls)
        {
            _populatingControls = true;

            try
            {
                populateControls();
            }
            finally
            {
                _populatingControls = false;
            }
        }

        return _uiControls;
    }

    /**
     * Protected method Populate controls
     * 
     * @throws Exception
     */
    protected void populateControls() throws Exception
    {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            if (Element.class.isAssignableFrom(field.getType()))
            {
                try
                {
                    field.setAccessible(true);
                    addControl((Element) field.get(this));
                }
                catch (IllegalArgumentException ex)
                {
                    throw new RuntimeException();
                }
                catch (IllegalAccessException ex)
                {
                    throw new RuntimeException();
                }
            }
        }
    }

    public final void addControl(Element control) throws Exception
    {
        Map<String, Element> controls = getControls();

        if (controls.containsKey(control.getName()))
        {
            throw new Exception("Can’t add control " + control.getName()
                + " to the controls collection of – it already exists");
        }

        controls.put(control.getName(), control);
    }

    public boolean hasControl(String name) throws Exception
    {
        return getControls().containsKey(name);
    }

    public final Element getUiControl(String name) throws Exception
    {
        Element control = getControls().get(name);
        if (control == null)
        {
            throw new NoSuchControlException("Page does not have control '" + name + "'");
        }
        return control;
    }

}