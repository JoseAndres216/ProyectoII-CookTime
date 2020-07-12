package logic.utilities;

import com.google.gson.Gson;
import logic.structures.simplelist.SimpleList;
import logic.structures.TreeNode;
import logic.ServerManager;
import logic.users.AbstractUser;
import logic.users.Recipe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Class for the search management in the data structures.
 */
public abstract class Searcher {
    private Searcher() {
    }

    static Gson gson = new Gson();
    //cantidad de elementos en la lista de resultados para agregar a la lista de matches
    static final int MAX_RESULTS = 15;

    /**
     * Method for getting matches, using a key and the recipes tree, the search works
     * with regular expressions.
     *
     * @param key the key is given by the user, its compared with the recipes name.
     * @return list with all possible matches.
     */
    public static String findRecipes(String key) {
        TreeNode<Recipe> root = ServerManager.getInstance().getGlobalRecipes().getRoot();

        return gson.toJson(findRecipesAux(root, MAX_RESULTS, key), ServerManager.RECIPES_LIST_TYPE);
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
     * with regular expressions.
     *
     * @param key    the key is given by the user, its compared with the recipes name.
     * @param isUser boolean to say if the search is in the users tree (true), or in the
     *               enterprises tree (false)
     * @return list with all possible matches.
     */
    public static String findUsers(String key, boolean isUser) {
        TreeNode<AbstractUser> root;
        if (isUser) {
            root = ServerManager.getInstance().getUsers().getRoot();
        } else {
            root = ServerManager.getInstance().getEnterprises().getRoot();
        }
        return gson.toJson(findUsersAux(root, MAX_RESULTS, key), ServerManager.RECIPES_LIST_TYPE);


    }

    /**
     * Aux method for findRecipes(), uses iteration for searching on the tree, level by level.
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
                    results.append(node.getData().getEmail());
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


