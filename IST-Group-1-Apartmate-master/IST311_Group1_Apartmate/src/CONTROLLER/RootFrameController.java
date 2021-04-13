package CONTROLLER;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

/**
 * @author Cole Daubenspeck Nov 11, 2018
 */
public class RootFrameController 
{
    @FXML
    MenuBar menuBar;
    
    @FXML
    public void handleMainMenuButton(ActionEvent actionEvent) {
        Stage stage = (Stage)menuBar.getScene().getWindow();
        stage.hide();
        MainMenuController mmc = MainMenuController.getMainMenuController(stage);
        mmc.setupView();
        mmc.show();
    }
    
    @FXML
    public void handleQuitButton(ActionEvent actionEvent) {
        //close the stage and end the program
        PersistentDataController.getPersistentDataController().writeDataFile();
        ((Stage)menuBar.getScene().getWindow()).close();
    }
}
