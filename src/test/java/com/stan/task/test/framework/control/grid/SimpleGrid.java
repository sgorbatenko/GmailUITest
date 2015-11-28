
package com.stan.task.test.framework.control.grid;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.stan.task.test.framework.TestLogger;
import com.stan.task.test.framework.control.CheckBox;
import com.stan.task.test.framework.control.Element;
import com.stan.task.test.framework.control.ElementHtmlDoc;
import com.stan.task.test.framework.control.ElementLocator;
import com.stan.task.test.framework.control.HtmlDoc;
import com.stan.task.test.framework.datastructures.DataTable;
import com.stan.task.test.framework.exception.ControlLayerException;
import com.stan.task.test.framework.page.Page;
import com.stan.task.test.framework.utils.ScrollUtils;
import com.stan.task.test.framework.utils.WebElementHelper;

/**
 * The SimpleGrid Class: no header, no checkboxes, no paging - just grid. Base class for many other grids
 */
public class SimpleGrid extends Element // implements Grid
{
    protected static final String XPATH_CHILD_PREFIX = ".";

    private static final String ROWS_WITH_LINK_EQUALS_TEXT_XPATH = XPATH_CHILD_PREFIX + "//tr[td//a[text()='%s']]";
    private static final String ROW_COLUMNTEXTWITHICON_XPATH = XPATH_CHILD_PREFIX + "//tr/td/div[contains(text(),'%s')]/../../td/div[contains(@class, 'x-grid-cell-inner')]//label[text()='%s']";

    protected static final String ROW_CONTAINING_ANY_CELL_WITH_EXACT_TEXT_XPATH = XPATH_CHILD_PREFIX + "//tr[td/div[contains(@class, 'x-grid-cell-inner') and text()='%s']]";

    protected static final String ROW_CONTAINING_ANY_CELL_WITH_ELEMENT_WITH_EXACT_TEXT_XPATH = XPATH_CHILD_PREFIX + "//tr[td/div[text()='%s']]";

    protected static final String ROW_CONTAINING_ANY_CELL_WITH_LINK_WITH_EXACT_TEXT_XPATH = XPATH_CHILD_PREFIX + "//tr[td/div[contains(@class, 'x-grid-cell-inner')]/a[text()='%s']]";

    protected static final String GRID_VIEW_PANEL_CSS = "div.x-grid-view";
    protected static final String TABLE_CSS = GRID_VIEW_PANEL_CSS + " table.x-grid-table";

    private static final String CELL_WITH_COLUMN_ORDINAL_IN_ROW_WITH_EXACT_TEXT_CSS = "tr:has(**td > div:text(**%s**)**) td:nth-child(%s)";
    private static final String CELL_WITH_COLUMN_ORDINAL_IN_ROW_WITH_PARTIAL_TEXT_CSS = "tr:has(**td > div:contains(**%s**)**) td:nth-child(%s)";

    private static final String DIRTY_CELL_CSS = ".x-grid-dirty-cell";

    private static final String CELL_BY_CONTAINER_ROW_AND_COLUMN_ORDINAL_XPATH = XPATH_CHILD_PREFIX + "//tr[contains(@id, '%s')]//tr[%d]/td[%d]/div[contains(@class, 'x-grid-cell-inner')]";
    private static final String CELL_BY_ROW_AND_COLUMN_ORDINAL_XPATH = XPATH_CHILD_PREFIX + "//tr[%d]/td[%d]/div[contains(@class, 'x-grid-cell-inner')]";
    private static final String CELL_BY_COLUMN_ORDINAL_WITH_EXACT_TEXT_XPATH = XPATH_CHILD_PREFIX + "//tr/td[%d]/div[contains(@class, 'x-grid-cell-inner') and text()='%s']";

    public static final String QUERY_ROWS_CSS = " .zA";
    public static final String SUBQUERY_CELLS_CSS = " div span";

    protected static final String COLUMN_HEADERS_TEXT_CSS = ".x-column-header-text";

    private static final String ROW_IS_SELECTED_ATTRIBUTE_SUBSTRING = null;

    private static final String ROW_CHECKBOX_CSS = "T-Jo";

