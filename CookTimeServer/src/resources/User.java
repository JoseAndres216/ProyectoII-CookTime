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
    public Response.ResponseBuilder createUser(@QueryParam("info") String json) {
        try {
            ServerManager.getInstance().createSubject(true, json);
            return Response.status(Response.Status.CREATED);
        } catch (NoSuchAlgorithmException | IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
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
     * @throws NoSuchAlgorithmException from the encrypted
     */
    @Path("/verify")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String verifyUser(@QueryParam("user") String email,
                             @QueryParam("pass") String password) throws NoSuchAlgorithmException {
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

    public Response.ResponseBuilder createRecipe(@QueryParam("recipe") String recipe,
                                                 @QueryParam("user") String user) {
        AbstractUser userObjct = ServerManager.getInstance().getUser(user);
        if (userObjct == null) {
            return Response.status(Response.Status.NOT_FOUND);
        }
        userObjct.addRecipe(recipe);
        return Response.status(Response.Status.CREATED);
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
    public Response.ResponseBuilder likeRecipe(@QueryParam("recipe") String recipeName,
                                               @QueryParam("user") String email) {
        try {
            Recipe recipe = ServerManager.getInstance().findRecipe(recipeName);
            AbstractUser user = ServerManager.getInstance().getUser(email);
            if (!ServerManager.getInstance().likeRecipe(recipe, user)) {
                return Response.status(Response.Status.NOT_FOUND);
            }
            return Response.status(Response.Status.ACCEPTED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.CONFLICT);
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
    public Response.ResponseBuilder shareRecipe(@QueryParam("recipe") String recipeName,
                                                @QueryParam("user") String email) {
        try {
            Recipe recipe = ServerManager.getInstance().findRecipe(recipeName);
            AbstractUser user = ServerManager.getInstance().getUser(email);
            if (!ServerManager.getInstance().shareRecipe(recipe, user)) {
                return Response.status(Response.Status.NOT_FOUND);
            }
            return Response.status(Response.Status.ACCEPTED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.CONFLICT);
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
    public Response.ResponseBuilder rateRecipe(@QueryParam("recipe") String recipeName,
                                               @QueryParam("user") String email,
                                               @QueryParam("rating") int rating) {
        try {
            Recipe recipe = ServerManager.getInstance().findRecipe(recipeName);
            AbstractUser user = ServerManager.getInstance().getUser(email);
            if (!ServerManager.getInstance().rate(recipe, user, rating)) {
                return Response.status(Response.Status.NOT_FOUND);
            }
            return Response.status(Response.Status.ACCEPTED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.CONFLICT);
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
    public Response.ResponseBuilder commentRecipe(@QueryParam("recipe") String recipeName,
                                                  @QueryParam("comment") String comment,
                                                  @QueryParam("user") String email) {
        try {
            Recipe recipe = ServerManager.getInstance().findRecipe(recipeName);
            AbstractUser user = ServerManager.getInstance().getUser(email);
            if (!ServerManager.getInstance().commentRecipe(recipe, comment, user)) {
                return Response.status(Response.Status.NOT_FOUND);
            }
            return Response.status(Response.Status.ACCEPTED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.CONFLICT);
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
    @Path("/menu/difficulty")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMymenuDiff(@QueryParam("user") String user) {

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
    @Path("/menu/ranked")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMymenuRated(@QueryParam("user") String user) {
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
    public Response.ResponseBuilder addFollower(@QueryParam("user") String user, @QueryParam("newFollower") String follower, @QueryParam("isUser") boolean isUser) {

        try {
            AbstractUser newfollower;
            AbstractUser followed = ServerManager.getInstance().getUser(user);
            newfollower = getSubject(follower, isUser);
            if (newfollower != null && followed != null) {
                followed.addFollower(newfollower);
                return Response.status(Response.Status.OK);

            }
            return Response.status(Response.Status.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR);
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
        try {
            ServerManager.getInstance().addChefRequest(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
