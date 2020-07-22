package logic.utilities;

import com.google.gson.Gson;
import logic.ServerManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static logic.ServerSettings.*;

public interface JsonWriter {
    Gson gson = new Gson();
    Logger log = Logger.getLogger("JsonWriterLog");


    static void updateUsers() {
        String jsonUsers = gson.toJson(ServerManager.getInstance().getUsers(), BST_TYPE);
        try (FileWriter file = new FileWriter(USERS_JSON_PATH)) {
            file.write(jsonUsers);

        } catch (IOException e) {
            log.log(Level.WARNING, e.getMessage());
        }
    }

    static void updateEnterprises() {
        String enterprises = gson.toJson(ServerManager.getInstance().getEnterprises(), SPLAY_TYPE);
        try (FileWriter file = new FileWriter(ENTERPRISES_JSON_PATH)) {
            file.write(enterprises);
        } catch (IOException e) {
            log.log(Level.WARNING, e.getMessage());

        }
    }

    static void updateRecipes() {
        String jsonRecipes = gson.toJson(ServerManager.getInstance().getGlobalRecipes(), AVL_TYPE);
        try (FileWriter file = new FileWriter(RECIPES_JSON_PATH)) {
            file.write(jsonRecipes);

        } catch (IOException e) {
            log.log(Level.WARNING, e.getMessage());

        }
    }

}
