package polynomials;


import utils.Helper;

import java.util.Vector;
import java.util.function.DoubleFunction;

public class StirlingPolynomial {

    private final double[] x ,y ;
    private final int n;
    private final double a, h;
    private final double[][] finDiff;
    public final DoubleFunction<Double> stirlingFunc;
    public final Vector<Double> stirlX, stirlY;


    public StirlingPolynomial(double[] x, double[] y, double h, double[][] finDiff) {
        this.x = x;
        this.y = y;
        this.n = x.length;
        this.h = h;
        this.a = x[n/2];
        this.finDiff = finDiff;
        this.stirlingFunc = solve();

//        double minX = x[0], maxX = x[n-1];
        stirlX = new Vector<>();
        stirlY = new Vector<>();
        double step = 0.1;
        if(h < step){
            step = h/2;
        }
        for(double i = x[0] - 0.1; i < x[n-1] + 0.2; i+= step){
            stirlX.add(i);
            stirlY.add(stirlingFunc.apply(i));
        }
    }


    public DoubleFunction<Double> solve(){
        DoubleFunction<Double> t = x -> (x - a) / h;
        DoubleFunction<Double> func = x -> y[n/2];

        for(int k = 1; k < n/2+1; k++){
            DoubleFunction<Double> tempFunc = x -> 1.0;

            for(int m = 1; m <= k-1; m++){
                DoubleFunction<Double> prevTempFunc = tempFunc;
                int finalM = m;
                tempFunc = x -> prevTempFunc.apply(x) * (Math.pow(t.apply(x), 2) - Math.pow(finalM, 2));
            }

            DoubleFunction<Double> prevFunc = func;
            DoubleFunction<Double> finalTempFunc = tempFunc;
            int finalK = k;
            int finalN = n / 2;
            func = x -> prevFunc.apply(x) +
                    finalTempFunc.apply(x) * (t.apply(x) * (finDiff[-finalK + finalN][2*finalK] + finDiff[-(finalK-1) + finalN][2*finalK]) / (2 * Helper.factorial(2 * finalK - 1 ) ) +
                     Math.pow(t.apply(x), 2) * finDiff[-finalK + finalN][2*finalK + 1] / Helper.factorial(2 * finalK));
        }

        return func;
    }


}
