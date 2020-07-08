package gui.chefs;

import gui.start.StartController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import static gui.login.LogInController.START_VIEW;

public class ChefRController {
    @FXML
    private Button backMenuButton;

    public void pressedBackButton() {
        StartController.mainMenuAcces(START_VIEW, backMenuButton);
    }
}
