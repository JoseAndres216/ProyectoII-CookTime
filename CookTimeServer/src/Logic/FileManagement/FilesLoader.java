package Logic.FileManagement;

import Logic.DataStructures.BinarySearchTree.BST;
import Logic.Users.AbstractUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Scanner;

public class FilesLoader {
    public static void loadUsers() {
        try {
            Scanner sc = new Scanner(new File(
                    new StringBuilder().append("C:\\Users\\eduar\\")
                            .append("Desktop\\CookTime\\")
                            .append("ProyectoII-CookTime\\CookTimeServer\\src")
                            .append("\\Logic\\FileManagement\\Outputs\\Users.json").toString()));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Type complexType = new TypeToken<BST<AbstractUser>>() {
                }.getType();
                BST<AbstractUser> users = new Gson().fromJson(line, complexType);
                System.out.println(users);


            }
            sc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}