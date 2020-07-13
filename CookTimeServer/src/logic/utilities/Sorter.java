package logic.utilities;

import logic.ServerManager;
import logic.structures.TreeNode;
import logic.structures.simplelist.Node;
import logic.structures.simplelist.SimpleList;
import logic.users.Recipe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This interface its for sorting the data collections for the cookTime server
 */
public abstract class Sorter {

    public static final int MAX_DIGITS_RADIX = 2;
    public static final int MAX_RESULTS_SUGGESTS = 18;

    private Sorter() {
    }

    // Auxiliar method for quick sort for recipes, using rating
    private static Node<Recipe> quicksortAux(Node<Recipe> start, Node<Recipe> end) {
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

    //main quick sort method for recipes, using rating
    private static void quickSort(Node<Recipe> start, Node<Recipe> end) {
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
     * Method for sorting a recipes list, based on rating, MUST SEND A TEMPORAL REFERENCE, FOR AVOIDING UNWANTED CHANGES TO LIST
     *
     * @param source simple linked list with recipes
     * @return source, sorted from best rated, to worst rated
     */
    public static SimpleList<Recipe> byhighRated(SimpleList<Recipe> source) {

        quickSort(source.getHead(), source.getTail());
        return source;

    }

    /**
     * Method for getting the recipes list ordered using radix sort
     * MUST SEND A TEMPORAL REFERENCE, FOR AVOIDING UNWANTED CHANGES TO LIST
     *
     * @return simple linked list
     */
    public static SimpleList<Recipe> bydifficulty(SimpleList<Recipe> source) {
        SimpleList<Recipe> temp = source;
        int counter = 1;
        while (counter <= MAX_DIGITS_RADIX) {
            //buckets for the digits
            LinkedList<Queue<Recipe>> buckets = new LinkedList<>();
            Queue<Recipe> zeros = new LinkedList<>();
            Queue<Recipe> ones = new LinkedList<>();
            Queue<Recipe> twos = new LinkedList<>();
            Queue<Recipe> threes = new LinkedList<>();
            Queue<Recipe> fours = new LinkedList<>();
            Queue<Recipe> fives = new LinkedList<>();
            Queue<Recipe> sixes = new LinkedList<>();
            Queue<Recipe> sevens = new LinkedList<>();
            Queue<Recipe> eights = new LinkedList<>();
            Queue<Recipe> nines = new LinkedList<>();

            //add the buckets to the list.
            buckets.add(zeros);
            buckets.add(ones);
            buckets.add(twos);
            buckets.add(threes);
            buckets.add(fours);
            buckets.add(fives);
            buckets.add(sixes);
            buckets.add(sevens);
            buckets.add(eights);
            buckets.add(nines);

            for (int i = 0; i < temp.len(); i++) {
                //for each recipe, sort it based on the last digit of the difficult
                Recipe actual = source.indexElement(i);
                int actualDifficult = actual.getDifficulty();
                int digit = ((int) (actualDifficult % Math.pow(10, counter))) / (int) (Math.pow(10, counter - 1));
                //add the recipes to the corresponding bucket on the buckets list.

                buckets.get(digit).add(actual);
            }
            //empty the results list to fill it with the buckets
            temp = new SimpleList<>();
            //empty the buckets
            for (Queue<Recipe> bucket : buckets) {
                for (Recipe recipe : bucket) {
                    temp.append(recipe);
                }
            }

            //update the counter
            counter++;
        }
        return temp;
    }

    /**
     * Method for ordering the source of recipes, uses selection sort, based on difficulty
     *
     * @param source simple linked source of recipes
     * @return source of recipes, from easiest to hardest.
     */
    public static SimpleList<Recipe> byDuration(SimpleList<Recipe> source) {

        for (Node<Recipe> first = source.getHead(); first.getNext() != null; first = first.getNext()) {
            Node<Recipe> smaller = first;
            Node<Recipe> temp = smaller.getNext();
            while (temp != null) {
                if (temp.getData().getDuration() < smaller.getData().getDuration()) {
                    smaller = temp;
                }
                temp = temp.getNext();
            }
            source.swap(first, smaller);
        }
        return source;
    }

    /**
     * Method for getting a recomendation of recipes based on the global ranking
     *
     * @return list with the highest ranked recipes in the server.
     */
    public static SimpleList<Recipe> recipesRatedSugests() {
        SimpleList<Recipe> globalRecipes = ServerManager.getInstance().getGlobalRecipes().inOrder();
        return byhighRated(globalRecipes);
        /*
        convertir el arbol de recetas a in order, y luego ordenar la lista con byhighRated
        enviar la lista de recetas calificadas.
         */

    }

    /**
     * Method for getting random recipes suggests
     *
     * @return list with random recipes
     */
    public static SimpleList<Recipe> randomSugest() {

        return randomRecipesAux(ServerManager.getInstance().getGlobalRecipes().getRoot());
    }

    /**
     * Aux method for randomSugest(), uses iteration for getting random recipes from the recipes tree
     *
     * @param root first node of the tree
     * @return Simple list with all random recipes
     */
    private static SimpleList<Recipe> randomRecipesAux(TreeNode<Recipe> root) {
        //lista para agregar los matches
        int counter = MAX_RESULTS_SUGGESTS;
        SimpleList<Recipe> results = new SimpleList<>();
        //cola para recorrer el arbol por niveles
        Queue<TreeNode<Recipe>> cola = new LinkedList<>();
        cola.add(root);
        //mientras aun hayan elementos por agregar y el arbol no se haya acabado
        while (counter != 0 && !cola.isEmpty()) {
            double randomInt = Math.random();
            TreeNode<Recipe> node = cola.peek();
            System.out.println("Recipe analized: " + node.getData());
            if (node != null) {
                // if the actual node matches, adds it to the list, and
                if (randomInt % 2 == 0) {
                    System.out.println("Recipe added: " + node.getData());
                    results.append(node.getData());
                    counter--;
                }
                cola.remove();
                //add the nodes children to que queue to compare on next recursion.
                if (node.getLeft() != null) {
                    cola.add(node.getLeft());
                }
                if (node.getRight() != null) {
                    cola.add(node.getRight());
                }
            }
        }
        return results;
    }
}
