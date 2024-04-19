package polynomials;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.DoubleFunction;

public class LagrangePolynomial {

    private final double[] x ,y ;
    private final int n;
//    private double x_0;
    private double[] lagrangeX, lagrangeValue;
    private DoubleFunction<Double> lagrangeFunc;
    double minX, maxX;

    public LagrangePolynomial(double[] x, double[] y){
        this.x = x;
        this.y = y;
        this.n = x.length;
//        this.x_0 = x_0;
        this.lagrangeFunc = solve();

//        for(int i = 0; i < n; i++){
//            lagrangeValue[i] = lagrangeFunc.apply(x[i]);
//        }
//        minX = Double.valueOf(Arrays.stream(x).min().toString());
        minX = Collections.min(Arrays.stream(x).boxed().toList());
        maxX = Collections.max(Arrays.stream(x).boxed().toList());
//        maxX = Double.valueOf(Arrays.stream(x).max().toString());
        int s = (int) ((maxX + 0.2 - minX) / 0.1);
        lagrangeX = new double[s];
        lagrangeValue = new double[s];
        int j = 0;
        for(double i = minX - 0.1; i <= (maxX + 0.1); i+= 0.1, j++){
            lagrangeX[j] = rounding(i);
            lagrangeValue[j] = rounding(lagrangeFunc.apply(i));
//            System.out.println("i = " + i + " x = " + lagrangeX[j] + " y = " + lagrangeValue[j]);
        }

    }

    private double rounding(double number){
        BigDecimal help = new BigDecimal(number);
        help = help.setScale(5, RoundingMode.HALF_UP);
        return help.doubleValue();
    }

    private DoubleFunction<Double> solve(){
        DoubleFunction<Double> interpolationFunc = x -> 0.0;
        for(int i = 0; i < n; i++){
            DoubleFunction<Double> termFunc = x -> 1.0;
            double x_i = x[i];
            for(int j = 0; j < n; j++){
                if(j != i) {
                    double x_j = x[j];
                    DoubleFunction<Double> prevFunc = termFunc;
                    termFunc = x -> prevFunc.apply(x) * ((x - x_j) / (x_i - x_j));
                }
            }
            DoubleFunction<Double> prevFunc = interpolationFunc;
            double y_i = y[i];
            DoubleFunction<Double> finalTermFunc = termFunc;
            interpolationFunc = x -> prevFunc.apply(x) + y_i * finalTermFunc.apply(x);
        }

        return interpolationFunc;
    }

    public double getLagrangeFuncValue(double x_0){
        return lagrangeFunc.apply(x_0);
    }

    public double[] getLagrangeValue() {
        return lagrangeValue;
    }
    public double[] getLagrangeX(){
        return lagrangeX;
    }

    public DoubleFunction<Double> getLagrangeFunc(){
        return lagrangeFunc;
    }
}
