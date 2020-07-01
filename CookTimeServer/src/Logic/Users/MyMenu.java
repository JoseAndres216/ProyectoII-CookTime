package Logic.Users;

import Logic.DataStructures.SimpleList.SimpleList;
import Logic.ServerManager;
import com.google.gson.Gson;

import static Logic.ServerManager.RECIPE_TYPE;

public class MyMenu {
    protected SimpleList<Recipe> ownedRecipes;

    public void addRecipe(String jsonRecipe) {
        //create the object
        Recipe newRecipe = new Gson().fromJson(jsonRecipe, RECIPE_TYPE);
        //add recipe to the global repository
        ServerManager.getInstance().addRecipe(newRecipe);
        //add ther recipe to the local repository
        if (this.ownedRecipes == null) {
            this.ownedRecipes = new SimpleList<>();
        }
        this.ownedRecipes.append(newRecipe);
    }

    public SimpleList<Recipe> recientFirst() {
        return null;
    }

    public SimpleList<Recipe> highRatedFirst() {
        return null;
    }

    public SimpleList<Recipe> difficulty() {
        return null;
    }
}
