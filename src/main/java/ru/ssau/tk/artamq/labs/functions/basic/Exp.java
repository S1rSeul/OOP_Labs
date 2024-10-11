package ru.ssau.tk.artamq.labs.functions.basic;

import ru.ssau.tk.artamq.labs.functions.interfaces.Function;

// Класс, объекты которого представляют собой экспоненту
public class Exp implements Function {
    public double getLeftDomainBorder() {
        return Double.MIN_EXPONENT;
    }

    public double getRightDomainBorder() {
        return Double.MAX_EXPONENT;
    }

    public double getFunctionValue(double x) {
        return (Math.exp(x));
    }
}
