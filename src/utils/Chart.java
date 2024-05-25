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

        show(xyDataset1, xyDataset2);
    }

    public void show(XYDataset dots, XYDataset line1){

        XYItemRenderer rendererLine = new StandardXYItemRenderer();
        XYItemRenderer rendererLine2 = new StandardXYItemRenderer();
        NumberAxis XAxis = new NumberAxis("x");
        NumberAxis YAxis = new NumberAxis("y");

        XYItemRenderer rendererScatter = new XYShapeRenderer();
        XYPlot plot = new XYPlot(dots, XAxis, YAxis, rendererScatter);
//        plot.setDataset(2, line2);
//        plot.setRenderer(2, rendererLine2);
        plot.setRenderer(1, rendererLine);
        plot.setDataset(1, line1);


        JFreeChart chart = new JFreeChart(plot);

        JFrame frame = new JFrame("График");
        frame.getContentPane().add(new ChartPanel(chart));

        frame.setSize(800, 800);
        frame.show();
    }

    public void drawForAll(double[] x, double[] y, Vector<Double> lagrX, Vector<Double> lagrY,
                           Vector<Double> newX, Vector<Double> newY, Vector<Double> gaussX, Vector<Double> gaussY,
                           Vector<Double> stirlX, Vector<Double> stirlY,  Vector<Double> bessX, Vector<Double> bessssY
                           ){

        XYSeriesCollection xyDataset1 = new XYSeriesCollection();
        XYSeriesCollection xyDataset2 = new XYSeriesCollection();
//        xyDataset2.addSeries(series4);

        XYSeries series1 = new XYSeries("Точное значение");
        int n = x.length;
        for(int i = 0; i < n; i++){
            series1.add(x[i], y[i]);
        }
        xyDataset1.addSeries(series1);

        XYSeries series2 = new XYSeries("Метод Лагранжа");
        n = lagrX.size();
        for(int i = 0; i < n; i++){
            series2.add(lagrX.get(i), lagrY.get(i));
        }
        xyDataset2.addSeries(series2);
        if(newX != null) {
            XYSeries series3 = new XYSeries("Метод Ньютона");
            n = newX.size();
            for (int i = 0; i < n; i++) {
                series3.add(newX.get(i), newY.get(i));
            }
            xyDataset2.addSeries(series3);
        }

        if(gaussX != null) {
            XYSeries series4 = new XYSeries("Метод Гаусса");
            n = gaussX.size();
            for (int i = 0; i < n; i++) {
                series4.add(gaussX.get(i), gaussY.get(i));
            }
            xyDataset2.addSeries(series4);
        }

        if(stirlX != null) {
            XYSeries series5 = new XYSeries("Метод Стирлинга");
            n = stirlX.size();
            for (int i = 0; i < n; i++) {
                series5.add(stirlX.get(i), stirlY.get(i));
            }
            xyDataset2.addSeries(series5);
        }

        if(bessX != null) {
            XYSeries series6 = new XYSeries("Метод Бесселя");
            n = bessX.size();
            for (int i = 0; i < n; i++) {
                series6.add(bessX.get(i), bessssY.get(i));
            }
            xyDataset2.addSeries(series6);
        }

        show(xyDataset1, xyDataset2);

    }

}
