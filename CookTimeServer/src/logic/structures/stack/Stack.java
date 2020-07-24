package logic.structures.stack;

import logic.structures.simplelist.SimpleList;

import java.io.Serializable;

public class Stack<T extends Serializable> implements Serializable {
    private SimpleList<T> elements;

    public void push(T data) {
        elements.append(data);
    }

    public Stack( ) {
        this.elements = new SimpleList<>();
    }

    public T peek() {
        return elements.getTail().getData();
    }

    public void pop() {
        elements.deleteLast();
    }

    @Override
    public String toString() {
        return "Stack{" +
                "elements=" + elements +
                '}';
    }
}
