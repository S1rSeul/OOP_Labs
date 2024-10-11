package ru.ssau.tk.artamq.labs;

import ru.ssau.tk.artamq.labs.functions.Functions;
import ru.ssau.tk.artamq.labs.functions.basic.Log;
import ru.ssau.tk.artamq.labs.functions.threads.Task;

import java.util.concurrent.ThreadLocalRandom;

public class MainNonThreads {
    public static void main(String[] args) {
        nonThread();
    }

    public static void nonThread() {
        Task task = new Task();
        int numOfTasks = 100;
        task.setTaskCount(numOfTasks);

        for (int i = 0; i < numOfTasks; i++) {
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
            double result = Functions.integrate(log, leftBound, rightBound, step);
            System.out.printf("Result %s %s %s %s\n", leftBound, rightBound, step, result);
        }
    }
}
