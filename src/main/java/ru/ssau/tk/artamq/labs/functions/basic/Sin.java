package ru.ssau.tk.artamq.labs.functions.basic;

// Класс, объекты которого представляют собой синус
public class Sin extends TrigonometricFunction {

    @Override
    public double getFunctionValue(double x) {
        return Math.sin(x);
    }
}
