package ru.ssau.tk.artamq.labs;
import ru.ssau.tk.artamq.labs.functions.*;

// Основной класс
public class Main {

    // Точка входа в программу
    public static void main(String[] args) {
        FunctionPoint newPoint = new FunctionPoint(1.5, 24);
        FunctionPoint newPoint1 = new FunctionPoint(3.66, 60);
        double[] values = new double[] {0, 1, 4, 9, 16, 25};
        TabulatedFunction fun = new TabulatedFunction(0, 5, values);

        // Проверка метода deletePoint
        fun.traverse();
        fun.deletePoint(0);
        fun.traverse();
        fun.deletePoint(3);
        fun.traverse();

        // Проверка метода addPoint
        fun.addPoint(newPoint);
        fun.traverse();
        fun.addPoint(newPoint1);
        fun.traverse();
    }
}
