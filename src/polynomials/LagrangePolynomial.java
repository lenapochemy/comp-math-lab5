package polynomials;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Vector;
import java.util.function.DoubleFunction;

public class LagrangePolynomial {

    private final double[] x ,y ;
    private final int n;
    private final Vector<Double> lagrangeX, lagrangeValue;
    private final DoubleFunction<Double> lagrangeFunc;
    double minX, maxX;

    public LagrangePolynomial(double[] x, double[] y){
        this.x = x;
        this.y = y;
        this.n = x.length;
        this.lagrangeFunc = solve();

        minX = x[0];
        maxX = x[n-1];
        lagrangeX = new Vector<>();
        lagrangeValue = new Vector<>();
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
        for(double i = minX - 0.1; i < maxX + 0.2; i+= step){
//            System.out.println("x = " + rounding(i) + " y = " + rounding(lagrangeFunc.apply(i)));
            lagrangeX.add(rounding(i));
            lagrangeValue.add(rounding(lagrangeFunc.apply(i)));
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

//    public double getLagrangeFuncValue(double x_0){
//        return lagrangeFunc.apply(x_0);
//    }

    public Vector<Double> getLagrangeValue() {
        return lagrangeValue;
    }
    public Vector<Double> getLagrangeX(){
        return lagrangeX;
    }

    public DoubleFunction<Double> getLagrangeFunc(){
        return lagrangeFunc;
    }
}
