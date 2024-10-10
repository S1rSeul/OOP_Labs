package ru.ssau.tk.artamq.labs.functions;

import ru.ssau.tk.artamq.labs.functions.exceptions.FunctionPointIndexOutOfBoundsException;
import ru.ssau.tk.artamq.labs.functions.exceptions.InappropriateFunctionPointException;
import ru.ssau.tk.artamq.labs.functions.interfaces.TabulatedFunction;

import java.io.*;

// Класс, объект которого описывает табулированную функцию, представляет собой двусвязный циклический список точек
public class LinkedListTabulatedFunction implements TabulatedFunction, Externalizable {
    @Serial
    private static final long serialVersionUID = 1L;

    private FunctionNode head; // Голова списка
    private FunctionNode tail; // Хвост списка
    private int pointsCount; // Количество элементов списка

    public LinkedListTabulatedFunction() {
        head = new FunctionNode();
        tail = head;
    }

    // Конструктор объекта функции по границам и количеству точек
    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (leftX >= rightX)
            throw new IllegalArgumentException("Левая граница больше или равна правой (" + leftX + " >= " + rightX + ")");

        if (pointsCount < 2)
            throw new IllegalArgumentException("Количество точек меньше двух(" + pointsCount + ")");

        head = new FunctionNode();
        tail = head;

