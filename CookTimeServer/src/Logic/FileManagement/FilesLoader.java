package Logic.FileManagement;

import Logic.DataStructures.BinarySearchTree.BST;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
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
                BST users = new Gson().fromJson(line, BST.class);
                System.out.println(users);

            }
            sc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}