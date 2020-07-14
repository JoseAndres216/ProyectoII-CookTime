package logic.utilities;

import logic.structures.avl.AVLTree;
import logic.structures.bst.BST;
import logic.structures.splay.SplayTree;
import logic.users.AbstractUser;
import logic.users.Recipe;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static logic.ServerManager.*;
import static logic.ServerSettings.*;

public interface JsonLoader {

    static BST<AbstractUser> loadUsers() {
        BST<AbstractUser> users;
        try (Scanner sc = new Scanner(new File(USERS_JSON_PATH))) {

            String line = sc.nextLine();
            users = new Gson().fromJson(line, BST_TYPE);
            return users;
            //close the file
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static SplayTree<AbstractUser> loadEnterprises() {
        try (Scanner sc = new Scanner(new File(ENTERPRISES_JSON_PATH))) {


            String line = sc.nextLine();

            return new Gson().fromJson(line, SPLAY_TYPE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static AVLTree<Recipe> loadGlobalRecipes() {
        try (Scanner sc = new Scanner(new File(RECIPES_JSON_PATH))) {

            String line = sc.nextLine();

            return new Gson().fromJson(line, AVL_TYPE);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}