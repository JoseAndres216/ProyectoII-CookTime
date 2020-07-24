package logic.users;

import logic.ServerManager;
import logic.ServerSettings;
import logic.structures.simplelist.SimpleList;

import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Level;

import static logic.ServerSettings.NOTIFICATION_COMMENTED_MESSAGE;
import static logic.ServerSettings.NOTIFICATION_RATED_MESSAGE;
import static logic.users.AbstractUser.getNotNull;


public class Recipe implements Comparable<Recipe>, Serializable {

    //Atributes of the recipes
    private String name;
    private String author;
    private String type;
    private float duration;
    private int servings;
    private int difficulty;
    private String timing;
    private String tags;
    private String ingredients;
    private int price;
    private float rating;
    private float ratingsTotal;
    private int timesRated;
    private SimpleList<String> comments;
    private int likes;
    private String steps;


    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
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

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public float getRatingsTotal() {
        return ratingsTotal;
    }

    public void setRatingsTotal(float ratingsTotal) {
        this.ratingsTotal = ratingsTotal;
    }

    public int getTimesRated() {
        return timesRated;
    }

    public void setTimesRated(int timesRated) {
        this.timesRated = timesRated;
    }

    public SimpleList<String> getComments() {
        if (comments == null) {
            this.comments = new SimpleList<>();


        }
        return comments;
    }

    public void setComments(SimpleList<String> comments) {
        this.comments = comments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
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
        ServerManager.getInstance().getUser(this.author).addNotification(user.getName() + NOTIFICATION_RATED_MESSAGE + rating);
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
        //this.comments.append(comment);
        ServerManager.getInstance().getUser(this.author).getRecipe(this.name).getComments().append(comment);
        ServerManager.getInstance().getUser(this.author).addNotification(user.getName() + NOTIFICATION_COMMENTED_MESSAGE + this.name);
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
            AbstractUser user1 = ServerManager.getInstance().getUser(this.author);
            AbstractUser user2 = ServerManager.getInstance().getEnterprise(this.author);
            AbstractUser owner = getNotNull(user1, user2);
            user.updateFeed(this);
            owner.addNotification(user.getEmail() + ServerSettings.NOTIFICATION_SHARED_MESSAGE + " " + this.name);
        } catch (Exception e) {
            ServerSettings.RECIPES_LOG.log(Level.WARNING, e.getMessage());
        }
    }
}
