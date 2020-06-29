package Logic.FileManagement;

import Logic.ServerManager;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;

import static Logic.FileManagement.FilesLoader.BST_TYPE;
import static Logic.FileManagement.FilesLoader.SPLAY_TYPE;
import static Logic.ServerManager.ENTERPRISES_JSON_PATH;
import static Logic.ServerManager.USERS_JSON_PATH;

public class FilesWriter {


    public static void updateUsers() {
        String jsonUsers = new Gson().toJson(ServerManager.getInstance().getUsers(), BST_TYPE);
        System.out.println(jsonUsers);

        try (FileWriter file = new FileWriter(USERS_JSON_PATH)) {
            file.write(jsonUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateEnterprises() {

        try (FileWriter file = new FileWriter(ENTERPRISES_JSON_PATH)) {
            String jsonEnterprises = new Gson().toJson(ServerManager.getInstance().getEnterprises(), SPLAY_TYPE);
            file.write(jsonEnterprises);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}