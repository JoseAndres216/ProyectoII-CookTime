package Logic;

import Logic.DataStructures.AVLTree.AVLTree;
import Logic.DataStructures.BinarySearchTree.BST;
import Logic.DataStructures.BinarySearchTree.Node;
import Logic.DataStructures.SplayTree.SplayTree;
import Logic.FileManagement.FilesLoader;
import Logic.Users.AbstractUser;
import Logic.Users.Enterprise;
import Logic.Users.Recipes;
import Logic.Users.User;
import com.google.gson.Gson;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServerManager {
    public static final String ENTERPRISES_JSON_PATH =
            "C:\\Users\\eduar\\Desktop\\CookTime\\ProyectoII-CookTime\\CookTimeServer\\src\\Logic\\FileManagement\\DataBase\\Enterprises.json";
    public static final String USERS_JSON_PATH =
            "C:\\Users\\eduar\\Desktop\\CookTime\\ProyectoII-CookTime\\CookTimeServer\\src\\Logic\\FileManagement\\DataBase\\Users.json";
    private static ServerManager instance = null;
    private BST<AbstractUser> users;
    private SplayTree<AbstractUser> enterprises;
    private AVLTree<Recipes> globalRecipes;

    //Constructor
    private ServerManager() {
        this.setEnterprises(FilesLoader.loadEnterprises());
        this.setUsers(FilesLoader.loadUsers());

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
    public AbstractUser findUser(boolean user, String email) throws NullPointerException {
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

        }
        return exists;
    }

    /**
     * Method for adding a user to the server
     *
     * @param subjectData should be string in format json, with the attributes of user/enterprise to create
     * @param isUser      true if want to create a user, false if a enterprise
     */
    public void createSubject(boolean isUser, String subjectData) {
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
    }

    public boolean verifyUser(boolean isUser, String email, String passwordNotEncrypted) {
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
    public AbstractUser getEnterprise(String email) throws IllegalArgumentException {
        return this.findUser(false, email);
    }

    public AVLTree<Recipes> getGlobalRecipes() {
        return globalRecipes;
    }

    public void setGlobalRecipes(AVLTree<Recipes> globalRecipes) {
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
    public String encryptPassword(String word) {
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
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            System.out.println(new NoSuchAlgorithmException(e));
        }


        return null;
    }


}
