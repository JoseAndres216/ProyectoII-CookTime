package Logic.Users;

import Logic.DataStructures.SimpleList.Node;
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

        if (this.ownedRecipes == null || this.ownedRecipes.len() == 0) {
            System.out.println("List cant be null or empty");
        }
        for (Node<Recipe> i = this.ownedRecipes.getHead(); i != null; i = i.getNext()) {
            for (Node<Recipe> j = this.ownedRecipes.getHead(); j != null; j = j.getNext()) {
                if (i.getData().compareTo(j.getData()) < 0) {
                    this.ownedRecipes.swap(i, j);
                }
            }
        }
        return this.ownedRecipes;
    }


    public SimpleList<Recipe> highRatedFirst() {
        this.sort(this.ownedRecipes.getHead(), this.ownedRecipes.getTail());
        return this.ownedRecipes;
    }

    // takes first and last node
    Node<Recipe> paritionLast(Node<Recipe> start, Node<Recipe> end) {
        if (start == end ||
                start == null || end == null)
            return start;

        Node<Recipe> pivotPrev = start;
        Node<Recipe> curr = start;
        Recipe pivot = end.getData();

        // iterate till one before the end,
        // no need to iterate till the end
        // because end is pivot
        while (start != end) {
            if (start.getData().compareTo(pivot) < 0) {
                // keep tracks of last modified item
                pivotPrev = curr;
                Recipe temp = curr.getData();
                curr.setData(start.getData());
                start.setData(temp);
                curr = curr.getNext();
            }
            start = start.getNext();
        }

        // swap the position of curr i.e.
        // next suitable index and pivot
        Recipe temp = curr.getData();
        curr.setData(pivot);
        end.setData(temp);

        // return one previous to current
        // because current is now pointing to pivot
        return pivotPrev;
    }

    void sort(Node<Recipe> start, Node<Recipe> end) {
        if (start == end)
            return;

        // split list and partion recurse
        Node<Recipe> pivotPrev = paritionLast(start, end);
        sort(start, pivotPrev);

        // if pivot is picked and moved to the start,
        // that means start and pivot is same
        // so pick from next of pivot
        if (pivotPrev != null &&
                pivotPrev == start)
            sort(pivotPrev.getNext(), end);

            // if pivot is in between of the list,
            // start from next of pivot,
            // since we have pivotPrev, so we move two nodes
        else if (pivotPrev != null &&
                pivotPrev.getNext() != null)
            sort(pivotPrev.getNext().getNext(), end);
    }

    public SimpleList<Recipe> difficulty() {
        return null;
    }
}
