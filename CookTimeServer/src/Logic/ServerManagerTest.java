package Logic;

import Logic.Users.Enterprise;
import Logic.Users.User;
import org.junit.Assert;
import org.junit.Test;

public class ServerManagerTest {
    //test for searching users that are avaliable on the tree
    @Test
    public void testFindAvaliableUsers() {
        User test1 = new User("eduardo");
        User test2 = new User("papu");
        User test3 = new User("miguel");
        User test4 = new User("carlos");
        User test5 = new User("juan");
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
        Enterprise test1 = new Enterprise("eduardo");
        Enterprise test2 = new Enterprise("papu");
        Enterprise test3 = new Enterprise("miguel");
        Enterprise test4 = new Enterprise("carlos");
        Enterprise test5 = new Enterprise("juan");
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
        User test1 = new User("eduardo");
        User test2 = new User("papu");
        User test3 = new User("miguel");
        User test4 = new User("carlos");
        User test5 = new User("juan");
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
}