
package com.stan.task.framework.datastructures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.stan.task.framework.exception.CoreLevelException;

/*
 * Table structure which uses HashMap as row object representing weakly typed, propertyName, propertyValue pairs
 * Data Row list and column name lists are extended lists
 */
public class DataTable<T>
{
    private ArrayList<HashMap<String, T>> _rowList = new ArrayList<HashMap<String, T>>();
    private ArrayList<String> _columnNameList = new ArrayList<String>();

    // Extended map holding some or all column names as keys, objects representing
    // default values to use as default Row and/or examples of the column types
    private HashMap<String, T> _defaultRowTemplate = new HashMap<String, T>();

    private String _testName = "";

    public DataTable(String testName)
    {
        _testName = testName;
    }

    public DataTable(String testName, ArrayList<String> columnNames)
    {
        _testName = testName;

        _columnNameList = columnNames;
    }

    public DataTable(String testName, HashMap<String, T> defaultRowTemplate)
    {
        _testName = testName;

        _columnNameList = (ArrayList) defaultRowTemplate.keySet();

        _defaultRowTemplate = defaultRowTemplate;
    }

    public DataTable(String testName, ArrayList<String> columnNames, HashMap<String, T> defaultRowTemplate)
    {
        _testName = testName;

        _columnNameList = columnNames;

        _defaultRowTemplate = defaultRowTemplate;
    }

    /**
     * Get the number of columns in the table
     *
     * @return the number of columns in the table
     */
    public int getColumnCount()
    {
        return _columnNameList.size();
    }

    /**
     * Get the Number of rows in the table
     *
     * @return Number of rows in the table
     */
    public int getRowCount()
    {
        return _rowList.size();
    }

    /**
     * set the column name list, syncing up any existing rows to the new column
     * name list (Does not change or do anything to the default row template)
     *
     * @param columnNameList
     */
    public void setColumnNames(ArrayList<String> columnNameList)
    {
        _columnNameList = columnNameList;
    }

    /**
     * Add a column with new row default value, and sync existing rows to the
     * column
     *
     * @param columnName
     * @param defaultValue
     */
    public void addColumn(String columnName, T defaultValue)
    {
        String effectiveColumnName = columnName;

        if (_columnNameList.contains(columnName))
        {
            effectiveColumnName = columnName + "<+>";
        }

        _columnNameList.add(effectiveColumnName);

        _defaultRowTemplate.put(effectiveColumnName, defaultValue);

    }

    /**
     * If a column name is duplicated in the table (it occurs more than once), build the
     * actual column name in this table given the root column name and the 1-based occurence ordinal
     *
     * @param rootColumnName
     * @param occurenceOrdinal
     * @return the actual column name given the column label and the 1-based occurence ordinal
     */
    public static String getDuplicatedColumnName(String rootColumnName, int occurenceOrdinal)
    {
        String columnName = rootColumnName;

        for (int index = 1; index < occurenceOrdinal; index++)
        {
            columnName += "<+>";
        }

        return columnName;
    }


    /**
     * Set the default row template for this table, Sets the default row
     * template for the table, which is a reference row to be copied to create
     * new rows (Does not change or do anything to the column name list or any
     * rows)
     *
     * @param defaultRowTemplate
     */
    public void setDefaultRowTemplate(HashMap<String, T> defaultRowTemplate)
    {
        _defaultRowTemplate = defaultRowTemplate;
    }

    /**
     * Gets the default row template for the table, which is a reference row to
     * be copied to create new rows
     *
     * @return the default row template
     */
    public HashMap<String, T> getDefaultRowTemplate()
    {
        return _defaultRowTemplate;
    }

    /**
     * Returns the list of rows (not a copy)
     *
     * @return list of rows
     */
    public ArrayList<HashMap<String, T>> getRows()
    {
        return _rowList;
    }



    /**
     * Adds row to the table, returning the Row Index of the added row
     *
     * @param row
     * @return Row Index of the added row
     */
    public int addRow(HashMap<String, T> row)
    {
        _rowList.add(row);

        return getRowCount() - 1;
    }

    /**
     * Adds rows to the table
     *
     * @param rows
     */
    public void addRows(ArrayList<HashMap<String, T>> rows)
    {
        for (HashMap<String, T> rowObject : rows)
        {
            addRow(rowObject);
        }
    }

    /**
     * Returns row at the specified row index
     *
     * @param rowIndex
     * @return row at the specified row index
     */
    public HashMap<String, T> getRow(int rowIndex)
    {
        return _rowList.get(rowIndex);
    }

    /**
     * Get the values from all the rows of the column with specified column name
     * (if a row doesn't have column value, which should not happen, use null
     * for that row)
     *
     * @param columnName
     * @return the values from all the rows of the column with specified column
     *         name
     */
    public ArrayList<T> getColumnValues(String columnName)
    {
        if (!_columnNameList.contains(columnName))
        {
            throw new CoreLevelException("Cannot get values from column '" + columnName
                + "' from table, no such column in table");
        }

        ArrayList<T> columnValueList = new ArrayList<T>();

        for (HashMap<String, T> row : _rowList)
        {
            if (row.containsKey(columnName))
            {
                columnValueList.add(row.get(columnName));
            }
            else
            {
                columnValueList.add(null);
            }
        }

        return columnValueList;
    }

    /**
     * Get a list of the values in the column with the specified zero-based index
     *
     * @param columnIndex
     *        zero-based column index
     * @return Get a list of the values in the column with the specified zero-based index
     */
    public ArrayList<T> getColumnValues(int columnIndex)
    {
        if (columnIndex >= _columnNameList.size())
        {
            throw new CoreLevelException("Cannot get values from column " + String.valueOf(columnIndex)
                + " from table, table only has " + String.valueOf(_columnNameList.size()) + " columns");
        }

        return getColumnValues(_columnNameList.get(columnIndex));
    }