    public SimpleGrid(Page parentBrowserItem, ElementLocator elementLocator, String fieldControlName)
    {
        super(parentBrowserItem, elementLocator, fieldControlName);
    }

    public SimpleGrid(Page parentBrowserItem, List<ElementLocator> elementLocators, String fieldControlName)
    {
        super(parentBrowserItem, elementLocators, fieldControlName);
    }

    public SimpleGrid(WebElement element, String controlName)
    {
        super(element, controlName);
    }

    /**
     * return true if the grid has a row with the exact text in the column with the specified 1-based ordinal
     * 
     * @param columnOrdinal
     *        1-based column ordinal
     * @param exactCellText
     *        exact cell text to match
     * @return return true if the grid has a row with the exact text in the column with the specified 1-based ordinal
     */
    public boolean hasRowWithTextInColumn(int columnOrdinal, String exactCellText)
    {
        String locator = String.format(CELL_BY_COLUMN_ORDINAL_WITH_EXACT_TEXT_XPATH, columnOrdinal, exactCellText);
        WebElement row = findChildSeleniumWebElement(By.xpath(locator));
        return row != null;
    }

    /**
     * Selects a row containing the specified text in the specified column.
     * 
     * @param columnOrdinal
     *        the 1-based column ordinal of the column to search for the text
     * @param exactText
     *        the text to find
     */
    public void selectRowWithTextInColumn(int columnOrdinal, String exactText)
    {
        String locator = String.format(CELL_BY_COLUMN_ORDINAL_WITH_EXACT_TEXT_XPATH, columnOrdinal, exactText);
        WebElement row = findChildSeleniumWebElement(By.xpath(locator));
        row.click();

    }

    public int getRowCountWithCellEqualsText(String text)
    {
        List<WebElement> rows = findChildSeleniumWebElements(By.xpath(ROW_CONTAINING_ANY_CELL_WITH_EXACT_TEXT_XPATH.replaceFirst("%s",
            text)));

        int size = 0;

        if (rows != null)
        {
            size = rows.size();
        }
        return size;
    }

    public int getRowCountWithCellWithLinkEqualsText(String text)
    {
        List<WebElement> rows = findChildSeleniumWebElements(By.xpath(ROW_CONTAINING_ANY_CELL_WITH_LINK_WITH_EXACT_TEXT_XPATH
            .replaceFirst("%s", text)));

        int size = 0;

        if (rows != null)
        {
            size = rows.size();
        }
        return size;
    }

    public boolean isIncorrectlyFilledCellPresent()
    {
        WebElement tableElement = findChildSeleniumWebElement(By.cssSelector(DIRTY_CELL_CSS));
        return tableElement != null;
    }

    /**
     * Gets text of cell by its 1-based row and column ordinals
     * 
     * @param rowOrdinal
     *        1-based row ordinal
     * @param columnOrdinal
     *        1-based column ordinal
     * 
     * @return cell text
     */
    public String getCellText(int rowOrdinal, int columnOrdinal)
    {
        try
        {
            String filledInXpath = String.format(CELL_BY_ROW_AND_COLUMN_ORDINAL_XPATH, rowOrdinal, columnOrdinal);
            WebElement cell = findChildSeleniumWebElement(By.xpath(filledInXpath));

            return cell.getText();
        }
        catch (Throwable exception)
        {
            throw new ControlLayerException(
                "Could not get cell text with row ordinal " + rowOrdinal
                    + " and column ordinal " + columnOrdinal,
                exception);
        }
    }

    /**
     * Gets the cell text by container name and its 1-based row and column ordinals
     * 
     * @param container
     *        - container name
     * @param rowOrdinal
     *        1-based row ordinal
     * @param columnOrdinal
     *        1-based column ordinal
     * @return cell text
     */
    public String getCellText(String container, int rowOrdinal, int columnOrdinal)
    {
        String filledInXpath = String.format(CELL_BY_CONTAINER_ROW_AND_COLUMN_ORDINAL_XPATH,
            container,
            rowOrdinal,
            columnOrdinal);
        WebElement cell = findChildSeleniumWebElement(By.xpath(filledInXpath));
        return cell.getText();
    }

