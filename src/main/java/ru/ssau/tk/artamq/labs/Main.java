package ru.ssau.tk.artamq.labs;

import ru.ssau.tk.artamq.labs.functions.*;
import ru.ssau.tk.artamq.labs.functions.exceptions.InappropriateFunctionPointException;
import ru.ssau.tk.artamq.labs.functions.interfaces.TabulatedFunction;

// Основной класс
public class Main {
    // Точка входа в программу
    public static void main(String[] args) throws InappropriateFunctionPointException {
        double[] values = new double[] {0, 1, 2, 3, 4};
        FunctionPoint newSetPointException = new FunctionPoint(12, 5);
        FunctionPoint newSetPoint = new FunctionPoint(20, 50);
        FunctionPoint exceptionPoint = new FunctionPoint(3, 100);

        // Функции, вызывающие исключения
        // TabulatedFunction exceptionFunction = new LinkedListTabulatedFunction(5, 2, values);
        // TabulatedFunction exceptionFunction1 = new ArrayTabulatedFunction(1, 5, 1);

        // Работающая функция
        TabulatedFunction function = new ArrayTabulatedFunction(0, 5, values);

        // Вывод изначального списка
        function.traverse();

        // Вывод значения x, присутствующего в объекте
        System.out.printf("Значение функции в точке %s = %s\n", 5, function.getFunctionValue(5));

        // Вывод значения x, не присутствующего в объекте
        System.out.printf("Значение функции в точке %s = %s\n", 3, function.getFunctionValue(3));

        // Вывод количества точек в функции
        System.out.printf("Количество точек в функции: %s\n", function.getPointsCount());

        // Исключение getPoint
        // System.out.println(function.getPoint(10));

        // Вывод точки по индексу
        System.out.printf("Точка по 3-му индексу: %s\n", function.getPoint(3).toString());

        // Исключение setPoint
        // function.setPoint(0, newSetPointException);

        // Установка точки
        function.setPoint(4, newSetPoint);
        function.traverse();

        // Удаление точек
        function.deletePoint(4);
        function.traverse();
        function.deletePoint(3);
        function.traverse();
        function.deletePoint(2);
        function.traverse();
        // Здесь вылетает исключение
        function.deletePoint(1);

        // Исключение добавление точки
        // function.addPoint(new FunctionPoint(0, 20));

        // Добавление точки
        function.addPoint(new FunctionPoint(10, 10));
        function.traverse();
        function.addPoint(new FunctionPoint(3, 100));
        function.traverse();
        function.addPoint(new FunctionPoint(-1, -10));
        function.traverse();
        System.out.printf("Количество точек в функции: %s\n", function.getPointsCount());
    }
}
