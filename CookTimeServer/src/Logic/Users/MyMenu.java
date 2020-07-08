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
     * Method for getting the ordered list of local recipes, based on the qualifications uses quick sort
     *
     * @return ordered SimpleLinked list, with recipes orderd, from best rated, to worst.
     */
    public SimpleList<Recipe> highRatedFirst() {
        SimpleList<Recipe> tempList = this.ownedRecipes;

        this.quickSort(tempList.getHead(), tempList.getTail());
        return tempList;

    }

    //auxiliary method for quick sort
    private Node<Recipe> quicksortAux(Node<Recipe> start, Node<Recipe> end) {
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
            //compares rating
            if (start.getData().getRating() > pivot.getRating()) {
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

    //main quick sort method
    private void quickSort(Node<Recipe> start, Node<Recipe> end) {
        if (start == end)
            return;

        // split list and partion recurse
        Node<Recipe> pivotPrev = quicksortAux(start, end);
        quickSort(start, pivotPrev);

        // if pivot is picked and moved to the start,
        // that means start and pivot is same
        // so pick from next of pivot
        if (pivotPrev != null &&
                pivotPrev == start)
            quickSort(pivotPrev.getNext(), end);

            // if pivot is in between of the list,
            // start from next of pivot,
            // since we have pivotPrev, so we move two nodes
        else if (pivotPrev != null &&
                pivotPrev.getNext() != null)
            quickSort(pivotPrev.getNext().getNext(), end);
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
