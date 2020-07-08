package gui.users;


import logic.structures.simplelist.SimpleList;
import logic.ServerManager;
import logic.users.AbstractUser;
import gui.start.StartController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import static gui.login.LogInController.START_VIEW;

public class UserMController {
    @FXML
    private Button backMenuButton;

    @FXML
    private ListView<String> usersList;

    public void pressedBackButton() {
        StartController.mainMenuAcces(START_VIEW, backMenuButton);
    }

    public void pressedRefreshButton() {
        SimpleList<AbstractUser> list = ServerManager.getInstance().getUsers().inOrder();
        for (int i = 0; i < list.len(); i++) {
            this.usersList.getItems().add(list.indexElement(i).getEmail());

        }
    }
}
