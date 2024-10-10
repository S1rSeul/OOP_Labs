package ru.ssau.tk.artamq.labs.functions.threads;

import ru.ssau.tk.artamq.labs.functions.interfaces.Function;

// Класс, объект которого описывает задание для интегрирования
public class Task {
    private Function function; // Функция для интегрирования
    private double leftBound; // Левая граница интегрирования
    private double rightBound; // Правая граница интегрирования
    private double step; // Шаг дискретизации
    private int taskCount; // Количество заданий

    public Task() {
    }

    public synchronized Function getFunction() {
        return function;
    }

    public synchronized void setFunction(Function function) {
        this.function = function;
    }

    public synchronized double getLeftBound() {
        return leftBound;
    }

    public synchronized void setLeftBound(double leftBound) {
        this.leftBound = leftBound;
    }

    public synchronized double getRightBound() {
        return rightBound;
    }

    public synchronized void setRightBound(double rightBound) {
        this.rightBound = rightBound;
    }

    public synchronized double getStep() {
        return step;
    }

    public synchronized void setStep(double step) {
        this.step = step;
    }

    public synchronized int getTaskCount() {
        return taskCount;
    }

    public synchronized void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }
}
