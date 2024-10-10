package ru.ssau.tk.artamq.labs.functions.interfaces;

import ru.ssau.tk.artamq.labs.functions.FunctionPoint;

public interface TabulatedFunctionFactory {
    TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount);

    TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values);

    TabulatedFunction createTabulatedFunction(FunctionPoint[] pointsArray);
}
