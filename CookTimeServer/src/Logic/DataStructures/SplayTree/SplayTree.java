package Logic.DataStructures.SplayTree;

import Logic.DataStructures.SimpleList.SimpleList;
import Logic.DataStructures.TreeNode;
import com.google.gson.annotations.Expose;

public class SplayTree<T extends Comparable<T>> {
    @Expose
    private TreeNode<T> root;

    public SplayTree() {
        root = null;
    }

    private void printHelper(TreeNode<T> currPtr, String indent, boolean last) {
        // print the tree structure on the screen
        if (currPtr != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }

            System.out.println(currPtr.getData());

            printHelper(currPtr.getLeft(), indent, false);
            printHelper(currPtr.getRight(), indent, true);
        }
    }

    private TreeNode<T> searchTreeHelper(TreeNode<T> node, T key) {
        if (node == null || 0 == node.getData().compareTo(key)) {
            return node;
        }

        if (key.compareTo(node.getData()) < 0) {
            return searchTreeHelper(node.getLeft(), key);
        }
        return searchTreeHelper(node.getRight(), key);
    }

    private void deleteNodeHelper(TreeNode<T> node, T key) {
        TreeNode<T> x = null;
        TreeNode<T> t;
        TreeNode<T> s;
        while (node != null) {
            if (node.getData().compareTo(key) == 0) {
                x = node;
            }

            if (node.getData().compareTo(key) <= 0) {
                node = node.getRight();
            } else {
                node = node.getLeft();
            }
        }

        if (x == null) {
            System.out.println("Couldn't find key in the tree");
            return;
        }
        // split operation
        splay(x);
        if (x.getRight() != null) {
            t = x.getRight();
            t.setParent(null);
        } else {
            t = null;
        }
        s = x;
        s.setRight(null);

        // join operation
        if (s.getLeft() != null) { // remove x
            s.getLeft().setParent(null);
        }
        root = join(s.getLeft(), t);
    }

    // rotate left at node x
    private void leftRotate(TreeNode<T> x) {
        TreeNode<T> y = x.getRight();
        x.setRight(y.getLeft());
        if (y.getLeft() != null) {
            y.getLeft().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == null) {
            this.root = y;
        } else if (x == x.getParent().getLeft()) {
            x.getParent().setLeft(y);
        } else {
            x.getParent().setRight(y);
        }
        y.setLeft(x);
        x.setParent(y);
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

    // joins two trees s and t
    private TreeNode<T> join(TreeNode<T> s, TreeNode<T> t) {
        if (s == null) {
            return t;
        }

        if (t == null) {
            return s;
        }
        TreeNode<T> x = maximum(s);
        splay(x);
        x.setRight(t);
        t.setParent(x);
        return x;
    }

    private void preOrderHelper(TreeNode<T> node) {
        if (node != null) {
            System.out.print(node.getData() + " ");
            preOrderHelper(node.getLeft());
            preOrderHelper(node.getRight());
        }
    }

    private void inOrderHelper(TreeNode<T> node) {
        if (node != null) {
            inOrderHelper(node.getLeft());
            System.out.print(node.getData() + " ");
            inOrderHelper(node.getRight());
        }
    }

    private void postOrderHelper(TreeNode<T> node) {
        if (node != null) {
            postOrderHelper(node.getLeft());
            postOrderHelper(node.getRight());
            System.out.print(node.getData() + " ");
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

    // search the tree for the key k
    // and return the corresponding node
    public TreeNode<T> searchTree(T k) {
        TreeNode<T> x = searchTreeHelper(root, k);
        if (x != null) {
            splay(x);
        }
        return x;
    }

    // find the node with the minimum key
    public TreeNode<T> minimum(TreeNode<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    // find the node with the maximum key
    public TreeNode<T> maximum(TreeNode<T> node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }

    // find the successor of a given node
    public TreeNode<T> successor(TreeNode<T> x) {
        // if the right subtree is not null,
        // the successor is the leftmost node in the
        // right subtree
        if (x.getRight() != null) {
            return minimum(x.getRight());
        }

        // else it is the lowest ancestor of x whose
        // left child is also an ancestor of x.
        TreeNode<T> y = x.getParent();
        while (y != null && x == y.getRight()) {
            x = y;
            y = y.getParent();
        }
        return y;
    }

    // find the predecessor of a given node
    public TreeNode<T> predecessor(TreeNode<T> x) {
        // if the left subtree is not null,
        // the predecessor is the rightmost node in the
        // left subtree
        if (x.getLeft() != null) {
            return maximum(x.getLeft());
        }

        TreeNode<T> y = x.getParent();
        while (y != null && x == y.getLeft()) {
            x = y;
            y = y.getParent();
        }

        return y;
    }

    // insert the key to the tree in its appropriate position
    public void insert(T key) {
        TreeNode<T> node = new TreeNode<>(key);
        TreeNode<T> y = null;
        TreeNode<T> x = this.root;

        while (x != null) {
            y = x;
            if (node.getData().compareTo(x.getData()) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        // y is parent of x
        node.setParent(y);
        if (y == null) {
            root = node;
        } else if (node.getData().compareTo(y.getData()) < 0) {
            y.setLeft(node);
        } else {
            y.setRight(node);
        }

        // splay node
        splay(node);
    }

    // delete the node from the tree
    void deleteNode(T data) {
        deleteNodeHelper(this.root, data);
    }

    // print the tree structure on the screen
    public void print() {
        printHelper(this.root, "", true);
    }


    public TreeNode<T> getRoot() {
        return this.root;
    }
}