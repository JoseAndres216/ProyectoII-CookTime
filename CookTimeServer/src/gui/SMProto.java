package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SMProto extends Application {

    public static final String LOG_IN_SERVER_FXML = "logInServer.fxml";

    public static final String LOGIN_FXML_PATH ="C:\\Users\\eduar\\Desktop\\CookTime\\ProyectoII-CookTime\\CookTimeServer\\src\\gui\\login\\"
                    + LOG_IN_SERVER_FXML;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        URL url = new File(LOGIN_FXML_PATH).toURI().toURL();

        Parent root = FXMLLoader.load(url);
        stage.setTitle("Registration Form FXML Application");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }
}

