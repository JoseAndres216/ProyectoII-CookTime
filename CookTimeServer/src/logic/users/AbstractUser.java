package logic.users;

import com.google.gson.Gson;
import logic.ServerManager;
import logic.ServerSettings;
import logic.structures.simplelist.SimpleList;
import logic.structures.stack.Stack;
import logic.utilities.Encrypter;

import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Level;

import static logic.ServerSettings.NOTIFICATION_ADDED_RECIPE;
import static logic.ServerSettings.RECIPE_TYPE;


public class AbstractUser implements Comparable<AbstractUser>, Serializable {
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

    public static AbstractUser getNotNull(AbstractUser user1, AbstractUser user2) {
        if (user1 != null) {
            return user1;
        } else return user2;
    }

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
                Objects.equals(getFollowers(), user.getFollowers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getEmail(), getPassword(), getNewsFeed(), getNotifications(), getMyMenu(), getFollowers(), getRating(), isChef());
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

    public void setEmail(String email) {
        this.email = email;
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

    public void encryptPassword() {
        this.setPassword(Encrypter.encryptPassword(this.getPassword()));
    }

    public void addNotification(String notification) {
        if (this.getNotifications() == null) {
            this.setNotifications(new Stack<>());
        }
        this.getNotifications().push(notification);
        ServerSettings.ABSTRACT_USER_LOG.log(Level.INFO, () -> "added a notification for :  " + this.email + ", notification: " + notification);

        ServerManager.getInstance().saveInfo();
    }

    public void addRecipe(String jsonRecipe) {
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
            String userEmail = this.getFollowers().indexElement(i).email;
            //did it this way cause the reference its not the same
            //GET USER IN THE BST TREE
            AbstractUser user1 = ServerManager.getInstance().getUser(userEmail);
            AbstractUser user2 = ServerManager.getInstance().getEnterprise(userEmail);
            AbstractUser user = getNotNull(user1, user2);

            user.addNotification(this.getName() + NOTIFICATION_ADDED_RECIPE);
            user.updateFeed(newRecipe);
        }

        ServerManager.getInstance().saveInfo();
        ServerSettings.ABSTRACT_USER_LOG.log(Level.INFO, () -> "added a recipe: " + jsonRecipe);
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

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public boolean isChef() {
        return this.isChef;
    }

    public void setChef(boolean chef) {
        isChef = chef;
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
        if (this.notifications == null) {
            this.notifications = new Stack<>();
        }
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

    public Recipe getRecipe(String name) {
        SimpleList<Recipe> temp = this.myMenu.ownedRecipes;
        for (int i = 0; i < temp.len(); i++) {
            if (temp.indexElement(i).getName().equals(name)) {
                return temp.indexElement(i);
            }

        }
        return null;
    }
}
