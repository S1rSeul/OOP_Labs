package ru.ssau.tk.artamq.labs.functions;

// Класс, объект которого описывает табулированную функцию
public class TabulatedFunction {
    private FunctionPoint[] points; // Массив точек функции
    private int pointsCount; // Количество точек в массиве
    private final int ARRAY_SIZE = 100; // Константная длина массива

    // Конструктор объекта функции по границам и количеству точек
    public TabulatedFunction(double leftX, double rightX, int pointsCount) {
        points = new FunctionPoint[ARRAY_SIZE];
        this.pointsCount = pointsCount;

        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * step;
            points[i] = new FunctionPoint(x, 0);
        }
    }

    // Конструктор объекта функции по границам и массиву значений функции
    public TabulatedFunction(double leftX, double rightX, double[] values) {
        pointsCount = values.length;
        points = new FunctionPoint[ARRAY_SIZE];

        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * step;
            points[i] = new FunctionPoint(x, values[i]);
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
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) { return Double.NaN; }

        for (int i = 0; i < pointsCount - 1; i++) {
            if (x >= points[i].getX() && x <= points[i + 1].getX()) {
                FunctionPoint p1 = points[i];
                FunctionPoint p2 = points[i + 1];

                return p1.getY() + (p2.getY() - p1.getY()) * ((x - p1.getX()) / (p2.getX() - p1.getX()));
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
        return new FunctionPoint(points[index].getX(), points[index].getY());
    }

    // Внутренний метод проверки точки по индексу и значению по x на принадлежность интервалу
    private boolean checkNewPoint(int index, double pointX) {
        if (index > 0 && index < pointsCount - 1) {
            return !(pointX <= points[index - 1].getX()) && !(pointX >= points[index + 1].getX());
        }
        else if (index == 0) {
            return !(pointX >= points[1].getX());
        }
        else if (index == pointsCount - 1) {
            return !(pointX <= points[pointsCount - 2].getX());
        }

        return true;
    }

    // Сеттер нового значения точки в переданном индексе
    public void setPoint(int index, FunctionPoint point) {
        if (checkNewPoint(index, point.getX())) {
            points[index] = new FunctionPoint(point.getX(), point.getY());
        }
    }

    // Геттер значения точки по x, по индексу
    public double getPointX(int index) {
        return points[index].getX();
    }

    // Геттер значения точки по y, по индексу
    public double getPointY(int index) {
        return points[index].getY();
    }

    // Сеттер нового значения точки по x, по индексу
    public void setPointX(int index, double x) {
        if (checkNewPoint(index, x)) {
            points[index] = new FunctionPoint(x, points[index].getY());
        }
    }

    // Сеттер нового значения точки по y, по индексу
    public void setPointY(int index, double y) {
        points[index] = new FunctionPoint(points[index].getX(), y);
    }

    // Удаление точки по индексу
    public void deletePoint(int index) {
        System.arraycopy(points, index + 1, points, index, pointsCount - index);

        pointsCount--;
    }

    // Внутренний метод увеличения массива
    private void extendArray() {
        FunctionPoint[] newPoints = new FunctionPoint[points.length + ARRAY_SIZE];

        System.arraycopy(points, 0, newPoints, 0, pointsCount);

        points = newPoints;
    }

    // Добавление новой точки
    public void addPoint(FunctionPoint point) {
        if (pointsCount == points.length) { extendArray(); }

        int insertIndex = pointsCount;
        for (int i = 0; i < pointsCount; i++) {
            if (points[i].getX() > point.getX()) {
                insertIndex = i;
                break;
            }
        }

        if (insertIndex != pointsCount) {
            System.arraycopy(points, insertIndex, points, insertIndex + 1, pointsCount - insertIndex);
        }

        points[insertIndex] = point;

        pointsCount++;
    }

    // Вывод
    public void traverse() {
        for (int i = 0; i < pointsCount; i++) {
            System.out.print("(" + points[i].getX() + ", " + points[i].getY() + ") ");
        }
        System.out.println();
    }
}
