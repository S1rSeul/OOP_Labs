package ru.ssau.tk.artamq.labs.functions;

import ru.ssau.tk.artamq.labs.functions.exceptions.FunctionPointIndexOutOfBoundsException;
import ru.ssau.tk.artamq.labs.functions.exceptions.InappropriateFunctionPointException;
import ru.ssau.tk.artamq.labs.functions.interfaces.TabulatedFunction;

import java.io.*;

// Класс, объект которого описывает табулированную функцию, представляет собой массив точек
public class ArrayTabulatedFunction implements TabulatedFunction, Externalizable {
    @Serial
    private static final long serialVersionUID = 1L;

    private FunctionPoint[] points; // Массив точек функции
    private int pointsCount; // Количество точек в массиве

    // Пустой конструктор
    public ArrayTabulatedFunction() {
    }

    // Конструктор объекта функции по границам и количеству точек
    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (leftX >= rightX)
            throw new IllegalArgumentException("Левая граница больше или равна правой (" + leftX + " >= " + rightX + ")");

        if (pointsCount < 2)
            throw new IllegalArgumentException("Количество точек меньше двух(" + pointsCount + ")");

        points = new FunctionPoint[pointsCount + 10];
        this.pointsCount = pointsCount;

        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * step;
            points[i] = new FunctionPoint(x, 0);
        }
    }

    // Конструктор объекта функции по границам и массиву значений функции
    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) {
        if (leftX >= rightX)
            throw new IllegalArgumentException("Левая граница больше или равна правой (" + leftX + " >= " + rightX + ")");

        if (values.length < 2)
            throw new IllegalArgumentException("Количество точек меньше двух(" + values.length + ")");

        pointsCount = values.length;
        points = new FunctionPoint[pointsCount + 10];

        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * step;
            points[i] = new FunctionPoint(x, values[i]);
        }
    }

    // Конструктор объекта функции по массиву точек функции
    public ArrayTabulatedFunction(FunctionPoint[] pointsArray) {
        if (pointsArray.length < 2)
            throw new IllegalArgumentException("Количество точек меньше двух(" + pointsArray.length + ")");

        pointsCount = pointsArray.length;
        points = new FunctionPoint[pointsCount + 10];

        points[0] = pointsArray[0];
        for (int i = 1; i < pointsCount; i++) {
            if (pointsArray[i].getX() > pointsArray[i - 1].getX())
                points[i] = pointsArray[i];
            else
                throw new IllegalArgumentException("Точки в переданном массиве не упорядочены по значению x");
        }
    }

    // Геттер левой границы функции
    public double getLeftDomainBorder() {
        return points[0].getX();
    }

    // Геттер правой границы функции
    public double getRightDomainBorder() {
        return points[pointsCount - 1].getX();
    }

    // Получаем значение функции в заданной точке
    public double getFunctionValue(double x) {
        if (x >= getLeftDomainBorder() && x <= getRightDomainBorder()) {
            for (int i = 0; i < pointsCount - 1; i++) {
                if (x >= points[i].getX() && x <= points[i + 1].getX()) {
                    FunctionPoint p1 = points[i];
                    FunctionPoint p2 = points[i + 1];

                    return p1.getY() + (p2.getY() - p1.getY()) * ((x - p1.getX()) / (p2.getX() - p1.getX()));
                }
            }
        }
        return Double.NaN;
    }

    // Геттер количества точек
    public int getPointsCount() {
        return pointsCount;
    }

    // Геттер точки функции по индексу
    public FunctionPoint getPoint(int index) {
        if (index < 0 || index > pointsCount - 1)
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " выходит за границы набора точек");

        return new FunctionPoint(points[index].getX(), points[index].getY());
    }

    // Внутренний метод проверки точки по индексу и значению по x на принадлежность интервалу
    private boolean checkNewPoint(int index, double pointX) {
        if (index > 0 && index < pointsCount - 1) {
            return !(pointX <= points[index - 1].getX()) && !(pointX >= points[index + 1].getX());
        } else if (index == 0) {
            return !(pointX >= points[1].getX());
        } else if (index == pointsCount - 1) {
            return !(pointX <= points[pointsCount - 2].getX());
        }

        return true;
    }

    // Сеттер нового значения точки в переданном индексе
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (index < 0 || index > pointsCount - 1)
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " выходит за границы набора точек");

        if (checkNewPoint(index, point.getX()))
            points[index] = new FunctionPoint(point.getX(), point.getY());
        else
            throw new InappropriateFunctionPointException("Координата x(" + point.getX() + ") задаваемой точки лежит вне интервала");
    }

    // Геттер значения точки по x, по индексу
    public double getPointX(int index) {
        if (index < 0 || index > pointsCount - 1)
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " выходит за границы набора точек");

        return points[index].getX();
    }

    // Геттер значения точки по y, по индексу
    public double getPointY(int index) {
        if (index < 0 || index > pointsCount - 1)
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " выходит за границы набора точек");

        return points[index].getY();
    }

    // Сеттер нового значения точки по x, по индексу
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if (index < 0 || index > pointsCount - 1)
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " выходит за границы набора точек");

        if (checkNewPoint(index, x))
            points[index] = new FunctionPoint(x, points[index].getY());
        else
            throw new InappropriateFunctionPointException("Координата x(" + x + ") задаваемой точки лежит вне интервала");
    }

    // Сеттер нового значения точки по y, по индексу
    public void setPointY(int index, double y) {
        if (index < 0 || index > pointsCount - 1)
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " выходит за границы набора точек");

        points[index] = new FunctionPoint(points[index].getX(), y);
    }

    // Удаление точки по индексу
    public void deletePoint(int index) {
        if (index < 0 || index > pointsCount - 1)
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " выходит за границы набора точек");

        if (pointsCount < 3)
            throw new IllegalStateException("Количество точек меньше трех(" + pointsCount + ")");

        System.arraycopy(points, index + 1, points, index, pointsCount - index);

        pointsCount--;
    }

    // Внутренний метод увеличения массива
    private void extendArray() {
        FunctionPoint[] newPoints = new FunctionPoint[points.length + 10];

        System.arraycopy(points, 0, newPoints, 0, pointsCount);

        points = newPoints;
    }

    // Добавление новой точки
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if (pointsCount == points.length)
            extendArray();

        int insertIndex = pointsCount;
        for (int i = 0; i < pointsCount; i++) {
            if (points[i].getX() > point.getX()) {
                insertIndex = i;
                break;
            } else if (point.getX() == points[i].getX())
                throw new InappropriateFunctionPointException("Точка с такой абсциссой(" + point.getX() + ") уже существует");
        }

        if (insertIndex != pointsCount)
            System.arraycopy(points, insertIndex, points, insertIndex + 1, pointsCount - insertIndex);

        points[insertIndex] = point;

        pointsCount++;
    }

    @Override
    public String toString() {
        String out = "{";
        for (int i = 0; i < pointsCount; i++) {
            out += points[i].toString();
            if (i < pointsCount - 1)
                out += ", ";
        }
        out += "}";
        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof TabulatedFunction))
            return false;

        if (o instanceof ArrayTabulatedFunction) {
            ArrayTabulatedFunction other = (ArrayTabulatedFunction) o;
            if (this.pointsCount != other.pointsCount)
                return false;
            for (int i = 0; i < pointsCount; i++) {
                if (!this.points[i].equals(other.points[i]))
                    return false;
            }
            return true;
        }

        TabulatedFunction other = (TabulatedFunction) o;
        if (this.pointsCount != other.getPointsCount())
            return false;
        for (int i = 0; i < pointsCount; i++) {
            if (!this.points[i].equals(other.getPoint(i)))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int bits = pointsCount;
        for (int i = 0; i < pointsCount; i++) {
            bits ^= points[i].hashCode();
        }
        return bits;
    }

    @Override
    public ArrayTabulatedFunction clone() {
        try {
            ArrayTabulatedFunction cloned = (ArrayTabulatedFunction) super.clone();
            cloned.points = new FunctionPoint[pointsCount];
            for (int i = 0; i < pointsCount; i++) {
                cloned.points[i] = points[i].clone();
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(pointsCount);
        for (FunctionPoint point : points) {
            out.writeObject(point);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        pointsCount = in.readInt();
        points = new FunctionPoint[pointsCount + 10];
        for (int i = 0; i < pointsCount; i++) {
            points[i] = (FunctionPoint) in.readObject();
        }
    }
}
