package logic.users;

import com.google.gson.Gson;
import logic.ServerManager;
import logic.structures.simplelist.SimpleList;
import logic.utilities.Sorter;

import java.io.Serializable;

import static logic.ServerSettings.RECIPES_LIST_TYPE;
import static logic.ServerSettings.RECIPE_TYPE;


public class MyMenu implements Serializable {

    protected SimpleList<Recipe> ownedRecipes;


    /**
     * Method for adding a recipe in the user's my menu
     *
     * @param jsonRecipe recipe in json format
     */
    public void addRecipe(String jsonRecipe) {
        //create the object
        Recipe newRecipe = new Gson().fromJson(jsonRecipe, RECIPE_TYPE);
        //add recipe to the global repository
        ServerManager.getInstance().addRecipe(newRecipe);
        //add the recipe to the local repository
        if (this.ownedRecipes == null) {
            this.ownedRecipes = new SimpleList<>();
        }
        this.ownedRecipes.addFirst(newRecipe);
    }

    /**
     * Method for getting the recipes list ordered from newer to older
     * ** no le implementé el sort porque la lista ya se crea con ese orden desde el inicio
     *
     * @return simple list of recipes.
     */
    public String byRecentFirst() {
        return new Gson().toJson(this.ownedRecipes, RECIPES_LIST_TYPE);
    }

    /**
     * Method for getting the my menu ordered by difficulty, uses the sorter tool
     *
     * @return simple list with the recipes, ordered by difficult.
     */
    public String byDifficulty() {
        SimpleList<Recipe> temp = this.ownedRecipes;
        return new Gson().toJson(Sorter.bydifficulty(temp), RECIPES_LIST_TYPE);

    }

    /**
     * Method for getting the my menu list ordered by rating, uses the sorter tool
     *
     * @return simple list with recipes ordered by rating.
     */
    public String byRating() {
        SimpleList<Recipe> temp = this.ownedRecipes;
        return new Gson().toJson(Sorter.byhighRated(temp), RECIPES_LIST_TYPE);

    }


}
