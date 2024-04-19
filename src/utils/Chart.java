package utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.Map;

public class Chart {

    public Chart(){
    }
    public void drawGraphics(double[] x, double[] y, double[] polX, double[] polValue, String name){
        int n = x.length;
        XYSeries series1 = new XYSeries("Исходные данные");
        XYSeries series2 = new XYSeries(name);
//        XYSeries series3 = new XYSeries("Многочлен Ньютона");


        for(int i = 0; i < n; i++){
            series1.add(x[i], y[i]);
        }
        n = polX.length;
        for(int i = 0; i < n; i++){
            series2.add(polX[i], polValue[i]);
        }

//        n = newtonX.length;
//        for(int i = 0; i < n; i++){
//            series3.add(newtonX[i], newtonvalue[i]);
//        }

        XYSeriesCollection xyDataset1 = new XYSeriesCollection();
        xyDataset1.addSeries(series1);
        XYSeriesCollection xyDataset2 = new XYSeriesCollection();
        xyDataset2.addSeries(series2);
//        xyDataset2.addSeries(series3);

        show(xyDataset1, xyDataset2);
    }

    public void show(XYDataset dots, XYDataset line){

        XYItemRenderer rendererLine = new XYLineAndShapeRenderer();
        NumberAxis XAxis = new NumberAxis("x");
        NumberAxis YAxis = new NumberAxis("y");

        XYItemRenderer rendererScatter = new XYShapeRenderer();
        XYPlot plot = new XYPlot(dots, XAxis, YAxis, rendererScatter);
        plot.setRenderer(1, rendererLine);
        plot.setDataset(1, line);

        JFreeChart chart = new JFreeChart(plot);

        JFrame frame = new JFrame("График");
        frame.getContentPane().add(new ChartPanel(chart));

        frame.setSize(800, 800);
        frame.show();
    }


    public void drawTwoGraphics(double[] x, double[] y, double[]  polX, double[] polValue, String name){
        XYSeries series1 = new XYSeries("input data");
        int n = x.length;
        for(int i = 0; i < n; i++){
            series1.add(x[i], y[i]);
        }
        XYSeries series2 = new XYSeries(name);
        n = polX.length;
        for(int i = 0; i < n; i++){
            series2.add(polX[i], polValue[i]);
        }

        XYSeriesCollection xyDataset = new XYSeriesCollection();
        xyDataset.addSeries(series1);
        xyDataset.addSeries(series2);

        showLine(xyDataset, "aaaaa");
    }

    public void showLine(XYDataset xyDataset, String  methodName){
        JFreeChart chart = ChartFactory
                .createXYLineChart(methodName, "x", "y",
                        xyDataset,
                        PlotOrientation.VERTICAL,
                        true, true, true);

        JFrame frame = new JFrame("График");
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(800, 800);
        frame.show();
    }
}
