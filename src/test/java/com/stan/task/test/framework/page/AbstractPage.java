
package com.stan.task.test.framework.page;

import java.util.HashMap;
import java.util.Map;

import com.stan.task.test.framework.control.Element;

/**
 * The AbstractPage Class.
 */
public abstract class AbstractPage
{
    Map<String, Element> _uiControls = new HashMap();

    abstract void populateUiControls();

    public Element getUiControl(String controlName)
    {
        return _uiControls.get(controlName);
    }

    public void addUiControl(Element el)
    {
        _uiControls.put(el.getName(), el);
    }

}