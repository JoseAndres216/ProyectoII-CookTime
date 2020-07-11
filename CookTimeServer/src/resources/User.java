package resources;

import logic.ServerManager;
import logic.users.AbstractUser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;

/**
 * Resources class for handling request related to a user's profile
 * the user isn't verified because it's already done on the filter
 */
@Path("/user")
public class User {
    /**
     * TESTED
     * Method for adding a user on the server, the verification of the existence of the server
     * happens at logic level on the server.
     *
     * @param json json format object with all the users propertys
     * @return server http response code
     */
    @PUT
    @Path("/create")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response.ResponseBuilder createUser(@QueryParam("info") String json) throws NoSuchAlgorithmException {
        try {
            ServerManager.getInstance().createSubject(true, json);
            return Response.status(Response.Status.CREATED);

        } catch (NoSuchAlgorithmException | IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE);
        }
    }

    /**
     * TESTED
     * used for the login of the user, the client Will try to get the user info, it'll be verified and
     * if the username and the password matches, then a json with user info will be sent.
     *
     * @param email    user email, this is the unique identifier on the system
     * @param password user password, unencrypted
     * @return Json format string with the user's data, or null if not found.
     * @throws NoSuchAlgorithmException from the encrypter
     */
    @Path("/verify")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String verifyUser(@QueryParam("email") String email,
                             @QueryParam("pwrd") String password) throws NoSuchAlgorithmException {
        String result = null;
        System.out.println(email + "**" + password);
        if (ServerManager.getInstance().verifyUser(true, email, password)) {
            result = ServerManager.getInstance().getUserJson(email);
            System.out.println(result);
        } else {
            System.out.println("no se pudo verificar al usuario");
        }
        return result;
    }

    /**
     * TESTED, ERROR NULL ON AVL TREE
     * For adding a recipe on the server, must come on json format
     * and the user email for adding the recipe, wont come verified, cause it already verified on the
     * filter
     *
     * @param recipe json-format recipe
     * @param user   user's email
     * @return message
     */
    @PUT
    @Path("/create/recipe")
    public String createRecipe(@QueryParam("recipe") String recipe,
                               @QueryParam("user") String user) {
        AbstractUser userObjct = ServerManager.getInstance().getUser(user);
        if (userObjct == null) {
            return "User not found";
        }
        userObjct.addRecipe(recipe);
        return "Recipe added";
    }

    /**TESTED
     * Method for commenting a recipe on the server
     *
     * @param recipeName the name of the recipe to be commented, as its the unique identifier on the tree
     * @param comment    the message to be commented
     * @param email      the user that commented, for the notification
     * @return true if commented, false if there was an error.
     */
    @POST
    @Path("/comment/recipe")
    public boolean commentRecipe(@QueryParam("recipe") String recipeName,
                                 @QueryParam("comment") String comment,
                                 @QueryParam("user") String email) {
        return ServerManager.getInstance().commentRecipe(recipeName, comment, email, true);
         /*
        create method for getting a recipe on the server and comment it, the method on class Recipe its done,
        missing the access from serverManager
         */
    }


    /**
     * Method for getting the MyMenu section, by recient first
     *
     * @param user user for searching the mymenu
     * @return json format of the mymenu list object.
     */
    @GET
    @Path("/mymenu/recientFirst")
    public String getMymenuRecient(@QueryParam("user") String user) {
         /*
         make method for getting serialized my menu lists
          */
        return null;
    }

    /**
     * Method for getting the MyMenu section,by difficult
     *
     * @param user user for searching the mymenu
     * @return json format of the mymenu object.
     */
    @GET
    @Path("/mymenu/difficulty")
    public String getMymenuDiff(@QueryParam("user") String user) {
        /*
         make method for getting serialized my menu lists
         */
        return null;
    }

    /**
     * Method for getting the MyMenu section, by best rated first.
     *
     * @param user user for searching the mymenu
     * @return json format of the mymenu object.
     */
    @GET
    @Path("/mymenu/rated")
    public String getMymenuRated(@QueryParam("user") String user) {
        /*
        Getter for the mymenu

         */
        return null;
    }

    /**
     * TESTED
     * Method for getting the notifications stack, ideal for notifying the user of iteractions with it's profile
     *
     * @param user user that owns the notifications stack
     * @return Stack of notifications on json format
     */
    @GET
    @Path("/notifications")
    public String getNotifications(@QueryParam("user") String user) {
        if (ServerManager.getInstance().getUser(user) == null) {
            return null;
        }
        return ServerManager.getInstance().getUser(user).getSerializedNotifications();
    }

    /**
     * TESTED
     * Method for getting the user's followers list
     *
     * @param user user email
     * @return json format of the simple linked list with the users
     */
    @GET
    @Path("/followers")
    public String getFollowers(@QueryParam("user") String user) {

        return ServerManager.getInstance().getUser(user).getSerializedFollowers();

        /*
        method for getting the followers of an user.
         */

    }

    /**
     * TESTED
     * Method for getting the user's followers list
     *
     * @param user user email
     * @return message of status of request
     */
    @POST
    @Path("/followers")
    public boolean addFollower(@QueryParam("user") String user, @QueryParam("newFollower") String follower) {

        try {
            AbstractUser newfollower = ServerManager.getInstance().getUser(follower);
            AbstractUser followed = ServerManager.getInstance().getUser(user);

            System.out.println("New follower" + newfollower);
            System.out.println("Followed" + followed);

            if (newfollower != null && followed != null) {
                followed.addFollower(newfollower);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        /*
        method for getting the followers of an user.
         */

    }

    /**
     * Method for handling requests to be chef
     *
     * @param user user email
     */
    @PUT
    @Path("/request/chef")
    public void chefRequest(@QueryParam("user") String user) {
        /*
        hacer un stack de usuarios que hicieron request para ponerlo en la gui.
        ServerManager.getInstance().chefR(user)
         */
    }
}
