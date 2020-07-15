package resources;

import logic.ServerManager;
import logic.users.AbstractUser;
import logic.users.Recipe;

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
    public Response.ResponseBuilder createUser(@QueryParam("info") String json) {
        try {
            ServerManager.getInstance().createSubject(true, json);
            System.out.println("Created user: " + json);
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
        System.out.println(email + "*<>*" + password);
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
    @Path("/recipe")
    @Produces(MediaType.APPLICATION_JSON)
    public String createRecipe(@QueryParam("recipe") String recipe,
                               @QueryParam("email") String user) {
        AbstractUser userObjct = ServerManager.getInstance().getUser(user);
        if (userObjct == null) {
            return "User not found";
        }
        userObjct.addRecipe(recipe);
        return "Recipe added";
    }

    /**
     * TESTED
     * Method for commenting a recipe on the server
     *
     * @param recipeName the name of the recipe to be commented, as its the unique identifier on the tree
     * @param comment    the message to be commented
     * @param email      the user that commented, for the notification
     * @return true if commented, false if there was an error.
     */
    @POST
    @Path("/recipe/comment")
    public boolean commentRecipe(@QueryParam("recipe") String recipeName,
                                 @QueryParam("comment") String comment,
                                 @QueryParam("email") String email) {
        try {
            Recipe recipe = ServerManager.getInstance().findRecipe(recipeName);
            AbstractUser user = ServerManager.getInstance().getUser(email);
            return ServerManager.getInstance().commentRecipe(recipe, comment, user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Method for getting the MyMenu section, by recient first
     *
     * @param user user for searching the mymenu
     * @return json format of the mymenu list object.
     */
    @GET
    @Path("/mymenu/recientFirst")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMymenuRecient(@QueryParam("email") String user) {
        try {
            return ServerManager.getInstance().getUser(user).myMenuRecients();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Method for getting the MyMenu section,by difficult
     *
     * @param user user for searching the mymenu
     * @return json format of the mymenu object.
     */
    @GET
    @Path("/mymenu/difficulty")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMymenuDiff(@QueryParam("email") String user) {

        try {
            return ServerManager.getInstance().getUser(user).myMenuDifficulty();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Method for getting the MyMenu section, by best rated first.
     *
     * @param user user for searching the mymenu
     * @return json format of the mymenu object.
     */
    @GET
    @Path("/mymenu/rated")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMymenuRated(@QueryParam("email") String user) {
        try {
            return ServerManager.getInstance().getUser(user).myMenuRated();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
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
    @Produces(MediaType.APPLICATION_JSON)
    public String getNotifications(@QueryParam("email") String user) {
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
    @Produces(MediaType.APPLICATION_JSON)
    public String getFollowers(@QueryParam("email") String user) {
        return ServerManager.getInstance().getUser(user).getSerializedFollowers();
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
    public boolean addFollower(@QueryParam("email") String user, @QueryParam("newFollower") String follower) {

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
    public void chefRequest(@QueryParam("email") String user) {
        try {
            ServerManager.getInstance().addChefRequest(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
