package logic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import logic.files.JsonLoader;
import logic.files.JsonWriter;
import logic.structures.TreeNode;
import logic.structures.avl.AVLTree;
import logic.structures.bst.BST;
import logic.structures.simplelist.SimpleList;
import logic.structures.splay.SplayTree;
import logic.users.AbstractUser;
import logic.users.Enterprise;
import logic.users.Recipe;
import logic.users.User;
import logic.utilities.Encrypter;
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
    public static final Type RECIPES_LIST_TYPE = new TypeToken<SimpleList<String>>() {
    }.getType();
    public static final Type USER_LIST_TYPE = new TypeToken<SimpleList<String>>() {
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
    public static final String NOTIFICATION_COMMENTED_MESSAGE = " commented the recipe: ";
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
        loadServer();
    }

    //Get instance for the singleton
    public static synchronized ServerManager getInstance() {
        if (instance == null) {
            instance = new ServerManager();
        }
        return instance;
    }

    private void loadServer() {
        this.setEnterprises(JsonLoader.loadEnterprises());
        this.setUsers(JsonLoader.loadUsers());
        this.setGlobalRecipes(JsonLoader.loadGlobalRecipes());
    }

    public void saveInfo() {
        JsonWriter.updateUsers();
        JsonWriter.updateEnterprises();
        JsonWriter.updateRecipes();
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
            return null;
        }
        return current.getData();
    }

    /**
     * Intern method for getting a recipe from the global recipes, can be used for commenting the recipe
     * or rating it
     *
     * @param name name of the recipe to be searched
     * @return Recipe, or null if not found
     */
    public Recipe findRecipe(String name) {
        TreeNode<Recipe> current = this.globalRecipes.getRoot();
        while (current.getLeft() != null || current.getRight() != null) {
            if (current.getData().getName().compareTo(name) == 0) {
                break;
            } else if (current.getData().getName().compareTo(name) > 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        if (name.compareTo(current.getData().getName()) != 0) {
            return null;
        }
        return current.getData();
    }

    /**
     * Method for adding a comment to a recipe
     *
     * @param recipe  name of the recipe
     * @param comment comment made by the user, REMEMBER SPACES AND \n
     * @param user    email of the user that made the comment
     * @return true if done the comment, false if the recipe or user dint exist
     */
    public boolean commentRecipe(Recipe recipe, String comment, AbstractUser user) {
        if (recipe != null && user != null) {
            recipe.addComment(comment, user);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Driver method for adding a recipe to the global recipes and then saving it
     * to the json file.
     * !!!RECIPE MUST BE ADDED IN THE USER'S MYMENU AND FOLLOWERS FEED!!!
     *
     * @param newRecipe json file for the new recipe
     */
    public void addRecipe(Recipe newRecipe) {
        this.globalRecipes.insert(newRecipe);
        this.saveInfo();
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
        Gson gson = new Gson();
        //for saving code and simplify the project.
        if (!isUser) {
            Enterprise newSubject = gson.fromJson(subjectData, Enterprise.class);
            newSubject.encryptPassword();
            System.out.println("Enterprise created: " + newSubject.getEmail());
            this.enterprises.insert(newSubject);
        } else {
            User newSubject = gson.fromJson(subjectData, User.class);
            newSubject.encryptPassword();
            this.users.insert(newSubject);
            System.out.println("User created: " + newSubject.getEmail());

        }
        this.saveInfo();
    }


    /**
     * Method for verifying the existence of a user on the server,
     *
     * @param isUser               boolean, true if the type is an user, false if enterprise
     * @param email                email of the user
     * @param passwordNotEncrypted password of the user/enterprise
     * @return true if the user exists and the password is right
     * @throws NoSuchAlgorithmException from the encrypter
     */
    public boolean verifyUser(boolean isUser, String email, String passwordNotEncrypted) throws NoSuchAlgorithmException {
        String enctryptedPass = Encrypter.encryptPassword(passwordNotEncrypted);
        try {
            AbstractUser user = this.findUser(isUser, email);
            assert user != null;
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

    /**
     * Driver method for findUser()
     *
     * @param email email of the user to get
     * @return json format of the user, if found
     */
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

    /**
     * Getter
     *
     * @return AVL tree, containing the global recipes
     */
    public AVLTree<Recipe> getGlobalRecipes() {
        return globalRecipes;
    }

    /**
     * Setter for changing the gobal recipes field, used for loading the recipes from the server
     *
     * @param globalRecipes new avl tree with updated recipes
     */
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
     *
     * @param key String for searching on the recipes, its compared with the recipes' name
     *            and using regular expressions
     * @return json converted simple linked list with recipes
     */
    public String searchRecipes(String key) {
        return Searcher.findRecipes(key);
    }

    /**
     * Method for searching on the recipes, returns 15 results max
     *
     * @param key    String for searching on the recipes, its compared with the recipes' name
     *               and using regular expressions
     * @param isUser true if the type is user, false if its enterprise
     * @return json converted simple linked list with users
     */
    public String searchSubject(String key, boolean isUser) {
        return Searcher.findUsers(key, isUser);
    }


}
