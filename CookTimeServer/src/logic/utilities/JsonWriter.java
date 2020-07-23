package logic.utilities;

import logic.ServerManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

import static logic.ServerSettings.*;

public interface JsonWriter {


    static void updateUsers() {
        String jsonUsers = GSON_INSTANCE.toJson(ServerManager.getInstance().getUsers(), BST_TYPE);
        try (FileWriter file = new FileWriter(USERS_JSON_PATH)) {
            file.write(jsonUsers);

        } catch (IOException e) {
            JSON_WRITER_LOG.log(Level.WARNING, e.getMessage());
        }
    }

    static void updateEnterprises() {
        String enterprises = GSON_INSTANCE.toJson(ServerManager.getInstance().getEnterprises(), SPLAY_TYPE);
        try (FileWriter file = new FileWriter(ENTERPRISES_JSON_PATH)) {
            file.write(enterprises);
        } catch (IOException e) {
            JSON_WRITER_LOG.log(Level.WARNING, e.getMessage());

        }
    }

    static void updateRecipes() {
        String jsonRecipes = GSON_INSTANCE.toJson(ServerManager.getInstance().getGlobalRecipes(), AVL_TYPE);
        try (FileWriter file = new FileWriter(RECIPES_JSON_PATH)) {
            file.write(jsonRecipes);

        } catch (IOException e) {
            JSON_WRITER_LOG.log(Level.WARNING, e.getMessage());

        }
    }

}
