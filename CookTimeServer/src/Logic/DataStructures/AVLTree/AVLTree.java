package Logic.DataStructures.AVLTree;


import Logic.DataStructures.SimpleList.SimpleList;

import java.util.LinkedList;
import java.util.Queue;

public class AVLTree<T extends Comparable<T>> {
    Node<T> root;

    public AVLTree() {
        root = null;
    }

    public SimpleList<T> inOrder() {
        return this.inOrder(this.root, new SimpleList<>());
    }

    private SimpleList<T> inOrder(Node<T> node, SimpleList<T> result) {
        if (node == null) {
            System.out.println("Leaf");

        } else {
            inOrder(node.getLeft(), result);
            result.append(node.getData());
            inOrder(node.getRight(), result);
        }
        return result;
    }


    public void delete(T key) {
        root = delete(root, key);
    }

    public void insert(T key) {
        root = insert(root, key);
    }

    private Node<T> insert(Node<T> node, T key) {
        if (node == null) {
            return new Node<>(key);
        } else if (node.getData().compareTo(key) > 0) {
            node.setLeft(insert(node.getLeft(), key));
        } else if (node.getData().compareTo(key) < 0) {
            node.setRight(insert(node.getRight(), key));
        } else {
            throw new IllegalArgumentException("The node its already added");
        }
        return rebalance(node);
    }

    private Node<T> delete(Node<T> node, T key) {
        if (node == null) {
            return node;
        } else if (node.getData().compareTo(key) > 0) {
            node.setLeft(delete(node.getLeft(), key));
        } else if (node.getData().compareTo(key) < 0) {
            node.setRight(delete(node.getRight(), key));
        } else {
            if (node.getLeft() == null || node.getRight() == null) {
                node = (node.getLeft() == null) ? node.getRight() : node.getLeft();
            } else {
                Node<T> mostLeftChild = mostLeftChild(node.getRight());
                node.setData(mostLeftChild.getData());
                node.setRight(delete(node.getRight(), node.getData()));
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }

    private Node<T> mostLeftChild(Node<T> node) {
        Node<T> current = node;
        /* loop down to find the leftmost leaf */
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    private Node<T> rebalance(Node<T> z) {
        updateHeight(z);
        int balance = getBalance(z);
        if (balance > 1) {
            if (height(z.getRight().getRight()) > height(z.getRight().getLeft())) {
                z = rotateLeft(z);
            } else {
                z.setRight(rotateRight(z.getRight()));
                z = rotateLeft(z);
            }
        } else if (balance < -1) {
            if (height(z.getLeft().getLeft()) > height(z.getLeft().getRight())) {
                z = rotateRight(z);
            } else {
                z.setLeft(rotateLeft(z.getLeft()));
                z = rotateRight(z);
            }
        }
        return z;
    }

    private Node<T> rotateRight(Node<T> y) {
        Node<T> x = y.getLeft();
        Node<T> z = x.getRight();
        x.setRight(y);
        y.setLeft(z);
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private Node<T> rotateLeft(Node<T> y) {
        Node<T> x = y.getRight();
        Node<T> z = x.getLeft();
        x.setLeft(y);
        y.setRight(z);
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private void updateHeight(Node<T> n) {
        n.setDepth(1 + Math.max(height(n.getLeft()), height(n.getRight())));
    }

    private int height(Node<T> n) {
        return n == null ? -1 : n.getDepth();
    }

    private int getBalance(Node<T> n) {
        return (n == null) ? 0 : height(n.getRight()) - height(n.getLeft());
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