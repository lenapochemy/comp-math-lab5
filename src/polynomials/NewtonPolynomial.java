package polynomials;

import utils.Helper;

import java.util.Vector;
import java.util.function.DoubleFunction;

public class NewtonPolynomial {
    private final double[] x ,y ;
    private final int n;
    private final Vector<Double> newtonX, newtonValue;
    private final DoubleFunction<Double> newtonFunc;

    public NewtonPolynomial(double[] x, double[] y){
        this.x = x;
        this.y = y;
        this.n = x.length;
        this.newtonFunc = solve();

        newtonX = new Vector<>();
        newtonValue = new Vector<>();
        double step = 0.1;
        double minH = x[0];
        for(int i = 0; i < n; i++){
            if(minH < x[i]){
                minH = x[i];
            }
        }
        if(minH < step){
            step = minH/2;
        }
        for(double i = x[0] - 0.1; i < x[n-1] + 0.2; i+= step){
            newtonX.add(Helper.rounding(i));
            newtonValue.add(Helper.rounding(newtonFunc.apply(i)));
        }
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

    public Vector<Double> getNewtonX() {
        return newtonX;
    }

    public Vector<Double> getNewtonValue() {
        return newtonValue;
    }

    public DoubleFunction<Double> getNewtonFunc() {
        return newtonFunc;
    }
}
