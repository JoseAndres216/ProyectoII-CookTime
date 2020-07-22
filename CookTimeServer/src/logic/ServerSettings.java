package logic;

import com.google.gson.reflect.TypeToken;
import logic.structures.bst.BST;
import logic.structures.simplelist.SimpleList;
import logic.structures.splay.SplayTree;
import logic.users.AbstractUser;
import logic.users.Recipe;

import java.lang.reflect.Type;

/**
 *
 */
public interface ServerSettings {
    //Max amount of results in searches and recommendations.
    int MAX_RESULTS = 15;

    Type BST_TYPE = new TypeToken<BST<AbstractUser>>() {
    }.getType();
    Type SPLAY_TYPE = new TypeToken<SplayTree<AbstractUser>>() {
    }.getType();
    Type AVL_TYPE = new TypeToken<BST<Recipe>>() {
    }.getType();
    Type RECIPE_TYPE = new TypeToken<Recipe>() {
    }.getType();
    Type RECIPES_LIST_TYPE = new TypeToken<SimpleList<String>>() {
    }.getType();
    Type USER_LIST_TYPE = new TypeToken<SimpleList<String>>() {
    }.getType();

    //Constant notification messages
    //nofication for rated the recipe, add username at front, and qualification at end.
    String NOTIFICATION_RATED_MESSAGE = " rated the recipe with: ";
    //Notification for shared the recipe, add user name at front
    String NOTIFICATION_SHARED_MESSAGE = " shared the recipe.";
    //Notification for comments, add username at front, message at end.
    String NOTIFICATION_COMMENTED_MESSAGE = " commented the recipe: ";
    //Notification for added a recipe, should add the name at the beggining
    String NOTIFICATION_ADDED_RECIPE = " added a new recipe!";
    String DATA_BASE_PATH = "logic/files/database/";


    String JSON_ENTERPRISES = "Enterprises.json";
    String ENTERPRISES_JSON_PATH = DATA_BASE_PATH + JSON_ENTERPRISES;

    String JSON_USERS = "Users.json";
    String USERS_JSON_PATH = DATA_BASE_PATH + JSON_USERS;

    String JSON_RECIPES = "Recipes.json";
    String RECIPES_JSON_PATH = DATA_BASE_PATH + JSON_RECIPES;


    String USER_NOT_FOUND_LOG = "User not found, email: ";
}
