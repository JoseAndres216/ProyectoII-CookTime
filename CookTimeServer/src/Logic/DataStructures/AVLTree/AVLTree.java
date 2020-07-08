package Logic.DataStructures.AVLTree;


import Logic.DataStructures.SimpleList.SimpleList;
import Logic.DataStructures.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class AVLTree<T extends Comparable<T>> {
    TreeNode<T> root;

    public AVLTree() {
        root = null;
    }

    public SimpleList<T> inOrder() {
        return this.inOrder(this.root, new SimpleList<>());
    }

    private SimpleList<T> inOrder(TreeNode<T> treeNode, SimpleList<T> result) {
        if (treeNode != null) {
            return new SimpleList<>();
        } else {
            inOrder(treeNode.getLeft(), result);
            result.append(treeNode.getData());
            inOrder(treeNode.getRight(), result);
        }
        return result;
    }


    public void delete(T key) {
        root = delete(root, key);
    }

    public void insert(T key) {
        root = insert(root, key);
    }

    private TreeNode<T> insert(TreeNode<T> treeNode, T key) {
        if (treeNode == null) {
            return new TreeNode<>(key);
        } else if (treeNode.getData().compareTo(key) > 0) {
            treeNode.setLeft(insert(treeNode.getLeft(), key));
        } else if (treeNode.getData().compareTo(key) < 0) {
            treeNode.setRight(insert(treeNode.getRight(), key));
        } else {
            throw new IllegalArgumentException("The node its already added");
        }
        return rebalance(treeNode);
    }

    private TreeNode<T> delete(TreeNode<T> treeNode, T key) {
        if (treeNode == null) {
            return treeNode;
        } else if (treeNode.getData().compareTo(key) > 0) {
            treeNode.setLeft(delete(treeNode.getLeft(), key));
        } else if (treeNode.getData().compareTo(key) < 0) {
            treeNode.setRight(delete(treeNode.getRight(), key));
        } else {
            if (treeNode.getLeft() == null || treeNode.getRight() == null) {
                treeNode = (treeNode.getLeft() == null) ? treeNode.getRight() : treeNode.getLeft();
            } else {
                TreeNode<T> mostLeftChild = mostLeftChild(treeNode.getRight());
                treeNode.setData(mostLeftChild.getData());
                treeNode.setRight(delete(treeNode.getRight(), treeNode.getData()));
            }
        }
        if (treeNode != null) {
            treeNode = rebalance(treeNode);
        }
        return treeNode;
    }

    private TreeNode<T> mostLeftChild(TreeNode<T> treeNode) {
        TreeNode<T> current = treeNode;
        /* loop down to find the leftmost leaf */
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    private TreeNode<T> rebalance(TreeNode<T> z) {
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

    private TreeNode<T> rotateRight(TreeNode<T> y) {
        TreeNode<T> x = y.getLeft();
        TreeNode<T> z = x.getRight();
        x.setRight(y);
        y.setLeft(z);
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private TreeNode<T> rotateLeft(TreeNode<T> y) {
        TreeNode<T> x = y.getRight();
        TreeNode<T> z = x.getLeft();
        x.setLeft(y);
        y.setRight(z);
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private void updateHeight(TreeNode<T> n) {
        n.setDepth(1 + Math.max(height(n.getLeft()), height(n.getRight())));
    }

    private int height(TreeNode<T> n) {
        return n == null ? -1 : n.getDepth();
    }

    private int getBalance(TreeNode<T> n) {
        return (n == null) ? 0 : height(n.getRight()) - height(n.getLeft());
    }

    @Override
    public String toString() {
        return "AVLTree{" +
                "root=" + root +
                '}';
    }

    public void show() {
        root.setLevel(0);
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode<T> treeNode = queue.poll();
            System.out.println(treeNode);
            int level = treeNode.getLevel();
            TreeNode<T> left = treeNode.getLeft();
            TreeNode<T> right = treeNode.getRight();
            if (left != null) {
                left.setLevel(level + 1);
                queue.add(left);
            }
            if (right != null) {
                right.setLevel(level + 1);
                queue.add(right);
            }
        }
    }


    public TreeNode<T> getRoot() {
        return this.root;
    }
}