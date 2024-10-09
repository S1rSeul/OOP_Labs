package ru.ssau.tk.artamq.labs.functions.meta;

import ru.ssau.tk.artamq.labs.functions.interfaces.Function;

// Класс представляющий собой возведение функции в степень
public class Power implements Function {
    private final Function base;
    private final double power;

    public Power(Function base, double power) {
        this.base = base;
        this.power = power;
    }

    public double getLeftDomainBorder() {
        return base.getLeftDomainBorder();
    }

    public double getRightDomainBorder() {
        return base.getRightDomainBorder();
    }

    public double getFunctionValue(double x) {
        return Math.pow(base.getFunctionValue(x), power);
    }
}