    public boolean hasScroll()
    {
        try
        {
            WebElement scrollableElement = getScrollableElement();

            return ScrollUtils.hasScroll(scrollableElement);
        }
        catch (Throwable exception)
        {
            return false;
        }
    }

    public void scrollTo(WebElement scrollToElement)
    {
        if (!hasScroll())
        {
            throw new ControlLayerException("Grid doesn't have scroll");
        }
        WebElement scrollableElement = getScrollableElement();
        ScrollUtils.scrollTo(scrollToElement, scrollableElement);
    }

    /**
     * Scrolls the content of the grid to the left.
     * 
     * @param pixels
     *        the number of pixels by which the contents is scrolled to the left respective to the beginning.
     */
    public void scrollLeftIfScrollable(String pixels)
    {
        try
        {
            WebElement scrollableElement = getScrollableElement();
            WebElementHelper.setScrollLeft(scrollableElement, pixels);
        }
        catch (Throwable e)
        {
            return;
        }
    }

    /**
     * Scrolls the content to the left to its maximum value.
     */
    public void scrollToLeft()
    {
        int maxValue = ScrollUtils.getLeftMaximunScrollValue(getScrollableElement());
        scrollLeftIfScrollable(String.valueOf(maxValue));
    }

    /**
     * Scroll to this element if it is scrollable
     * 
     * @param elementToScrollTo
     */
    public void scrollToIfScrollable(WebElement elementToScrollTo)
    {
        try
        {
            WebElement scrollableElement = getScrollableElement();

            ScrollUtils.scrollTo(elementToScrollTo, scrollableElement);
        }
        catch (Throwable exception)
        {
            return;
        }
    }

    private WebElement getScrollableElement()
    {
        WebElement scrollableElement = getSeleniumWebElement(true).findElement(By.cssSelector(GRID_VIEW_PANEL_CSS));
        return scrollableElement;
    }

    protected List<String> getColumnHeaders() // String cssSelector)
    {
        // ArrayList<String> allColumnHeaders = new ArrayList<String>();
        //
        // String columnHeader;
        //
        // scrollToRight();
        //
        //
        // for (int iteration = 0; iteration < 2; iteration++)
        // {
        // if (iteration == 1)
        // {
        // scrollToLeft();
        //
        // }
        //
        // for (WebElement columnHeaderWebElement : getSeleniumWebElement(true).findElements(By.cssSelector(cssSelector)))
        // {
        // columnHeader = GridHtmlDoc.cleanHtmlText(columnHeaderWebElement.getText());
        //
        // if ((iteration == 0 || !allColumnHeaders.contains(columnHeader)) && !columnHeader.equals(""))
        // {
        // allColumnHeaders.add(columnHeader);
        // }
        // }
        //
        // }
        //
        // scrollToRight();
        //
        // return allColumnHeaders;
        return null;
    }


    public void scrollToRight()
    {
        try
        {
            WebElement scrollableElement = getScrollableElement();
            WebElementHelper.setScrollLeft(scrollableElement, "0");
        }
        catch (Throwable e)
        {
            return;
        }
    }

    public boolean hasRowWithIconContainingColumnValue(String textRowIdentifier, String value)
    {
        String locator = String.format(ROW_COLUMNTEXTWITHICON_XPATH,
            textRowIdentifier,
            value);
        WebElement row = findChildSeleniumWebElement(By.xpath(locator));
        return row != null;
    }

    public int getRowCountWithCellEqualsLinkText(String exactLinkCellText)
    {
        String locator = String.format(ROWS_WITH_LINK_EQUALS_TEXT_XPATH, exactLinkCellText);

        List<WebElement> rows = findChildSeleniumWebElements(By.xpath(locator));

        int size = 0;

        if (rows != null)
        {
            size = rows.size();
        }
        return size;
    }

    public int getRowCount()
    {
        return 0;
    }

