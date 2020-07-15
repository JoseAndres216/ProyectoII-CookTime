package gui.users;


import gui.start.StartController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import logic.structures.simplelist.SimpleList;
import logic.users.AbstractUser;
import logic.utilities.JsonLoader;

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
        SimpleList<AbstractUser> list = JsonLoader.loadUsers().inOrder();
        this.usersList.getItems().removeAll();
        for (int i = 0; i < list.len(); i++) {
            this.usersList.getItems().add(list.indexElement(i).getEmail());

        }
    }
}
