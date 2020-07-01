package Logic.FileManagement;

import Logic.DataStructures.AVLTree.AVLTree;
import Logic.DataStructures.BinarySearchTree.BST;
import Logic.DataStructures.SplayTree.SplayTree;
import Logic.Users.AbstractUser;
import Logic.Users.Recipe;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static Logic.ServerManager.*;

public abstract class JsonLoader {


    public static BST<AbstractUser> loadUsers() {
        BST<AbstractUser> users;
        try {
            Scanner sc = new Scanner(new File(USERS_JSON_PATH));
            String line = sc.nextLine();
            users = new Gson().fromJson(line, BST_TYPE);
            sc.close();
            return users;
            //close the file
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SplayTree<AbstractUser> loadEnterprises() {
        try {
            Scanner sc = new Scanner(new File(ENTERPRISES_JSON_PATH));

            String line = sc.nextLine();
            SplayTree<AbstractUser> enterprises = new Gson().fromJson(line, SPLAY_TYPE);
            sc.close();
            return enterprises;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static AVLTree<Recipe> loadGlobalRecipes() {
        try {
            Scanner sc = new Scanner(new File(RECIPES_JSON_PATH));
            String line = sc.nextLine();
            AVLTree<Recipe> globalRecipes = new Gson().fromJson(line, AVL_TYPE);
            sc.close();
            return globalRecipes;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}