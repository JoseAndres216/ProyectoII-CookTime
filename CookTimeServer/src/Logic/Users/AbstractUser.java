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
    protected Stack<String> notifications;
    protected Stack<Recipe> newsFeed;

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

    public int compareTo(AbstractUser user) {
        return this.email.compareTo(user.email);
    }

    public void makeChef() {

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
        ServerManager.getInstance().addRecipe(newRecipe);
    }
}
