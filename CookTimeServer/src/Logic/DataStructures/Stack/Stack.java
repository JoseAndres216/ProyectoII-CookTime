package Logic.DataStructures.Stack;

import Logic.DataStructures.SimpleList.SimpleList;

public class Stack<T> {
    private SimpleList<T> elements = new SimpleList<>();

    public void push(T data) {
        elements.append(data);
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
