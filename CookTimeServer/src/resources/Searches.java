package resources;

import logic.ServerManager;
import logic.utilities.Searcher;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
/*
 * Esta clase es para las busquedas en el servidor, cada metodo retorna como maximo una lista con
 * 15 resultados
 */
public class Searches {
    /**
     * Method for searching recipes on the server for recipes
     *
     * @param key String key to be compared with the recipes name, using regular expressions
     * @return Simple list in json format, with possible results.
     */
    @GET
    @Path("/recipes")
    @Produces(MediaType.APPLICATION_JSON)
    public String searchRecipes(@QueryParam("key") String key) {
        // Return some cliched textual content
        return ServerManager.getInstance().searchRecipes(key);
    }

    /**
     * Method for sending random recipes requests
     *
     * @return json format string with recipes' data.
     */
    @GET
    @Path("/recipes/random")
    @Produces(MediaType.APPLICATION_JSON)
    public String randomRecipes() {
        return Searcher.recipesRandomSuggests();
    }

    /**
     * Method for sending best rated recipes requests
     *
     * @return json format string with recipes' data.
     */
    @GET
    @Path("/recipes/ranked")
    @Produces(MediaType.APPLICATION_JSON)
    public String ratedRecipes() {
        return Searcher.recipesRatedSuggests();
    }

    /**
     * Method for searching users on the server for users
     *
     * @param key String key to be compared with the users name, using regular expressions
     * @return Simple list in json format, with possible results.
     */
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public String searchUsers(@QueryParam("key") String key) {
        return ServerManager.getInstance().searchSubject(key, true);
    }

    /**
     * Method for sending random users requests
     *
     * @return json format string with recipes' data.
     */
    @GET
    @Path("/users/random")
    @Produces(MediaType.APPLICATION_JSON)
    public String randomUsers() {
        return Searcher.randomSuggestUsers();
    }

    /**
     * Method for sending best rated users requests
     *
     * @return json format string with recipes' data.
     */
    @GET
    @Path("/users/ranked")
    @Produces(MediaType.APPLICATION_JSON)
    public String ratedUsers() {
        return Searcher.sugestRatedUsers();
    }

    /**
     * Method for searching users on the server for enterprises
     *
     * @param key String key to be compared with the enterprise name, using regular expressions
     * @return Simple list in json format, with possible results.
     */
    @GET
    @Path("/enterprises")
    @Produces(MediaType.APPLICATION_JSON)
    public String searchEnterprises(@QueryParam("key") String key) {
        // Return some cliched textual content
        return ServerManager.getInstance().searchSubject(key, false);
    }

    /**
     * Method for sending random enterprises requests
     *
     * @return json format string with recipes' data.
     */
    @GET
    @Path("/enterprises/random")
    @Produces(MediaType.APPLICATION_JSON)
    public String randomEnterprises() {
        return Searcher.randomSuggestEnterprise();
    }

    /**
     * Method for sending best rated enterprises requests
     *
     * @return json format string with recipes' data.
     */
    @GET
    @Path("/users/ranked")
    @Produces(MediaType.APPLICATION_JSON)
    public String ratedEnterprises() {
        return Searcher.sugestRatedEnterprises();
    }
}