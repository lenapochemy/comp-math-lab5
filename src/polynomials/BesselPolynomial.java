package polynomials;


import utils.Helper;

import java.util.function.DoubleFunction;

public class BesselPolynomial {

    private final double[] x ,y ;
    private final int n;
    private final double a, h;
    private final double[][] finDiff;
    public DoubleFunction<Double> besselFunc;
    public double[] besselX, besselY;


    public BesselPolynomial(double[] x, double[] y, double h, double[][] finDiff) {
        this.x = x;
        this.y = y;
        this.n = x.length;
        this.h = h;
        this.a = x[n/2 - 1];


        this.finDiff = finDiff;
        this.besselFunc = solve();


        double minX = x[0], maxX = x[n-1];
        int s = (int) ((maxX + 0.2 - minX) / 0.1);
        besselX = new double[s];
        besselY = new double[s];
        int j = 0;
        for(double i = minX - 0.1; i <= (maxX + 0.1); i+= 0.1, j++){
            besselX[j] = i;
            besselY[j] = besselFunc.apply(i);
        }
    }


    public DoubleFunction<Double> solve(){
        DoubleFunction<Double> t = x -> (x - a) / h;
        DoubleFunction<Double> func = x -> (y[n/2 - 1] + y[n/2])/2 + (t.apply(x) - 0.5) * finDiff[n/2-1][2];
//        DoubleFunction<Double> func = x -> 0.0;

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
                    finalTempFunc.apply(x) * (finDiff[-finalK + finalN][2*finalK + 1] + finDiff[-(finalK-1) + finalN][2*finalK + 1]) / (2 * Helper.factorial(2 * finalK) ) +
                    finalTempFunc.apply(x) * (t.apply(x) - 0.5) * finDiff[-finalK + finalN][2*finalK + 2] / Helper.factorial(2 * finalK + 1);
        }

        return func;
    }


}
