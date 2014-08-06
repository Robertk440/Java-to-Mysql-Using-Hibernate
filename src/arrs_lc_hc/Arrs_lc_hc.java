/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arrs_lc_hc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author RobertK
 */
public class Arrs_lc_hc extends Frame implements ActionListener, WindowListener, KeyListener {
/**Setting variables for the program*/
    JTable jtable;
    DefaultTableModel model;
    String[] header = {"Qns", "Q1", "Q2", "Q3", "Q4", "Q5", "Q6", "Q7", "Q8", "Q9", "Q10", "Result"};
    String[][] fileContents;
    int fileLines = 21;
    int lineEntries = 11;
    int resultColum = 12;
    int rowCount = 21;
    int columnCount = 11;
    JButton clear, save, close, open, saveAs, barGraph, lineGraph;
    /**Setting an instance of classes*/
    fileFunctions _fileFunctions = new fileFunctions();
    tableFunctions _tableFunctions = new tableFunctions();
    JFreeChart _jFreeChart = new JFreeChart();

    public static void main(String[] args) {

    
        Arrs_lc_hc frame = new Arrs_lc_hc();
        frame.run();
        /**Calls the run method*/
    }

    private void run() {
        /**The run method calls all the methods required on load*/
        SpringLayout layout = new SpringLayout();/**Setting an instance of a spring layout.
         * (Spring layout is a layout manager used to position elements on the screen)*/
        FrameSettings(layout, 940, 680, "Reporting System Jtable");/**Calling the frameSettings method and passing (layout,width,height and frame title)*/
        this.addWindowListener(this);/**Adding a window listener to the frame*/
        displayTable(layout);/**Calling the displayTable method and passing it (layout)*/
        Buttons(layout);/**Calling the Buttons method and passing it (layout)*/
        setVisible(true);/**Setting the frame to visible*/ 
    }

    public void FrameSettings(SpringLayout layout, int w, int h, String title) {
        /**This method Sets the settings of the frame dependent on the values passed*/
        setSize(w, h);
        setResizable(false);
        setTitle(title);
        setLocationRelativeTo(null);
        setLayout(layout);
    }

    private void displayTable(SpringLayout layout) {
        /**Setting model and jtable to equal their setup methods*/
        model = tmodelSetup(model, header, 24, 11);
        jtable = jtableSetup(jtable, layout, model, 12, 23, 900, 577, 23);
        model.setValueAt("Avg:", 22, 11);/**Setting values in specific*/
        model.setValueAt("Mode:", 23, 0);
    }

