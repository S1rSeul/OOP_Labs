package ru.ssau.tk.artamq.labs.functions.meta;

import ru.ssau.tk.artamq.labs.functions.interfaces.Function;

// Класс представляющий собой масштабирование функции
public class Scale implements Function {
    private final Function function;
    private final double scaleX;
    private final double scaleY;

    public Scale(Function function, double scaleX, double scaleY) {
        if (scaleX == 0)
            throw new IllegalArgumentException("scaleX не может быть равен нулю");

        this.function = function;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public double getLeftDomainBorder() {
        return function.getLeftDomainBorder() * scaleX;
    }

    public double getRightDomainBorder() {
        return function.getRightDomainBorder() * scaleX;
    }

    public double getFunctionValue(double x) {
        return function.getFunctionValue(x / scaleX) * scaleY;
    }
}
