import Logic.DataStructures.SimpleList.SimpleList;
import Logic.FileManagement.JsonLoader;
import Logic.FileManagement.JsonWriter;
import Logic.ServerManager;
import Logic.Users.Enterprise;
import Logic.Users.Recipe;
import Logic.Users.User;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

public class ServerManagerTest {
    public static final String PASSWORD = "password";
    ServerManager server = ServerManager.getInstance();

    //test for searching users that are avaliable on the tree
    @Test
    public void testFindAvaliableUsers() {
        final User TEST_USER1 = new User("EDUARDO", PASSWORD);
        final User TEST_USER2 = new User("JUAN", PASSWORD);
        final User TEST_USER3 = new User("CARLOS", PASSWORD);
        final User TEST_USER4 = new User("VICTOR", PASSWORD);
        final User TEST_USER5 = new User("MANUEL", PASSWORD);
        try {

            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER1));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER2));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER3));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER4));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER5));
            JsonWriter.updateUsers();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertTrue(ServerManager.getInstance().existUser(true, TEST_USER1.getEmail()));
        Assert.assertTrue(ServerManager.getInstance().existUser(true, TEST_USER2.getEmail()));
        Assert.assertTrue(ServerManager.getInstance().existUser(true, TEST_USER3.getEmail()));
        Assert.assertTrue(ServerManager.getInstance().existUser(true, TEST_USER4.getEmail()));
        Assert.assertTrue(ServerManager.getInstance().existUser(true, TEST_USER5.getEmail()));
    }

    @Test
    public void testFindAvaliableEnterprises() {
        final Enterprise TEST_ENTERPRISE1 = new Enterprise("MUSSI", PASSWORD);
        final Enterprise TEST_ENTERPRISE2 = new Enterprise("TP IND.", PASSWORD);
        final Enterprise TEST_ENTERPRISE3 = new Enterprise("ASETEC", PASSWORD);
        final Enterprise TEST_ENTERPRISE4 = new Enterprise("SUR", PASSWORD);
        final Enterprise TEST_ENTERPRISE5 = new Enterprise("KK", PASSWORD);
        try {
            final String JsonTest = new Gson().toJson(TEST_ENTERPRISE1, Enterprise.class);
            final String JsonTest2 = new Gson().toJson(TEST_ENTERPRISE2, Enterprise.class);
            final String JsonTest3 = new Gson().toJson(TEST_ENTERPRISE3, Enterprise.class);
            final String JsonTest4 = new Gson().toJson(TEST_ENTERPRISE4, Enterprise.class);
            final String JsonTest5 = new Gson().toJson(TEST_ENTERPRISE5, Enterprise.class);
            ServerManager.getInstance().createSubject(false, JsonTest);
            ServerManager.getInstance().createSubject(false, JsonTest2);
            ServerManager.getInstance().createSubject(false, JsonTest3);
            ServerManager.getInstance().createSubject(false, JsonTest4);
            ServerManager.getInstance().createSubject(false, JsonTest5);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void addEnterprises() {
        final Enterprise TEST_ENTERPRISE6 = new Enterprise("MUSSI", PASSWORD);
        final Enterprise TEST_ENTERPRISE7 = new Enterprise("GROOVES.", PASSWORD);
        final Enterprise TEST_ENTERPRISE8 = new Enterprise("BALLAS", PASSWORD);
        final Enterprise TEST_ENTERPRISE9 = new Enterprise("MARA", PASSWORD);
        final Enterprise TEST_ENTERPRISE10 = new Enterprise("KKK", PASSWORD);
        try {

            final String JsonTest = new Gson().toJson(TEST_ENTERPRISE6, Enterprise.class);
            final String JsonTest2 = new Gson().toJson(TEST_ENTERPRISE7, Enterprise.class);
            final String JsonTest3 = new Gson().toJson(TEST_ENTERPRISE8, Enterprise.class);
            final String JsonTest4 = new Gson().toJson(TEST_ENTERPRISE9, Enterprise.class);
            final String JsonTest5 = new Gson().toJson(TEST_ENTERPRISE10, Enterprise.class);
            ServerManager.getInstance().createSubject(false, JsonTest);
            ServerManager.getInstance().createSubject(false, JsonTest2);
            ServerManager.getInstance().createSubject(false, JsonTest3);
            ServerManager.getInstance().createSubject(false, JsonTest4);
            ServerManager.getInstance().createSubject(false, JsonTest5);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertTrue(ServerManager.getInstance().existUser(false, TEST_ENTERPRISE6.getEmail()));
        Assert.assertTrue(ServerManager.getInstance().existUser(false, TEST_ENTERPRISE7.getEmail()));
        Assert.assertTrue(ServerManager.getInstance().existUser(false, TEST_ENTERPRISE8.getEmail()));
        Assert.assertTrue(ServerManager.getInstance().existUser(false, TEST_ENTERPRISE9.getEmail()));
        Assert.assertTrue(ServerManager.getInstance().existUser(false, TEST_ENTERPRISE10.getEmail()));
    }

    @Test
    public void testExistsUsers() {
        final User TEST_USER6 = new User("MAIKOL", PASSWORD);
        final User TEST_USER7 = new User("MESSI", PASSWORD);
        final User TEST_USER8 = new User("ZLATAN", PASSWORD);
        final User TEST_USER9 = new User("CARL", PASSWORD);
        final User TEST_USER10 = new User("JUNIOR", PASSWORD);

        try {

            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER6));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER7));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER8));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER9));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER10));
            JsonWriter.updateUsers();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertEquals(true, ServerManager.getInstance().existUser(true, TEST_USER6.getEmail()));
        Assert.assertEquals(true, ServerManager.getInstance().existUser(true, TEST_USER7.getEmail()));
        Assert.assertEquals(true, ServerManager.getInstance().existUser(true, TEST_USER8.getEmail()));
        Assert.assertEquals(true, ServerManager.getInstance().existUser(true, TEST_USER9.getEmail()));
        Assert.assertEquals(true, ServerManager.getInstance().existUser(true, TEST_USER10.getEmail()));

    }

    @Test
    public void testVerifyPasswordCorrect() throws NoSuchAlgorithmException {
        String password = "Patito29381";
        String encryptedPass = ServerManager.getInstance().encryptPassword(password);
        final User TEST_USER11 = new User("CRISTIAN", password);

        Gson gson = new Gson();
        String testUserString = gson.toJson(TEST_USER11, TEST_USER11.getClass());
        try {

            ServerManager.getInstance().createSubject(true, testUserString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertTrue(ServerManager.getInstance().verifyUser(true, TEST_USER11.getEmail(), password));
    }

    @Test
    public void testVerifyUnexistantUser() throws NoSuchAlgorithmException {
        String password = "Patito29381";
        String encryptedPass = ServerManager.getInstance().encryptPassword(password);
        final User TEST_USER12 = new User("MARCOS", password);

        Gson gson = new Gson();
        String testUserString = gson.toJson(TEST_USER12, TEST_USER12.getClass());
        try {

            ServerManager.getInstance().createSubject(true, testUserString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertFalse(ServerManager.getInstance().verifyUser(true, "UNEXISTANT USER", password));
    }

    @Test
    public void testVerifyPasswordWrong() throws NoSuchAlgorithmException {
        String password = "Patito29381";
        String encryptedPass = ServerManager.getInstance().encryptPassword(password);
        final User TEST_USER13 = new User("CRISTIAN", password);

        Gson gson = new Gson();
        String testUserString = gson.toJson(TEST_USER13, TEST_USER13.getClass());

        try {

            ServerManager.getInstance().createSubject(true, testUserString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertFalse(ServerManager.getInstance().verifyUser(true, TEST_USER13.getEmail(), "WRONG PASSWORD"));
    }

    @Test
    public void testWritingUsers() throws NoSuchAlgorithmException {
        //Gson instance
        Gson jsonConverter = new Gson();
        //Test users for adding to the json files
        String PASSWORD = "testPassword";
        final String TEST_USER1 = jsonConverter.toJson(new User("EDUARDO", PASSWORD), User.class);
        final String TEST_USER2 = jsonConverter.toJson(new User("JUAN", PASSWORD), User.class);
        final String TEST_USER3 = jsonConverter.toJson(new User("CARLOS", PASSWORD), User.class);
        final String TEST_USER4 = jsonConverter.toJson(new User("PEDRO", PASSWORD), User.class);
        final String TEST_USER5 = jsonConverter.toJson(new User("MANUEL", PASSWORD), User.class);
        //add the users to the tree
        ServerManager.getInstance().createSubject(true, TEST_USER1);
        ServerManager.getInstance().createSubject(true, TEST_USER2);
        ServerManager.getInstance().createSubject(true, TEST_USER3);
        ServerManager.getInstance().createSubject(true, TEST_USER4);
        ServerManager.getInstance().createSubject(true, TEST_USER5);

        //write the files
        ServerManager.getInstance().getUsers().print();

        JsonWriter.updateUsers();

        JsonLoader.loadUsers().print();
    }

    @Test
    public void testWritingEnterprises() throws NoSuchAlgorithmException {
        //Gson instance
        Gson jsonConverter = new Gson();
        //Test users for adding to the json files
        String PASSWORD = "testPassword";
        final String TEST_USER1 = jsonConverter.toJson(new Enterprise("A", PASSWORD), Enterprise.class);
        final String TEST_USER2 = jsonConverter.toJson(new Enterprise("B", PASSWORD), Enterprise.class);
        final String TEST_USER3 = jsonConverter.toJson(new Enterprise("E", PASSWORD), Enterprise.class);
        final String TEST_USER4 = jsonConverter.toJson(new Enterprise("ASC", PASSWORD), Enterprise.class);
        final String TEST_USER5 = jsonConverter.toJson(new Enterprise("SF", PASSWORD), Enterprise.class);
        //add the users to the tree
        ServerManager.getInstance().createSubject(false, TEST_USER1);
        ServerManager.getInstance().createSubject(false, TEST_USER2);
        ServerManager.getInstance().createSubject(false, TEST_USER3);
        ServerManager.getInstance().createSubject(false, TEST_USER4);
        ServerManager.getInstance().createSubject(false, TEST_USER5);

        ServerManager.getInstance().getEnterprises().print();
        //write the files
        JsonWriter.updateEnterprises();
        JsonLoader.loadEnterprises().print();
    }

    @Test
    public void addRecipe() {
        //Gson instance
        Gson jsonConverter = new Gson();
        //Test users for adding to the json files
        String PASSWORD = "testPassword";
        final User TEST_USER1 = new User("EDUARDO", PASSWORD);

        //recipe
        Recipe testRecipe = new Recipe();
        testRecipe.setName("Maruchan sabor Cancer 2.0");
        testRecipe.setType("Moncha salva tandas");
        testRecipe.setPrice(650);
        testRecipe.setDuration(125);
        testRecipe.setAuthor(new User("Eduardo", "jajaja"));
        testRecipe.setDifficulty(0);
        SimpleList<String> tags = new SimpleList<>();
        tags.append("Cancer");
        tags.append("china");
        tags.append("wakala las de queso");
        tags.append("rikas las de poio");
        testRecipe.setTags(tags);


        final String jsonRecipe = jsonConverter.toJson(testRecipe);

        TEST_USER1.addRecipe(jsonRecipe);
        JsonWriter.updateRecipes();

    }
}