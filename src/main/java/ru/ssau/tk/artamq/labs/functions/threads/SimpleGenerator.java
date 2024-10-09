package ru.ssau.tk.artamq.labs.functions.threads;

import ru.ssau.tk.artamq.labs.functions.basic.Log;

import java.util.concurrent.ThreadLocalRandom;

public class SimpleGenerator implements Runnable {
    private final Task task;

    public SimpleGenerator(Task task) {
        this.task = task;
    }

    public void run() {
        int taskCount = task.getTaskCount();

        synchronized (task) {
            for (int i = 0; i < taskCount; i++) {
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

                try {
                    task.wait();
                    task.notify();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
