package logic.structures.splay;

import logic.structures.TreeNode;
import logic.structures.simplelist.SimpleList;

import java.io.Serializable;

public class SplayTree<T extends Comparable<T> & Serializable> {
    private TreeNode<T> root;

    public SplayTree() {
        root = null;
    }

    // rotate left at node x
    private void leftRotate(TreeNode<T> x) {
        TreeNode<T> temp = x.getRight();
        x.setRight(temp.getLeft());
        if (temp.getLeft() != null) {
            temp.getLeft().setParent(x);
        }
        temp.setParent(x.getParent());
        if (x.getParent() == null) {
            this.root = temp;
        } else if (x == x.getParent().getLeft()) {
            x.getParent().setLeft(temp);
        } else {
            x.getParent().setRight(temp);
        }
        temp.setLeft(x);
        x.setParent(temp);
    }

    // rotate right at node x
    private void rightRotate(TreeNode<T> x) {
        TreeNode<T> y = x.getLeft();
        x.setLeft(y.getRight());
        if (y.getRight() != null) {
            y.getRight().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == null) {
            this.root = y;
        } else if (x == x.getParent().getRight()) {
            x.getParent().setRight(y);
        } else {
            x.getParent().setLeft(y);
        }
        y.setRight(x);
        x.setParent(y);
    }

    // Splaying operation. It moves x to the root of the tree
    private void splay(TreeNode<T> x) {
        while (x.getParent() != null) {
            if (x.getParent().getParent() == null) {
                if (x == x.getParent().getLeft()) {
                    // zig rotation
                    rightRotate(x.getParent());
                } else {
                    // zag rotation
                    leftRotate(x.getParent());
                }
            } else if (x == x.getParent().getLeft() && x.getParent() == x.getParent().getParent().getLeft()) {
                // zig-zig rotation
                rightRotate(x.getParent().getParent());
                rightRotate(x.getParent());
            } else if (x == x.getParent().getRight() && x.getParent() == x.getParent().getParent().getRight()) {
                // zag-zag rotation
                leftRotate(x.getParent().getParent());
                leftRotate(x.getParent());
            } else if (x == x.getParent().getRight() && x.getParent() == x.getParent().getParent().getLeft()) {
                // zig-zag rotation
                leftRotate(x.getParent());
                rightRotate(x.getParent());
            } else {
                // zag-zig rotation
                rightRotate(x.getParent());
                leftRotate(x.getParent());
            }
        }
    }

    public SimpleList<T> inOrder() {
        return this.inOrder(this.root, new SimpleList<>());
    }

    private SimpleList<T> inOrder(TreeNode<T> node, SimpleList<T> result) {
        if (node == null) {
            return new SimpleList<>();

        } else {
            inOrder(node.getLeft(), result);
            result.append(node.getData());
            inOrder(node.getRight(), result);
        }
        return result;
    }

    // insert the key to the tree in its appropriate position
    public void insert(T key) {
        TreeNode<T> node = new TreeNode<>(key);
        TreeNode<T> temp = null;
        TreeNode<T> x = this.root;

        while (x != null) {
            temp = x;
            if (node.getData().compareTo(x.getData()) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        // temp is parent of x
        node.setParent(temp);
        if (temp == null) {
            root = node;
        } else if (node.getData().compareTo(temp.getData()) < 0) {
            temp.setLeft(node);
        } else {
            temp.setRight(node);
        }

        // splay node
        splay(node);
    }


    public TreeNode<T> getRoot() {

        return this.root;
    }
}