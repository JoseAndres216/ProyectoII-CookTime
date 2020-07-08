package UI.UserManagement;


import Logic.DataStructures.SimpleList.SimpleList;
import Logic.ServerManager;
import Logic.Users.AbstractUser;
import UI.Startview.StartController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import static UI.logIn.LogInController.START_VIEW;

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