    /**
     * Get the value of the cell at specified row index and column name,
     * throwing exception if there is no such cell
     *
     * @param rowIndex
     * @param columnName
     * @return value of the cell at specified row index and column name
     */
    public T getCellValue(int rowIndex, String columnName)
    {
        return getRow(rowIndex).get(columnName);
    }

    /**
     * Get the value of the cell in the target column, in the row where the value of the cell in column searchColumnName is exactly searchColumnValue
     *
     * @param searchColumnName
     * @param searchColumnValue
     * @param targetColumnName
     * @return the value of the cell in the target column, in the row where the value of the cell in column searchColumnName is exactly searchColumnValue
     */
    public T getCellValue(String searchColumnName, String searchColumnValue, String targetColumnName)
    {
        HashMap<String, T> row = rowWithTextInColumn(searchColumnName, searchColumnValue);

        if (row == null)
        {
            return null;
        }

        return row.get(targetColumnName);
    }

    /**
     * return 0-based index of the first row with all the specified exact values
     *
     * @param values
     *        list of exact values to match
     * @return -based index of the first row with all the specified values, or -1 if no
     *         such row exists
     */
    public int indexOfRowWithCellValues(List<T> values)
    {
        for (int rowIndex = 0; rowIndex < getRowCount(); rowIndex++)
        {
            boolean rowContainsAllValues = true;
            Collection<T> rowValues = getRow(rowIndex).values();

            for (T value : values)
            {
                // If the row doesn't contain the value (treat null and empty string as equals)
                if (!rowValues.contains(value)
                    &&
                    !(value == null & rowValues.contains("")))
                {
                    rowContainsAllValues = false;

                    break;
                }
            }

            if (rowContainsAllValues)
            {
                return rowIndex;
            }
        }

        return -1;
    }

    /**
     * Return True if a row exists with all the specified values
     *
     * @param values
     * @return True if a row exists with all the specified values
     */
    public boolean hasRowWithValues(List<T> values)
    {
        return indexOfRowWithCellValues(values) != -1;
    }

    /**
     * Get the row with a cell containing specified partial text, throwing exception if not found
     *
     * @param partialText
     * @return Get the row with a cell containing specified partial text, throwing exception if not found
     */
    public HashMap<String, T> rowWithCellContainingText(String partialText)
    {
        for (HashMap<String, T> row : getRows())
        {
            for (T cellValue : row.values())
            {
                if (cellValue != null && cellValue.toString().contains(partialText))
                {
                    return row;
                }

                // TestLogger.log("'" + partialText + "' not found in " + TestLogger.getLogString(cellValue));
            }
        }

        return null;
    }



    /**
     * Return the row with exact specified text in the specified column, or null if not found
     *
     * @param columnName
     * @param exactText
     * @return the row with exact specified text in the specified column, or null if not found
     */
    public HashMap<String, T> rowWithTextInColumn(String columnName, String exactText)
    {
        if (exactText != null)
        {
            for (HashMap<String, T> row : getRows())
            {
                if (row.containsKey(columnName) && exactText.equals(row.get(columnName)))
                {
                    return row;
                }
            }
        }

        return null;
    }

    /**
     * the Row Index of the first row with a cell whose text matches specified exact Text, or -1 if not found
     *
     * @param exactText
     * @return the Row Index of the first row with a cell whose text matches specified exact Text, or -1 if not found
     */
    public int rowIndexWithCellEqualsText(String exactText)
    {
        int rowIndex = -1;

        for (HashMap<String, T> row : getRows())
        {
            rowIndex++;

            for (T cellValue : row.values())
            {
                if (cellValue != null && cellValue.equals(exactText))
                {
                    return rowIndex;
                }
            }
        }

        return -1;
    }

    /**
     * Return the number of rows that have at least one cell with the exact text specified
     *
     * @param exactText
     * @return the number of rows that have at least one cell with the exact text specified
     */
    public int getRowCountWithCellEqualsText(String exactText)
    {
        int matchingRowCount = 0;

        if (exactText != null)
        {
            boolean rowContainsCellEqualsText;

            for (HashMap<String, T> row : getRows())
            {
                rowContainsCellEqualsText = false;

                for (T cellValue : row.values())
                {
                    if (exactText.equals(cellValue.toString()))
                    {
                        rowContainsCellEqualsText = true;
                        break;
                    }
                }

                if (rowContainsCellEqualsText)
                {
                    matchingRowCount++;
                }
            }
        }

        return matchingRowCount;
    }

    /**
     * return True if the table contains at least one row with a cell whose text equals the specified text
     *
     * @param exactText
     * @return True if the table contains at least one row with a cell whose text equals the specified text
     */
    public boolean hasRowWithCellEqualsText(String exactText)
    {
        if (exactText != null)
        {
            for (HashMap<String, T> row : getRows())
            {
                for (T cellValue : row.values())
                {
                    if (exactText.equals(cellValue.toString()))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * return True if the table contains at least one row with a cell whose text equals the specified text in the specified column
     *
     * @param columnName
     * @param exactText
     * @return True if the table contains at least one row with a cell whose text equals the specified text in the specified column
     */
    public boolean hasRowWithTextInColumn(String columnName, String exactText)
    {
        if (exactText != null)
        {
            for (HashMap<String, T> row : getRows())
            {
                if (row.containsKey(columnName) && exactText.equals(row.get(columnName)))
                {
                    return true;
                }
            }
        }

        return false;
    }
}