    public DataTable<String> getTable(String rowsQueryCss)
    {
        DataTable<String> table = new DataTable<String>("Table Data");
        int colIndex;

        for (WebElement rowElement : GetElementsByCSS(rowsQueryCss))
        {
            HashMap<String, String> newRow = new HashMap<String, String>();

            colIndex = -1;

            for (WebElement cell : rowElement.findElements(By.cssSelector(SUBQUERY_CELLS_CSS)))
            {
                colIndex++;

                table.addColumn(String.valueOf(colIndex), "");

                newRow.put(Integer.toString(colIndex), HtmlDoc.cleanHtmlText(cell.getText()));
            }

            table.addRow(newRow);
        }

        return table;
    }

    /**
     * If the row's selected state does not pass the specified state, click the row to toggle the state
     * 
     * @param exactText
     *        the text to find
     * @param isSelected
     *        the selected state of the row to set
     */
    public void setSelectedStateForRowContainingExactCellText(String exactText, boolean isSelected)
    {
        TestLogger.writeStep("Set the row's selected state to " + isSelected
            + " for row containing cell with exact text '" + exactText + "' in " + getDescription());

        WebElement row = getRowContainingCellWithExactText(exactText);

        if (row == null)
        {
            TestLogger.fail("Did not find row with a cell with exact text '" + exactText
                + "' in " + getDescription());
        }
        else
        {
            try
            {
                CheckBox checkbox = new CheckBox(row.findElement(By.cssSelector(ROW_CHECKBOX_CSS)),
                    "Row CheckBox");
                if (!checkbox.isChecked())
                    checkbox.click();
                else TestLogger.log("(Row was not clicked, its selection state was already found to be " + isSelected + ")");
            }
            catch (Exception exception)
            {
                TestLogger.fail("Error clicking Row Checkbox in " + getDescription() + " - " + exception.getMessage());
            }

        }
    }

    /**
     * Return true if the row containing a cell with the specified exact text is selected
     * 
     * @param exactText
     * @return true if the row containing a cell with the specified exact text is selected
     */
    public boolean getSelectedStateForRowContainingExactCellText(String exactText)
    {
        // String xpath = String.format(SELECTED_CHECKBOX_XPATH, textToFind);
        // getParentClientBrowser().waitForElementPresentAndVisibleByXpath(getSeleniumWebElement(false),
        // String.format(CHECKBOX_XPATH, textToFind));
        // WebElement row = findChildSeleniumWebElement(By.xpath(xpath));
        // return row != null;
        WebElement row = getRowContainingCellWithExactText(exactText);

        if (row == null)
        {
            throw new ControlLayerException("Did not find row with a cell with exact text '" + exactText
                + "' in "
                + getDescription());
        }

        return row.getAttribute(CLASS_ATTRIBUTE).contains(ROW_IS_SELECTED_ATTRIBUTE_SUBSTRING);
    }

    /**
     * return the row selenium web element that contains a cell with the exact specified text, or null if no matching row found
     * 
     * @param exactCellText
     * @return the row selenium web element that contains a cell with the exact specified text, or null if no matching row found
     */
    public WebElement getRowContainingCellWithExactText(String exactCellText)
    {
        for (int trialIndex = 0; trialIndex < 3; trialIndex++)
        {
            try
            {
                List<WebElement> rowSeleniumWebElements = getRowSeleniumWebElements();

                if (rowSeleniumWebElements != null)
                {
                    // If current row has a cell with the exact text, return it
                    for (WebElement rowWebElement : rowSeleniumWebElements)
                    {
                        if (new ElementHtmlDoc(rowWebElement, "Grid Row")
                            .hasElementWithExactText(SUBQUERY_CELLS_CSS, exactCellText))
                        {
                            return rowWebElement;
                        }
                    }
                }
            }
            catch (Exception exception)
            {
                // if we get error on the last iteration, fail
                if (trialIndex == 2)
                {
                    TestLogger.fail("Could not query grid rows of " + getDescription(), exception);
                }
            }
        }
        return null;
    }

    /**
     * Return a list of the selenium web elements for the grid's rows, for internal use only
     * 
     * @return a list of the selenium web elements for the grid's rows
     */
    protected List<WebElement> getRowSeleniumWebElements()
    {
        return findChildSeleniumWebElements(By.cssSelector(QUERY_ROWS_CSS));
    }
}