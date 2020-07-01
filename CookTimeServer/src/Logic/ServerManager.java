package Logic;

import Logic.DataStructures.AVLTree.AVLTree;
import Logic.DataStructures.BinarySearchTree.BST;
import Logic.DataStructures.BinarySearchTree.Node;
import Logic.DataStructures.SplayTree.SplayTree;
import Logic.FileManagement.JsonLoader;
import Logic.Users.AbstractUser;
import Logic.Users.Enterprise;
import Logic.Users.Recipe;
import Logic.Users.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.security.MessageDigest;
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
    public static final String ENTERPRISES_JSON_PATH;
    public static final String USERS_JSON_PATH;
    public static final String RECIPES_JSON_PATH;
    private static ServerManager instance = null;

    public static final String DATA_BASE_PATH = "C:\\Users\\eduar\\Desktop\\CookTime\\ProyectoII-CookTime\\CookTimeServer\\src\\Logic\\FileManagement\\DataBase\\";

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

    public static void main(String[] args) {

    }

    /**
     * Main method for finding users/enterprises
     *
     * @param user  bool, true if user, false if enterprise
     * @param email search key
     * @return user who has the email
     * @throws NullPointerException if not found
     */
    public AbstractUser findUser(boolean user, String email) {
        Node<AbstractUser> current;
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
        String enctryptedPass = this.encryptPassword(passwordNotEncrypted);
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

    /**
     * Method for setting the user as a chef
     *
     * @param email key of the user to be chef
     */
    public void makeChef(String email) {
        this.getUser(email).makeChef();
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
     * Method for encryptation the passwords
     *
     * @return
     */
    public String encryptPassword(String word) throws NoSuchAlgorithmException {
        try {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(word.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = new StringBuilder().append("0").append(hashtext).toString();
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException(e);
        }
    }


}
