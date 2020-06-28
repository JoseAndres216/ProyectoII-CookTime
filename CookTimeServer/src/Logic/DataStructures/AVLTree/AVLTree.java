package Logic.DataStructures.AVLTree;


import Logic.DataStructures.BinarySearchTree.Node;

public class AVLTree<T extends Comparable<T>> {
    private Node<T> root;

    private int height(Node<T> node) {
        if (node == null)
            return 0;

        return node.getHeight();
    }

    public void insert(T newData) {
        this.insert(this.root, newData);
    }

    private Node<T> insert(Node<T> node, T value) {
        /* 1.  Perform the normal BST rotation */
        if (node == null) {
            return (new Node<>(value));
        }
        if (value.compareTo(node.getData()) < 0) {
            node.setLeft(insert(node.getLeft(), value));
        } else {
            node.setRight(insert(node.getRight(), value));
        }
        /* 2. Update height of this ancestor node */
        node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) + 1);
        /* 3. Get the balance factor of this ancestor node to check whether
           this node became unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && value.compareTo(node.getLeft().getData()) < 0)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && value.compareTo(node.getRight().getData()) > 0)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && value.compareTo(node.getLeft().getData()) > 0) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && value.compareTo(node.getRight().getData()) < 0) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }

        /* return the (unchanged) node pointer */
        return node;
    }

    private Node<T> rightRotate(Node<T> node) {
        Node<T> x = node.getLeft();
        Node<T> temp = x.getRight();

        // Perform rotation
        x.setRight(node);
        node.setLeft(temp);

        // Update heights
        node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) + 1);
        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);

        // Return new root
        return x;
    }

    private Node<T> leftRotate(Node<T> node) {
        Node<T> y = node.getRight();
        Node<T> temp = y.getLeft();

        // Perform rotation
        y.setLeft(node);
        node.setRight(temp);

        //  Update heights
        node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) + 1);
        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);

        // Return new root
        return y;
    }

    // Get Balance factor of node N
    private int getBalance(Node<T> node) {
        if (node == null)
            return 0;
        return height(node.getLeft()) - height(node.getRight());
    }

    public void preOrder(Node<T> root) {
        if (root != null) {
            preOrder(root.getLeft());
            System.out.printf(root.getData().toString());
            preOrder(root.getRight());
        }
    }

    private Node<T> minValueNode(Node<T> node) {
        Node<T> current = node;
        /* loop down to find the leftmost leaf */
        while (current.getLeft() != null)
            current = current.getLeft();
        return current;
    }

    @Override
    public String toString() {
        return "AVLTree{" +
                "root=" + root +
                '}';
    }

    private Node<T> deleteNode(Node<T> root, T value) {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null)
            return null;

        if (value.compareTo(root.getData()) < 0)
            root.setLeft(deleteNode(root.getLeft(), value));

        else if (value.compareTo(root.getData()) > 0)
            root.setRight(deleteNode(root.getRight(), value));
            // if value is same as root's value

        else {
            // root with only one child or no child
            if ((root.getLeft() == null) || (root.getRight() == null)) {
                Node<T> temp;
                if (root.getLeft() != null)
                    temp = root.getLeft();
                else
                    temp = root.getRight();
                root = temp;
            } else {
                // node with two children: Get the inorder successor (smallest in the right subtree)
                Node<T> temp = minValueNode(root.getRight());
                // Copy the inorder successor's data to this node
                root.setData(temp.getData());
                // Delete the inorder successor
                root.setRight(deleteNode(root.getRight(), temp.getData()));
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return null;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.setHeight(Math.max(height(root.getLeft()), height(root.getRight())) + 1);

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whetherd
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && getBalance(root.getLeft()) >= 0)
            return rightRotate(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.getLeft()) < 0) {
            root.setLeft(leftRotate(root.getLeft()));
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.getRight()) <= 0)
            return leftRotate(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.getRight()) > 0) {
            root.setRight(rightRotate(root.getRight()));
            return leftRotate(root);
        }

        return root;
    }

    public void delete(T value) {
        this.deleteNode(this.root, value);
    }
}
