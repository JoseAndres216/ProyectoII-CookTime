import com.google.gson.Gson;
import logic.ServerManager;
import logic.users.AbstractUser;
import logic.users.Enterprise;
import logic.users.Recipe;
import logic.users.User;
import logic.utilities.Encrypter;
import logic.utilities.JsonWriter;
import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.Queue;

public class ServerManagerTest {
    public static final String PASSWORD = "password";
    public static final String NAME1 = "EDUARDO";
    public static final String NAME2 = "JUAN";
    public static final String TEST_PASSWORD = "testPassword";
    ServerManager server = ServerManager.getInstance();

    @Test
    public void testFindAvaliableUsers() {
        final User tUser1 = new User(NAME1, PASSWORD);
        final User testUser2 = new User(NAME2, PASSWORD);
        final User testUser3 = new User("CARLOS", PASSWORD);
        final User testUser4 = new User("VICTOR", PASSWORD);
        final User testUser5 = new User("MANUEL", PASSWORD);
        try {

            ServerManager.getInstance().createSubject(true, new Gson().toJson(tUser1));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(testUser2));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(testUser3));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(testUser4));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(testUser5));
            JsonWriter.updateUsers();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertTrue(ServerManager.getInstance().existUser(true, tUser1.getEmail()));
        Assert.assertTrue(ServerManager.getInstance().existUser(true, testUser2.getEmail()));
        Assert.assertTrue(ServerManager.getInstance().existUser(true, testUser3.getEmail()));
        Assert.assertTrue(ServerManager.getInstance().existUser(true, testUser4.getEmail()));
        Assert.assertTrue(ServerManager.getInstance().existUser(true, testUser5.getEmail()));
    }

    @Test
    public void testFindAvaliableEnterprises() {
        final Enterprise testEnterprise1 = new Enterprise("MUSSI", PASSWORD);
        final Enterprise testEnterprise2 = new Enterprise("TP IND.", PASSWORD);
        final Enterprise testEnterprise3 = new Enterprise("ASETEC", PASSWORD);
        final Enterprise testEnterprise4 = new Enterprise("SUR", PASSWORD);
        final Enterprise testEnterprise5 = new Enterprise("KK", PASSWORD);
        try {
            final String JsonTest = new Gson().toJson(testEnterprise1, Enterprise.class);
            final String JsonTest2 = new Gson().toJson(testEnterprise2, Enterprise.class);
            final String JsonTest3 = new Gson().toJson(testEnterprise3, Enterprise.class);
            final String JsonTest4 = new Gson().toJson(testEnterprise4, Enterprise.class);
            final String JsonTest5 = new Gson().toJson(testEnterprise5, Enterprise.class);
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
        String encryptedPass = Encrypter.encryptPassword(password);
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
        String encryptedPass = Encrypter.encryptPassword(password);
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
        String encryptedPass = Encrypter.encryptPassword(password);
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

        final String TEST_USER1 = jsonConverter.toJson(new User(NAME1, PASSWORD), User.class);
        final String TEST_USER2 = jsonConverter.toJson(new User(NAME2, PASSWORD), User.class);
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

    }

    @Test
    public void testWritingEnterprises() throws NoSuchAlgorithmException {
        //Gson instance
        Gson jsonConverter = new Gson();
        //Test users for adding to the json files

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

    }

    @Test
    public void addRecipe() throws NoSuchAlgorithmException {
        //Gson instance
        Gson jsonConverter = new Gson();
        //Test users for adding to the json files

        String PASSWORD = "testPassword";
        final User TEST_USER1 = new User(NAME1, PASSWORD);
        ServerManager.getInstance().createSubject(true, jsonConverter.toJson(TEST_USER1, User.class));
        AbstractUser testUser = ServerManager.getInstance().getUser(NAME1);

        //recipe
        Recipe testRecipe = new Recipe();
        testRecipe.setName("Maruchan sabor Cancer 2.0");
        testRecipe.setType("Moncha salva tandas");
        testRecipe.setPrice(650);
        testRecipe.setDuration(125);
        testRecipe.setAuthor(testUser.getEmail());
        testRecipe.setDifficulty(0);


        final String jsonRecipe = jsonConverter.toJson(testRecipe);

        try {
            testUser.addRecipe(jsonRecipe);
            JsonWriter.updateRecipes();
            JsonWriter.updateUsers();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ServerManager.getInstance().getGlobalRecipes().inOrder());

    }

    @Test
    public void printTrees() {
        Queue<AbstractUser> test = new LinkedList<>();
        test.add(null);
        System.out.println(test.peek());
        /*  ServerManager.getInstance().getUsers().print();
         *//* System.out.println(ServerManager.getInstance().getGlobalRecipes().inOrder());
        System.out.println(ServerManager.getInstance().getUsers().inOrder());
        System.out.println(ServerManager.getInstance().getEnterprises().inOrder());*/
    }
}