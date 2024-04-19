package polynomials;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.DoubleFunction;

public class NewtonPolynomial {
    private final double[] x ,y ;
    private final int n;
    //    private double x_0;
    private double[] newtonX, newtonValue;
    private DoubleFunction<Double> newtonFunc;
    double minX, maxX;

    public NewtonPolynomial(double[] x, double[] y){
        this.x = x;
        this.y = y;
        this.n = x.length;
//        this.x_0 = x_0;
        this.newtonFunc = solve();


        minX = Collections.min(Arrays.stream(x).boxed().toList());
        maxX = Collections.max(Arrays.stream(x).boxed().toList());
//        maxX = Double.valueOf(Arrays.stream(x).max().toString());
        int s = (int) ((maxX - minX + 0.2) / 0.1);
        newtonX = new double[s];
        newtonValue = new double[s];
        int j = 0;
        for(double i = minX - 0.1; i <= maxX + 0.1; i+= 0.1, j++){
            newtonX[j] = rounding(i);
            newtonValue[j] = rounding(newtonFunc.apply(i));
//            System.out.println("i = " + i + " x = " + lagrangeX[j] + " y = " + lagrangeValue[j]);
        }
    }

    private double rounding(double number){
        BigDecimal help = new BigDecimal(number);
        help = help.setScale(5, RoundingMode.HALF_UP);
        return help.doubleValue();
    }

    private double separatedDiffs(int i, int k){
        if(k == 0){
            return y[i];
        }
        if(k == 1){
            return (y[i+1] - y[i]) / (x[i+1] - x[i]);
        }
        return (separatedDiffs(i+1, k - 1) - separatedDiffs(i, k-1)) / (x[i+k] - x[i]);
    }

    private DoubleFunction<Double> solve(){

        DoubleFunction<Double> func = x -> y[0];

        for(int k = 1; k < n; k++){

            DoubleFunction<Double> termFunc = x -> 1.0;
            for(int j = 0; j < k; j++){
                double x_j = x[j];
                DoubleFunction<Double> prevFunc = termFunc;
                termFunc = x -> prevFunc.apply(x) * (x - x_j);
            }
            double f = separatedDiffs(0, k);

            DoubleFunction<Double> finalTermFunc = termFunc;
            DoubleFunction<Double> prevFunc = func;
            func = x -> prevFunc.apply(x) + f * finalTermFunc.apply(x);
        }
        return func;
    }

    public double[] getNewtonX() {
        return newtonX;
    }

    public double[] getNewtonValue() {
        return newtonValue;
    }

    public DoubleFunction<Double> getNewtonFunc() {
        return newtonFunc;
    }
}
