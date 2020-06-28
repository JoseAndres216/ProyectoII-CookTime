package Logic.DataStructures.SimpleList;

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

    public void emptyList() {
        this.head = this.tail = null;
    }

    public int len() {
        return this.len;
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public void insertLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            this.head = this.tail = newNode;
        } else {
            this.tail.setNext(newNode);
            this.tail = newNode;
        }
        this.len++;
    }


    public T getElement(int i) {
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

    public boolean booleanSearch(T data) {
        boolean is = false;
        Node<T> temp = this.head;
        while (temp != null) {
            if (temp.getData() == data) {
                is = true;
                break;
            }
            temp = temp.getNext();
        }
        return is;
    }

}
