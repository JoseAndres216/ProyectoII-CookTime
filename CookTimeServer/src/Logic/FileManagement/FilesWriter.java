package Logic.FileManagement;

import Logic.DataStructures.BinarySearchTree.BST;
import Logic.ServerManager;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;

public class FilesWriter {

    public static void updateUsers() {
        String jsonUsers = new Gson().toJson(ServerManager.getInstance().getUsers(), BST.class);
        System.out.println(jsonUsers);
        try (FileWriter file = new FileWriter("C:\\Users\\eduar\\Desktop\\CookTime\\ProyectoII-CookTime\\CookTimeServer\\src\\Logic\\FileManagement\\Outputs\\Users.json")) {
            file.write(jsonUsers);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}