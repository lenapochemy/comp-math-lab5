package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Helper {

    public static double checkDiffInterval(double[] x){
        int n = x.length;
        double diff = rounding(Math.abs(x[0] - x[1]));
        for(int i = 1; i < n-1; i++){
            if(rounding(Math.abs(x[i] - x[i+1])) != diff){
                return -1;
            }
        }
        return diff;
    }


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
        String res =  String.format(" %-3s|", "i");
        res += String.format(" %-3s|", " ");
        res += String.format(" %-10s|", "x");
        res += String.format(" %-10s|", "y");
        for(int i = 2; i < n; i++){
            if(i == 2){
                res += String.format(" %-10s|" , ("∆y_i"));
            }
            res += String.format(" %-10s|" , ("∆y^" + (i) + " _i"));
        }

        System.out.println(res);
        int m = n+1;
        int s;
        if(n % 2 == 0){
            s = n/2 - 1;
        } else s = n/2;
        for(int i = 0; i < n; i++, m--){
            res = String.format(" %-3s|", i);
            res += String.format(" %-3s|", i - s);
            for(int j = 0; j < m; j++){
                String str;
                if(rounding(finDiff[i][j]) >= 0) {
                    str = " " + rounding(finDiff[i][j]);
                } else str = Double.toString(rounding(finDiff[i][j]));
                res += String.format(" %-10s|", str);
//                System.out.print(finDiff[i][j] + " ");
            }
            System.out.println(res);
        }

    }

    public static double rounding(double number){
        BigDecimal help = new BigDecimal(number);
        help = help.setScale(6, RoundingMode.HALF_UP);
        return help.doubleValue();
    }



    public static int factorial(int num){
        if(num <= 1) return 1;
        return num * factorial(num - 1);
    }


}
