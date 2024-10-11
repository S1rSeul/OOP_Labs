package ru.ssau.tk.artamq.labs.functions.basic;

import ru.ssau.tk.artamq.labs.functions.interfaces.Function;

// Абстрактный класс тригонометрических функций
public abstract class TrigonometricFunction implements Function {
    public double getLeftDomainBorder() {
        return Double.NEGATIVE_INFINITY;
    }

    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    public abstract double getFunctionValue(double x);
}
