package polynomials;

import utils.Helper;
import java.util.Vector;
import java.util.function.DoubleFunction;

public class GaussPolynomial {

    private final double[] x ,y ;
    private final int n;
    private final double a, h;
    private final Vector<Double> gaussY, gaussX;
    private final DoubleFunction<Double> funcXMoreA, funcXLessA;
    private final double[][] finDiff;

    public GaussPolynomial(double[] x, double[] y, double h, double[][] finDiff) {
        this.x = x;
        this.y = y;
        this.n = x.length;
        this.h = h;
        this.a = x[n/2];

        this.finDiff = finDiff;

        this.funcXMoreA = solveForXMoreA();
        this.funcXLessA = solveForXLessA();

        gaussY = new Vector<>();
        gaussX = new Vector<>();
        for(double i = x[0] - 0.1; i < x[n-1] + 0.2; i+= 0.1){
            gaussX.add(i);
            if(i < a){
                gaussY.add(funcXLessA.apply(i));
            } else {
                gaussY.add(funcXMoreA.apply(i));
            }
        }

    }

    public double gaussFunc(double num){
        if(num > a){
            System.out.println("Используется первая итнерполяционная формула Гаусса (x>a)");
            return funcXMoreA.apply(num);
        } else {
            System.out.println("Используется вторая итнерполяционная формула Гаусса (x<a)");
            return funcXLessA.apply(num);
        }
    }


    private DoubleFunction<Double> solveForXMoreA(){
        DoubleFunction<Double> t = x -> (x - a) / h;

        DoubleFunction<Double> func = x -> y[n/2];
        int count;
        if(n % 2 == 0){
            count = n /2;
        } else {
            count = n/2 + 1;
        }
        for(int k = 1; k < count; k++){
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
            func = x -> prevFunc.apply(x) +
                    finalTempFunc.apply(x) * finDiff[-(finalK-1) + finalN][2*finalK] / Helper.factorial(2 * finalK -1) +
                    finalTempFunc.apply(x) * (t.apply(x) - finalK) * finDiff[-finalK + finalN][2 * finalK +1] / Helper.factorial(2 * finalK);

        }
        return func;
    }


    private DoubleFunction<Double> solveForXLessA(){
        DoubleFunction<Double> t = x -> (x - a) / h;

        DoubleFunction<Double> func = x -> y[n/2];
        int count;
        if(n % 2 == 0){
            count = n /2;
        } else {
            count = n/2 + 1;
        }

        for(int k = 1; k < count; k++){
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
            func = x -> prevFunc.apply(x) +
                    finalTempFunc1.apply(x) * finDiff[-finalK + finalN][2*finalK] / Helper.factorial(2 * finalK -1) +
                    finalTempFunc2.apply(x) * finDiff[-finalK + finalN][2 * finalK +1] / Helper.factorial(2 * finalK);
        }

        return func;
    }

    public Vector<Double> getGaussX() {
        return gaussX;
    }

    public Vector<Double> getGaussY() {
        return gaussY;
    }

}
