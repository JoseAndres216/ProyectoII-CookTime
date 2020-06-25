package UI.logIn;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class LogInController {
    @FXML
    private Button logInButton;
    @FXML
    private TextField usernameEntry;
    @FXML
    private PasswordField passwordEntry;

    public void pressedLogIn() {
        try {
            Parent newRoot = FXMLLoader.load(getClass().getResource("..//Startview/startServerM.fxml"));
            System.out.println("Loaded FXML");
            Scene newScene = new Scene(newRoot);
            Stage source = (Stage) logInButton.getScene().getWindow();
            source.setScene(newScene);
        } catch (IOException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}
