
package com.stan.task.test.framework.control;

public enum LocatorType
{
    /**
     * Find by Id
     */
    ID,

    /**
     * Find by Name
     */
    NAME,

    /**
     * Find by Class name
     */
    CLASS_NAME,

    /**
     * Find by Tag Name
     */
    TAG_NAME,

    /**
     * Find by Link Text
     */
    LINK_TEXT,

    /**
     * Find by Partial Link Text
     */
    PARTIAL_LINK_TEXT,

    /**
     * Find by Xpath
     */
    XPATH,

    /**
     * Find by CSS selector. Supports 3 sub-queries: has(**cs subquery**), contains(**partial text**), and text(**exact text**) which must be used only at the
     * end of an element
     */
    CSS_SELECTOR,
}