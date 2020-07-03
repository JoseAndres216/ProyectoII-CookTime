package UI.ChefRequest;

import UI.Startview.StartController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import static UI.logIn.LogInController.START_VIEW;

public class ChefRController {
    @FXML
    private Button backMenuButton;

    public void pressedBackButton() {
        StartController.mainMenuAcces(START_VIEW, backMenuButton);
    }
}
