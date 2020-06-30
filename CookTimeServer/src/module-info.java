module Logic.Users {
    requires junit;
    requires gson;
    requires json.simple;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    exports Logic;
    exports Logic.Users;
    opens Logic.Users;
    exports Logic.DataStructures.SimpleList;
    exports Logic.DataStructures.AVLTree;
    exports Logic.DataStructures.SplayTree;
    exports Logic.DataStructures.Stack;
    exports Logic.DataStructures.BinarySearchTree;
    exports Logic.FileManagement;
    opens Logic.DataStructures.SimpleList;
    opens Logic.DataStructures.AVLTree;
    opens Logic.DataStructures.SplayTree;
    opens Logic.DataStructures.Stack;
    opens Logic.DataStructures.BinarySearchTree;
}
