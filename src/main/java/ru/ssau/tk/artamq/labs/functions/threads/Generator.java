package ru.ssau.tk.artamq.labs.functions.threads;

import ru.ssau.tk.artamq.labs.functions.basic.Log;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

public class Generator extends Thread {
    private final Task task;
    private final Semaphore semaphore;

    public Generator(Task task, Semaphore semaphore) {
        this.task = task;
        this.semaphore = semaphore;
    }

    public void run() {
        try {
            int taskCount = task.getTaskCount();

            for (int i = 0; i < taskCount; i++) {
                semaphore.acquire();

                double base = ThreadLocalRandom.current().nextDouble(1, 10);
                Log log = new Log(base);
                task.setFunction(log);

                double leftBound = ThreadLocalRandom.current().nextDouble(0, 100);
                task.setLeftBound(leftBound);

                double rightBound = ThreadLocalRandom.current().nextDouble(100, 200);
                task.setRightBound(rightBound);

                double step = ThreadLocalRandom.current().nextDouble(0, 1);
                task.setStep(step);

                System.out.printf("%s. Source %s %s %s\n", i, leftBound, rightBound, step);

                semaphore.release();
            }
        } catch (Exception e) {
            System.out.println(e instanceof InterruptedException?
                    "Generator: Прерывание во время генерации задач.":
                    "Неизвестная ошибка");
        }
    }
}
