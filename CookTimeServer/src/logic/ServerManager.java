package logic;

import logic.structures.avl.AVLTree;
import logic.structures.bst.BST;
import logic.structures.simplelist.SimpleList;
import logic.structures.splay.SplayTree;
import logic.structures.TreeNode;
import logic.files.JsonLoader;
import logic.utilities.Encrypter;
import logic.users.AbstractUser;
import logic.users.Enterprise;
import logic.users.Recipe;
import logic.users.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import logic.utilities.Searcher;

import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;

public class ServerManager {
    public static final Type BST_TYPE = new TypeToken<BST<AbstractUser>>() {
    }.getType();
    public static final Type SPLAY_TYPE = new TypeToken<SplayTree<AbstractUser>>() {
    }.getType();
    public static final Type AVL_TYPE = new TypeToken<AVLTree<Recipe>>() {
    }.getType();
    public static final Type RECIPE_TYPE = new TypeToken<Recipe>() {
    }.getType();
    public static final Type RECIPES_LIST_TYPE = new TypeToken<SimpleList<Recipe>>() {
    }.getType();
    public static final Type USER_LIST_TYPE = new TypeToken<SimpleList<AbstractUser>>() {
    }.getType();
    public static final String ENTERPRISES_JSON_PATH;
    public static final String USERS_JSON_PATH;
    public static final String RECIPES_JSON_PATH;
    //Constant notification messages
    //nofication for rated the recipe, add username at front, and qualification at end.
    public static final String NOTIFICATION_RATED_MESSAGE = " rated the recipe with: ";
    //Notification for shared the recipe, add user name at front
    public static final String NOTIFICATION_SHARED_MESSAGE = " shared the recipe.";
    //Notification for comments, add username at front, message at end.
    public static final String NOTIFICATION_COMMENTED_MESSAGE = " commented the recipe ";
    //Notification for added a recipe, should add the name at the beggining
    public static final String NOTIFICATION_ADDED_RECIPE = " added a new recipe!";
    public static final String DATA_BASE_PATH = "C:\\Users\\eduar\\Desktop\\CookTime\\ProyectoII-CookTime\\CookTimeServer\\src\\logic\\files\\database\\";
    private static ServerManager instance = null;

    static {
        final String JsonEnterprises = "Enterprises.json";
        ENTERPRISES_JSON_PATH = DATA_BASE_PATH + JsonEnterprises;

        final String JsonUsers = "Users.json";
        USERS_JSON_PATH = DATA_BASE_PATH + JsonUsers;

        final String JsonRecipes = "Recipes.json";
        RECIPES_JSON_PATH = DATA_BASE_PATH + JsonRecipes;

    }

    private BST<AbstractUser> users;
    private SplayTree<AbstractUser> enterprises;
    private AVLTree<Recipe> globalRecipes;

    //Constructor
    private ServerManager() {
        this.setEnterprises(JsonLoader.loadEnterprises());
        this.setUsers(JsonLoader.loadUsers());
        this.setGlobalRecipes(JsonLoader.loadGlobalRecipes());
    }

    //Get instance for the singleton
    public static synchronized ServerManager getInstance() {
        if (instance == null) {
            instance = new ServerManager();
        }
        return instance;
    }

    /**
     * Main method for finding users/enterprises
     *
     * @param user  bool, true if user, false if enterprise
     * @param email search key
     * @return user who has the email
     * @throws NullPointerException if not found
     */
    private AbstractUser findUser(boolean user, String email) {
        TreeNode<AbstractUser> current;
        if (user) {
            current = this.users.getRoot();
        } else {
            current = this.enterprises.getRoot();
        }
        while (current.getLeft() != null || current.getRight() != null) {
            if (current.getData().getEmail().compareTo(email) == 0) {
                break;
            } else if (current.getData().getEmail().compareTo(email) > 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        if (email.compareTo(current.getData().getEmail()) != 0) {
            throw new NullPointerException("The user isn't registered in the server");
        }
        return current.getData();
    }

    public void addRecipe(Recipe newRecipe) {
        this.globalRecipes.insert(newRecipe);
    }

    /**
     * Main method for finding users/enterprises
     *
     * @param user  bool, true if user, false if enterprise
     * @param email search key
     * @return true if found, false if not
     */
    public boolean existUser(boolean user, String email) {
        boolean exists = false;
        try {
            this.findUser(user, email);
            exists = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return exists;
    }

    /**
     * Method for adding a user to the server
     *
     * @param subjectData should be string in format json, with the attributes of user/enterprise to create
     * @param isUser      true if want to create a user, false if a enterprise
     */
    public void createSubject(boolean isUser, String subjectData) throws NoSuchAlgorithmException {
        try {
            Gson gson = new Gson();
            //for saving code and simplify the project.
            if (!isUser) {
                Enterprise newSubject = gson.fromJson(subjectData, Enterprise.class);
                newSubject.encryptPassword();
                this.enterprises.insert(newSubject);
            } else {
                User newSubject = gson.fromJson(subjectData, User.class);
                newSubject.encryptPassword();
                this.users.insert(newSubject);

            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean verifyUser(boolean isUser, String email, String passwordNotEncrypted) throws NoSuchAlgorithmException {
        String enctryptedPass = Encrypter.encryptPassword(passwordNotEncrypted);
        try {
            AbstractUser user = this.findUser(isUser, email);
            return user.getPass().equals(enctryptedPass);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Method for getting a user object, given an email
     *
     * @param email key to search, its the non repeatable parameter for users
     * @return User object with the given email
     * @throws IllegalArgumentException if the user isnt in the tree
     */
    public AbstractUser getUser(String email) {
        return this.findUser(true, email);
    }
    public String getUserJson(String email) {
        return new Gson().toJson((this.findUser(true, email)), AbstractUser.class);
    }

    /**
     * Method for getting a Enterprise object, given an email
     *
     * @param email key to search, its the non repeatable parameter for Enterprise
     * @return Enterprise object with the given email
     * @throws IllegalArgumentException if the enterprise isnt in the tree
     */
    public AbstractUser getEnterprise(String email) {
        return this.findUser(false, email);
    }

    public AVLTree<Recipe> getGlobalRecipes() {
        return globalRecipes;
    }

    public void setGlobalRecipes(AVLTree<Recipe> globalRecipes) {
        this.globalRecipes = globalRecipes;
    }

    public BST<AbstractUser> getUsers() {
        return this.users;
    }
    public void setUsers(BST<AbstractUser> users) {
        this.users = users;
    }

    public SplayTree<AbstractUser> getEnterprises() {
        return this.enterprises;
    }

    public void setEnterprises(SplayTree<AbstractUser> enterprises) {
        this.enterprises = enterprises;
    }

    /**
     * Method for searching on the recipes, returns 15 results max
     * @param key String for searching on the recipes, its compared with the recipes' name
     *            and using regular expressions
     * @return json converted simple linked list with recipes
     */
    public String searchRecipes(String key ){
        return Searcher.findRecipes(key);
    }
    public String searchSubject(String key, boolean isUser){
        return Searcher.findUsers(key,isUser);
    }

}
