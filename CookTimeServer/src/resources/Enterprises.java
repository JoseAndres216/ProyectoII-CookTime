package resources;

import logic.ServerManager;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;

/**
 * Resources class for handling request related to a user's profile
 * the user isn't verified because it's already done on the filter
 */
@Path("/enterprise")
public class Enterprises {
    /**
     * For adding a recipe on the server, must come on json format
     * and the user email for adding the recipe, wont come verified, cause it already verified on the
     * filter
     *
     * @param recipe json-format recipe
     * @param user   enterprise's email
     */
    @PUT
    @Path("/create/recipe")
    public void createRecipe(@QueryParam("recipe") String recipe,
                             @QueryParam("user") String user) {
        ServerManager.getInstance().getEnterprise(user).addRecipe(recipe);
    }

    /**
     * Method for commenting a recipe on the server
     *
     * @param recipeName the name of the recipe to be commented, as its the unique identifier on the tree
     * @param comment    the message to be commented
     * @param email      the user that commented, for the notification
     */
    @POST
    @Path("/comment/recipe")
    public void commentRecipe(@QueryParam("recipe") String recipeName,
                              @QueryParam("comment") String comment,
                              @QueryParam("user") String email) {
        /*
        create method for getting a recipe on the server and comment it, the method on class Recipe its done,
        missing the access from serverManager
         */
    }

    /**
     * Method for delete a recipe
     *
     * @param recipe recipe's name, should be searched on the tree and deleted
     *               and also on the users' feed.
     */
    @DELETE
    @Path("/delete/recipe")
    public void deleteRecipe(@QueryParam("recipe") String recipe) {
        /*
        Implement the method deleteRecipe on the serverManager,
        must delete the recipe on the user's myMenu, the user's feed
        ?????????????????????????????????????????????????????????????
         */
    }

    /**
     * Method for getting the MyMenu section, should be sent ordered or not?
     *
     * @param user user for searching the mymenu
     * @return json format of the mymenu object.
     */
    @GET
    @Path("/mymenu")
    public String getMymenu(@QueryParam("user") String user) {
        /*
        Getter for the mymenu
        should be sorted on server or on client??
         */
        return null;
    }

    /**
     * Method for getting the notifications stack, ideal for notifying the user of iteractions with it's profile
     *
     * @param user user that owns the notifications stack
     * @return Stack of notifications on json format
     */
    @GET
    @Path("/notifications")
    public String getNotifications(@QueryParam("user") String user) {
        return ServerManager.getInstance().getEnterprise(user).getSerializedNotifications();
    }

    /**
     * Method for getting the user's followers list
     *
     * @param user user email
     * @return json format of the simple linked list with the users
     */
    @GET
    @Path("/followers")
    public String getFollowers(@QueryParam("user") String user) {

        return ServerManager.getInstance().getEnterprise(user).getSerializedFollowers();
        /*
        method for getting the followers of an user.
         */
    }

    /**
     * Method for getting the user's followed list
     *
     * @param user user email
     * @return json format of the simple linked list with the users
     */
    @GET
    @Path("/followed")
    public String getFollowed(@QueryParam("user") String user) {
        return ServerManager.getInstance().getEnterprise(user).getSerializedFollowing();
    }
    /**
     * used for the login of the enterprise, the client Will try to get the enterprise info, it'll be verified and
     * if the username and the password matches, then a json with user info will be sent.
     * @param email enterprise email, this is the unique identifier on the system
     * @param password enterprise password, unencrypted
     * @return Json format string with the enterprise's data, or null if not found.
     * @throws NoSuchAlgorithmException from the encrypter
     */
    @Path("/verify")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String verifyEnterprise(@QueryParam("name") String email,
                                   @QueryParam("name") String password) throws NoSuchAlgorithmException {
        String result = null;
        if(ServerManager.getInstance().verifyUser(false,email, password)){
            Response.ok();
            result = ServerManager.getInstance().getUserJson(email);
        }
        else{
            System.out.println("la empresa no existe");
        }
        return  result;
    }

    @PUT
    @Path("/create")
    /**
     * Method for adding a enterprise on the server, the verification of the existence of the server
     * happens at logic level on the server.
     * @param json json format object with all the enterprises propertys
     */
    public void createEnterprise(@QueryParam("info") String json){
                /*
        crear el usuario con el json dado en la clase ServerManager.createEnterprise
         */

    }

}
