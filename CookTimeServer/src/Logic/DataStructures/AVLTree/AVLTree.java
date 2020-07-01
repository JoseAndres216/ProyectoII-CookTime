package Logic.DataStructures.AVLTree;


import java.util.LinkedList;
import java.util.Queue;

public class AVLTree<T extends Comparable<T>> {
    Node<T> root;

    public AVLTree() {
        root = null;
    }

    public T max() {
        Node<T> local = root;
        if (local == null)
            return null;
        while (local.getRight() != null)
            local = local.getRight();
        return local.getData();
    }

    public T min() {
        Node<T> local = root;
        if (local == null)
            return null;
        while (local.getLeft() != null) {
            local = local.getLeft();
        }
        return local.getData();
    }

    private int depth(Node<T> node) {
        if (node == null)
            return 0;
        return node.getDepth();
    }

    public Node<T> insert(T data) {
        root = insert(root, data);
        switch (balanceNumber(root)) {
            case 1:
                root = rotateLeft(root);
                break;
            case -1:
                root = rotateRight(root);
                break;
            default:
                break;
        }
        return root;
    }

    public Node<T> insert(Node<T> node, T data) {
        if (node == null)
            return new Node<>(data);
        if (node.getData().compareTo(data) > 0) {
            node = new Node<>(node.getData(), insert(node.getLeft(), data),
                    node.getRight());
        } else if (node.getData().compareTo(data) < 0) {
            node = new Node<>(node.getData(), node.getLeft(), insert(
                    node.getRight(), data));
        }
        // After insert the new node, check and rebalance the current node if
        // necessary.
        switch (balanceNumber(node)) {
            case 1:
                node = rotateLeft(node);
                break;
            case -1:
                node = rotateRight(node);
                break;
            default:
                return node;
        }
        return node;
    }

    private int balanceNumber(Node<T> node) {
        int left = depth(node.getLeft());
        int right = depth(node.getRight());
        if (left - right >= 2)
            return -1;
        else if (left - right <= -2)
            return 1;
        return 0;
    }

    private Node<T> rotateLeft(Node<T> node) {
        Node<T> q = node;
        Node<T> p = q.getRight();
        Node<T> c = q.getLeft();
        Node<T> a = p.getLeft();
        Node<T> b = p.getRight();
        q = new Node<>(q.getData(), c, a);
        p = new Node<>(p.getData(), q, b);
        return p;
    }

    private Node<T> rotateRight(Node<T> node) {
        Node<T> q = node;
        Node<T> p = q.getLeft();
        Node<T> c = q.getRight();
        Node<T> a = p.getLeft();
        Node<T> b = p.getRight();
        q = new Node<>(q.getData(), b, c);
        p = new Node<>(p.getData(), a, q);
        return p;
    }

    public boolean search(T data) {
        Node<T> local = root;
        while (local != null) {
            if (local.getData().compareTo(data) == 0)
                return true;
            else if (local.getData().compareTo(data) > 0)
                local = local.getLeft();
            else
                local = local.getRight();
        }
        return false;
    }

    @Override
    public String toString() {
        return "AVLTree{" +
                "root=" + root +
                '}';
    }

    public void show() {
        root.level = 0;
        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<T> node = queue.poll();
            System.out.println(node);
            int level = node.level;
            Node<T> left = node.getLeft();
            Node<T> right = node.getRight();
            if (left != null) {
                left.level = level + 1;
                queue.add(left);
            }
            if (right != null) {
                right.level = level + 1;
                queue.add(right);
            }
        }
    }
}