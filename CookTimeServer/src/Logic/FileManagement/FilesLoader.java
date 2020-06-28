package Logic.FileManagement;

import Logic.DataStructures.BinarySearchTree.BST;
import Logic.DataStructures.SplayTree.SplayTree;
import Logic.Users.AbstractUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Scanner;

import static Logic.ServerManager.ENTERPRISES_JSON_PATH;
import static Logic.ServerManager.USERS_JSON_PATH;

public class FilesLoader {

    public static final Type BST_TYPE = new TypeToken<BST<AbstractUser>>() {
    }.getType();
    public static final Type SPLAY_TYPE = new TypeToken<SplayTree<AbstractUser>>() {
    }.getType();

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
}