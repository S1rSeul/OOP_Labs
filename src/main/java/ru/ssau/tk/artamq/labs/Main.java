package ru.ssau.tk.artamq.labs;

import ru.ssau.tk.artamq.labs.functions.*;
import ru.ssau.tk.artamq.labs.functions.basic.*;
import ru.ssau.tk.artamq.labs.functions.exceptions.InappropriateFunctionPointException;
import ru.ssau.tk.artamq.labs.functions.interfaces.Function;
import ru.ssau.tk.artamq.labs.functions.interfaces.TabulatedFunction;

import java.io.*;

// Основной класс
public class Main {
    // Точка входа в программу
    public static void main(String[] args) throws InappropriateFunctionPointException {
        // Начальные функции
        double[] values = {1, 4, 9, 16, 25, 36};
        TabulatedFunction linkedList = new LinkedListTabulatedFunction(0, 5, values);
        TabulatedFunction linkedList2 = new LinkedListTabulatedFunction(0, 5, values);
        TabulatedFunction otherLinkedList = new LinkedListTabulatedFunction(0, 10, values);
        TabulatedFunction arrayList = new ArrayTabulatedFunction(0, 5, values);
        TabulatedFunction arrayList2 = new ArrayTabulatedFunction(0, 5, values);
        TabulatedFunction otherArrayList = new ArrayTabulatedFunction(0, 10, values);

        // toString и hashCode
        System.out.println("Связанный список: ");
        System.out.println(linkedList.toString());
        System.out.println(linkedList.hashCode());

        System.out.println("Массив: ");
        System.out.println(arrayList.toString());
        System.out.println(arrayList.hashCode());

        // equal
        System.out.println("Корректный equal между массивом и списком: ");
        System.out.println(arrayList.equals(linkedList));
        System.out.println(linkedList.equals(arrayList));

        System.out.println("Корректный equal между массивом и массивом: ");
        System.out.println(arrayList.equals(arrayList2));

        System.out.println("Корректный equal между списком и списком: ");
        System.out.println(linkedList.equals(linkedList2));

        System.out.println("Некорректный equal между массивом и списком: ");
        System.out.println(arrayList.equals(otherLinkedList));
        System.out.println(linkedList.equals(otherArrayList));

        System.out.println("Некорректный equal между массивом и массивом: ");
        System.out.println(arrayList.equals(otherArrayList));

        System.out.println("Некорректный equal между списком и списком: ");
        System.out.println(linkedList.equals(otherLinkedList));

        // hashCode
        System.out.println("Хэш-код для других массива и списка: ");
        System.out.println(otherArrayList.toString());
        System.out.println(otherArrayList.hashCode());
        System.out.println(otherLinkedList.hashCode());

        System.out.println("Хэш-код после незначитального изменения объекта: ");
        otherArrayList.setPointY(0, 1.0001);
        System.out.println(otherArrayList.hashCode());

        // Клонирование
        TabulatedFunction clonedArray = arrayList.clone();
        TabulatedFunction clonedList = linkedList.clone();

        System.out.println("Исходные и клонированные объекты: ");
        System.out.println(arrayList.toString());
        System.out.println(linkedList.toString());
        System.out.println(clonedArray.toString());
        System.out.println(clonedList.toString());

        System.out.println("Исходные и клонированные объекты после изменения исходных: ");
        FunctionPoint newArrayPoint = new FunctionPoint(50, 100);
        FunctionPoint newListPoint = new FunctionPoint(15, 25);
        arrayList.addPoint(newArrayPoint);
        linkedList.addPoint(newListPoint);
        System.out.println(arrayList.toString());
        System.out.println(linkedList.toString());
        System.out.println(clonedArray.toString());
        System.out.println(clonedList.toString());
    }
}
