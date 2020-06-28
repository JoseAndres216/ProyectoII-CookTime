package Logic;

import Logic.Users.AbstractUser;
import Logic.Users.Enterprise;
import Logic.Users.User;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class ServerManagerTest {
    //test for searching users that are avaliable on the tree
    @Test
    public void testFindAvaliableUsers() {
        User test1 = new User("eduardo", "password");
        User test2 = new User("papu", "password");
        User test3 = new User("miguel", "password");
        User test4 = new User("carlos", "password");
        User test5 = new User("juan", "password");
        ServerManager.getInstance().getUsers().insert(test1);
        ServerManager.getInstance().getUsers().insert(test2);
        ServerManager.getInstance().getUsers().insert(test3);
        ServerManager.getInstance().getUsers().insert(test4);
        ServerManager.getInstance().getUsers().insert(test5);

        Assert.assertEquals(test1, ServerManager.getInstance().getUser("eduardo"));
        Assert.assertEquals(test2, ServerManager.getInstance().getUser("papu"));
        Assert.assertEquals(test3, ServerManager.getInstance().getUser("miguel"));
        Assert.assertEquals(test4, ServerManager.getInstance().getUser("carlos"));
        Assert.assertEquals(test5, ServerManager.getInstance().getUser("juan"));
    }

    @Test
    public void testFindAvaliableEnterprises() {
        Enterprise test1 = new Enterprise("eduardo", "password");
        Enterprise test2 = new Enterprise("papu", "password");
        Enterprise test3 = new Enterprise("miguel", "password");
        Enterprise test4 = new Enterprise("carlos", "password");
        Enterprise test5 = new Enterprise("juan", "password");
        ServerManager.getInstance().getEnterprises().insert(test1);
        ServerManager.getInstance().getEnterprises().insert(test2);
        ServerManager.getInstance().getEnterprises().insert(test3);
        ServerManager.getInstance().getEnterprises().insert(test4);
        ServerManager.getInstance().getEnterprises().insert(test5);

        Assert.assertEquals(test1, ServerManager.getInstance().getEnterprise("eduardo"));
        Assert.assertEquals(test2, ServerManager.getInstance().getEnterprise("papu"));
        Assert.assertEquals(test3, ServerManager.getInstance().getEnterprise("miguel"));
        Assert.assertEquals(test4, ServerManager.getInstance().getEnterprise("carlos"));
        Assert.assertEquals(test5, ServerManager.getInstance().getEnterprise("juan"));
    }

    @Test
    public void testExistsUsers() {
        User test1 = new User("eduardo", "password");
        User test2 = new User("papu", "password");
        User test3 = new User("miguel", "password");
        User test4 = new User("carlos", "password");
        User test5 = new User("juan", "password");
        ServerManager.getInstance().getUsers().insert(test1);
        ServerManager.getInstance().getUsers().insert(test2);
        ServerManager.getInstance().getUsers().insert(test3);
        // ServerManager.getInstance().getUsers().insert(test4);
        ServerManager.getInstance().getUsers().insert(test5);

        Assert.assertEquals(true, ServerManager.getInstance().existUser(true, "eduardo"));
        Assert.assertEquals(true, ServerManager.getInstance().existUser(true, "papu"));
        Assert.assertEquals(true, ServerManager.getInstance().existUser(true, "miguel"));
        Assert.assertEquals(false, ServerManager.getInstance().existUser(true, "carlos"));
        Assert.assertEquals(true, ServerManager.getInstance().existUser(true, "juan"));
    }

    @Test
    public void testJsonAddUser() {
        AbstractUser testUser = new User("eduardo", "patitoDePeluche");
        AbstractUser testUser2 = new User("juan", "quesoCrema");
        AbstractUser testUser3 = new User("carlos", "tolis");

        Gson gson = new Gson();
        String tstUserString = gson.toJson(testUser, testUser.getClass());
        String tstUserString2 = gson.toJson(testUser2, testUser.getClass());
        String tstUserString3 = gson.toJson(testUser3, testUser.getClass());
        System.out.println(tstUserString);

        ServerManager.getInstance().createUser(tstUserString);
        ServerManager.getInstance().createUser(tstUserString2);
        ServerManager.getInstance().createUser(tstUserString3);

        Assert.assertTrue(ServerManager.getInstance().existUser(true, "eduardo"));
        Assert.assertTrue(ServerManager.getInstance().existUser(true, "juan"));
        Assert.assertTrue(ServerManager.getInstance().existUser(true, "carlos"));
        ServerManager.getInstance().getUsers().print();


    }

    @Test
    public void test_verifyPasswordCorrect() {
        String password = "Patito29381";
        String encryptedPass = ServerManager.getInstance().encryptPassword(password);
        AbstractUser testUser = new User("eduardo", password);

        Gson gson = new Gson();
        String testUserString = gson.toJson(testUser, testUser.getClass());
        ServerManager.getInstance().createUser(testUserString);
        Assert.assertTrue(ServerManager.getInstance().verifyUser("eduardo", password));
    }

    @Test
    public void test_verifyUnexistantUser() {
        String password = "Patito29381";
        String encryptedPass = ServerManager.getInstance().encryptPassword(password);
        AbstractUser testUser = new User("eduardo", password);

        Gson gson = new Gson();
        String testUserString = gson.toJson(testUser, testUser.getClass());
        ServerManager.getInstance().createUser(testUserString);

        Assert.assertFalse(ServerManager.getInstance().verifyUser("juan", password));
    }

    @Test
    public void test_verifyPasswordWrong() {
        String password = "Patito29381";
        String encryptedPass = ServerManager.getInstance().encryptPassword(password);
        AbstractUser testUser = new User("eduardo", password);

        Gson gson = new Gson();
        String testUserString = gson.toJson(testUser, testUser.getClass());
        ServerManager.getInstance().createUser(testUserString);

        Assert.assertFalse(ServerManager.getInstance().verifyUser("eduardo", "password"));
    }
}