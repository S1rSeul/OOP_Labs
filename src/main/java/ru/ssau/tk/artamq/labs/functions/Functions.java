package ru.ssau.tk.artamq.labs.functions;

import ru.ssau.tk.artamq.labs.functions.interfaces.Function;
import ru.ssau.tk.artamq.labs.functions.meta.*;

// Класс, содержащий вспомогательные методы для работы с функциями
public class Functions {
    private Functions() {
        throw new UnsupportedOperationException("Создание экземпляра запрещено");
    }

    public static Function shift(Function f, double shiftX, double shiftY) {
        return new Shift(f, shiftX, shiftY);
    }

    public static Function scale(Function f, double scaleX, double scaleY) {
        return new Scale(f, scaleX, scaleY);
    }

    public static Function power(Function f, double power) {
        return new Power(f, power);
    }

    public static Function sum(Function f1, Function f2) {
        return new Sum(f1, f2);
    }

    public static Function mult(Function f1, Function f2) {
        return new Mult(f1, f2);
    }

    public static Function composition(Function insideFun, Function outsideFun) {
        return new Composition(insideFun, outsideFun);
    }
}
