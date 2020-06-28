package Logic.FileManagement;

import Logic.DataStructures.BinarySearchTree.BST;
import Logic.DataStructures.SplayTree.SplayTree;
import Logic.ServerManager;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;

import static Logic.ServerManager.ENTERPRISES_JSON_PATH;
import static Logic.ServerManager.USERS_JSON_PATH;

public class FilesWriter {


    public static void updateUsers() {
        String jsonUsers = new Gson().toJson(ServerManager.getInstance().getUsers(), BST.class);
        System.out.println(jsonUsers);

        try (FileWriter file = new FileWriter(USERS_JSON_PATH)) {
            file.write(jsonUsers);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateEnterprises() {

        String jsonEnterprises = new Gson().toJson(ServerManager.getInstance().getEnterprises(), SplayTree.class);
        System.out.println(jsonEnterprises);
        try (FileWriter file = new FileWriter(ENTERPRISES_JSON_PATH)) {
            file.write(jsonEnterprises);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}