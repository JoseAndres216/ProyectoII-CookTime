package Logic.Users;

import Logic.DataStructures.SimpleList.SimpleList;
import Logic.DataStructures.Stack.Stack;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public abstract class AbstractUser implements Comparable<AbstractUser> {
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

    public void encryptPassword() {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(this.password.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            this.password = hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            System.out.println(new NoSuchAlgorithmException(e));
        }


    }

    public String getPass() {
        return this.password;
    }
}
