package gui.login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.utilities.Encrypter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;


public class LogInController {
    public static final String SERVER_CONFIG_FILE = "ServerConfiguration.txt";
    public static final String START_SERVER_M_FXML = "startServerM.fxml";
    public static final String GUI_PATH = "C:\\Users\\eduar\\Desktop\\CookTime\\ProyectoII-CookTime\\CookTimeServer\\src\\gui\\";
    public static final String START_VIEW = GUI_PATH + "start\\" + START_SERVER_M_FXML;
    private static final String CONFIG_PATH = GUI_PATH +"login\\"+SERVER_CONFIG_FILE;
    //files for the configuration of server

    @FXML
    private Button logInButton;
    @FXML
    private TextField usernameEntry;
    @FXML
    private PasswordField passwordEntry;


    private String[] getLoginSets() throws FileNotFoundException {

        String[] array = null;
        try (Scanner fileScanner = new Scanner(new File(CONFIG_PATH))) {
            String line = fileScanner.nextLine();
            array = line.split("#");
        } catch (Exception e) {
            System.out.println("error cargando los archivos de configuracion");
            e.printStackTrace();
        }
        return array;
    }

    private void verifyUser(String[] array, String username, String password) throws NoSuchAlgorithmException {
        String encryptedGivenPass = Encrypter.encryptPassword(password);
        String encryptedUserName = Encrypter.encryptPassword(username);
        System.out.println(Arrays.toString(array));

        if (!(encryptedGivenPass.equals(array[1]) && encryptedUserName.equals(array[0]))) {
            System.out.println(Arrays.toString(array));
            System.out.println("Given username: ");
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    public void pressedLogIn() {
        try {
            this.verifyUser(this.getLoginSets(), this.usernameEntry.getText(), this.passwordEntry.getText());

            URL url = new File(START_VIEW).toURI().toURL();

            Parent root = FXMLLoader.load(url);
            Scene newScene = new Scene(root);
            Stage source = (Stage) logInButton.getScene().getWindow();
            source.setScene(newScene);

        } catch (IOException | NullPointerException | NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
    }
}
