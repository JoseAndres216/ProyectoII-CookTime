package Logic.FileManagement;

import Logic.ServerManager;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;

import static Logic.FileManagement.JsonLoader.BST_TYPE;
import static Logic.FileManagement.JsonLoader.SPLAY_TYPE;
import static Logic.ServerManager.ENTERPRISES_JSON_PATH;
import static Logic.ServerManager.USERS_JSON_PATH;

public abstract class JsonWriter {
    private static Gson gson = new Gson();

    private JsonWriter() {
    }

    public static void updateUsers() {
        String jsonUsers = gson.toJson(ServerManager.getInstance().getUsers(), BST_TYPE);
        System.out.println(jsonUsers);

        try (FileWriter file = new FileWriter(USERS_JSON_PATH)) {
            file.write(jsonUsers);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateEnterprises() {
        String enterprises = gson.toJson(ServerManager.getInstance().getEnterprises(), SPLAY_TYPE);

        try (FileWriter file = new FileWriter(ENTERPRISES_JSON_PATH)) {

            file.write(enterprises);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
