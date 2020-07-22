package logic.users;

import com.google.gson.Gson;
import logic.ServerManager;
import logic.structures.simplelist.SimpleList;
import logic.structures.stack.Stack;
import logic.utilities.Encrypter;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static logic.ServerSettings.NOTIFICATION_ADDED_RECIPE;
import static logic.ServerSettings.RECIPE_TYPE;


public class AbstractUser implements Comparable<AbstractUser>, Serializable {
    private final transient Logger log = Logger.getLogger("AbstractUserLog");
    //User personal data and credentials
    private String name;
    private String email;
    private String password;
    private Stack<Recipe> newsFeed;
    //For notifications logic, UI and logical work
    private Stack<String> notifications;
    //user's mymenu, for sorting algorithms.
    private MyMenu myMenu = new MyMenu();
    //user's followers
    private SimpleList<AbstractUser> followers = new SimpleList<>();
    private int rating;
    private boolean isChef = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractUser user = (AbstractUser) o;
        return getRating() == user.getRating() &&
                isChef() == user.isChef() &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getNewsFeed(), user.getNewsFeed()) &&
                Objects.equals(getNotifications(), user.getNotifications()) &&
                Objects.equals(getMyMenu(), user.getMyMenu()) &&
                Objects.equals(getFollowers(), user.getFollowers()) &&
                Objects.equals(getLog(), user.getLog());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getEmail(), getPassword(), getNewsFeed(), getNotifications(), getMyMenu(), getFollowers(), getRating(), getLog(), isChef());
    }

    //methods for the logic of followers
    public void addFollower(AbstractUser user) {
        if (this.getFollowers() == null) {
            this.setFollowers(new SimpleList<>());
        }
        this.getFollowers().append(user);
        ServerManager.getInstance().saveInfo();
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public int compareTo(AbstractUser user) {
        return this.getEmail().compareTo(user.getEmail());
    }


    @Override
    public String toString() {
        return "AbstractUser{" +
                "email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }

    public String getPass() {
        return this.getPassword();
    }

    public void encryptPassword() throws NoSuchAlgorithmException {
        this.setPassword(Encrypter.encryptPassword(this.getPassword()));
    }

    public void addNotification(String notification) {
        if (this.getNotifications() == null) {
            this.setNotifications(new Stack<>());
        }
        this.getNotifications().push(notification);
        ServerManager.getInstance().saveInfo();
    }

    public void addRecipe(String jsonRecipe) {
        getLog().log(Level.INFO, () -> "added a recipe: " + jsonRecipe);
        if (this.getMyMenu() == null) {
            this.setMyMenu(new MyMenu());
        }
        Recipe newRecipe = new Gson().fromJson(jsonRecipe, RECIPE_TYPE);
        newRecipe.setAuthor(this.getEmail());
        this.getMyMenu().addRecipe(new Gson().toJson(newRecipe));
        this.updateFeed(newRecipe);
        //add the new notification and the recipe to the feed of the followers
        if (this.getFollowers() == null) {
            this.setFollowers(new SimpleList<>());
        }
        for (int i = 0; i < this.getFollowers().len(); i++) {
            this.getFollowers().indexElement(i).addNotification(this.getName() + NOTIFICATION_ADDED_RECIPE);
            this.getFollowers().indexElement(i).updateFeed(newRecipe);
        }

        ServerManager.getInstance().saveInfo();
    }

    public void updateFeed(Recipe newRecipe) {
        if (this.getNewsFeed() == null) {
            this.setNewsFeed(new Stack<>());
        }
        this.getNewsFeed().push(newRecipe);
    }

    public String getSerializedFollowers() {
        return
                new Gson().toJson(this.getFollowers(), SimpleList.class);
    }

    public String getSerializedNotifications() {
        if (this.getNotifications() == null) {
            this.setNotifications(new Stack<>());
        }
        return new Gson().toJson(this.getNotifications(), Stack.class);
    }

    /**
     * Method for getting a json-format of the news feed stack
     *
     * @return String of json formatted news feed.
     */
    public String getSerializedNewsFeed() {
        return new Gson().toJson(this.getNewsFeed(), Stack.class);
    }

    public String myMenuRated() {
        return this.getMyMenu().byRating();
    }

    public String myMenuDifficulty() {
        return this.getMyMenu().byDifficulty();
    }

    public String myMenuRecients() {
        return this.getMyMenu().byRecentFirst();
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
        this.setChef(true);
    }

    public boolean share(Recipe recipe) {
        try {
            recipe.share(this);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Stack<Recipe> getNewsFeed() {
        return newsFeed;
    }

    public void setNewsFeed(Stack<Recipe> newsFeed) {
        this.newsFeed = newsFeed;
    }

    public Stack<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(Stack<String> notifications) {
        this.notifications = notifications;
    }

    public MyMenu getMyMenu() {
        return myMenu;
    }

    public void setMyMenu(MyMenu myMenu) {
        this.myMenu = myMenu;
    }

    public SimpleList<AbstractUser> getFollowers() {
        return followers;
    }

    public void setFollowers(SimpleList<AbstractUser> followers) {
        this.followers = followers;
    }

    public Logger getLog() {
        return log;
    }

    public void setChef(boolean chef) {
        isChef = chef;
    }
}
