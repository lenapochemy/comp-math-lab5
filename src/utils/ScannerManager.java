package utils;

import exceptions.FileException;
import exceptions.IncorrectValueException;
import polynomials.Polynomials;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
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
    //вернет true если ввод с клавиатуры
//    public boolean sayInputMode(){
//        boolean flag = false;
//        while(!flag) {
//            try {
//                System.out.print("Вы хотите вводить данные с клавиатуры или из файла? (k/f) ");
//                String ans = scanner.nextLine().trim();
//                switch (ans) {
//                    case "" ->
//                        throw new NullPointerException();
//                    case "k" -> {
//                        flag = true;
//                        return true;
//                    }
//                    case "f" -> {
//                        flag = true;
//                        return false;
//                    }
//                    default -> throw new IncorrectValueException();
//                }
//            } catch (IncorrectValueException | NullPointerException e){
//                System.out.println("Ответ должен быть \"k\" или \"f\"");
//            }
//        }
//        return flag;
//    }

    //вернет true если ввод точек
//    public boolean sayInputValueType(){
//        boolean flag = false;
//        while(!flag) {
//            try {
//                System.out.print("Задать исходные данные точками или функцией? (1/2)");
//                String ans = scanner.nextLine().trim();
//                switch (ans) {
//                    case "" ->
//                            throw new NullPointerException();
//                    case "1" -> {
//                        flag = true;
//                        return true;
//                    }
//                    case "2" -> {
//                        flag = true;
//                        return false;
//                    }
//                    default -> throw new IncorrectValueException();
//                }
//            } catch (IncorrectValueException | NullPointerException e){
//                System.out.println("Ответ должен быть \"1\" или \"2\"");
//            } catch (NoSuchElementException e){
//                System.out.println("Данные не найдены в файле");
//                System.exit(0);
//            }
//        }
//        return flag;
//    }
//
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


//    public FileWriter sayFileToWrite(){
//        String sFile;
//        FileWriter writer = null;
//        while(writer == null){
//            try{
//                System.out.println("Введите путь к файлу:");
//                sFile = scanner.nextLine().trim();
//                if(fileMode) System.out.println(sFile);
//                if(sFile.isEmpty()) throw new NullPointerException();
//                File file = new File(sFile);
//                if(file.exists() && !file.canWrite()) throw new FileException();
//                writer = new FileWriter(file);
//            } catch (NullPointerException e){
//                System.out.println("Путь не может быть пустым");
//            } catch (FileException e){
//                System.out.println("В файл невозможно записать");
//            } catch (IOException e){
//                System.out.println("Файл не найден");
//            } catch (NoSuchElementException e){
//                System.out.println("Данные не найдены в файле");
//                System.exit(0);
//            }
//        }
//        return writer;
//    }

    public Polynomials sayMethodFromTwo(){
        boolean flag = false;
        while(!flag) {
            try {
                System.out.println("Выберите метод:\n\t1. Многочлен Лагранжа\n\t2. Многочлен Ньютона с разделенными разностями");
                String ans = scanner.nextLine().trim();
                if(fileMode) System.out.println(ans);
                switch (ans) {
                    case "" ->
                            throw new NullPointerException();
                    case "1" -> {
                        flag = true;
                        return Polynomials.LAGRANGE;
                    }
                    case "2" -> {
                        flag = true;
                        return Polynomials.NEWTON;
                    }
                    default -> throw new IncorrectValueException();
                }
            } catch (IncorrectValueException | NullPointerException e){
                System.out.println("Ответ должен быть положительным числом, не большим 5");
                if(fileMode) errorEnd();
            } catch (NoSuchElementException e){
                System.out.println("Введите значение");
//                System.exit(0);
            }
        }
        return null;
    }


    public Polynomials sayMethod(){
        boolean flag = false;
        while(!flag) {
            try {
                System.out.println("Выберите метод:\n\t1. Многочлен Лагранжа\n\t2. Многочлен Ньютона с разделенными разностями\n\t" +
                        "3. Многочлен Гаусса\n\t4. Схема Стрилинга\n\t5. Схема Бесселя");
                String ans = scanner.nextLine().trim();
                if(fileMode) System.out.println(ans);
                switch (ans) {
                    case "" ->
                            throw new NullPointerException();
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
                        return Polynomials.GAUSS;
                    }
                    case "4" -> {
                        flag = true;
                        return Polynomials.STIRLING;
                    }
                    case "5" -> {
                        flag = true;
                        return Polynomials.BESSEL;
                    }
                    default -> throw new IncorrectValueException();
                }
            } catch (IncorrectValueException | NullPointerException e){
                System.out.println("Ответ должен быть положительным числом, не большим 5");
                if(fileMode) errorEnd();
            } catch (NoSuchElementException e){
                System.out.println("Введите значение");
//                System.exit(0);
            }
        }
        return null;
    }

    public double[][] sayInitialDotsData(){
        int n = sayN();
        double[][] data = new double[2][n];
        System.out.println("Введите исходные точки парами (x, y) через пробел");
        for(int i = 0; i < n; i++){
            String[] num = new String[2];
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
//            map.put(x, y);
            data[0][i] = x;
            data[1][i] = y;
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

    public double sayH(){
        double h = -1;
        while(h < 0){
            h = sayDoubleNumber("значение шага между точками");
        }
        return h;
    }

    public int sayN(){
        int num = 0;
        String sNum;
        while (num < 2  || num > 12){
            try {
                System.out.print("Введите количество точек от 2 до 12: ");
                sNum = scanner.nextLine().trim();
                if(fileMode) System.out.println(sNum);
                if(sNum.isEmpty()) throw new NullPointerException();
                num = Integer.parseInt(sNum);
                if(num < 2  || num > 12) throw new IncorrectValueException();
            } catch (IncorrectValueException e){
                System.out.println("Значение количества точек должно быть положительным числои из промежутка [2;12]");
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
