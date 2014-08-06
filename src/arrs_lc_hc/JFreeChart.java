/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arrs_lc_hc;

import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author RobertK
 */
public class JFreeChart {
    
     public DefaultCategoryDataset DataSet(int[] intDataArray, String[] stringDataArray, String Name)
       {
           /**this method adds values from an array and returns a data set */
           DefaultCategoryDataset dataset = new DefaultCategoryDataset();
           if (intDataArray.length==stringDataArray.length)/**Only adds array(s) to the dataset if they are the same length*/
           {
            for(int i=0;i<intDataArray.length;i++)
            {
             dataset.setValue(intDataArray[i], Name,stringDataArray[i] );/** Item Value, Name of item, Name value belongs to. example. 09,Score,Student1 */
            }    
           }
           return dataset;
       }
    
    
     public void displayBarGraph(DefaultCategoryDataset dataSet, String graphName, String xName, String yName) {
        /**Displays a Bar graph with the values passed to it in the dataset*/
        org.jfree.chart.JFreeChart chart = ChartFactory.createBarChart3D(graphName, xName, yName, dataSet, PlotOrientation.VERTICAL, false, true, false);//Creating instance of JfreeChart
        CategoryPlot p = chart.getCategoryPlot();//creating instance of a categoryplot 
        p.setRangeGridlinePaint(Color.red);
        ChartFrame Cframe = new ChartFrame(graphName, chart);//creates an instance of a ChartFrame, The graph needs to me displayed in this frame
        Cframe.setVisible(true);
        Cframe.setSize(1300, 500);//Setting size of frame
        Cframe.setLocationRelativeTo(null);
    }
     
     
       public void displayLineGraph(DefaultCategoryDataset dataSet ,String graphName, String xName, String yName) {
       /**Displays a Line graph with the values passed to it in the dataset*/
        org.jfree.chart.JFreeChart chart = ChartFactory.createLineChart3D(graphName, xName, yName, dataSet, PlotOrientation.VERTICAL, false, true, false);
        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.red);
        ChartFrame frame = new ChartFrame(graphName, chart);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setSize(1300, 500);
       }
    
       
      
}