package Logic.DataStructures.BinarySearchTree;

public class Node<T extends Comparable<T>> {

    private T data;
    private Node<T> left;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    private Node<T> right;

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

    public Node(T data) {
        this.data = data;
        left = null;
        right = null;
    }
}
