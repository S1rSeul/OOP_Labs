package ru.ssau.tk.artamq.labs.functions.threads;

import ru.ssau.tk.artamq.labs.functions.Functions;
import ru.ssau.tk.artamq.labs.functions.interfaces.Function;

// Класс, объект которого описывает простой интегратор для решений интегралов
public class SimpleIntegrator implements Runnable {
    private final Task task; // Поле заданий

    public SimpleIntegrator(Task task) {
        this.task = task;
    }

    // Метод запуска потока
    public void run() {
        synchronized (task) {
            for (int i = 0; i < task.getTaskCount(); i++) {
                Function function = task.getFunction();
                double leftBound = task.getLeftBound();
                double rightBound = task.getRightBound();
                double step = task.getStep();

                double result = Functions.integrate(function, leftBound, rightBound, step);

                System.out.printf("Result %s %s %s %s\n", leftBound, rightBound, step, result);

                try {
                    task.notify();
                    task.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
