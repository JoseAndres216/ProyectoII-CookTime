package Logic.DataStructures.SplayTree;

import Logic.DataStructures.BinarySearchTree.Node;

public class SplayTree<T extends Comparable<T>> {
    private Node<T> root;

    public SplayTree() {
        root = null;
    }

    private void printHelper(Node<T> currPtr, String indent, boolean last) {
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

    private Node<T> searchTreeHelper(Node<T> node, T key) {
        if (node == null || 0 == node.getData().compareTo(key)) {
            return node;
        }

        if (key.compareTo(node.getData()) < 0) {
            return searchTreeHelper(node.getLeft(), key);
        }
        return searchTreeHelper(node.getRight(), key);
    }

    private void deleteNodeHelper(Node<T> node, T key) {
        Node<T> x = null;
        Node<T> t;
        Node<T> s;
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
            t.parent = null;
        } else {
            t = null;
        }
        s = x;
        s.setRight(null);

        // join operation
        if (s.getLeft() != null) { // remove x
            s.getLeft().parent = null;
        }
        root = join(s.getLeft(), t);
    }

    // rotate left at node x
    private void leftRotate(Node<T> x) {
        Node<T> y = x.getRight();
        x.setRight(y.getLeft());
        if (y.getLeft() != null) {
            y.getLeft().parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.getLeft()) {
            x.parent.setLeft(y);
        } else {
            x.parent.setRight(y);
        }
        y.setLeft(x);
        x.parent = y;
    }

    // rotate right at node x
    private void rightRotate(Node<T> x) {
        Node<T> y = x.getLeft();
        x.setLeft(y.getRight());
        if (y.getRight() != null) {
            y.getRight().parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.getRight()) {
            x.parent.setRight(y);
        } else {
            x.parent.setLeft(y);
        }
        y.setRight(x);
        x.parent = y;
    }

    // Splaying operation. It moves x to the root of the tree
    private void splay(Node<T> x) {
        while (x.parent != null) {
            if (x.parent.parent == null) {
                if (x == x.parent.getLeft()) {
                    // zig rotation
                    rightRotate(x.parent);
                } else {
                    // zag rotation
                    leftRotate(x.parent);
                }
            } else if (x == x.parent.getLeft() && x.parent == x.parent.parent.getLeft()) {
                // zig-zig rotation
                rightRotate(x.parent.parent);
                rightRotate(x.parent);
            } else if (x == x.parent.getRight() && x.parent == x.parent.parent.getRight()) {
                // zag-zag rotation
                leftRotate(x.parent.parent);
                leftRotate(x.parent);
            } else if (x == x.parent.getRight() && x.parent == x.parent.parent.getLeft()) {
                // zig-zag rotation
                leftRotate(x.parent);
                rightRotate(x.parent);
            } else {
                // zag-zig rotation
                rightRotate(x.parent);
                leftRotate(x.parent);
            }
        }
    }

    // joins two trees s and t
    private Node<T> join(Node<T> s, Node<T> t) {
        if (s == null) {
            return t;
        }

        if (t == null) {
            return s;
        }
        Node<T> x = maximum(s);
        splay(x);
        x.setRight(t);
        t.parent = x;
        return x;
    }

    private void preOrderHelper(Node<T> node) {
        if (node != null) {
            System.out.print(node.getData() + " ");
            preOrderHelper(node.getLeft());
            preOrderHelper(node.getRight());
        }
    }

    private void inOrderHelper(Node<T> node) {
        if (node != null) {
            inOrderHelper(node.getLeft());
            System.out.print(node.getData() + " ");
            inOrderHelper(node.getRight());
        }
    }

    private void postOrderHelper(Node<T> node) {
        if (node != null) {
            postOrderHelper(node.getLeft());
            postOrderHelper(node.getRight());
            System.out.print(node.getData() + " ");
        }
    }

    // Pre-Order traversal
    // Node->Left Subtree->Right Subtree
    public void preorder() {
        preOrderHelper(this.root);
    }

    // In-Order traversal
    // Left Subtree -> Node -> Right Subtree
    public void inorder() {
        inOrderHelper(this.root);
    }

    // Post-Order traversal
    // Left Subtree -> Right Subtree -> Node
    public void postorder() {
        postOrderHelper(this.root);
    }

    // search the tree for the key k
    // and return the corresponding node
    public Node<T> searchTree(T k) {
        Node<T> x = searchTreeHelper(root, k);
        if (x != null) {
            splay(x);
        }
        return x;
    }

    // find the node with the minimum key
    public Node<T> minimum(Node<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    // find the node with the maximum key
    public Node<T> maximum(Node<T> node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }

    // find the successor of a given node
    public Node<T> successor(Node<T> x) {
        // if the right subtree is not null,
        // the successor is the leftmost node in the
        // right subtree
        if (x.getRight() != null) {
            return minimum(x.getRight());
        }

        // else it is the lowest ancestor of x whose
        // left child is also an ancestor of x.
        Node<T> y = x.parent;
        while (y != null && x == y.getRight()) {
            x = y;
            y = y.parent;
        }
        return y;
    }

    // find the predecessor of a given node
    public Node<T> predecessor(Node<T> x) {
        // if the left subtree is not null,
        // the predecessor is the rightmost node in the
        // left subtree
        if (x.getLeft() != null) {
            return maximum(x.getLeft());
        }

        Node<T> y = x.parent;
        while (y != null && x == y.getLeft()) {
            x = y;
            y = y.parent;
        }

        return y;
    }

    // insert the key to the tree in its appropriate position
    public void insert(T key) {
        Node<T> node = new Node<>(key);
        Node<T> y = null;
        Node<T> x = this.root;

        while (x != null) {
            y = x;
            if (node.getData().compareTo(x.getData()) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        // y is parent of x
        node.parent = y;
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


    public Node<T> getRoot() {
        return this.root;
    }
}