package ru.ssau.tk.artamq.labs.functions.meta;

import ru.ssau.tk.artamq.labs.functions.interfaces.Function;

// Класс представляющий собой произведение двух функций
public class Mult implements Function {
    private final Function fun1;
    private final Function fun2;

    public Mult(Function fun1, Function fun2) {
        this.fun1 = fun1;
        this.fun2 = fun2;
    }

    public double getLeftDomainBorder() {
        return Math.max(fun1.getLeftDomainBorder(), fun2.getLeftDomainBorder());
    }

    public double getRightDomainBorder() {
        return Math.min(fun1.getRightDomainBorder(), fun2.getRightDomainBorder());
    }

    public double getFunctionValue(double x) {
        return fun1.getFunctionValue(x) * fun2.getFunctionValue(x);
    }
}
