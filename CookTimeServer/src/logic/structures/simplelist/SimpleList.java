package logic.structures.simplelist;

import java.io.Serializable;

public class SimpleList<T extends Serializable> implements Serializable {

    private Node<T> head = null;
    private Node<T> tail = null;
    private int len = 0;

    public SimpleList() {
        this.head = null;
        this.tail = null;
        this.len = 0;
    }

    public void swap(Node<T> i, Node<T> j) {
        T temp = i.getData();
        i.setData(j.getData());
        j.setData(temp);
    }

    public Node<T> getHead() {
        return this.head;
    }

    public Node<T> getTail() {
        return this.tail;
    }

    public int len() {
        return this.len;
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public void append(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            this.head = this.tail = newNode;
            this.head.setNext(this.tail);
        } else {
            this.tail.setNext(newNode);
            this.tail = newNode;
        }
        this.len++;

    }

    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);

        if (this.head != null) {
            newNode.setNext(this.head);
        }
        this.head = this.tail = newNode;
        this.len++;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SimpleList{");
        sb.append("head=").append(head);
        sb.append(", len=").append(len);
        sb.append('}');
        return sb.toString();
    }

    public T indexElement(int i) {
        if (i > (len - 1)) {
            throw new IllegalArgumentException("Index out of range, max: " + (this.len - 1) + "given :" + i);

        } else if (i == (len - 1)) {
            return this.tail.getData();
        }
        Node<T> temp = this.head;
        int counter = 0;
        while (counter < i) {
            temp = temp.getNext();
            counter++;
        }
        return temp.getData();
    }

    public void deleteLast() {
        if (this.len == 0) {
            return;
        }
        Node<T> temp = this.head;
        while (temp.getNext() != this.tail) {
            temp = temp.getNext();
        }
        temp.setNext(null);
        this.tail = temp;
        this.len--;
    }
}
