/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arrs_lc_hc;

import javax.swing.JTable;

/**
 *
 * @author RobertK
 */
public class tableFunctions {
/**This class is designed to get values from JTables*/
    public String[] ColumnValue(JTable table, int x, int yStart, int yStop) {
        /**Gets the value from a column(x) starting at row (yStart) and stopping at row (yStop)
         returns a string array*/
        String[] columnValue = new String[yStop - yStart];
        for (int i = yStart; i < yStop; i++) {
            if (table.getValueAt(i, x) != null) {
                columnValue[i - yStart] = table.getValueAt(i, x).toString();
            } else {
                columnValue[i - yStart] = "0";
            }
        }
        return columnValue;
    }

    public String[] RowValue(JTable table, int xStart, int xStop, int y) {
        /**Gets the value from a row(y) starting at column (xStart) and stopping at column (xStop)
         returns a string array*/
        String[] rowValue = new String[xStop - xStart];
        for (int i = xStart; i < xStop; i++) {
            if (table.getValueAt(y, i) != null) {
                rowValue[i - xStart] = table.getValueAt(y, i).toString();
            } else {
                rowValue[i - xStart] = "0";
            }
        }
        return rowValue;
    }

    public String[] TableValue(JTable table, int xStart, int xStop, int yStart, int yStop) {
        /**This method gets all the values from a table and places them in to an array
         *Example array format
         * array[0] = A,A,B,D,C,A,B,C,D,A,C,D
         * array[1] = B,B,C,A,B,D,C,D,A,B,D,B
         * array[2] = C,D,A,B,A,C,A,B,B,C,A,C
         */
        String[] tableValue = new String[yStop - yStart];
        String row;
        int skippedLines = 0;
        boolean BlankLine;
        for (int y = yStart; y < yStop; y++) {
            row = "";
            BlankLine = false;
            for (int x = xStart; x < xStop; x++) {
                if (table.getValueAt(y, x) != null) {
                    row += table.getValueAt(y, x) + ",";
                } else {
                    skippedLines++;
                    BlankLine = true;
                    break;
                }
            }
            if (BlankLine == false) {
                String substring = row.substring(0, row.length() - 1);
                tableValue[y - skippedLines] = substring;
            }
        }
        return tableValue;
    }
}