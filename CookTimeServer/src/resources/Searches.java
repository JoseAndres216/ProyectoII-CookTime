package resources;

import logic.ServerManager;

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
     * Method for searching users on the server for users
     *
     * @param key String key to be compared with the users name, using regular expressions
     * @return Simple list in json format, with possible results.
     */
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public String searchUsers(@QueryParam("key") String key) {
        // Return some cliched textual content
        return ServerManager.getInstance().searchSubject(key, true);
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
}