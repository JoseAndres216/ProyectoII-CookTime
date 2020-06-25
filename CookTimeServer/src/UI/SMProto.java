package UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SMProto extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("logIn\\logInServer.fxml"));
        stage.setTitle("Registration Form FXML Application");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }
}

