package Logic;

import Logic.FileManagement.FilesWriter;
import Logic.Users.AbstractUser;
import Logic.Users.Enterprise;
import Logic.Users.User;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class ServerManagerTest {
    ServerManager server = ServerManager.getInstance();

    //test for searching users that are avaliable on the tree
    @Test
    public void testFindAvaliableUsers() {
        final User TEST_USER1 = new User("EDUARDO", "password");
        final User TEST_USER2 = new User("JUAN", "password");
        final User TEST_USER3 = new User("CARLOS", "password");
        final User TEST_USER4 = new User("VICTOR", "password");
        final User TEST_USER5 = new User("MANUEL", "password");
        try {

            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER1));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER2));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER3));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER4));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER5));
            FilesWriter.updateUsers();

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
        final Enterprise TEST_ENTERPRISE1 = new Enterprise("MUSSI", "password");
        final Enterprise TEST_ENTERPRISE2 = new Enterprise("TP IND.", "password");
        final Enterprise TEST_ENTERPRISE3 = new Enterprise("ASETEC", "password");
        final Enterprise TEST_ENTERPRISE4 = new Enterprise("SUR", "password");
        final Enterprise TEST_ENTERPRISE5 = new Enterprise("KK", "password");
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
        Assert.assertTrue(ServerManager.getInstance().existUser(false, TEST_ENTERPRISE1.getEmail()));
        Assert.assertTrue(ServerManager.getInstance().existUser(false, TEST_ENTERPRISE2.getEmail()));
        Assert.assertTrue(ServerManager.getInstance().existUser(false, TEST_ENTERPRISE3.getEmail()));
        Assert.assertTrue(ServerManager.getInstance().existUser(false, TEST_ENTERPRISE4.getEmail()));
        Assert.assertTrue(ServerManager.getInstance().existUser(false, TEST_ENTERPRISE5.getEmail()));
    }

    @Test
    public void addEnterprises() {
        final Enterprise TEST_ENTERPRISE6 = new Enterprise("MUSSI", "password");
        final Enterprise TEST_ENTERPRISE7 = new Enterprise("GROOVES.", "password");
        final Enterprise TEST_ENTERPRISE8 = new Enterprise("BALLAS", "password");
        final Enterprise TEST_ENTERPRISE9 = new Enterprise("MARA", "password");
        final Enterprise TEST_ENTERPRISE10 = new Enterprise("KKK", "password");
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
        final User TEST_USER6 = new User("MAIKOL", "password");
        final User TEST_USER7 = new User("MESSI", "password");
        final User TEST_USER8 = new User("ZLATAN", "password");
        final User TEST_USER9 = new User("CARL", "password");
        final User TEST_USER10 = new User("JUNIOR", "password");

        try {

            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER6));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER7));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER8));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER9));
            ServerManager.getInstance().createSubject(true, new Gson().toJson(TEST_USER10));
            FilesWriter.updateUsers();

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
    public void testVerifyPasswordCorrect() {
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
    public void testVerifyUnexistantUser() {
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
    public void testVerifyPasswordWrong() {
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
    public void testWriteUsers() {
        AbstractUser testUser = new User("eduardo", "patitoDePeluche");
        AbstractUser testEnterprise = new Enterprise("Trevor Philips Industry", "fuck");
        Gson gson = new Gson();
        String tstUserString = gson.toJson(testUser, User.class);
        String testEnterString = gson.toJson(testEnterprise, Enterprise.class);
        try {

            ServerManager.getInstance().createSubject(true, tstUserString);
            ServerManager.getInstance().createSubject(false, testEnterString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    @Test
    public void testWriteEnterprises() {
        final Enterprise TEST_ENTERPRISE11 = new Enterprise("AAA", "password");
        final Enterprise TEST_ENTERPRISE12 = new Enterprise("ASDF IND.", "password");
        final Enterprise TEST_ENTERPRISE13 = new Enterprise("ASERWETEC", "password");
        final Enterprise TEST_ENTERPRISE14 = new Enterprise("ADF", "password");
        final Enterprise TEST_ENTERPRISE15 = new Enterprise("ASDF", "password");
        try {
            final String JsonTest = new Gson().toJson(TEST_ENTERPRISE11, Enterprise.class);
            final String JsonTest2 = new Gson().toJson(TEST_ENTERPRISE12, Enterprise.class);
            final String JsonTest3 = new Gson().toJson(TEST_ENTERPRISE13, Enterprise.class);
            final String JsonTest4 = new Gson().toJson(TEST_ENTERPRISE14, Enterprise.class);
            final String JsonTest5 = new Gson().toJson(TEST_ENTERPRISE15, Enterprise.class);
            ServerManager.getInstance().createSubject(false, JsonTest);
            ServerManager.getInstance().createSubject(false, JsonTest2);
            ServerManager.getInstance().createSubject(false, JsonTest3);
            ServerManager.getInstance().createSubject(false, JsonTest4);
            ServerManager.getInstance().createSubject(false, JsonTest5);
            FilesWriter.updateEnterprises();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    @Test
    public void testStartServer() {
        ServerManager.getInstance();
    }
}