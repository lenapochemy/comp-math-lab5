import polynomials.*;
import utils.Chart;
import utils.Helper;
import utils.InputType;
import utils.ScannerManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;
import java.util.function.DoubleFunction;


public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ScannerManager scannerManager = new ScannerManager(scanner);

        Chart chart = new Chart();

        Map<Integer, DoubleFunction<Double>> funcMap = new HashMap<>();
        funcMap.put(1, Math::sin);
        funcMap.put(2, x -> x * x + 3 * x - 4);
        funcMap.put(3, x -> x * Math.cos(x) - 3);

        String[] funcString = new String[]{
                "sin(x)",
                "x²3x-4",
                "x*cos(x)-3"
        };

        DoubleFunction<Double> inputFunc = null;
        double[][] data;
        double h = 0, x_0, x_n;
        int n;
        boolean diffInterval = false; //разное расстояние между узлами

        InputType inputType = scannerManager.sayInputValueType();
        boolean fromFile = scannerManager.sayType("Ввод данных из файла", "Ввод данных с клавиатуры");
        if(fromFile){
            scannerManager.setScanner(scannerManager.sayNewScanner());
            scannerManager.setFileMode(true);
        }

        switch (inputType){
            case FROM_DOTS -> {
                data = scannerManager.sayInitialDotsData();
                h = Helper.checkDiffInterval(data[0]);
                if(h < 0){
                    System.out.println("cringe h = " + h);
                    diffInterval = true;
                }
            }
            case FROM_INTERVALS -> {
                x_0 = scannerManager.sayA();
                x_n = scannerManager.sayB(x_0);
                n = scannerManager.sayN();
                h = (x_n - x_0) / (n - 1);
                data = scannerManager.sayInitialEquidistantData(x_0, h, n);
            }
            default -> {
                int number = scannerManager.sayFunctionNumber(funcString);
                x_0 = scannerManager.sayA();
                x_n = scannerManager.sayB(x_0);
                n = scannerManager.sayN();
                h = (x_n - x_0) / (n - 1);
                inputFunc = funcMap.get(number);
                data = scannerManager.sayInitialFuncData(x_0, h, n, inputFunc);
            }
        }

        scannerManager.setFileMode(false);
        scannerManager.setScanner(scanner);


        Polynomials method = scannerManager.sayMethod(diffInterval, data[0].length);

        GaussPolynomial gaussPolynomial = null;
        DoubleFunction<Double> mainFunction = x -> 1.0;
        Vector<Double> polX = null, polY = null;
        String name = null;
        double[][] finiteDiffs = Helper.finiteDiffs(data[0], data[1]);

        switch (method) {
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
                gaussPolynomial = new GaussPolynomial(data[0], data[1], h, finiteDiffs);
                polX = gaussPolynomial.getGaussX();
                polY = gaussPolynomial.getGaussY();
                name = "Многочлен Гаусса";
            }
            case STIRLING -> {
                StirlingPolynomial stirlingPolynomial = new StirlingPolynomial(data[0], data[1], h, finiteDiffs);
                mainFunction = stirlingPolynomial.stirlingFunc;
                name = "Многочлен Стирлинга";
                polX = stirlingPolynomial.stirlX;
                polY = stirlingPolynomial.stirlY;
            }
            case BESSEL -> {
                BesselPolynomial besselPolynomial = new BesselPolynomial(data[0], data[1], h, finiteDiffs);
                mainFunction = besselPolynomial.besselFunc;
                name = "Многочлен Бесселя";
                polX = besselPolynomial.besselX;
                polY = besselPolynomial.besselY;
            }
            default -> {
                polX = null;
                polY = null;
                name = null;
            }
        }

        chart.drawGraphics(data[0], data[1], polX, polY, name);
        while (true) {
            double num = scannerManager.sayDoubleNumber("числа для вычислений");
            if (inputFunc != null) {
                System.out.println("Точное значение: " + inputFunc.apply(num));
            }
            if (method == Polynomials.GAUSS) {
                System.out.println("Вычисленное значение: " + gaussPolynomial.gaussFunc(num));
            } else {
                System.out.println("Вычисленное значение: " + mainFunction.apply(num));
            }
        }

    }
}

//C:\Users\Elena\IdeaProjects\compMath\lab5\src\files\data1