package logic.users;

import com.google.gson.Gson;
import jdk.nashorn.internal.runtime.ECMAException;
import logic.ServerManager;
import logic.structures.simplelist.SimpleList;
import logic.structures.stack.Stack;
import logic.utilities.Encrypter;


import java.security.NoSuchAlgorithmException;

import static logic.ServerSettings.NOTIFICATION_ADDED_RECIPE;
import static logic.ServerSettings.RECIPE_TYPE;


public class AbstractUser implements Comparable<AbstractUser> {
    //User personal data and credentials
    protected String name;
    protected String email;
    protected String password;

    protected Stack<Recipe> newsFeed;
    //For notifications logic, UI and logical work
    protected Stack<String> notifications;
    //user's mymenu, for sorting algorithms.
    protected MyMenu myMenu = new MyMenu();
    //user's followers
    protected SimpleList<AbstractUser> followers = new SimpleList<>();
    private int rating;
    private boolean isChef = false;


    //methods for the logic of followers
    public void addFollower(AbstractUser user) {
        if (this.followers == null) {
            this.followers = new SimpleList<>();
        }
        this.followers.append(user);
        ServerManager.getInstance().saveInfo();
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
        if (this.notifications == null) {
            this.notifications = new Stack<>();
        }
        this.notifications.push(notification);
        ServerManager.getInstance().saveInfo();
    }

    public void addRecipe(String jsonRecipe) {
        System.out.println(this + "added a recipe: " + jsonRecipe);
        if (this.myMenu == null) {
            this.myMenu = new MyMenu();
        }
        Recipe newRecipe = new Gson().fromJson(jsonRecipe, RECIPE_TYPE);
        newRecipe.setAuthor(this.email);

        this.myMenu.addRecipe(new Gson().toJson(newRecipe));
        //add the new notification and the recipe to the feed of the followers
        if (this.followers == null) {
            this.followers = new SimpleList<>();
        }
        for (int i = 0; i < this.followers.len(); i++) {
            this.followers.indexElement(i).updateFeed(newRecipe);
        }

        ServerManager.getInstance().saveInfo();
    }

    public void updateFeed(Recipe newRecipe) {
        if (this.newsFeed == null) {
            this.newsFeed = new Stack<>();
        }
        this.newsFeed.push(newRecipe);
        this.addNotification(this.name + NOTIFICATION_ADDED_RECIPE);

    }

    public String getSerializedFollowers() {
        return
                new Gson().toJson(this.followers, SimpleList.class);
    }

    public String getSerializedNotifications() {
        if (this.notifications == null) {
            this.notifications = new Stack<>();
        }
        return new Gson().toJson(this.notifications, Stack.class);
    }

    /**
     * Method for getting a json-format of the news feed stack
     *
     * @return String of json formatted news feed.
     */
    public String getSerializedNewsFeed() {
        return new Gson().toJson(this.newsFeed, Stack.class);
    }

    public String myMenuRated() {
        return this.myMenu.byRating();
    }

    public String myMenuDifficulty() {
        return this.myMenu.byDifficulty();
    }

    public String myMenuRecients() {
        return this.myMenu.byRecentFirst();
    }

    public String getName() {
        return this.name;
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isChef() {
        return this.isChef;
    }

    public void makeChef() {
        System.out.println(this + " is now chef!!");
        this.isChef = true;
    }

    public boolean share(Recipe recipe) {
        try {
            recipe.share(this);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
