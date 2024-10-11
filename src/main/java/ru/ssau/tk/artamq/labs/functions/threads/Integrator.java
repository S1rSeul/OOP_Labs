package ru.ssau.tk.artamq.labs.functions.threads;

import ru.ssau.tk.artamq.labs.functions.Functions;
import ru.ssau.tk.artamq.labs.functions.interfaces.Function;

import java.util.concurrent.Semaphore;

// Класс, объект которого описывает поток для решения заданий
public class Integrator extends Thread {
    private final Task task; // Поле заданий
    private final Semaphore semaphore; // Семафор

    public Integrator(Task task, Semaphore semaphore) {
        this.task = task;
        this.semaphore = semaphore;
    }

    public void run() {
        try {
            for (int i = 0; i < task.getTaskCount(); i++) {
                semaphore.acquire();

                Function function = task.getFunction();
                double leftBound = task.getLeftBound();
                double rightBound = task.getRightBound();
                double step = task.getStep();

                double result = Functions.integrate(function, leftBound, rightBound, step);

                System.out.printf("Result %s %s %s %s\n", leftBound, rightBound, step, result);

                semaphore.release();
            }
        } catch (Exception e) {
            System.out.println(e instanceof InterruptedException?
                    "Integrator: Прерывание во время интеграции задач.":
                    "Неизвестная ошибка");
        }
    }
}