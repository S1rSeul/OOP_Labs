package ru.ssau.tk.artamq.labs.functions.basic;

// Класс, объекты которого представляют собой косинус
public class Cos extends TrigonometricFunction {

    @Override
    public double getFunctionValue(double x) {
        return Math.cos(x);
    }
}
