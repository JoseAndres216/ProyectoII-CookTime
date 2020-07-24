package logic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import logic.structures.bst.BST;
import logic.structures.simplelist.SimpleList;
import logic.structures.splay.SplayTree;
import logic.users.AbstractUser;
import logic.users.Recipe;

import java.lang.reflect.Type;
import java.util.logging.Logger;

public final class ServerSettings {
    //Max amount of results in searches and recommendations.
    public static final int MAX_RESULTS = 15;
    public static final Type BST_TYPE = new TypeToken<BST<AbstractUser>>() {
    }.getType();
    public static final Type SPLAY_TYPE = new TypeToken<SplayTree<AbstractUser>>() {
    }.getType();
    public static final Type AVL_TYPE = new TypeToken<BST<Recipe>>() {
    }.getType();
    public static final Type RECIPE_TYPE = new TypeToken<Recipe>() {
    }.getType();
    public static final Type RECIPES_LIST_TYPE = new TypeToken<SimpleList<String>>() {
    }.getType();
    public static final Type USER_LIST_TYPE = new TypeToken<SimpleList<String>>() {
    }.getType();
    //Constant notification messages
    //nofication for rated the recipe, add username at front, and qualification at end.
    public static final String NOTIFICATION_RATED_MESSAGE = " rated the recipe with: ";
    //Notification for shared the recipe, add user name at front
    public static final String NOTIFICATION_SHARED_MESSAGE = " shared the recipe.";
    //Notification for comments, add username at front, message at end.
    public static final String NOTIFICATION_COMMENTED_MESSAGE = " commented the recipe: ";
    //Notification for added a recipe, should add the name at the beggining
    public static final String NOTIFICATION_ADDED_RECIPE = " added a new recipe!";
    public static final String DATA_BASE_PATH = "C:\\Users\\eduar\\Desktop\\CookTime\\ProyectoII-CookTime\\CookTimeServer\\src\\logic\\database\\";
    public static final String JSON_ENTERPRISES = "Enterprises.json";
    public static final String ENTERPRISES_JSON_PATH = DATA_BASE_PATH + JSON_ENTERPRISES;
    public static final String JSON_USERS = "Users.json";
    public static final String USERS_JSON_PATH = DATA_BASE_PATH + JSON_USERS;
    public static final String JSON_RECIPES = "Recipes.json";
    public static final String RECIPES_JSON_PATH = DATA_BASE_PATH + JSON_RECIPES;
    public static final String USER_NOT_FOUND_LOG = "User not found, email: ";
    public static final Logger ENCRYPT_LOG = Logger.getLogger("EncryptLog");
    public static final Logger JSON_LOADER_LOG = Logger.getLogger("JsonLoaderLog");
    public static final Gson GSON_INSTANCE = new Gson();
    public static final Logger JSON_WRITER_LOG = Logger.getLogger("JsonWriterLog");
    public static final int MAX_DIGITS_RADIX = 2;
    public static final Logger ABSTRACT_USER_LOG = Logger.getLogger("AbstractUserLog");
    public static final Logger RECIPES_LOG = Logger.getLogger("RecipesLog");


    private ServerSettings() {
    }
}
