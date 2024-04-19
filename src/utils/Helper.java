package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Helper {


    public static double[][] finiteDiffs(double[] x, double[] y){
        int n = x.length;
        double[][] finDiff = new double[n][n+1];
        for(int i = 0; i < n; i++){
            finDiff[i][0] = x[i];
            finDiff[i][1] = y[i];
        }
        int m = n-1;
        for(int j = 2; j < n+1; j++, m--){
            for(int i = 0; i < m; i++){
                finDiff[i][j] =  finDiff[i+1][j-1] - finDiff[i][j-1];
//                 System.out.println("ij = " + finDiff[i][j] + " i1j_1 = " + finDiff[i+1][j-1] + " ij_1 = " + finDiff[i][j-1]);
            }
        }
        printFiniteDiffs(finDiff, n);

        return finDiff;
    }

    private static void printFiniteDiffs(double[][] finDiff, int n){
        System.out.println("Таблица конечных разностей");
        String res =  String.format(" %-7s|", "i");
        res += String.format(" %-7s|", "x");
        res += String.format(" %-7s|", "y");
        for(int i = 2; i < n; i++){
            if(i == 2){
                res += String.format(" %-7s|" , ("∆y_i"));
            }
            res += String.format(" %-7s|" , ("∆y^" + (i) + " _i"));
        }

        System.out.println(res);
        int m = n+1;
        for(int i = 0; i < n; i++, m--){
            res = String.format(" %-7s|", i);
            for(int j = 0; j < m; j++){
                res += String.format(" %-7s|", rounding(finDiff[i][j]));
//                System.out.print(finDiff[i][j] + " ");
            }
            System.out.println(res);
        }

    }

    public static double rounding(double number){
        BigDecimal help = new BigDecimal(number);
        help = help.setScale(5, RoundingMode.HALF_UP);
        return help.doubleValue();
    }



    public static int factorial(int num){
        if(num <= 1) return 1;
        return num * factorial(num - 1);
    }


}
