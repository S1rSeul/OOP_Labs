package ru.ssau.tk.artamq.labs;

import ru.ssau.tk.artamq.labs.functions.threads.SimpleGenerator;
import ru.ssau.tk.artamq.labs.functions.threads.SimpleIntegrator;
import ru.ssau.tk.artamq.labs.functions.threads.Task;

public class MainSimpleThreads {
    public static void main(String[] args) {
        simpleThreads();
    }

    public static void simpleThreads() {
        Task task = new Task();
        int taskNum = 100;
        task.setTaskCount(taskNum);

        SimpleGenerator generator = new SimpleGenerator(task);
        SimpleIntegrator integrator = new SimpleIntegrator(task);

        Thread generatorThread = new Thread(generator);
        Thread integratorThread = new Thread(integrator);

        generatorThread.setPriority(Thread.NORM_PRIORITY);
        integratorThread.setPriority(Thread.NORM_PRIORITY);

        generatorThread.start();
        integratorThread.start();

        try {
            generatorThread.join();
            integratorThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Все задачи были выполнены");
    }
}
