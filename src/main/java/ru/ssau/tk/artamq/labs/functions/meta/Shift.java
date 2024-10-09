package ru.ssau.tk.artamq.labs.functions.meta;

import ru.ssau.tk.artamq.labs.functions.interfaces.Function;

// Класс представляющий собой сдвиг функции
public class Shift implements Function {
    private final Function function;
    private final double shiftX;
    private final double shiftY;

    public Shift(Function function, double shiftX, double shiftY) {
        this.function = function;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
    }

    public double getLeftDomainBorder() {
        return function.getLeftDomainBorder() + shiftX;
    }

    public double getRightDomainBorder() {
        return function.getRightDomainBorder() + shiftX;
    }

    public double getFunctionValue(double x) {
        return function.getFunctionValue(x - shiftX) + shiftY;
    }
}
