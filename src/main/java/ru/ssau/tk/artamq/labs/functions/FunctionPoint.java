package ru.ssau.tk.artamq.labs.functions;

// Класс, объект которого описывает точку табулированной функции
public class FunctionPoint {
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
    public void setX(double x) { this.x = x; }

    // Сеттер по y
    public void setY(double y) { this.y = y; }
}
