
package com.stan.task.framework.pagefactory;

import java.lang.reflect.Field;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.stan.task.framework.control.LocatorType;


public class Annotations extends org.openqa.selenium.support.pagefactory.Annotations
{
    private Field _field;

    public Annotations(Field field)
    {
        super(field);
        _field = field;
    }

    public LocatorType getLocatorType()
    {
        FindBy findBy = _field.getAnnotation(FindBy.class);
        if (findBy == null)
        {
            return null;
        }
        return getLocatorFromFindBy(findBy);
    }

    public String getValue()
    {
        FindBy findBy = _field.getAnnotation(FindBy.class);
        if (findBy == null)
        {
            return null;
        }
        if (!"".equals(findBy.className()))
        {
            return findBy.className();
        }
        else if (!"".equals(findBy.css()))
        {
            return findBy.css();
        }
        else if (!"".equals(findBy.id()))
        {
            return findBy.id();
        }
        else if (!"".equals(findBy.linkText()))
        {
            return findBy.linkText();
        }
        else if (!"".equals(findBy.name()))
        {
            return findBy.name();
        }
        else if (!"".equals(findBy.partialLinkText()))
        {
            return findBy.partialLinkText();
        }
        else if (!"".equals(findBy.tagName()))
        {
            return findBy.tagName();
        }
        else if (!"".equals(findBy.xpath()))
        {
            return findBy.xpath();
        }
        else if (!"".equals(findBy.using()))
        {
            return findBy.using();
        }
        return null;
    }

    protected LocatorType getLocatorFromFindBy(FindBy findBy)
    {
        LocatorType ans = getLocatorTypeFromShortFindBy(findBy);
        if (ans == null)
        {
            ans = getLocatorTypeFromLongFindBy(findBy);
        }

        return ans;
    }

    protected LocatorType getLocatorTypeFromLongFindBy(FindBy findBy)
    {
        How how = findBy.how();
        switch (how)
        {
            case CLASS_NAME:
                return LocatorType.CLASS_NAME;

            case CSS:
                return LocatorType.CSS_SELECTOR;

            case ID:
                return LocatorType.ID;

            case ID_OR_NAME:
                return LocatorType.ID;

            case LINK_TEXT:
                return LocatorType.LINK_TEXT;

            case NAME:
                return LocatorType.NAME;

            case PARTIAL_LINK_TEXT:
                return LocatorType.PARTIAL_LINK_TEXT;

            case TAG_NAME:
                return LocatorType.TAG_NAME;

            case XPATH:
                return LocatorType.XPATH;

            default:
                // Note that this shouldn't happen (eg, the above matches all
                // possible values for the How enum)
                throw new IllegalArgumentException("Cannot determine how to locate element " + _field);
        }
    }

    protected LocatorType getLocatorTypeFromShortFindBy(FindBy findBy)
    {
        if (!"".equals(findBy.className()))
        {
            return LocatorType.CLASS_NAME;
        }
        else if (!"".equals(findBy.css()))
        {
            return LocatorType.CSS_SELECTOR;
        }
        else if (!"".equals(findBy.id()))
        {
            return LocatorType.ID;
        }
        else if (!"".equals(findBy.linkText()))
        {
            return LocatorType.LINK_TEXT;
        }
        else if (!"".equals(findBy.name()))
        {
            return LocatorType.NAME;
        }
        else if (!"".equals(findBy.partialLinkText()))
        {
            return LocatorType.PARTIAL_LINK_TEXT;
        }
        else if (!"".equals(findBy.tagName()))
        {
            return LocatorType.TAG_NAME;
        }
        else if (!"".equals(findBy.xpath()))
        {
            return LocatorType.XPATH;
        }


        // Fall through
        return null;
    }
}
