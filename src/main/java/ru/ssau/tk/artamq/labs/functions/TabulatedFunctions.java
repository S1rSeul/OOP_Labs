package ru.ssau.tk.artamq.labs.functions;

import ru.ssau.tk.artamq.labs.functions.interfaces.Function;
import ru.ssau.tk.artamq.labs.functions.interfaces.TabulatedFunction;

import java.io.*;

// Класс, содержащий вспомогательные методы для работы с табулированными функциями
public class TabulatedFunctions {
    private TabulatedFunctions() {
        throw new UnsupportedOperationException("Создание экземпляра запрещено");
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

        TabulatedFunction tabulatedFunction = new ArrayTabulatedFunction(pointsArray);

        return tabulatedFunction;
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

            TabulatedFunction tabulatedFunction = new ArrayTabulatedFunction(pointsArray);

            return tabulatedFunction;
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

            TabulatedFunction tabulatedFunction = new ArrayTabulatedFunction(pointsArray);

            return tabulatedFunction;
        } else
            throw new IOException();
    }
}
