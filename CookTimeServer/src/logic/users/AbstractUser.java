package logic.users;

import logic.structures.simplelist.SimpleList;
import logic.structures.stack.Stack;
import logic.utilities.Encrypter;
import com.google.gson.Gson;

import java.security.NoSuchAlgorithmException;

import static logic.ServerManager.NOTIFICATION_ADDED_RECIPE;
import static logic.ServerManager.RECIPE_TYPE;


public class AbstractUser implements Comparable<AbstractUser> {
    //User personal data and credentials
    protected String name;
    protected String email;
    protected String password;
    //user info for UI representation

    protected MyMenu myMenu = new MyMenu();

    //For notifications logic, UI and logical work
    protected SimpleList<AbstractUser> followers = new SimpleList<>();
    protected SimpleList<AbstractUser> following = new SimpleList<>();

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
        return "AbstractUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getPass() {
        return this.password;
    }

    public void encryptPassword() throws NoSuchAlgorithmException {
        this.password = Encrypter.encryptPassword(this.password);
    }

    public void addNotification(String notification) {
        this.notifications.push(notification);
    }

    public void addRecipe(String jsonRecipe) {
        this.myMenu.addRecipe(jsonRecipe);
        Recipe newRecipe = new Gson().fromJson(jsonRecipe, RECIPE_TYPE);
        //add the new notification and the recipe to the feed of the followers
        for (int i = 0; i < this.followers.len(); i++) {
            this.followers.indexElement(i).updateFeed(newRecipe);
        }
    }

    private void updateFeed(Recipe newRecipe) {
        this.newsFeed.push(newRecipe);
        this.addNotification(this.name + NOTIFICATION_ADDED_RECIPE);

    }
}
