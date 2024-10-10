package ru.ssau.tk.artamq.labs.functions;

import ru.ssau.tk.artamq.labs.functions.interfaces.Function;
import ru.ssau.tk.artamq.labs.functions.interfaces.TabulatedFunction;
import ru.ssau.tk.artamq.labs.functions.interfaces.TabulatedFunctionFactory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

// Класс, содержащий вспомогательные методы для работы с табулированными функциями
public abstract class TabulatedFunctions {
    private static TabulatedFunctionFactory factory = new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();

    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory newFactory) {
        factory = newFactory;
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
        return factory.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
        return factory.createTabulatedFunction(leftX, rightX, values);
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] pointsArray) {
        return factory.createTabulatedFunction(pointsArray);
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> cls, double leftX, double rightX, int pointsCount) {
        try {
            Constructor<? extends TabulatedFunction> constructor = cls.getConstructor(double.class, double.class, int.class);
            return constructor.newInstance(leftX, rightX, pointsCount);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> cls, double leftX, double rightX, double[] values) {
        try {
            Constructor<? extends TabulatedFunction> constructor = cls.getConstructor(double.class, double.class, double[].class);
            return constructor.newInstance(leftX, rightX, values);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> cls, FunctionPoint[] pointsArray) {
        try {
            Constructor<? extends TabulatedFunction> constructor = cls.getConstructor(FunctionPoint[].class);
            return constructor.newInstance((Object) pointsArray);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    // Табуляции переданной функции
    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) {
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder())
            throw new IllegalArgumentException("Границы табулирования выходят за область определния функции");

        if (leftX >= rightX)
            throw new IllegalArgumentException("Левая граница больше или равна правой (" + leftX + " >= " + rightX + ")");

        if (pointsCount < 2)
            throw new IllegalArgumentException("Количество точек меньше двух(" + pointsCount + ")");

        FunctionPoint[] pointsArray = new FunctionPoint[pointsCount];

        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * step;
            pointsArray[i] = new FunctionPoint(x, function.getFunctionValue(x));
        }

        return factory.createTabulatedFunction(pointsArray);
    }

    public static TabulatedFunction tabulate(Class<? extends TabulatedFunction> cls, Function function, double leftX, double rightX, int pointsCount) {
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder())
            throw new IllegalArgumentException("Границы табулирования выходят за область определния функции");

        if (leftX >= rightX)
            throw new IllegalArgumentException("Левая граница больше или равна правой (" + leftX + " >= " + rightX + ")");

        if (pointsCount < 2)
            throw new IllegalArgumentException("Количество точек меньше двух(" + pointsCount + ")");

        FunctionPoint[] pointsArray = new FunctionPoint[pointsCount];

        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * step;
            pointsArray[i] = new FunctionPoint(x, function.getFunctionValue(x));
        }

        return createTabulatedFunction(cls, pointsArray);
    }

    // Вывод в байтовый поток
    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {
        try (DataOutputStream dataOut = new DataOutputStream(out)) {
            int pointsCount = function.getPointsCount();
            dataOut.writeInt(pointsCount);

            for (int i = 0; i < pointsCount; i++) {
                dataOut.writeDouble(function.getPointX(i));
                dataOut.writeDouble(function.getPointY(i));
            }
        }
    }

    // Ввод из байтового потока
    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException {
        try (DataInputStream dataIn = new DataInputStream(in)) {
            int pointsCount = dataIn.readInt();

            FunctionPoint[] pointsArray = new FunctionPoint[pointsCount];

            for (int i = 0; i < pointsCount; i++) {
                pointsArray[i] = new FunctionPoint();
                pointsArray[i].setX(dataIn.readDouble());
                pointsArray[i].setY(dataIn.readDouble());
            }

            return factory.createTabulatedFunction(pointsArray);
        }
    }

    public static TabulatedFunction inputTabulatedFunction(Class<? extends TabulatedFunction> cls, InputStream in) throws IOException {
        try (DataInputStream dataIn = new DataInputStream(in)) {
            int pointsCount = dataIn.readInt();

            FunctionPoint[] pointsArray = new FunctionPoint[pointsCount];

            for (int i = 0; i < pointsCount; i++) {
                pointsArray[i] = new FunctionPoint();
                pointsArray[i].setX(dataIn.readDouble());
                pointsArray[i].setY(dataIn.readDouble());
            }

            return createTabulatedFunction(cls, pointsArray);
        }
    }

    // Вывод в символьный поток
    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(out)) {
            writer.write(Integer.toString(function.getPointsCount()));
            writer.newLine();

            for (int i = 0; i < function.getPointsCount(); i++) {
                writer.write(Double.toString(function.getPointX(i)));
                writer.write(" ");
                writer.write(Double.toString(function.getPointY(i)));
                writer.write("\n");
            }
            writer.flush();
        }
    }

    // Ввод из символьного потока
    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.eolIsSignificant(false);
        tokenizer.parseNumbers();

        if (tokenizer.nextToken() == StreamTokenizer.TT_NUMBER) {
            int pointsCount = (int) tokenizer.nval;

            FunctionPoint[] pointsArray = new FunctionPoint[pointsCount];

            for (int i = 0; i < pointsCount; i++) {
                pointsArray[i] = new FunctionPoint();
                if (tokenizer.nextToken() == StreamTokenizer.TT_NUMBER) {
                    pointsArray[i].setX(tokenizer.nval);
                }
                if (tokenizer.nextToken() == StreamTokenizer.TT_NUMBER) {
                    pointsArray[i].setY(tokenizer.nval);
                }
            }

            return factory.createTabulatedFunction(pointsArray);
        } else
            throw new IOException();
    }

    public static TabulatedFunction readTabulatedFunction(Class<? extends TabulatedFunction> cls, Reader in) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.eolIsSignificant(false);
        tokenizer.parseNumbers();

        if (tokenizer.nextToken() == StreamTokenizer.TT_NUMBER) {
            int pointsCount = (int) tokenizer.nval;

            FunctionPoint[] pointsArray = new FunctionPoint[pointsCount];

            for (int i = 0; i < pointsCount; i++) {
                pointsArray[i] = new FunctionPoint();
                if (tokenizer.nextToken() == StreamTokenizer.TT_NUMBER) {
                    pointsArray[i].setX(tokenizer.nval);
                }
                if (tokenizer.nextToken() == StreamTokenizer.TT_NUMBER) {
                    pointsArray[i].setY(tokenizer.nval);
                }
            }

            return createTabulatedFunction(cls, pointsArray);
        } else
            throw new IOException();
    }
}
