package UI.UserManagement;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class UserMController {
    @FXML
    private Button backMenuButton;


    public void pressedBackButton() {
        try {
            Parent newRoot = FXMLLoader.load(getClass().getResource("..//Startview/startServerM.fxml"));
            Scene newScene = new Scene(newRoot);
            Stage source = (Stage) backMenuButton.getScene().getWindow();
            source.setScene(newScene);
        } catch (IOException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}
