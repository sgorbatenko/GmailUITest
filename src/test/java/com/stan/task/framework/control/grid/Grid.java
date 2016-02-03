
package com.stan.task.framework.control.grid;

import com.stan.task.framework.datastructures.DataTable;


/**
 * Interface for grid controls in the framework
 */
public interface Grid
{
    /**
     * Select the row containing the specified text
     *
     * @param textToFind
     */
    void selectRowContainingText(String textToFind);

    /**
     * Get number of rows on the current page of the grid
     *
     * @return number of rows on the current page of the grid
     */
    int getRowCount();

    /**
     * Gets all of the grid data as a table. If the grid is a paging grid, gets the data across ALL pages
     *
     * @return Gets all of the grid data as a table. If the grid is a paging grid, gets the data across ALL pages
     */
    DataTable getTable();

    String getCellText(String searchColumnName, String searchFieldExactTextValue, String targetColumnName);
}
