import polynomials.*;
import utils.Chart;
import utils.Helper;
import utils.ScannerManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.function.DoubleFunction;


public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ScannerManager scannerManager = new ScannerManager(scanner);

        Chart chart = new Chart();

        Map<Integer, DoubleFunction<Double>> funcMap = new HashMap<>();
        funcMap.put(1, x -> Math.sin(x) );
        funcMap.put(2, x -> x * x + 3 * x - 4);
        funcMap.put(3, x -> x * Math.cos(x) - 3);

        String[] funcString = new String[]{
                "sin(x)",
                "x²3x-4",
                "x*cos(x)-3"
        };

        DoubleFunction<Double> inputFunc = null;
        double[][] data;
        double h = 0;
        boolean diffInterval = false;


        if(scannerManager.sayType("Ввод данных точками", "Ввод данных через функцию")){ //ввод через точки
            if(scannerManager.sayType("Одинаковые интервалы между точками (равноотстоящие узлы)",
                    "Разные интервалы между точками")){ //равноотстоящие узлы
                if (!scannerManager.sayType("Ввод данных с клавиатуры", "Ввод данных из файла")) {
                    scannerManager.setScanner(scannerManager.sayNewScanner());
                    scannerManager.setFileMode(true);
                }
                double x_0 = scannerManager.sayA();
                double x_n = scannerManager.sayB(x_0);
                int n = scannerManager.sayN();
                h = (x_n - x_0) / (n - 1);
                data = scannerManager.sayInitialEquidistantData(x_0, h, n);

            } else  { //разные узлы
                if (!scannerManager.sayType("Ввод данных с клавиатуры", "Ввод данных из файла")) {
                    scannerManager.setScanner(scannerManager.sayNewScanner());
                    scannerManager.setFileMode(true);
                }
                data = scannerManager.sayInitialDotsData();
                diffInterval = true;
            }

        } else { //ввод функцией
            int number = scannerManager.sayFunctionNumber(funcString);

            if (!scannerManager.sayType("Ввод данных с клавиатуры", "Ввод данных из файла")) {
                scannerManager.setScanner(scannerManager.sayNewScanner());
                scannerManager.setFileMode(true);
            }

            double x_0 = scannerManager.sayA();
            double x_n = scannerManager.sayB(x_0);
            int n = scannerManager.sayN();
            h = (x_n - x_0) / (n - 1);
            inputFunc = funcMap.get(number);
            data = scannerManager.sayInitialFuncData(x_0, h, n, inputFunc);
                    }

        scannerManager.setFileMode(false);
        scannerManager.setScanner(scanner);

        Polynomials method;
        if(diffInterval) {
            method = scannerManager.sayMethodFromTwo();
        } else {
            method = scannerManager.sayMethod();
        }


        GaussPolynomial gaussPolynomial = null;
        DoubleFunction<Double> mainFunction = x -> 1.0;
        double[] polX = null, polY = null;
        String name;
        double[][] finiteDiffs = Helper.finiteDiffs(data[0], data[1]);
        DoubleFunction<Double> lagrange = null;

        switch (method){
            case LAGRANGE -> {
                LagrangePolynomial lagrangePolynomial = new LagrangePolynomial(data[0], data[1]);
                mainFunction = lagrangePolynomial.getLagrangeFunc();
                polX = lagrangePolynomial.getLagrangeX();
                polY = lagrangePolynomial.getLagrangeValue();
                name = "Многочлен Лагранжа";
            }
            case NEWTON -> {
                NewtonPolynomial newtonPolynomial = new NewtonPolynomial(data[0], data[1]);
                mainFunction = newtonPolynomial.getNewtonFunc();
                polX = newtonPolynomial.getNewtonX();
                polY = newtonPolynomial.getNewtonValue();
                name = "Многочлен Ньютона";
            }
            case GAUSS -> {
//                finiteDiffs = Helper.finiteDiffs(data[0], data[1]);
                gaussPolynomial = new GaussPolynomial(data[0], data[1], h, finiteDiffs);
//                mainFunction = gaussPolynomial.gaussXLess;
                polX = gaussPolynomial.gaussX;
                polY = gaussPolynomial.gaussXLess;
                name = "Многочлен Гаусса";
            }
            case STIRLING -> {
                StirlingPolynomial stirlingPolynomial = new StirlingPolynomial(data[0], data[1], h, finiteDiffs);
                mainFunction = stirlingPolynomial.stirlingFunc;
                LagrangePolynomial lagrangePolynomial = new LagrangePolynomial(data[0], data[1]);
                lagrange = lagrangePolynomial.getLagrangeFunc();
                name = "Многочлен Стирлинга";
                polX = stirlingPolynomial.stirlX;
                polY = stirlingPolynomial.stirlY;
//                gaussPolynomial = new GaussPolynomial(data[0], data[1], h, finiteDiffs);
//                DoubleFunction<Double> more = gaussPolynomial.funcXMoreA;
//                DoubleFunction<Double> less = gaussPolynomial.funcXLessA;
//                gauss = x -> (more.apply(x) + less.apply(x) / 2);
            }
            default -> {
                polX = null;
                polY = null;
                name = null;
            }
        }

        chart.drawGraphics(data[0], data[1], polX, polY, name);
//        chart.drawTwoGraphics(data[0], data[1], polX, polY, name);


        while (true) {
            double num = scannerManager.sayDoubleNumber("числа для вычислений");
            if(lagrange != null){
                System.out.println("lagrange: " + lagrange.apply(num));
            }
            if(inputFunc != null) {
                System.out.println("Точное значение: " + inputFunc.apply(num));
            }

            if(method == Polynomials.GAUSS) {
                System.out.println("Вычисленное значение: " + gaussPolynomial.gaussFunc(num));
            } else {
                System.out.println("Вычисленное значение: " + mainFunction.apply(num));
            }
        }


//        double[] x = {0.1, 0.2, 0.3, 0.4, 0.5};
//        double[] y = {1.25, 2.38, 3.79, 5.44, 7.14};
//        double[] x = {-2, -1, 0, 1, 2, 3, 4};
//        double[] y = {-3, 6, 2, 5, 3, -5, 0};
//        double[] x = {1, 2, 3, 4};
//        double[] y = {0, 3, 5, 7};
//        double[] x = {0.15, 0.2, 0.33, 0.47};
//        double[] y = {1.25, 2.38, 3.79, 5.44};
//        double[] x = {-2, -1, 0, 1, 2};
//        double[] y = {0, -2, -2, 0, 4};
//        double[] x = {9, 12, 15, 18, 21};
//        double[] y = {0.156434, 0.207912, 0.258819, 0.309017, 0.358368};

    }
}


//C:\Users\Elena\IdeaProjects\compMath\lab5\src\files\data1