package ru.ssau.tk.artamq.labs.functions.meta;

import ru.ssau.tk.artamq.labs.functions.interfaces.Function;

// Класс представляющий собой композицию двух функций
public class Composition implements Function {
    private final Function insideFun;
    private final Function outsideFun;

    public Composition(Function insideFun, Function outsideFun) {
        this.insideFun = insideFun;
        this.outsideFun = outsideFun;
    }

    public double getLeftDomainBorder() {
        return insideFun.getLeftDomainBorder();
    }

    public double getRightDomainBorder() {
        return insideFun.getRightDomainBorder();
    }

    public double getFunctionValue(double x) {
        return outsideFun.getFunctionValue(insideFun.getFunctionValue(x));
    }
}
