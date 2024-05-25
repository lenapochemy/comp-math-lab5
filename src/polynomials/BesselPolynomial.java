package polynomials;

import utils.Helper;

import java.util.Vector;
import java.util.function.DoubleFunction;

import static java.lang.Math.abs;

public class BesselPolynomial {

    private final double[] x ,y ;
    private final int n;
    private final double a, h;
    private final double[][] finDiff;
    public final DoubleFunction<Double> besselFunc;
    public final Vector<Double> besselX, besselY;


    public BesselPolynomial(double[] x, double[] y, double h, double[][] finDiff) {
        this.x = x;
        this.y = y;
        this.n = x.length;
        this.h = h;
        this.a = x[n/2 - 1];


        this.finDiff = finDiff;
        this.besselFunc = solve();

        double step = 0.1;
        if(h < step){
            step = h/2;
        }
        besselX = new Vector<>();
        besselY = new Vector<>();
        for(double i = x[0] - 0.1; i < (x[n-1] + 0.2); i+= step){
            besselX.add(i);
            besselY.add(besselFunc.apply(i));
        }
    }

    public boolean checkT(double x){
        double t = (x - a) / h;
        return abs(t) >= 0.25 && abs(t) <= 0.75;
    }

    public DoubleFunction<Double> solve(){
        DoubleFunction<Double> t = x -> (x - a) / h;
        DoubleFunction<Double> func = x -> (y[n/2 - 1] + y[n/2])/2 + (t.apply(x) - 0.5) * finDiff[n/2-1][2];

        for(int k = 1; k < n/2; k++){
            DoubleFunction<Double> tempFunc = x -> 1.0;

            for(int m = 0; m <= k; m++){
                DoubleFunction<Double> prevTempFunc = tempFunc;
                int finalM = m;
                tempFunc = x -> prevTempFunc.apply(x) * (t.apply(x) - finalM);
            }
            for(int m = 1; m <= k - 1; m++){
                DoubleFunction<Double> prevTempFunc = tempFunc;
                int finalM = m;
                tempFunc = x -> prevTempFunc.apply(x) * (t.apply(x) + finalM);
            }

            DoubleFunction<Double> prevFunc = func;
            DoubleFunction<Double> finalTempFunc = tempFunc;
            int finalK = k;
            int finalN = n / 2 - 1;

            func = x -> prevFunc.apply(x) +
                    finalTempFunc.apply(x) * ( (finDiff[-finalK + finalN][2*finalK + 1] + finDiff[-(finalK-1) + finalN][2*finalK + 1]) / (2 * Helper.factorial(2 * finalK) ) +
                            (t.apply(x) - 0.5) * finDiff[-finalK + finalN][2*finalK + 2] / Helper.factorial(2 * finalK + 1) );
        }

        return func;
    }


}