        double step = (rightX - leftX) / (pointsCount - 1);
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * step;
            addNodeToTail().data = new FunctionPoint(x, 0);
        }
    }

    // Конструктор объекта функции по границам и массиву значений функции
    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) {
        if (leftX >= rightX)
            throw new IllegalArgumentException("Левая граница больше или равна правой (" + leftX + " >= " + rightX + ")");

        if (values.length < 2)
            throw new IllegalArgumentException("Количество точек меньше двух(" + values.length + ")");

        head = new FunctionNode();
        tail = head;

        double step = (rightX - leftX) / (values.length - 1);
        for (int i = 0; i < values.length; i++) {
            double x = leftX + i * step;
            addNodeToTail().data = new FunctionPoint(x, values[i]);
        }
    }

    // Конструктор объекта функции по массиву точек функции
    public LinkedListTabulatedFunction(FunctionPoint[] pointsArray) {
        if (pointsArray.length < 2)
            throw new IllegalArgumentException("Количество точек меньше двух(" + pointsArray.length + ")");

        head = new FunctionNode();
        tail = head;

        addNodeToTail().data = new FunctionPoint(pointsArray[0].getX(), pointsArray[0].getY());

        for (int i = 1; i < pointsArray.length; i++) {
            if (pointsArray[i].getX() > pointsArray[i - 1].getX())
                addNodeToTail().data = new FunctionPoint(pointsArray[i].getX(), pointsArray[i].getY());
            else
                throw new IllegalArgumentException("Точки в переданном массиве не упорядочены по значению x");
        }
    }

    // Получение элемента списка по индексу
    private FunctionNode getNodeByIndex(int index) {
        FunctionNode current;
        int currentIndex;

        if (index >= pointsCount / 2) {
            current = tail;
            currentIndex = pointsCount - 1;
            while (currentIndex != index) {
                current = current.prev;
                currentIndex--;
            }
        } else {
            current = head.next;
            currentIndex = 0;
            while (currentIndex != index) {
                current = current.next;
                currentIndex++;
            }
        }

        return current;
    }

    // Добавление пустого элемента в хвост списка
    private FunctionNode addNodeToTail() {
        tail.next = new FunctionNode(head, tail);
        tail = tail.next;

        pointsCount++;
        return tail;
    }

    // Добавление пустого элемента по индексу
    private FunctionNode addNodeByIndex(int index) {
        if (index == 0) {
            FunctionNode newNode = new FunctionNode(head.next, head);
            head.next = newNode;
            return newNode;
        } else if (index == pointsCount) {
            return addNodeToTail();
        } else {
            FunctionNode newNodeNext = getNodeByIndex(index);
            FunctionNode newNodePrev = newNodeNext.prev;

            FunctionNode newNode = new FunctionNode(newNodeNext, newNodePrev);
            newNodePrev.next = newNode;
            newNodeNext.prev = newNode;

            return newNode;
        }
    }

    // Удаление элемента списка по индексу
    private FunctionNode deleteNodeByIndex(int index) {
        FunctionNode delete = new FunctionNode();
        if (index == 0) {
            delete = head.next;
            head.next = delete.next;
            delete.next.prev = head;
        } else if (index == pointsCount - 1) {
            delete = tail;
            tail = tail.prev;
            tail.next = head;
        } else {
            delete = getNodeByIndex(index);
            delete.prev.next = delete.next;
            delete.next.prev = delete.prev;
        }

        return delete;
    }

    // Геттер левой границы функции
    public double getLeftDomainBorder() {
        return head.next.data.getX();
    }

    // Геттер правой границы функции
    public double getRightDomainBorder() {
        return tail.data.getX();
    }

    // Получаем значение функции в заданной точке
    public double getFunctionValue(double x) {
        if (x >= getLeftDomainBorder() && x <= getRightDomainBorder()) {
            FunctionNode current = head.next;
            while (current != head) {
                if (x >= current.data.getX() && x <= current.next.data.getX()) {
                    FunctionPoint p1 = current.data;
                    FunctionPoint p2 = current.next.data;

                    return p1.getY() + (p2.getY() - p1.getY()) * ((x - p1.getX()) / (p2.getX() - p1.getX()));
                }
                current = current.next;
            }
        }
        return Double.NaN;
    }

    // Геттер количества точек
    public int getPointsCount() {
        return pointsCount;
    }

    // Геттер точки функции по индексу
    public FunctionPoint getPoint(int index) {
        if (index < 0 || index > pointsCount - 1)
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " выходит за границы набора точек");

        FunctionNode temp = getNodeByIndex(index);
        return new FunctionPoint(temp.data.getX(), temp.data.getY());
    }

    // Внутренний метод проверки точки по индексу и значению по x на принадлежность интервалу
    private boolean checkNewPoint(int index, double pointX) {
        if (index > 0 && index < pointsCount - 1) {
            FunctionNode temp = getNodeByIndex(index);
            return !(pointX <= temp.prev.data.getX()) && !(pointX >= temp.next.data.getX());
        } else if (index == 0) {
            return !(pointX >= head.next.next.data.getX());
        } else if (index == pointsCount - 1) {
            return !(pointX <= tail.prev.data.getX());
        }

        return true;
    }

    // Сеттер нового значения точки в переданном индексе
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (index < 0 || index > pointsCount - 1)
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " выходит за границы набора точек");

        if (checkNewPoint(index, point.getX()))
            getNodeByIndex(index).data = new FunctionPoint(point.getX(), point.getY());
        else
            throw new InappropriateFunctionPointException("Координата x(" + point.getX() + ") задаваемой точки лежит вне интервала");
    }

    // Геттер значения точки по x, по индексу
    public double getPointX(int index) {
        if (index < 0 || index > pointsCount - 1)
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " выходит за границы набора точек");

        return getNodeByIndex(index).data.getX();
    }

    // Геттер значения точки по y, по индексу
    public double getPointY(int index) {
        if (index < 0 || index > pointsCount - 1)
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " выходит за границы набора точек");

        return getNodeByIndex(index).data.getY();
    }

    // Сеттер нового значения точки по x, по индексу
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if (index < 0 || index > pointsCount - 1)
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " выходит за границы набора точек");

        if (checkNewPoint(index, x)) {
            FunctionNode temp = getNodeByIndex(index);
            temp.data = new FunctionPoint(x, temp.data.getY());
        } else
            throw new InappropriateFunctionPointException("Координата x(" + x + ") задаваемой точки лежит вне интервала");
    }

    // Сеттер нового значения точки по y, по индексу
    public void setPointY(int index, double y) {
        if (index < 0 || index > pointsCount - 1)
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " выходит за границы набора точек");

        FunctionNode temp = getNodeByIndex(index);
        temp.data = new FunctionPoint(temp.data.getX(), y);
    }

    // Удаление точки по индексу
    public void deletePoint(int index) {
        if (index < 0 || index > pointsCount - 1)
            throw new FunctionPointIndexOutOfBoundsException("Индекс " + index + " выходит за границы набора точек");

        if (pointsCount < 3)
            throw new IllegalStateException("Количество точек меньше трех(" + pointsCount + ")");

        deleteNodeByIndex(index);

        pointsCount--;
    }

    // Добавление новой точки
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        int insertIndex = pointsCount;
        FunctionNode current = head.next;
        int currentIndex = 0;

        while (current != head) {
            if (current.data.getX() > point.getX()) {
                insertIndex = currentIndex;
                break;
            } else if (point.getX() == current.data.getX())
                throw new InappropriateFunctionPointException("Точка с такой абсциссой(" + point.getX() + ") уже существует");
            currentIndex++;
            current = current.next;
        }

        addNodeByIndex(insertIndex).data = point;

        pointsCount++;
    }

    // Перевод объекта в формат строки
    @Override
    public String toString() {
        String out = "{";
        FunctionNode current = head.next;
        while (current != head) {
            out += current.data.toString();
            if (current != tail)
                out += ", ";
            current = current.next;
        }
        out += "}";
        return out;
    }

    // Сравнение данного и переданного объектов
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TabulatedFunction))
            return false;
        if (o instanceof LinkedListTabulatedFunction) {
            LinkedListTabulatedFunction other = (LinkedListTabulatedFunction) o;
            if (this.pointsCount != other.pointsCount)
                return false;
            FunctionNode currentThis = head.next;
            FunctionNode currentOther = other.head.next;
            while (currentThis != head) {
                if (!currentThis.data.equals(currentOther.data))
                    return false;
                currentThis = currentThis.next;
                currentOther = currentOther.next;
            }
            return true;
        }

        TabulatedFunction other = (TabulatedFunction) o;
        if (pointsCount != other.getPointsCount())
            return false;
        FunctionNode current = head.next;
        int index = 0;
        while (current != head) {
            if (!current.data.equals(other.getPoint(index)))
                return false;
            current = current.next;
            index++;
        }
        return true;
    }

    // Получение хэш-кода
    @Override
    public int hashCode() {
        int bits = pointsCount;
        FunctionNode current = head.next;
        while (current != head) {
            bits ^= current.data.hashCode();
            current = current.next;
        }
        return bits;
    }

    // Клонирование объекта
    @Override
    public LinkedListTabulatedFunction clone() {
        try {
            LinkedListTabulatedFunction cloned = (LinkedListTabulatedFunction) super.clone();
            cloned.head = new FunctionNode(cloned.head, cloned.head);
            cloned.tail = cloned.head;
            cloned.pointsCount = 0;

            FunctionNode current = head.next;
            while (current != head) {
                cloned.addNodeToTail().data = new FunctionPoint(current.data.getX(), current.data.getY());
                current = current.next;
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(pointsCount);
        out.writeObject(head);
        out.writeObject(tail);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        pointsCount = in.readInt();
        head = (FunctionNode) in.readObject();
        tail = (FunctionNode) in.readObject();
    }

    // Вложенный класс, объект которого описывает элемент списка
    public static class FunctionNode {
        private FunctionPoint data; // Точка функции
        private FunctionNode next; // Ссылка на следующий в списке элемент
        private FunctionNode prev; // Ссылка на предыдущий в списке элемент

        // Конструктор ноды без параметров
        public FunctionNode() {
            this.data = null;
            this.next = this;
            this.prev = this;
        }

        // Конструктор с переданными ссылками на следующий и предыдущий элемент
        public FunctionNode(FunctionNode next, FunctionNode prev) {
            this.data = null;
            this.next = next;
            this.prev = prev;
        }
    }
}
