package utils;

import exceptions.FileException;
import exceptions.IncorrectValueException;
import polynomials.Polynomials;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.DoubleFunction;


public class ScannerManager {
    private Scanner scanner;
    private boolean fileMode;
    public ScannerManager(Scanner scanner){
        this.scanner = scanner;
    }

    public void setScanner(Scanner scanner){
        this.scanner = scanner;
    }

    public void setFileMode(boolean fileMode){
        this.fileMode = fileMode;
    }

    public InputType sayInputValueType(){
        boolean flag = false;
        while(!flag) {
            try {
                System.out.println("Выберите один вариант:");
                System.out.println("\t1. Ввод данных точками");
                System.out.println("\t2. Ввод данных точками через интервалами");
                System.out.println("\t3. Ввод данных через функцию");
                String ans = scanner.nextLine().trim();
                switch (ans) {
                    case "" ->
                            throw new NullPointerException();
                    case "1" -> {
                        flag = true;
                        return InputType.FROM_DOTS;
                    }
                    case "2" -> {
                        flag = true;
                        return InputType.FROM_INTERVALS;
                    }
                    case "3" -> {
                        flag = true;
                        return InputType.FROM_FUNC;
                    }
                    default -> throw new IncorrectValueException();
                }
            } catch (IncorrectValueException | NullPointerException e){
                System.out.println("Ответ должен быть \"1\", \"2\" или \"3\"");
//            } catch (NoSuchElementException e){
//                System.out.println("Данные не найдены в файле");
//                System.exit(0);
            }
        }
        return InputType.FROM_FUNC;
    }

    public boolean sayType(String first, String second){
        boolean flag = false;
        while(!flag) {
            try {
                System.out.println("Выберите один вариант:\n\t1. " + first + "\n\t2. " + second );
                String ans = scanner.nextLine().trim();
                switch (ans) {
                    case "" ->
                            throw new NullPointerException();
                    case "1" -> {
                        flag = true;
                        return true;
                    }
                    case "2" -> {
                        flag = true;
                        return false;
                    }
                    default -> throw new IncorrectValueException();
                }
            } catch (IncorrectValueException | NullPointerException e){
                System.out.println("Ответ должен быть \"1\" или \"2\"");
            } catch (NoSuchElementException e){
                System.out.println("Данные не найдены в файле");
                System.exit(0);
            }
        }
        return flag;
    }

    public int sayFunctionNumber(String[] functionStrings){
        int n = functionStrings.length;
        int num = 0;
        String sNum;
        while (num <= 0 || num > n){
            try {
                System.out.println("Выберите функцию для решения: ");
                for(int i = 0; i < n; i++){
                    System.out.println("\t" + (i+1) + ". "+ functionStrings[i]);
                }
                sNum = scanner.nextLine().trim();
                if(fileMode) System.out.println(sNum);
                if(sNum.isEmpty()) throw new NullPointerException();
                num = Integer.parseInt(sNum);
                if(num <= 0 || num > n) throw new IncorrectValueException();
            } catch (IncorrectValueException e){
                System.out.println("Номер функции должен быть положительным числом, не большим " + n );
                if(fileMode) errorEnd();
            } catch (NullPointerException e){
                System.out.println("Номер функции не может быть пустым");
                if(fileMode) errorEnd();
            }  catch (NumberFormatException e){
                System.out.println("Номер функции должен быть целым числом");
                if(fileMode) errorEnd();
            }
        }
        return num;
    }

    public Scanner sayNewScanner(){
        String sFile;
        Scanner scanner1 = null;
        while(scanner1 == null){
            try{
                System.out.println("Введите путь к файлу:");
                sFile = scanner.nextLine().trim();
                if(sFile.isEmpty()) throw new NullPointerException();
                File file = new File(sFile);
                if(file.exists() && !file.canRead()) throw new FileException();
                scanner1 = new Scanner(file);
            } catch (NullPointerException e){
                System.out.println("Путь не может быть пустым");
            } catch (FileException e){
                System.out.println("Данные из файла невозможно прочитать");
            } catch (FileNotFoundException e){
                System.out.println("Файл не найден");
            }
        }
        return scanner1;
    }



    public Polynomials sayMethod(boolean diffIntervals, int n){
        String string = "Выберите метод \n";
        int count;
        boolean stirling = false;
        if(diffIntervals){ // разное расстояние
            string += """
                        \t1. Многочлен Лагранжа
                        \t2. Многочлен Ньютона с разделенными разностями
                        \t3. Все методы""";
            count = 2;
        } else { // одинаковое расстояние
            count = 4;
            if(n % 2 == 0){ //четное
                string += """
                        \t1. Многочлен Лагранжа
                        \t2. Многочлен Гаусса
                        \t3. Многочлен Бесселя
                        \t4. Все методы""";
            } else { // не четное
                string += """
                        \t1. Многочлен Лагранжа
                        \t2. Многочлен Гаусса
                        \t3. Многочлен Стрилинга
                        \t4. Все методы""";
                stirling = true;
            }
        }

        return sayPolynomials(string, count, stirling);
    }


