package logic.structures.bst;

import logic.structures.simplelist.SimpleList;
import logic.structures.TreeNode;

public class BST<T extends Comparable<T>> {

    private TreeNode<T> root;

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

    public void insert(T newData) throws IllegalArgumentException {
        TreeNode<T> newNode = new TreeNode<>(newData);
        //Caso en que el arbol esta vacio
        if (root == null) {
            root = newNode;
            return;
        }
        TreeNode<T> temp = root;
        boolean inserted = false;

        while (!inserted) {
            if (temp.getData().compareTo(newData) == 0) {
                throw new IllegalArgumentException("Repeated node, cannot insert");

            } else if (temp.getData().compareTo(newData) < 0) {
                if (temp.getRight() != null) {
                    temp = temp.getRight();
                } else {
                    temp.setRight(newNode);
                    inserted = true;
                }
            } else if (temp.getData().compareTo(newData) > 0) {
                if (temp.getLeft() != null) {
                    temp = temp.getLeft();
                } else {
                    temp.setLeft(newNode);
                    inserted = true;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "BST{" +
                "root=" + root +
                '}';
    }

    public void delete(T data) {
        TreeNode<T> parent = root;
        TreeNode<T> current = root;
        boolean isLeftChild = false;
        while (current.getData().compareTo(data) != 0) {
            parent = current;
            if (current.getData().compareTo(data) > 0) {
                isLeftChild = true;
                current = current.getLeft();
            } else {
                isLeftChild = false;
                current = current.getRight();
            }
            if (current == null) {
                throw new IllegalArgumentException("The node cannot be deleted, not found.");
            }
        }
        //if i am here that means we have found the node
        //Case 1: if node to be deleted has no children
        if (current.getLeft() == null && current.getRight() == null) {
            if (current == root) {
                root = null;
            }
            if (isLeftChild) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        }
        //Case 2 : if node to be deleted has only one child
        else if (current.getRight() == null) {
            if (current == root) {
                root = current.getLeft();
            } else if (isLeftChild) {
                parent.setLeft(current.getLeft());
            } else {
                parent.setRight(current.getLeft());
            }
        } else if (current.getLeft() == null) {
            if (current == root) {
                root = current.getRight();
            } else if (isLeftChild) {
                parent.setLeft(current.getRight());
            } else {
                parent.setRight(current.getRight());
            }
        }

        //case 3 node has two children
        else if (current.getLeft() != null && current.getRight() != null) {
//now we have found the minimum element in the right sub tree
            TreeNode<T> successor = getSuccessor(current);
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.setLeft(successor);
            } else {
                parent.setRight(successor);
            }
            successor.setLeft(current.getLeft());
        }


    }

    //returns the successor for the delete method
    public TreeNode<T> getSuccessor(TreeNode<T> deleleNode) {
        TreeNode<T> successsor = null;
        TreeNode<T> successsorParent = null;
        TreeNode<T> current = deleleNode.getRight();
        while (current != null) {
            successsorParent = successsor;
            successsor = current;
            current = current.getLeft();
        }

        if (successsor != deleleNode.getRight()) {
            assert successsorParent != null;
            successsorParent.setLeft(successsor.getRight());
            successsor.setRight(deleleNode.getRight());
        }
        return successsor;
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

    public void print() {
        this.printHelper(this.root, "  ", true);
    }

    public TreeNode<T> getRoot() {
        return this.root;
    }
}
