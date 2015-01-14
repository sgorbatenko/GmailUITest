
package com.stan.task.test.framework.control;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.stan.task.test.framework.exception.ControlLayerException;

/**
 * Html Document to support querying Document and Element HTML to find child
 * control and data nodes
 */
public class HtmlDoc
{
    private Document _document;

    public HtmlDoc(String htmlString)
    {
        _document = Jsoup.parse(htmlString);
    }

    protected Elements select(String query)
    {
        return _document.select(query);
    }

    /**
     * Get the Ids of the nodes returned using rhe given query not found
     *
     * @param query
     * @return the Ids of the nodes returned using rhe given query
     */
    public String[] getNodeIds(String query)
    {
        Elements elements = select(query);

        String[] ids = new String[elements.size()];

        for (int index = 0; index < elements.size(); index++)
        {
            ids[index] = elements.get(index).id();
        }

        return ids;
    }

    /**
     * Get the node Id matching the query, throwing exception if the node
     * doesn't exist or there is more than one node
     *
     * @param query
     * @return the node Id matching the query
     */
    public String getNodeId(String query)
    {
        String[] nodeIds = getNodeIds(query);

        if (nodeIds.length == 0)
        {
            throw new ControlLayerException("Could not find single HTML Node with query '" + query + "' no nodes were found");
        }

        if (nodeIds.length != 1)
        {
            throw new ControlLayerException("Could not find single HTML Node with query '" + query
                + "' - "
                + nodeIds.length
                + " nodes were found");
        }

        return nodeIds[0];
    }

    /**
     * Return string array with text of the nodes matching the specified query
     *
     * @param query
     * @return string array with text of the nodes matching the specified query
     */
    public ArrayList<String> getTextValues(String query)
    {
        Elements elements = select(query);

        ArrayList<String> textValues = new ArrayList<String>();

        for (org.jsoup.nodes.Element element : elements)
        {
            textValues.add(HtmlDoc.cleanHtmlText(element.text()));
        }

        return textValues;
    }

    /**
     * Return true if the query returns an element with the exact text specified
     *
     * @param query
     *        the query to perform
     * @param exactText
     *        text to match
     * @return true if the query returns an element with the exact text specified
     */
    public boolean hasElementWithExactText(String query, String exactText)
    {
        return getElementWithExactText(query, exactText) != null;
    }

    /**
     * Return the child element with the exact text specified from the query, or null if none found
     *
     * @param query
     *        the query to perform
     * @param exactText
     *        text to match
     * @return query result element with the exact text specified, or null if not found
     */
    protected org.jsoup.nodes.Element getElementWithExactText(String query, String exactText)
    {
        Elements elements = select(query);

        for (org.jsoup.nodes.Element element : elements)
        {
            if (exactText.equals(HtmlDoc.cleanHtmlText(element.text())))
            {
                return element;
            }
        }

        return null;
    }

    protected static String getCleanedElementText(org.jsoup.nodes.Element element)
    {
        String text = element.text();

        // Null strings or strings with single space are treated as empty strings
        if (text == null || "&nbsp;".equals(text)
            || " ".equals(text)
            || "\u0020".equals(text)
            || "\u00A0".equals(text))
        {
            text = "";
        }
        else
        {
            text = text.trim();
        }

        return text;
    }

    public static String cleanHtmlText(String text)
    {
        // Null strings or strings with single space are treated as empty strings
        if (text == null || "&nbsp;".equals(text)
            || " ".equals(text)
            || "\u0020".equals(text)
            || "\u00A0".equals(text))
        {
            return "";
        }

        return text.replace((char) 0xA0, ' ').trim();
    }
}