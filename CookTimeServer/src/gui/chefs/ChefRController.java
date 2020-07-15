package gui.chefs;

import gui.start.StartController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import logic.structures.simplelist.SimpleList;
import logic.users.AbstractUser;
import logic.utilities.JsonLoader;
import logic.utilities.JsonWriter;

import static gui.login.LogInController.START_VIEW;

public class ChefRController {
    @FXML
    private Button backMenuButton;
    @FXML
    private ListView<Button> usersList;
    @FXML
    private Button refreshButton;

    public void pressedBackButton() {
        StartController.mainMenuAcces(START_VIEW, backMenuButton);
    }

    public void pressedRefreshButton() {
        this.usersList.getItems().clear();
        SimpleList<AbstractUser> list = JsonLoader.loadUsers().inOrder();
        for (int i = 0; i < list.len(); i++) {
            if (!list.indexElement(i).isChef()) {
                Button tempButton = new Button();
                int finalI = i;
                tempButton.setText(list.indexElement(finalI).getEmail());
                tempButton.setOnAction(event -> {
                    list.indexElement(finalI).makeChef();
                });
                this.usersList.getItems().add(tempButton);
            }

        }
        JsonWriter.updateUsers();
    }
}
