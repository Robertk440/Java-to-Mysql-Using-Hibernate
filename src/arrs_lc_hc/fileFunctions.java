/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arrs_lc_hc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author RobertK
 */
public class fileFunctions {
    
     public String currentOpenFile;

      public String openFile() {
          /**Returns a string of a selected file*/
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(chooser);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            return chooser.getSelectedFile().getPath();
        }
     
      
        public String [][] readFile(String[][] readfile,String fileName,int fileLines, int lineEntries) {
            /**
             * Setting a new array for the file to be read into
             */
            readfile = new String[fileLines][lineEntries];
            try {
                BufferedReader br = new BufferedReader(new FileReader(fileName));/** This is used to read a file "fileName"*/
                for (int l = 0; l < fileLines; l++) {
                    String temp[];/**reads the file and splits the values in to an array "temp"*/
                    temp = br.readLine().split(",");
                    System.arraycopy(temp, 0, readfile[l], 0, temp.length); /**Copies temp array into the file array at index l*/
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return readfile;
        }
     
          public void WriteToFile(String[] saveLines, String txtFile) {
              /**Writes the values of an array to a file*/
            try {
                try (PrintWriter pw = new PrintWriter(txtFile)) {
                    for (int i = 0; i < saveLines.length; i++) {
                        pw.println(saveLines[i]);//writes array index to file
                    }
                }
                JOptionPane.showMessageDialog(null, "saved");
            } catch (IOException io) {
                JOptionPane.showMessageDialog(null, io + " - it's broken");
                System.out.println(io);
            }
        }
     public File createNewFile() {
         /**Creates a new file path to save to*/
        File file = new File("");
        JFileChooser filesave = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.csv", "csv");
        filesave.setFileFilter(filter);
        int returnVal = filesave.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = filesave.getSelectedFile();
          }
        return file;
    }
}