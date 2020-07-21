package logic.users;

import logic.ServerManager;
import logic.structures.simplelist.SimpleList;

import java.util.Objects;

import static logic.ServerSettings.NOTIFICATION_COMMENTED_MESSAGE;
import static logic.ServerSettings.NOTIFICATION_RATED_MESSAGE;


public class Recipe implements Comparable<Recipe> {

    //Atributes of the recipes
    private String name;
    private String author;
    private String type;
    private float duration;
    private int difficulty;
    private SimpleList<String> tags;
    private int price;
    private float rating;
    private float ratingsTotal;
    private int timesRated;
    private SimpleList<String> comments;
    private int servings;
    private int likes;


    public float getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Recipe{");
        sb.append("name='").append(name).append('\'');
        sb.append(", rating=").append(rating);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Method for given a rate to a recipe
     *
     * @param rating int value of the rating to give
     * @param user   user who gave the rating
     */
    public void rate(int rating, AbstractUser user) {
        this.timesRated++;
        this.ratingsTotal += rating;
        this.rating = (ratingsTotal / timesRated);
        ServerManager.getInstance().getUser(this.author).addNotification(user.name + NOTIFICATION_RATED_MESSAGE + rating);
    }

    /**
     * method for testing the sorting of recipes
     *
     * @param rating integer of ratings... only from one to five
     */
    public void rate(int rating) {
        this.timesRated++;
        this.ratingsTotal += rating;
        this.rating = (ratingsTotal / timesRated);
    }

    /**
     * Method for commentig a recipe
     *
     * @param comment comment to post
     * @param user    user who commented
     */
    public void addComment(String comment, AbstractUser user) {
        if (this.comments == null) {
            this.comments = new SimpleList<>();
        }
        this.comments.append(comment);
        ServerManager.getInstance().getUser(this.author).addNotification(user.name + NOTIFICATION_COMMENTED_MESSAGE + this.name);
        ServerManager.getInstance().saveInfo();
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

    /**
     * Method for liking a recipe
     *
     * @param user user that liked the recipe
     */
    public void addLike(AbstractUser user) {
        this.likes++;
    }

    /**
     * Method for liking a recipe
     *
     * @param user user that liked the recipe
     */
    public void share(AbstractUser user) {
        try {
            user.updateFeed(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
