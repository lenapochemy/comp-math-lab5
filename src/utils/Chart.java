package utils;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.Vector;

public class Chart {

    public Chart(){
    }
    public void drawGraphics(double[] x, double[] y, Vector<Double> polX, Vector<Double> polValue, String name){
        int n = x.length;
        XYSeries series1 = new XYSeries("Исходные данные");
        XYSeries series2 = new XYSeries(name);
        for(int i = 0; i < n; i++){
            series1.add(x[i], y[i]);
        }
        n = polX.size();
        for(int i = 0; i < n; i++){
            series2.add(polX.get(i), polValue.get(i));
        }
        XYSeriesCollection xyDataset1 = new XYSeriesCollection();
        xyDataset1.addSeries(series1);
        XYSeriesCollection xyDataset2 = new XYSeriesCollection();
        xyDataset2.addSeries(series2);

        show(xyDataset1, xyDataset2, null);
    }

    public void show(XYDataset dots, XYDataset line1, XYDataset line2){

        XYItemRenderer rendererLine = new StandardXYItemRenderer();
        XYItemRenderer rendererLine2 = new StandardXYItemRenderer();
        NumberAxis XAxis = new NumberAxis("x");
        NumberAxis YAxis = new NumberAxis("y");

        XYItemRenderer rendererScatter = new XYShapeRenderer();
        XYPlot plot = new XYPlot(dots, XAxis, YAxis, rendererScatter);
        plot.setDataset(2, line2);
        plot.setRenderer(2, rendererLine2);
        plot.setRenderer(1, rendererLine);
        plot.setDataset(1, line1);


        JFreeChart chart = new JFreeChart(plot);

        JFrame frame = new JFrame("График");
        frame.getContentPane().add(new ChartPanel(chart));

        frame.setSize(800, 800);
        frame.show();
    }

//    public void drawTwoGraphics(double[] x, double[] y, Vector<Double> polX, Vector<Double> polValue,
//                                Vector<Double> pol2X, Vector<Double> pol2Value){
//        int n = x.length;
//        XYSeries series1 = new XYSeries("Исходные данные");
//        XYSeries series2 = new XYSeries("Многочлен Лагранжа");
//        XYSeries series3 = new XYSeries("Многочлен Ньютона");
//        for(int i = 0; i < n; i++){
//            series1.add(x[i], y[i]);
//        }
//        n = polX.size();
//        for(int i = 0; i < n; i++){
//            series2.add(polX.get(i), polValue.get(i));
//        }
//        n = pol2X.size();
//        for(int i = 0; i < n; i++){
//            series3.add(pol2X.get(i), pol2Value.get(i));
//        }
//
//        XYSeriesCollection xyDataset1 = new XYSeriesCollection();
//        xyDataset1.addSeries(series1);
//        XYSeriesCollection xyDataset2 = new XYSeriesCollection();
//        xyDataset2.addSeries(series2);
//        XYSeriesCollection xyDataset3 = new XYSeriesCollection();
//        xyDataset3.addSeries(series3);
//
//        show(xyDataset1, xyDataset2, xyDataset3);
//    }

}
