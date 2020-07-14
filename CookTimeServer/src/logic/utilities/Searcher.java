package logic.utilities;

import com.google.gson.Gson;
import logic.ServerManager;
import logic.structures.TreeNode;
import logic.structures.simplelist.Node;
import logic.structures.simplelist.SimpleList;
import logic.users.AbstractUser;
import logic.users.Recipe;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import static logic.ServerSettings.MAX_RESULTS;
import static logic.ServerSettings.RECIPES_LIST_TYPE;
import static logic.utilities.Sorter.byhighRated;

/**
 * Class for the search management in the data structures.
 */
public interface Searcher {

    Gson gson = new Gson();

    /**
     * Method for getting matches, using a key and the recipes tree, the search works
     * with regular expressions.
     *
     * @param key the key is given by the user, its compared with the recipes name.
     * @return list with all possible matches.
     */
    static String findRecipes(String key) {
        TreeNode<Recipe> root = ServerManager.getInstance().getGlobalRecipes().getRoot();
        return gson.toJson(findRecipesAux(root, MAX_RESULTS, key), RECIPES_LIST_TYPE);
    }

    /**
     * Aux method for findRecipes(), uses iteration for searching on the tree, level by level.
     *
     * @param root    first node of the tree
     * @param counter amount of possible results
     * @param key     string for searching in recipes name
     * @return Simple list with all matches
     */
    static SimpleList<String> findRecipesAux(TreeNode<Recipe> root, int counter, String key) {
        //lista para agregar los matches
        SimpleList<String> results = new SimpleList<>();
        //cola para recorrer el arbol por niveles
        Queue<TreeNode<Recipe>> cola = new LinkedList<>();
        cola.add(root);
        //mientras aun hayan elementos por agregar y el arbol no se haya acabado
        while (counter != 0 && !cola.isEmpty()) {
            TreeNode<Recipe> node = cola.peek();
            if (node != null) {
                // if the actual node matches, adds it to the list, and
                if (node.getData().getName().contains(key)) {
                    results.append(node.getData().getName());
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

    /**
     * Method for getting matches, using a key and the recipes tree, the search works
     * with regular expressions. When finds an chef, adds it to the front fo results, but doesn't care if didn't find
     * all the possible three chefs.
     *
     * @param key    the key is given by the user, its compared with the recipes name.
     * @param isUser boolean to say if the search is in the users tree (true), or in the
     *               enterprises tree (false).
     * @return list with all possible matches.
     */
    static String findUsers(String key, boolean isUser) {
        TreeNode<AbstractUser> root;
        if (isUser) {
            root = ServerManager.getInstance().getUsers().getRoot();
        } else {
            root = ServerManager.getInstance().getEnterprises().getRoot();
        }
        return gson.toJson(findUsersAux(root, MAX_RESULTS, key), RECIPES_LIST_TYPE);


    }

    /**
     * Aux method for findRecipes(), uses iteration for searching on the tree, level by level.
     * The algorithm should find the chefs and add it to the front of the list, but still don't find all chefs
     *
     * @param root    first node of the tree
     * @param counter amount of possible results
     * @param key     string for searching in recipes name
     * @return Simple list with all matches
     */
    static SimpleList<String> findUsersAux(TreeNode<AbstractUser> root, int counter, String key) {
        //lista para agregar los matches
        SimpleList<String> results = new SimpleList<>();
        //cola para recorrer el arbol por niveles
        Queue<TreeNode<AbstractUser>> cola = new LinkedList<>();
        cola.add(root);
        //mientras aun hayan elementos por agregar y el arbol no se haya acabado
        while (counter != 0 && !cola.isEmpty()) {
            TreeNode<AbstractUser> node = cola.peek();
            if (node != null) {
                // if the actual node matches, adds it to the list, and
                if (node.getData().getEmail().contains(key)) {
                    if (node.getData().isChef()) {
                        results.addFirst(node.getData().getEmail());
                        counter--;
                        continue;
                    }
                    results.append(node.getData().getEmail());
                    counter--;
                }
                cola.remove();
                //add the nodes children to que queue to compare on next recursion.
                //doesn't matter if the nodes are null, cause the while will take out all null elements.
                cola.add(node.getLeft());
                cola.add(node.getRight());
            } else {
                cola.remove();
            }
        }
        return results;
    }

    /**
     * Method for getting a recomendation of recipes based on the global ranking
     *
     * @return list with the highest ranked recipes in the server.
     */
    static SimpleList<Recipe> recipesRatedSugests() {
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
    static SimpleList<Recipe> randomSugest() {
        TreeNode<Recipe> root = ServerManager.getInstance().getGlobalRecipes().getRoot();
        //lista para agregar los matches
        int counter = MAX_RESULTS;
        SimpleList<Recipe> results = new SimpleList<>();
        //cola para recorrer el arbol por niveles
        Queue<TreeNode<Recipe>> cola = new LinkedList<>();
        cola.add(root);
        //mientras aun hayan elementos por agregar y el arbol no se haya acabado
        while (counter != 0 && !cola.isEmpty()) {
            int randomInt = (new Random().nextInt(5));
            TreeNode<Recipe> node = cola.peek();
            if (node != null) {
                System.out.println("Recipe analized: " + node.getData());
                System.out.println(randomInt);
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

    /**
     * Method for getting a random recomendations of abstract users, can be both, users or enterprises
     *
     * @param users true if suggest are about users,false if enterprises
     * @return simple list, with the random users/enterprises selected
     */
    static SimpleList<AbstractUser> sugestRandomUsers(boolean users) {
        TreeNode<AbstractUser> root;
        root = ServerManager.getInstance().getUsers().getRoot();
        if (!users) {
            root = ServerManager.getInstance().getEnterprises().getRoot();
        }
        //lista para agregar los matches
        int counter = MAX_RESULTS;
        SimpleList<AbstractUser> results = new SimpleList<>();
        //cola para recorrer el arbol por niveles
        Queue<TreeNode<AbstractUser>> cola = new LinkedList<>();
        cola.add(root);
        //mientras aun hayan elementos por agregar y el arbol no se haya acabado
        while (counter != 0 && !cola.isEmpty()) {
            int randomInt = (new Random().nextInt(5));
            TreeNode<AbstractUser> node = cola.peek();
            if (node != null) {
                System.out.println("User analized: " + node.getData());
                // if the actual node matches, adds it to the list, and
                if (randomInt % 2 == 0) {
                    System.out.println("Users added: " + node.getData());
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

    /**
     * Driver method for searching random users on the server
     *
     * @return list with randomly picked users
     */
    static SimpleList<AbstractUser> randomSuggestUsers() {
        return sugestRandomUsers(true);
    }

    /**
     * Driver method for searching random enterprises on the server
     *
     * @return list with randomly picked enterprises
     */
    static SimpleList<AbstractUser> randomSuggestEnterprise() {
        return sugestRandomUsers(false);
    }

    /**
     * Method for getting suggest based on the ratings of users/enterprises
     *
     * @param user boolean, true if users, false if enterprises
     * @return simple list, with the result.
     */
    static SimpleList<AbstractUser> ratedSubjectSuggest(boolean user) {
        //Select the corresponding list
        SimpleList<AbstractUser> source = ServerManager.getInstance().getUsers().inOrder();
        if (!user) {
            source = ServerManager.getInstance().getEnterprises().inOrder();
        }
        //apply selection sort to the list.
        for (Node<AbstractUser> first = source.getHead(); first.getNext() != null; first = first.getNext()) {
            Node<AbstractUser> smaller = first;
            Node<AbstractUser> temp = smaller.getNext();
            while (temp != null) {
                if (temp.getData().getRating() > smaller.getData().getRating()) {
                    smaller = temp;
                }
                temp = temp.getNext();
            }
            source.swap(first, smaller);
        }
        //return the sorted list.
        return source;

    }

    static SimpleList<AbstractUser> sugestRatedUsers() {
        return Searcher.ratedSubjectSuggest(true);
    }

    static SimpleList<AbstractUser> sugestRatedEnterprises() {
        return Searcher.ratedSubjectSuggest(false);
    }
}


