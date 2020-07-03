package UI.UserManagement;


import UI.Startview.StartController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import static UI.logIn.LogInController.START_VIEW;

public class UserMController {
    @FXML
    private Button backMenuButton;


    public void pressedBackButton() {
        StartController.mainMenuAcces(START_VIEW, backMenuButton);
    }
}
