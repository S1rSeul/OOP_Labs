package ru.ssau.tk.artamq.labs;

import ru.ssau.tk.artamq.labs.functions.threads.Generator;
import ru.ssau.tk.artamq.labs.functions.threads.Integrator;
import ru.ssau.tk.artamq.labs.functions.threads.Task;

import java.util.concurrent.Semaphore;

public class ComplicatedThreads {
    public static void main(String[] args) {
        complicatedThreads();
    }

    public static void complicatedThreads() {
        Task task = new Task();
        int taskNum = 100;
        task.setTaskCount(taskNum);

        Semaphore semaphore = new Semaphore(1, true);

        Generator generator = new Generator(task, semaphore);
        Integrator integrator = new Integrator(task, semaphore);

        generator.setPriority(Thread.NORM_PRIORITY);
        integrator.setPriority(Thread.NORM_PRIORITY);

        generator.start();
        integrator.start();

        int millis = 50;

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        generator.interrupt();
        integrator.interrupt();
        System.out.printf("Потоки были прерваны через %s миллисекунд после запуска\n", millis);

        System.out.println("Основной поток завершен");
    }
}
