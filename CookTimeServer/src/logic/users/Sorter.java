package logic.users;

import logic.structures.simplelist.Node;
import logic.structures.simplelist.SimpleList;

/**
 * This interface its for sorting the data collections for the cookTime server
 */
public abstract class Sorter {


    // Auxiliar method for quick sort
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

    //main quick sort method
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
     * @param source simple linked list with recipes
     * @return source, sorted from best rated, to worst rated
     */
    public SimpleList<Recipe> highRated(SimpleList<Recipe> source) {

        quickSort(source.getHead(), source.getTail());
        return source;

    }
}
