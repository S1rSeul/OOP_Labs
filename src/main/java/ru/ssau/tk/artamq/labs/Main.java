package ru.ssau.tk.artamq.labs;

import ru.ssau.tk.artamq.labs.functions.*;
import ru.ssau.tk.artamq.labs.functions.interfaces.*;
import ru.ssau.tk.artamq.labs.functions.basic.*;

// Основной класс
public class Main {
    // Точка входа в программу
    public static void main(String[] args) {
        // Task1
        System.out.println("Task1: ");
        double[] values = {0, 1, 2, 4, 9, 16, 25};
        TabulatedFunction task1 = new LinkedListTabulatedFunction(0, 6, values);

        for (FunctionPoint p : task1) {
            System.out.println(p);
        }

        // Task2
        System.out.println("\nTask2: ");
        Function task2 = new Cos();
        TabulatedFunction tf;
        tf = TabulatedFunctions.tabulate(task2, 0, Math.PI, 11);
        System.out.println(tf.getClass());
        TabulatedFunctions.setTabulatedFunctionFactory(new
                LinkedListTabulatedFunction.LinkedListTabulatedFunctionFactory());
        tf = TabulatedFunctions.tabulate(task2, 0, Math.PI, 11);
        System.out.println(tf.getClass());
        TabulatedFunctions.setTabulatedFunctionFactory(new
                ArrayTabulatedFunction.ArrayTabulatedFunctionFactory());
        tf = TabulatedFunctions.tabulate(task2, 0, Math.PI, 11);
        System.out.println(tf.getClass());

        // Task3
        System.out.println("\nTask3: ");
        TabulatedFunction f;
        f = TabulatedFunctions.createTabulatedFunction(
                ArrayTabulatedFunction.class, 0, 10, 3);
        System.out.println(f.getClass());
        System.out.println(f);

        f = TabulatedFunctions.createTabulatedFunction(
                ArrayTabulatedFunction.class, 0, 10, new double[] {0, 10});
        System.out.println(f.getClass());
        System.out.println(f);

        f = TabulatedFunctions.createTabulatedFunction(
                LinkedListTabulatedFunction.class,
                new FunctionPoint[] {
                        new FunctionPoint(0, 0),
                        new FunctionPoint(10, 10)
                }
        );
        System.out.println(f.getClass());
        System.out.println(f);

        f = TabulatedFunctions.tabulate(
                LinkedListTabulatedFunction.class, new Sin(), 0, Math.PI, 11);
        System.out.println(f.getClass());
        System.out.println(f);

    }
}
