package ru.ssau.tk.artamq.labs.functions.interfaces;

import ru.ssau.tk.artamq.labs.functions.FunctionPoint;
import ru.ssau.tk.artamq.labs.functions.exceptions.InappropriateFunctionPointException;

public interface TabulatedFunction extends Function, Cloneable {

    int getPointsCount();
    FunctionPoint getPoint(int index);
    void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException;
    double getPointX(int index);
    double getPointY(int index);
    void setPointX(int index, double x) throws InappropriateFunctionPointException;
    void setPointY(int index, double y);
    void deletePoint(int index);
    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;
    TabulatedFunction clone();
}
