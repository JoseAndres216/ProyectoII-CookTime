package logic.users;

import com.google.gson.Gson;
import logic.structures.simplelist.SimpleList;

import java.util.Objects;

import static logic.ServerManager.*;


public class Recipe implements Comparable<Recipe> {

    //Atributes of the recipes
    private String name;
    private AbstractUser author;
    private String type;
    private float duration;
    private int difficulty;
    private SimpleList<String> tags;
    private int price;
    private float rating;
    private SimpleList<String> comments;


     public float getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AbstractUser getAuthor() {
        return author;
    }

    public void setAuthor(AbstractUser author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public SimpleList<String> getTags() {
        return tags;
    }

    public void setTags(SimpleList<String> tags) {
        this.tags = tags;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Method for given a rate to a recipe
     *
     * @param rating int value of the rating to give
     * @param user   user who gave the rating
     */
    public void rate(int rating, AbstractUser user) {
        this.rating += rating;
        this.author.addNotification(user.name + NOTIFICATION_RATED_MESSAGE + rating);
    }

    /**
     * Method for commentig a recipe
     *
     * @param comment comment to post
     * @param user    user who commented
     */
    public void addComment(String comment, AbstractUser user) {
        this.comments.append(comment);
        this.author.addNotification(user.name + NOTIFICATION_COMMENTED_MESSAGE + this);
    }

    /**
     * Method for sharing the recipe, notifies the owner (adds a notification message that
     * needs to be processed by the mobile client
     *
     * @param user user who shared the recipe
     */
    public void shareRecipe(AbstractUser user) {
        /*
        Agregar la receta compartida al feed de que la compartio
         */
        user.myMenu.addRecipe(new Gson().toJson(this));
        this.author.addNotification(user.name + NOTIFICATION_SHARED_MESSAGE);
    }

    @Override
    public int compareTo(Recipe recipe) {
        return this.name.compareTo(recipe.name);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author, type, duration, difficulty, tags, price, rating, comments);
    }

    @Override
    public String toString() {
        return "Recipes{" +
                "name='" + name + '\'' +
                ", author=" + author +
                '}';
    }
}
