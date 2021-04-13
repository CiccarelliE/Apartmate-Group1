package CONTROLLER;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class MainMenuUIController {

    public MainMenuUIController() {
    }
    
    @FXML
    public void handleActivityButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
        ActivityUsecaseController aucc = ActivityUsecaseController.getActivityUsecaseController(stage);
        aucc.setupView();
        aucc.show();
    }
    
    @FXML
    public void handleReminderButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
        ReminderUsecaseController aucc = ReminderUsecaseController.getReminderUsecaseController(stage);
        aucc.setupView();
        aucc.show();
    }
    
    @FXML
    public void handleUtilityButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
        UtilityUsecaseController uuc = UtilityUsecaseController.getUtilityUsecaseController(stage);
        uuc.setupView();
        uuc.show();
    }
    
    @FXML
    public void handleCostSharingButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
        CostSharingUsecaseController csuc = CostSharingUsecaseController.getCostSharingUsecaseController(stage);
        csuc.setupView();
        csuc.show();
    }

    @FXML
    public void handleQuitButton(ActionEvent actionEvent) {
        //close the stage and end the program
        PersistentDataController.getPersistentDataController().writeDataFile();
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }
    
    @FXML
    public void handleManageUsersButton(ActionEvent actionEvent) {
        Stage stage = ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
        stage.hide();
        UserManagementController umc = UserManagementController.getUserManagementController(stage);
        umc.setupView();
        umc.show();
    }
}
