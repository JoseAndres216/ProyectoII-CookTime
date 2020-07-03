package UI.Startview;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class StartController {
    public static final String CHEF_REQUESTS_FXML = "chefRequests.fxml";
    public static final String CHEF_REQ_PATH = "C:\\Users\\eduar\\Desktop\\CookTime\\ProyectoII-CookTime\\CookTimeServer\\src\\UI\\ChefRequest\\" + CHEF_REQUESTS_FXML;
    public static final String USER_MANAGEMENT_FXML = "userManagement.fxml";
    public static final String USER_MANAGEMENT_PATH = "C:\\Users\\eduar\\Desktop\\CookTime\\ProyectoII-CookTime\\CookTimeServer\\src\\UI\\UserManagement\\" + USER_MANAGEMENT_FXML;
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

    public static void mainMenuAcces(String chefReqPath, Button adminUsersButton) {
        try {
            URL url = new File(chefReqPath).toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene newScene = new Scene(root);
            Stage source = (Stage) adminUsersButton.getScene().getWindow();
            source.setScene(newScene);
        } catch (IOException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    public void pressedChefReq() {

        mainMenuAcces(CHEF_REQ_PATH, adminUsersButton);
    }

    public void pressedUserAdmin() {

        try {
            URL url = new File(USER_MANAGEMENT_PATH).toURI().toURL();
            Parent root = FXMLLoader.load(url);

            Scene newScene = new Scene(root);
            Stage source = (Stage) adminUsersButton.getScene().getWindow();
            source.setScene(newScene);
        } catch (IOException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}
