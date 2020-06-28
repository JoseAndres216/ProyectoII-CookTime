package Logic.Users;

import Logic.DataStructures.SimpleList.SimpleList;
import Logic.DataStructures.Stack.Stack;
import Logic.ServerManager;


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
    protected Stack<Recipes> newsFeed;

    //methods for the logic of followers
    protected void addFollower(AbstractUser user) {
        this.followers.insertLast(user);
    }

    protected void followUser(AbstractUser user) {
        this.following.insertLast(user);
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public int compareTo(AbstractUser user) {
        return this.email.compareTo(user.email);
    }

    public void makeChef() {

    }

    public String getPass() {
        return this.password;
    }

    public void encryptPassword() {
        this.password = ServerManager.getInstance().encryptPassword(this.password);
    }
}
