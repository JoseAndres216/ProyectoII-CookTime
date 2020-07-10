package logic.utilities;

import logic.ServerManager;
import org.junit.Test;

public class SearcherTest {

    @Test
    public void findUsers() {
        System.out.println((ServerManager.getInstance().searchSubject("EDUARDO", true)));
    }

}