package Logic.DataStructures;

import java.util.Objects;

public class TreeNode<T extends Comparable<T>> implements Comparable<TreeNode<T>> {

    private T data;
    private transient TreeNode<T> left;
    private transient TreeNode<T> right;
    private int level;
    private int depth;
    private transient TreeNode<T> parent;

    public TreeNode(T data) {
        this(data, null, null);
    }

    public TreeNode(T data, TreeNode<T> left, TreeNode<T> right) {
        this.data = data;
        this.left = left;
        this.right = right;
        if (left == null && right == null)
            setDepth(1);
        else if (left == null)
            setDepth(right.getDepth() + 1);
        else if (right == null)
            setDepth(left.getDepth() + 1);
        else
            setDepth(Math.max(left.getDepth(), right.getDepth()) + 1);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public TreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(TreeNode<T> left) {
        this.left = left;
    }

    public TreeNode<T> getRight() {
        return right;
    }

    public void setRight(TreeNode<T> right) {
        this.right = right;
    }

    /**
     * @return the depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * @param depth the depth to set
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public int compareTo(TreeNode<T> o) {
        return this.data.compareTo(o.data);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, left, right, getLevel(), depth);
    }

    @Override
    public String toString() {
        return "Level " + getLevel() + ": " + data;
    }

    public TreeNode<T> getParent() {
        return parent;
    }

    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}