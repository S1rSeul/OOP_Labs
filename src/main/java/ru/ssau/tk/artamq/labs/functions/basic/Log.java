package ru.ssau.tk.artamq.labs.functions.basic;

import ru.ssau.tk.artamq.labs.functions.interfaces.Function;

// Класс, объекты которого представляют собой логарифм по переданному основанию
public class Log implements Function {
    private final double base;

    public Log(double base) {
        if (base <= 0 || Math.abs(base - 1) < 1e-10)
            throw new IllegalArgumentException("Основание логарифма должно быть больше нуля и не равняться единице");

        this.base = base;
    }

    public double getLeftDomainBorder() {
        return 0;
    }

    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    public double getFunctionValue(double x) {
        if (x <= 0)
            throw new IllegalArgumentException("Аргумент логарифма должен быть положительным");

        return Math.log(x) / Math.log(base);
    }
}
