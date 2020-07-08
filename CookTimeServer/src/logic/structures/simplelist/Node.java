package logic.structures.simplelist;

public class Node<T> {
    private Node<T> next;
    private T data;
    private Node<T> prev;

    public Node(T data) {
        this.data = data;
        next = prev = null;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }
}
