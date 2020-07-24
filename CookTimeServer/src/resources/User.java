package resources;

import logic.ServerManager;
import logic.users.AbstractUser;
import logic.users.Recipe;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Resources class for handling request related to a user's profile
 * the user isn't verified because it's already done on the filter
 */
@Path("/user")
public class User {
    final Logger log = Logger.getLogger("UserRsrcLog");

    static AbstractUser getSubject(@QueryParam("newFollower") String follower, @QueryParam("isUser") boolean isUser) {
        AbstractUser newfollower;
        if (isUser) {
            newfollower = ServerManager.getInstance().getUser(follower);
        } else {
            newfollower = ServerManager.getInstance().getEnterprise(follower);
        }
        return newfollower;
    }

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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(@QueryParam("info") String json) {
        try {
            ServerManager.getInstance().createSubject(true, json);
            log.log(Level.WARNING, () -> "HTTP Response sent: " + Response.Status.OK);
            return Response.status(Response.Status.CREATED).build();
        } catch (IllegalArgumentException | NullPointerException e) {
            log.log(Level.WARNING, e.getMessage());
            log.log(Level.WARNING, () -> "HTTP Response sent: " + Response.Status.NOT_ACCEPTABLE);
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    /**
     * Method for getting a user's news feed
     *
     * @param email ID of the user
     * @return Json-format news feed stack
     */
    @GET
    @Path("/feed")
    @Consumes(MediaType.TEXT_PLAIN)
    public String getFeed(@QueryParam("email") String email) {
        String response = null;
        try {
            AbstractUser user = ServerManager.getInstance().getUser(email);
            response = user.getSerializedNewsFeed();
        } catch (Exception e) {
            log.log(Level.WARNING, () -> "Exception thrown in user:" + e.getMessage());
        }
        return response;
    }

    /**
     * TESTED
     * used for the login of the user, the client Will try to get the user info, it'll be verified and
     * if the username and the password matches, then a json with user info will be sent.
     *
     * @param email    user email, this is the unique identifier on the system
     * @param password user password, unencrypted
     * @return Json format string with the user's data, or null if not found.
     * @throws NoSuchAlgorithmException from the encrypted
     */
    @Path("/verify")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String verifyUser(@QueryParam("user") String email,
                             @QueryParam("pass") String password) throws NoSuchAlgorithmException {
        String result;
        log.log(Level.INFO, () -> "Verifying: " + email + " " + password);
        if (ServerManager.getInstance().verifyUser(true, email, password)) {
            result = ServerManager.getInstance().getUserJson(email);

            return result;
        } else {
            log.log(Level.INFO, () -> "Not verified: " + email + " " + password);
            return null;
        }

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

    public Response createRecipe(@QueryParam("recipe") String recipe,
                                                 @QueryParam("user") String user) {
        AbstractUser userObjct = ServerManager.getInstance().getUser(user);
        if (userObjct == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        userObjct.addRecipe(recipe);
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * Method for liking a recipe on the server
     *
     * @param recipeName the name of the recipe to be liked, as its the unique identifier on the tree
     * @param email      the user that liked, for the notification
     * @return true if commented, false if there was an error.
     */
    @POST
    @Path("/recipe/like")
    public Response likeRecipe(@QueryParam("recipe") String recipeName,
                                               @QueryParam("user") String email) {
        try {
            Recipe recipe = ServerManager.getInstance().findRecipe(recipeName);
            AbstractUser user = ServerManager.getInstance().getUser(email);
            if (ServerManager.getInstance().likeRecipe(recipe, user)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    /**
     * Method for liking a recipe on the server
     *
     * @param recipeName the name of the recipe to be liked, as its the unique identifier on the tree
     * @param email      the user that liked, for the notification
     * @return true if commented, false if there was an error.
     */
    @POST
    @Path("/recipe/share")
    public Response shareRecipe(@QueryParam("recipe") String recipeName,
                                                @QueryParam("user") String email) {
        try {
            Recipe recipe = ServerManager.getInstance().findRecipe(recipeName);
            AbstractUser user = ServerManager.getInstance().getUser(email);
            if (ServerManager.getInstance().shareRecipe(recipe, user)) {
                log.log(Level.INFO, () -> "Recipe not found: " + recipeName);
                log.log(Level.INFO, () -> "Http code sent " + 404);
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            log.log(Level.INFO, () -> "Recipe : " + recipeName);
            log.log(Level.INFO, () -> "Recipe shared by: " + email);

            return Response.status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    /**
     * Method for liking a recipe on the server
     *
     * @param recipeName the name of the recipe to be rated, as its the unique identifier on the tree
     * @param email      the user that rated, for the notification
     * @return true if rated, false if there was an error.
     */
    @POST
    @Path("/recipe/rate")
    public Response rateRecipe(@QueryParam("recipe") String recipeName,
                                               @QueryParam("user") String email,
                                               @QueryParam("rating") int rating) {
        try {
            Recipe recipe = ServerManager.getInstance().findRecipe(recipeName);
            AbstractUser user = ServerManager.getInstance().getUser(email);
            if (ServerManager.getInstance().rate(recipe, user, rating)) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return Response.status(Response.Status.CONFLICT).build();
        }
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
    public Response commentRecipe(@QueryParam("recipe") String recipeName,
                                                  @QueryParam("comment") String comment,
                                                  @QueryParam("user") String email) {
        try {
            Recipe recipe = ServerManager.getInstance().findRecipe(recipeName);
            AbstractUser user = ServerManager.getInstance().getUser(email);
            if (ServerManager.getInstance().commentRecipe(recipe, comment, user)) {
                log.log(Level.INFO, "Recipe not found");
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            log.log(Level.INFO, ()->"Recipe commented: " + recipeName + "by: " + email);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            log.log(Level.SEVERE, ()->"Not commented: " + recipeName + "by: " + email);
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    /**
     * Method for getting the MyMenu section, by recient first
     *
     * @param user user for searching the mymenu
     * @return json format of the mymenu list object.
     */
    @GET
    @Path("/menu/recent")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMymenuRecient(@QueryParam("user") String user) {
        try {
            return ServerManager.getInstance().getUser(user).myMenuRecients();

        } catch (Exception e) {
            log.log(Level.WARNING, e.getMessage());
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
    @Path("/menu/difficulty")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMymenuDiff(@QueryParam("user") String user) {

        try {
            return ServerManager.getInstance().getUser(user).myMenuDifficulty();

        } catch (Exception e) {
            log.log(Level.WARNING, e.getMessage());
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
    @Path("/menu/ranked")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMymenuRated(@QueryParam("user") String user) {
        try {
            return ServerManager.getInstance().getUser(user).myMenuRated();
        } catch (Exception e) {
            log.log(Level.WARNING, e.getMessage());
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
    @Produces(MediaType.APPLICATION_JSON)
    public String getFollowers(@QueryParam("user") String user) {
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
    public Response addFollower(@QueryParam("email") String user, @QueryParam("newFollower") String follower, @QueryParam("isUser") boolean isUser) {

        try {
            AbstractUser newfollower;
            AbstractUser followed = ServerManager.getInstance().getUser(user);
            newfollower = getSubject(follower, isUser);
            if (newfollower != null && followed != null) {
                followed.addFollower(newfollower);
                return Response.status(Response.Status.CREATED).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            log.log(Level.WARNING, e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Method for handling requests to be chef
     *
     * @param user user email
     */
    @PUT
    @Path("/request/chef")
    public void chefRequest(@QueryParam("user") String user) {
        try {
            ServerManager.getInstance().addChefRequest(user);
        } catch (Exception e) {
            log.log(Level.WARNING, e.getMessage());
        }
    }
}