    private DefaultTableModel tmodelSetup(DefaultTableModel dtm, String[] tHeader, int rowCount, int columnCount) {
       /**This method sets the properties for the default table model*/
        dtm = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                /**Allows user to edit cell values*/
                return true;
            }
        };
        dtm.setRowCount(rowCount);/**Setting the number of rows in the table*/
        for (int i = 0; i < columnCount + 1; i++) {
            dtm.addColumn(tHeader[i]);/**Adding the header array to the table, Giving the columns headings*/
        }
        return dtm;
    }

    private JTable jtableSetup(JTable table, SpringLayout layout, DefaultTableModel dtm, int x, int y, int w, int h, int rowHeight) {
          /**This method sets the properties for the JTable*/
        table = new JTable(dtm);
        JScrollPane sp = new JScrollPane(table); /**Adding the JTable to a Scroll Pane.*/
        add(sp);/**Adding Scroll pane to the screen*/
        layout.putConstraint(SpringLayout.WEST, sp, x, SpringLayout.WEST, this);/**Setting the location of the Scroll pane*/
        layout.putConstraint(SpringLayout.NORTH, sp, y, SpringLayout.NORTH, this);
        sp.setPreferredSize(new Dimension(w, h));/**Setting size of the Scroll pane*/       
        table.setRowHeight(rowHeight);/**Setting the height of the Row/Cell. */       
        table.getTableHeader().setBackground(new Color(00, 00, 99));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        table.getTableHeader().setForeground(Color.white);
        table.getTableHeader().setReorderingAllowed(false);/**Stops the user from moving the columns around. */
        table.addKeyListener(this);/**Adding a KeyListener to the table. Waiting for a key change.*/
        table.setDefaultRenderer(table.getColumnClass(0), new tblRenderer());/**Applying the custom cell render.*/

        return table;
    }

    public void Buttons(SpringLayout layout) {
        /**This method sets buttons to equal the displayButton method*/
        save = displayButton(layout, save, "Save", 120, 600, 100, 25);
        saveAs = displayButton(layout, saveAs, "Save as", 230, 600, 100, 25);
        open = displayButton(layout, open, "Open", 10, 600, 100, 25);
        close = displayButton(layout, close, "CLOSE", 810, 600, 100, 25);
        clear = displayButton(layout, clear, "Clear", 700, 600, 100, 25);
        barGraph = displayButton(layout, barGraph, "Results", 450, 600, 100, 25);
        lineGraph = displayButton(layout, lineGraph, "Score Ratio", 340, 600, 100, 25);
    }

    public JButton displayButton(SpringLayout buttonLayout, JButton button, String ButtonCaption, int x, int y, int w, int h) {    //The LocateButtons class pass through here and return a Button
       /**This method is used to pass a button through and give it the properties defined in this method */
        button = new JButton(ButtonCaption);
        add(button);
        button.addActionListener(this);
        buttonLayout.putConstraint(SpringLayout.WEST, button, x, SpringLayout.WEST, this);
        buttonLayout.putConstraint(SpringLayout.NORTH, button, y, SpringLayout.NORTH, this);
        button.setPreferredSize(new Dimension(w, h));

        return button;
    }

    private void displayFileContents(String[][] fileContents, int columnCount, int rowCount, JTable table) {
        /**This method takes an array and displays it in a table skipping the second row*/
        for (int y = 0; y < rowCount; y++) {
            if (y >= 1) {
                for (int x = 0; x < columnCount; x++) {
                    table.setValueAt(fileContents[y][x], y + 1, x);
                }

            } else {
                for (int x = 0; x < columnCount; x++) {
                    table.setValueAt(fileContents[y][x], y, x);
                }
            }
        }
    }

    private void clearTable(DefaultTableModel model, int xStart, int xStop, int yStart, int yStop) {
        /**Counts through the rows and sets value of cells at ""*/
        for (int x = xStart; x < xStop; x++) {
            for (int y = yStart; y < yStop; y++) {
                model.setValueAt(null, y, x);
            }
        }
    }

    private int BinarySearch(int[] intArray, int n, int x, boolean searchFirst) {
        /* This method uses a binary search to get the index of the first and last occurrences of "X" in a SORTED array*/
        int result = -1;
        int low = 0;
        int high = n - 1;

        while (low <= high) {
            /**While low is less than or equal to high keep looping through the array dividing it in half
             looking for "X"*/
            int mid = (low + high) / 2;
            if (intArray[mid] == x) {
                result = mid;
                if (searchFirst) {
                    high = mid - 1;/* Continue searching towards the lower end (left)*/
                } else {
                    low = mid + 1;/* Continue searching towards the higher end (right)*/
                }
            } else if (x < intArray[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return result;
    }

    private int numberOfDuplicates(int x, int[] intArray) {
        /**This method is passed an int array, Sorts it then finds how many times "X" appears in the array*/
        Arrays.sort(intArray);/**Sorts the array in to ascending order*/
        int firstIndex = BinarySearch(intArray, intArray.length, x, true);/**Calls the BinarySearch and passes the boolean as true*/
        if (firstIndex == -1) {
            return 0;
        } else {
            int lastIndex = BinarySearch(intArray, intArray.length, x, false);/**Calls the BinarySearch and passes the boolean as false*/
            return lastIndex - firstIndex + 1;/**LastIndex - firstIndex = number of duplicates*/
        }
    }

    private String[] CountToTen() {
        /**Simple method setting an array with the values of 1-10*/
        String[] count = new String[11];
        for (int i = 0; i < count.length; i++) 
            count[i] = Integer.toString(i);        
        return count;

    }

    private int[] lineGraphData(int[] intArray) {
        /**This methods returns an array of the number of duplicates of each value in an array */
        int[] intDataArray = new int[11];
        for (int i = 0; i < 11; i++) {
            intDataArray[i] = numberOfDuplicates(i, intArray);
        }
        return intDataArray;

    }

    private String calculateMode(String[] columnValues) {
        /**This method returns the mode of the answers given*/
        String mode = "";
        int A = 0;int B = 0;int C = 0;int D = 0;int most = 0;
        
        for (String value : columnValues) {
            /**For each string in String array*/
            if (value.equals("A")) A++;
            else if (value.equals("B"))B++;
            else if (value.equals("C"))C++;
            else if (value.equals("D"))D++;            
        }
        if (A == most) mode += "A";        
        if (A > most) {most = A; mode = "A";}
        
        if (B == most)mode += "B";
        if (B > most) {most = B; mode = "B";}
        
        if (C == most)mode += "C";        
        if (C > most) { most = C; mode = "C";}
        
        if (D == most)mode += "D";
        if (D > most) { most = D; mode = "D";}
        
        if (most == 0)mode = "N/A";

        return mode;
    }

    private int calculateCorrect(String[] correctAnswers, String[] givenAnswers) {
        /**Compares two arrays and counts how many are equal*/
        int c = 0;
        for (int i = 0; i < correctAnswers.length; i++)
        {
            if (!"0".equals(correctAnswers[i])) 
                if (correctAnswers[i].equals(givenAnswers[i]))c++;
        }
        return c;
    }

    private double calculateAvg(int[] scores) {
        /**Adds all the values in an array and divides by the array length to get the average*/
        double avg = 0;
        for (double value : scores)
            avg += value;   
        return avg / scores.length;
    }

    private int[] convertToIntArray(String[] convertFrom) {
        /**Converts a string array to an int array*/
        int[] convertTo = new int[convertFrom.length];
        for (int i = 0; i < convertFrom.length; i++) 
            convertTo[i] = Integer.parseInt(convertFrom[i].toString());
        return convertTo;
    }

    private void getScore() {
        /**This method loops through each row and sets a cells value to the number of correct answers*/
        for (int i = 2; i < 22; i++) {
            jtable.setValueAt(calculateCorrect(_tableFunctions.RowValue(jtable, 1, 11, 0), _tableFunctions.RowValue(jtable, 1, 11, i)), i, 11);
        }

    }

    private void getAvg() {
        /**Sets the value of a cell to the average of the total scores*/
        DecimalFormat format = new DecimalFormat("#.##");//defines new number format
        jtable.setValueAt(format.format(calculateAvg(convertToIntArray(_tableFunctions.ColumnValue(jtable, 11, 2, 22)))), 23, 11);

    }

    private void getMode() {
        /**Loops through the columns and returns the mode of answers given*/
        for (int i = 1; i < 11; i++) 
            jtable.setValueAt(calculateMode(_tableFunctions.ColumnValue(jtable, i, 2, 22)), 23, i);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == close) {
            System.exit(0);
        }

        if (e.getSource() == open) {
            _fileFunctions.currentOpenFile = _fileFunctions.openFile();
            displayFileContents(_fileFunctions.readFile(fileContents, _fileFunctions.currentOpenFile, fileLines, lineEntries), columnCount, rowCount, jtable);
            getScore();
            getAvg();
            getMode();
        }

        if (e.getSource() == clear) {
            clearTable(model, 1, 11, 0, 22);
            getScore();
            getAvg();
            getMode();
        }

        if (e.getSource() == save) {
            _fileFunctions.WriteToFile(_tableFunctions.TableValue(jtable, 0, 11, 0, 22), _fileFunctions.currentOpenFile);
        }
        if (e.getSource() == saveAs) {
            _fileFunctions.WriteToFile(_tableFunctions.TableValue(jtable, 0, 11, 0, 22), _fileFunctions.createNewFile().toString());
        }
        if (e.getSource() == barGraph) {
            _jFreeChart.displayBarGraph(_jFreeChart.DataSet(convertToIntArray(_tableFunctions.ColumnValue(jtable, 11, 2, 22)), _tableFunctions.ColumnValue(jtable, 0, 2, 22), "Score"), "Results", "Student Name", "Score");
        }
        if (e.getSource() == lineGraph) {
            _jFreeChart.displayLineGraph(_jFreeChart.DataSet(lineGraphData(convertToIntArray(_tableFunctions.ColumnValue(jtable, 11, 2, 22))), CountToTen(), "Score"), "Student Score Ratio", "Score", "No. of Students ");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        getMode();
        getScore();
        getAvg();
    }

    @Override
    public void windowClosing(WindowEvent we) {
        System.exit(0);
    }

    @Override
    public void windowIconified(WindowEvent we) {
    }

    @Override
    public void windowOpened(WindowEvent we) {
    }

    @Override
    public void windowClosed(WindowEvent we) {
    }

    @Override
    public void windowDeiconified(WindowEvent we) {
    }

    @Override
    public void windowActivated(WindowEvent we) {
    }

    @Override
    public void windowDeactivated(WindowEvent we) {
    }

    static class tblRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 0) {
                setBackground(new Color(255, 255, 153));
            } else {
                setBackground(Color.white);
            }

            if (row == 23 || column == 11) {
                setBackground(new Color(204, 255, 204));
            }
            if (row == 23 && column == 11) {
                setBackground(new Color(255, 255, 153));
            }
            if (row == 0 || row == 1 || row == 22) {
                setBackground(Color.white);
            }
            return this;
        }
    }
}