    public Polynomials sayPolynomials(String string, int count, boolean stirling){
        boolean flag = false;
        while(!flag) {
            try {
                System.out.println(string);
                String ans = scanner.nextLine().trim();
                if(fileMode) System.out.println(ans);
                if(count == 4){
                    switch (ans) {
                        case "" -> throw new NullPointerException();
                        case "1" -> {
                            flag = true;
                            return Polynomials.LAGRANGE;
                        }
                        case "2" -> {
                            flag = true;
                            return Polynomials.GAUSS;
                        }
                        case "3" -> {
                            flag = true;
                            if(stirling) {
                                return Polynomials.STIRLING;
                            } else {
                                return Polynomials.BESSEL;
                            }
                        }
                        case "4" -> {
                            flag = true;
                            return Polynomials.ALL;
                        }
                    }
                } else {
                    switch (ans) {
                        case "" -> throw new NullPointerException();
                        case "1" -> {
                            flag = true;
                            return Polynomials.LAGRANGE;
                        }
                        case "2" -> {
                            flag = true;
                            return Polynomials.NEWTON;
                        }
                        case "3" -> {
                            flag = true;
                            return Polynomials.ALL;
                        }
                    }
                }
            } catch (NullPointerException e){
                System.out.println("Ответ должен быть положительным числом, не большим 5");
//                if(fileMode) errorEnd();
            } catch (NoSuchElementException e){
                System.out.println("Введите значение");
            }
        }
        return null;
    }

    public double[][] sayInitialDotsData(){
        int n = sayN();
        double[][] data = new double[2][n];
        Map<Double, Double> map = new TreeMap<>();
        System.out.println("Введите исходные точки парами (x, y) через пробел");
        for(int i = 0; i < n; i++){
            String[] num;
            double x = 0,y = 0;
            String sNum;
            boolean flag = true;
            while (flag){
                try {
//                    System.out.print("Введите " + name +  " : ");
                    sNum = scanner.nextLine().trim();
                    if(fileMode) System.out.println(sNum);
                    if(sNum.isEmpty()) throw new NullPointerException();
                    num = sNum.split(" ",2);
                    x = Double.parseDouble(num[0]);
                    y = Double.parseDouble(num[1]);
                    flag = false;
                } catch (NullPointerException e){
                    System.out.println("Значение точки не может быть пустым");
                    if(fileMode) errorEnd();
                }  catch (NumberFormatException e) {
                    System.out.println("Значение точки должно быть парой чисел");
                    if (fileMode) errorEnd();
                } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("Нужно ввести два числа");
                    if(fileMode) errorEnd();
                } catch (NoSuchElementException e){
                    System.out.println("Данные не найдены в файле");
                    System.exit(0);
                }
            }
//            map.put(Double.parseDouble(num[0]), Double.parseDouble(num[1]));
            map.put(x, y);

        }
        int i = 0;
        for(Map.Entry<Double, Double> entry : map.entrySet()){
            data[0][i] = entry.getKey();
            data[1][i] = entry.getValue();
            i++;
        }
        return data;
    }

    public double sayA(){
        return sayDoubleNumber("левой границы интервала");
    }

    public double sayB(double a) {
        double b = a;
        while(b <= a) {
            b = sayDoubleNumber("правой границы интервала");
            if(b <= a) System.out.println("Значение правой границы интервала должно быть больше левой");
        }
        return b;
    }

    public double[][] sayInitialFuncData(double x_0, double h, int n, DoubleFunction<Double> func){
        double[][] data = new double[2][n];
        data[0][0] = x_0;
        for(int i = 1; i < n; i++){
            data[0][i] = x_0 + h * i;
        }

        for(int i = 0; i < n; i++){
            data[1][i] = func.apply(data[0][i]);
        }
        return data;
    }

    public double[][] sayInitialEquidistantData(double x_0, double h, int n){

        double[][] data = new double[2][n];
        data[0][0] = x_0;
        for(int i = 1; i < n; i++){
            data[0][i] = x_0 + h * i;
        }

        for(int i = 0; i < n; i++){
            data[1][i] = sayDoubleNumber("Введите значение y для x = " + data[0][i]);
        }
        return data;
    }


    public double sayDoubleNumber(String name){
        double num = 0;
        String sNum;
        boolean flag = true;
        while (flag){
            try {
                System.out.print("Введите значение " + name +  " : ");
                sNum = scanner.nextLine().trim();
                if(fileMode) System.out.println(sNum);
                if(sNum.isEmpty()) throw new NullPointerException();
                if(sNum.equals("exit")){
                    System.exit(0);
                }
                num = Double.parseDouble(sNum);
                flag = false;
            } catch (NullPointerException e){
                System.out.println("Значение " + name +" не может быть пустым");
                if(fileMode) errorEnd();
            }  catch (NumberFormatException e){
                System.out.println("Значение " + name + " должно быть числом");
                if(fileMode) errorEnd();
            } catch (NoSuchElementException e){
                System.out.println("Данные не найдены в файле");
                System.exit(0);
            }
        }
        return num;
    }

    public int sayN(){
        int num = 0;
        String sNum;
        while (num < 2){
            try {
                System.out.print("Введите количество точек: ");
                sNum = scanner.nextLine().trim();
                if(fileMode) System.out.println(sNum);
                if(sNum.isEmpty()) throw new NullPointerException();
                num = Integer.parseInt(sNum);
                if(num < 2) throw new IncorrectValueException();
            } catch (IncorrectValueException e){
                System.out.println("Значение количества точек должно быть положительным числом больше 2");
                if(fileMode) errorEnd();
            } catch (NullPointerException e){
                System.out.println("Количество точек не может быть пустым");
                if(fileMode) errorEnd();
            }  catch (NumberFormatException e){
                System.out.println("Количество точек должно быть целым числом");
                if(fileMode) errorEnd();
            }
        }
        return num;
    }

    private void errorEnd(){
        System.out.println("В файле неверные данные, программа завершена");
        System.exit(0);
    }

}
