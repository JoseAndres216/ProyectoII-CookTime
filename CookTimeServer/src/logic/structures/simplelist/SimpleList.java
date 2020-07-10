package logic.structures.simplelist;

public class SimpleList<T> {

    private Node<T> head = null;
    private Node<T> tail = null;
    private int len = 0;

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

    public SimpleList<T> append(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            this.head = this.tail = newNode;
        } else {
            this.tail.setNext(newNode);
            this.tail = newNode;
        }
        this.len++;
        return this;
    }

    public SimpleList<T> addFirst(T data) {
        Node<T> newNode = new Node<>(data);

        if (this.head != null) {
            newNode.setNext(this.head);
        }
        this.head = newNode;
        this.len++;
        return this;
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

    @Override
    public String toString() {
        if (this.head == null) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder("[ ");
        Node<T> temp = this.head;
        while (temp != null) {
            stringBuilder.append(temp.getData().toString());
            stringBuilder.append(", ");
            temp = temp.getNext();
        }
        stringBuilder.append(" ]");
        return stringBuilder.toString();
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
