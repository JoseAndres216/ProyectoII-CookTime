package UI.Startview;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class StartController {
    @FXML
    private Button chefRequestButton;
    @FXML
    private Button adminUsersButton;
    @FXML
    private Button exitButton;

    public void pressedExit() {
        Stage scene = (Stage) chefRequestButton.getScene().getWindow();
        scene.close();
    }

    public void pressedChefReq() {

        try {
            Parent newRoot = FXMLLoader.load(getClass().getResource("..//ChefRequest/chefRequests.fxml"));

            Scene newScene = new Scene(newRoot);
            Stage source = (Stage) adminUsersButton.getScene().getWindow();
            source.setScene(newScene);
        } catch (IOException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void pressedUserAdmin() {

        try {
            Parent newRoot = FXMLLoader.load(getClass().getResource("..//UserManagement/userManagement.fxml"));

            Scene newScene = new Scene(newRoot);
            Stage source = (Stage) adminUsersButton.getScene().getWindow();
            source.setScene(newScene);
        } catch (IOException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}
