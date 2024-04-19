package polynomials;

import utils.Helper;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.DoubleFunction;

public class GaussPolynomial {

    private final double[] x ,y ;
    private final int n;
    private final double a, h;
    public double[] gaussXMore, gaussXLess, gaussX;
    public final DoubleFunction<Double> funcXMoreA, funcXLessA;
    private final double[][] finDiff;
    double minX, maxX;

    public GaussPolynomial(double[] x, double[] y, double h, double[][] finDiff) {
        this.x = x;
        this.y = y;
        this.n = x.length;
        this.h = h;
        this.a = x[n/2];

//        this.finDiff = Helper.finiteDiffs(x, y);
        this.finDiff = finDiff;

//        this.x_0 = x_0;
//        this.gaussFunc = solve();
        this.funcXMoreA = solveForXMoreA();
        this.funcXLessA = solveForXLessA();

        minX = Collections.min(Arrays.stream(x).boxed().toList());
        maxX = Collections.max(Arrays.stream(x).boxed().toList());
        int s = (int) ((maxX - minX + 0.2) / 0.1);
        gaussXMore = new double[s];
        gaussXLess = new double[s];
        gaussX = new double[s];
        int j = 0;
        for(double i = minX - 0.1; i <= maxX + 0.1; i+= 0.1, j++){
            gaussX[j] = (i);
            gaussXMore[j] = funcXMoreA.apply(i);
            gaussXLess[j] = funcXLessA.apply(i);
//            System.out.println("i = " + i + " x = " + gaussXLess[j] + " y = " + gaussXMore[j]);
        }
//
//        for(double i = minX * 3; i <= maxX * 3; i+= 0.5 ){
//            System.out.println("i = " + i + " x = " + funcXLessA.apply(i) + " y = " + funcXMoreA.apply(i));
//
//        }

    }


//    private double rounding(double number){
//        BigDecimal help = new BigDecimal(number);
//        help = help.setScale(2, RoundingMode.HALF_UP);
//        return help.doubleValue();
//    }

    public double gaussFunc(double num){
        if(num > a){
            return funcXMoreA.apply(num);
        } else {
            return funcXLessA.apply(num);
        }
    }

    // n%2 != 0 => y_n -> y_ (-1)^n * [n/2]
    // n%2 == 0 => y_n -> y_ (-1)^(n+1) * [n+1/2]
    private DoubleFunction<Double> solveForXMoreA(){
        DoubleFunction<Double> t = x -> (x - a) / h;

        DoubleFunction<Double> func = x -> y[n/2];
//        System.out.print(y[n/2] + " + ");

        for(int k = 1; k < n/2+1; k++){
            DoubleFunction<Double> tempFunc = x -> 1.0;

            for(int m = 1-k; m <= k-1; m++){
                DoubleFunction<Double> prevTempFunc = tempFunc;
                int finalM = m;
                tempFunc = x -> prevTempFunc.apply(x) * (t.apply(x) - finalM);
            }

            DoubleFunction<Double> prevFunc = func;
            DoubleFunction<Double> finalTempFunc = tempFunc;
            int finalK = k;
            int finalN = n / 2;
//            System.out.print(finalTempFunc.apply(0.32) + " * " + finDiff[-(finalK-1) + finalN][2*finalK] + " / " + factorial(2 * finalK -1) + " + ");
//            System.out.print(finalTempFunc.apply(0.32) * (t.apply(0.32) - finalK) + " * " +
//                    finDiff[-finalK + finalN][2 * finalK +1] + " / " + factorial(2 * finalK) + " + ");
            func = x -> prevFunc.apply(x) +
                    finalTempFunc.apply(x) * finDiff[-(finalK-1) + finalN][2*finalK] / Helper.factorial(2 * finalK -1) +
                    finalTempFunc.apply(x) * (t.apply(x) - finalK) * finDiff[-finalK + finalN][2 * finalK +1] / Helper.factorial(2 * finalK);

        }
//        System.out.println();

        return func;
    }



    private DoubleFunction<Double> solveForXLessA(){
        DoubleFunction<Double> t = x -> (x - a) / h;

        DoubleFunction<Double> func = x -> y[n/2];
//        System.out.print(y[n/2] + " + ");

        for(int k = 1; k < n/2+1; k++){
            DoubleFunction<Double> tempFunc1 = x -> 1.0;
            for(int m = 1-k; m <= k-1; m++){
                DoubleFunction<Double> prevTempFunc = tempFunc1;
                int finalM = m;
                tempFunc1 = x -> prevTempFunc.apply(x) * (t.apply(x) - finalM);
            }
            DoubleFunction<Double> tempFunc2 = x -> 1.0;
            for(int m = 1-k; m <= k; m++){
                DoubleFunction<Double> prevTempFunc = tempFunc2;
                int finalM = m;
                tempFunc2 = x -> prevTempFunc.apply(x) * (t.apply(x) + finalM);
            }


            DoubleFunction<Double> prevFunc = func;
            DoubleFunction<Double> finalTempFunc1 = tempFunc1;
            DoubleFunction<Double> finalTempFunc2 = tempFunc2;
            int finalK = k;
            int finalN = n / 2;
//            System.out.print(finalTempFunc.apply(0.32) + " * " + finDiff[-(finalK-1) + finalN][2*finalK] + " / " + factorial(2 * finalK -1) + " + ");
//            System.out.print(finalTempFunc.apply(0.32) * (t.apply(0.32) - finalK) + " * " +
//                    finDiff[-finalK + finalN][2 * finalK +1] + " / " + factorial(2 * finalK) + " + ");
            func = x -> prevFunc.apply(x) +
                    finalTempFunc1.apply(x) * finDiff[-finalK + finalN][2*finalK] / Helper.factorial(2 * finalK -1) +
                    finalTempFunc2.apply(x) * finDiff[-finalK + finalN][2 * finalK +1] / Helper.factorial(2 * finalK);

        }
//        System.out.println();

        return func;
    }

    public double[] getGaussX() {
        return gaussX;
    }



}
