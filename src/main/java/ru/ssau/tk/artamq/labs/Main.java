package ru.ssau.tk.artamq.labs;

import ru.ssau.tk.artamq.labs.functions.Functions;
import ru.ssau.tk.artamq.labs.functions.basic.Exp;

// Основной класс
public class Main {
    // Точка входа в программу
    public static void main(String[] args) {
        Exp exp = new Exp();
        double exactValue = Math.E - 1;
        double n = 1;
        double estimatedValue;

        do {
            estimatedValue = Functions.integrate(exp, 0, 1, n);
            n /= 2;
        } while (Math.abs(estimatedValue - exactValue) >= 0.0000001);

        System.out.printf("Необходимый шаг дискретизации = %s\n", n);
        System.out.printf("При таком шаге мы получим = %s\n", estimatedValue);
        System.out.printf("Теоретическое значение = %s\n", exactValue);
    }
}
