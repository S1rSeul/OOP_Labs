package ru.ssau.tk.artamq.labs;

import ru.ssau.tk.artamq.labs.functions.Functions;
import ru.ssau.tk.artamq.labs.functions.TabulatedFunctions;
import ru.ssau.tk.artamq.labs.functions.basic.*;
import ru.ssau.tk.artamq.labs.functions.interfaces.Function;
import ru.ssau.tk.artamq.labs.functions.interfaces.TabulatedFunction;

import java.io.*;

// Основной класс
public class Main {
    // Точка входа в программу
    public static void main(String[] args) {
        Sin sin = new Sin();
        Cos cos = new Cos();

        System.out.println("Значения функции синуса: ");
        for (double x = 0; x <= 2*Math.PI; x += 0.1) {
            System.out.printf("sin(%s) = %s\n", roundToTenths(x), sin.getFunctionValue(roundToTenths(x)));
        }

        System.out.println();

        System.out.println("Значения функции косинуса: ");
        for (double x = 0; x <= 2*Math.PI; x += 0.1) {
            System.out.printf("cos(%s) = %s\n", roundToTenths(x), cos.getFunctionValue(roundToTenths(x)));
        }

        System.out.println();

        TabulatedFunction tabulatedSin = TabulatedFunctions.tabulate(sin, 0, 2*Math.PI, 10);
        TabulatedFunction tabulatedCos = TabulatedFunctions.tabulate(cos, 0, 2*Math.PI, 10);

        System.out.println("Значения табулированного синуса: ");
        for (double x = 0; x <= 2*Math.PI; x += 0.1) {
            System.out.printf("tabulatedSin(%s) = %s\n", roundToTenths(x), tabulatedSin.getFunctionValue(roundToTenths(x)));
        }

        System.out.println();

        System.out.println("Значения табулированного косинуса: ");
        for (double x = 0; x <= 2*Math.PI; x += 0.1) {
            System.out.printf("tabulatedCos(%s) = %s\n", roundToTenths(x), tabulatedCos.getFunctionValue(roundToTenths(x)));
        }

        System.out.println();

        Function sqrtSin = Functions.power(tabulatedSin, 2);
        Function sqrtCos = Functions.power(tabulatedCos, 2);
        Function sumSqrtSinSqrtCos = Functions.sum(sqrtSin, sqrtCos);

        System.out.println("Значения суммы квадратов таб. синуса и косинуса: ");
        for (double x = 0; x <= 2*Math.PI; x += 0.1) {
            System.out.printf("sin(%s)^2 + cos(%s)^2 = %s\n", roundToTenths(x), roundToTenths(x), sumSqrtSinSqrtCos.getFunctionValue(roundToTenths(x)));
        }

        System.out.println();

        Exp exp = new Exp();
        TabulatedFunction tabulatedExp = TabulatedFunctions.tabulate(exp, 0, 10, 11);

        System.out.println("Табулированная экспонента: ");
        System.out.printf("Количество точек: %s\n", tabulatedExp.getPointsCount());
        tabulatedExp.traverse();

        try (Writer writer = new FileWriter("tabulatedExp.txt")) {
            System.out.println("Попытка записи в файл");
            TabulatedFunctions.writeTabulatedFunction(tabulatedExp, writer);
            System.out.println("Запись завершена корректно");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Reader reader = new FileReader("tabulatedExp.txt")) {
            System.out.println("Попытка прочтения файла");
            TabulatedFunction readExp = TabulatedFunctions.readTabulatedFunction(reader);
            System.out.println("Прочтенная экспонента: ");
            System.out.printf("Количество точек: %s\n", readExp.getPointsCount());
            readExp.traverse();
            System.out.println("Файл прочтен корректно");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println();

        Log ln = new Log(Math.E);
        TabulatedFunction tabulatedLn = TabulatedFunctions.tabulate(ln, 1, 11, 11);

        System.out.println("Табулированный натуральный логарифм: ");
        System.out.printf("Количество точек: %s\n", tabulatedLn.getPointsCount());
        tabulatedLn.traverse();

        try (OutputStream out = new FileOutputStream("tabulatedLog.bin")) {
            System.out.println("Попытка записи в файл");
            TabulatedFunctions.outputTabulatedFunction(tabulatedLn, out);
            System.out.println("Запись завершена корректно");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (InputStream in = new FileInputStream("tabulatedLog.bin")) {
            System.out.println("Попытка прочтения файла");
            TabulatedFunction readLn = TabulatedFunctions.inputTabulatedFunction(in);
            System.out.println("Прочтенный логарифм: ");
            System.out.printf("Количество точек: %s\n", readLn.getPointsCount());
            readLn.traverse();
            System.out.println("Файл прочтен корректно");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static double roundToTenths(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
