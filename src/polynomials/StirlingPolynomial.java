package polynomials;


import utils.Helper;

import java.util.function.DoubleFunction;

public class StirlingPolynomial {

    private final double[] x ,y ;
    private final int n;
    private final double a, h;
    private final double[][] finDiff;
    public DoubleFunction<Double> stirlingFunc;
    public double[] stirlX, stirlY;


    public StirlingPolynomial(double[] x, double[] y, double h, double[][] finDiff) {
        this.x = x;
        this.y = y;
        this.n = x.length;
//        System.out.println("n = " + n);
        this.h = h;
        this.a = x[n/2];
//        System.out.println("a = " + a);

//        this.finDiff = Helper.finiteDiffs(x, y);
        this.finDiff = finDiff;
        this.stirlingFunc = solve();


        double minX = x[0], maxX = x[n-1];
        int s = (int) ((maxX + 0.2 - minX) / 0.1);
        stirlX = new double[s];
        stirlY = new double[s];
        int j = 0;
        for(double i = minX - 0.1; i <= (maxX + 0.1); i+= 0.1, j++){
            stirlX[j] = i;
            stirlY[j] = stirlingFunc.apply(i);
//            System.out.println("i = " + i + " x = " + lagrangeX[j] + " y = " + lagrangeValue[j]);
        }
    }


    public DoubleFunction<Double> solve(){
        DoubleFunction<Double> t = x -> (x - a) / h;
//        System.out.println(t.apply(0.2));

        DoubleFunction<Double> func = x -> y[n/2];
//        System.out.print(y[n/2] + " + ");

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
//            System.out.print(finalTempFunc.apply(0.2) + " * " + t.apply(0.2) + " * ( " + finDiff[-finalK + finalN][2*finalK] +" + "
//                    + finDiff[-(finalK-1) + finalN][2*finalK] + ") / ( 2 * " + Helper.factorial(2 * finalK) + " ) +");
//
//            System.out.println(finalTempFunc.apply(0.2) + " * " + Math.pow(t.apply(0.2), 2) + " * " + finDiff[-finalK + finalN][2*finalK + 1] + " / " + Helper.factorial(2 * finalK) + " + " );

            func = x -> prevFunc.apply(x) +
                    finalTempFunc.apply(x) * t.apply(x) * (finDiff[-finalK + finalN][2*finalK] + finDiff[-(finalK-1) + finalN][2*finalK]) / (2 * Helper.factorial(2 * finalK - 1 ) ) +
                    finalTempFunc.apply(x) * Math.pow(t.apply(x), 2) * finDiff[-finalK + finalN][2*finalK + 1] / Helper.factorial(2 * finalK);
        }
        System.out.println();

        return func;
    }


}
