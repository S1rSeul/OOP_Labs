package ru.ssau.tk.artamq.labs.functions;

import ru.ssau.tk.artamq.labs.functions.interfaces.Function;
import ru.ssau.tk.artamq.labs.functions.meta.*;

// Класс, содержащий вспомогательные методы для работы с функциями
public class Functions {
    // Запрет на создание объекта вне класса
    private Functions() {
        throw new UnsupportedOperationException("Создание экземпляра запрещено");
    }

    // Возвращает объект функции, полученной из исходной сдвигом вдоль осей
    public static Function shift(Function f, double shiftX, double shiftY) {
        return new Shift(f, shiftX, shiftY);
    }

    // Возвращает объект функции, полученной из исходной масштабированием вдоль осей
    public static Function scale(Function f, double scaleX, double scaleY) {
        return new Scale(f, scaleX, scaleY);
    }

    // Возвращает объект функции, являющейся заданной степенью исходной
    public static Function power(Function f, double power) {
        return new Power(f, power);
    }

    // Возвращает объект функции, являющейся суммой двух исходных
    public static Function sum(Function f1, Function f2) {
        return new Sum(f1, f2);
    }

    // Возвращает объект функции, являющейся произведением двух исходных
    public static Function mult(Function f1, Function f2) {
        return new Mult(f1, f2);
    }

    // Возвращает объект функции, являющейся композицией двух исходных
    public static Function composition(Function insideFun, Function outsideFun) {
        return new Composition(insideFun, outsideFun);
    }
}
