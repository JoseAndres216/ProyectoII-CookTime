package Logic.Users;

import Logic.DataStructures.SimpleList.SimpleList;
import Logic.DataStructures.Stack.Stack;
import Logic.ServerManager;
import com.google.gson.Gson;

import java.security.NoSuchAlgorithmException;

import static Logic.ServerManager.RECIPE_TYPE;


public class AbstractUser implements Comparable<AbstractUser> {
    //User personal data and credentials
    protected String name;
    protected String email;
    protected String password;
    //user info for UI representation

    protected MyMenu myMenu;

    //For notifications logic, UI and logical work
    protected SimpleList<AbstractUser> followers;
    protected SimpleList<AbstractUser> following;
    protected SimpleList<Recipe> ownedRecipes;
    protected Stack<String> notifications;
    protected Stack<Recipe> newsFeed;
    private boolean isChef = false;

    //methods for the logic of followers
    protected void addFollower(AbstractUser user) {
        this.followers.append(user);
    }

    protected void followUser(AbstractUser user) {
        this.following.append(user);
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public int compareTo(AbstractUser user) {
        return this.email.compareTo(user.email);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void makeChef() {
        this.isChef = true;
    }

    public String getPass() {
        return this.password;
    }

    public void encryptPassword() throws NoSuchAlgorithmException {
        this.password = ServerManager.getInstance().encryptPassword(this.password);
    }

    public void addNotification(String notification) {
        this.notifications.push(notification);
    }

    public void addRecipe(String jsonRecipe) {
        //create the object
        Recipe newRecipe = new Gson().fromJson(jsonRecipe, RECIPE_TYPE);
        //add recipe to the global repository
        ServerManager.getInstance().addRecipe(newRecipe);
        //add ther recipe to the local repository
        if (this.ownedRecipes == null) {
            this.ownedRecipes = new SimpleList<>();
        }
        this.ownedRecipes.append(newRecipe);
    }
}
