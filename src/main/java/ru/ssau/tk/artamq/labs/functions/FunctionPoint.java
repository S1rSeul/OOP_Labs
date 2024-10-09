package ru.ssau.tk.artamq.labs.functions;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

// Класс, объект которого описывает точку табулированной функции
public class FunctionPoint implements Externalizable {
    private double x; // Координата точки по оси абсцисс
    private double y; // Координата точки по оси ординат

    // Конструктор без параметров
    public FunctionPoint() {}

    // Конструктор с параметрами координат точек
    public FunctionPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Копирующий конструктор
    public FunctionPoint(FunctionPoint point) {
        this(point.x, point.y);
    }

    // Геттер по x
    public double getX() {
        return x;
    }

    // Геттер по y
    public double getY() {
        return y;
    }

    // Сеттер по x
    public void setX(double x) {
        this.x = x;
    }

    // Сеттер по y
    public void setY(double y) {
        this.y = y;
    }

    // Вывод точки в консоль
    @Override
    public String toString() {
        return "(" + x + "; " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || this.getClass() != o.getClass())
            return false;

        FunctionPoint other = (FunctionPoint) o;
        return Double.compare(x, other.x) == 0 && Double.compare(y, other.y) == 0;
    }

    @Override
    public int hashCode() {
        long xBits = Double.doubleToLongBits(x);
        long yBits = Double.doubleToLongBits(y);

        int xLower = (int) (xBits & 0xFFFFFFFFL);
        int xHigher = (int) ((xBits >> 32) & 0xFFFFFFFFL);

        int yLower = (int) (yBits & 0xFFFFFFFFL);
        int yHigher = (int) ((yBits >> 32) & 0xFFFFFFFFL);

        return xLower ^ xHigher ^ yLower ^ yHigher;
    }

    @Override
    public FunctionPoint clone() {
        return new FunctionPoint(this);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(x);
        out.writeDouble(y);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        x = in.readDouble();
        y = in.readDouble();
    }
}
