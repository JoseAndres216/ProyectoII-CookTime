package logic.users;

import logic.structures.simplelist.Node;
import logic.structures.simplelist.SimpleList;
import logic.ServerManager;
import com.google.gson.Gson;

import static logic.ServerManager.RECIPE_TYPE;

public class MyMenu {

    protected SimpleList<Recipe> ownedRecipes;

    /**
     * Method for adding a recipe in the user's my menu
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
    public SimpleList<Recipe> recientFirst() {
        return this.ownedRecipes;
    }


    /**
     * Method for getting the recipes list ordered using radix sort
     *
     * @return simple linked list
     */
    public SimpleList<Recipe> difficulty() {
        return null;
    }
}
