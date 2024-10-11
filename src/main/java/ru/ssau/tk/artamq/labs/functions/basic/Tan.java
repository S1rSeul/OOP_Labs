package ru.ssau.tk.artamq.labs.functions.basic;

// Класс, объекты которого представляют собой тангенс
public class Tan extends TrigonometricFunction {

    @Override
    public double getFunctionValue(double x) {
        return Math.tan(x);
    }
}
