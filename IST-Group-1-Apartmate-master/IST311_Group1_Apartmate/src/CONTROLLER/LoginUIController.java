package CONTROLLER;

import MODEL.User;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import MODEL.UserList;
import javafx.stage.Stage;

public class LoginUIController {
    
    private static User authenticatedUser;

    private UserList userList;

    //GUI elements defined in LoginUI.fxml
    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;
    @FXML
    Label statusText;

    public LoginUIController() {
        userList = PersistentDataController.getPersistentDataController().getPersistentDataCollection().getUserList();
    }
    
    public static User getAuthenticatedUser() {
        return authenticatedUser;
    }
    
    @FXML
    private void handleSubmitButton(ActionEvent event) throws IOException {
        //debug output to CLI showing user attempt
        String username = usernameField.getText();
        String password = passwordField.getText();
        System.out.println("Authenticating: \n" +
                                "-- Username = " + username + "\n" +
                                "-- Password = " + password);
        
        //if correct loin
        if(userList.authenticate(username, password)) {
            //change status text and debug print(will go unseen if this works)
            statusText.setText("Authenticated");
            statusText.setTextFill(Color.GREEN);
            System.out.println("Authenticated!");
            authenticatedUser = userList.getUser(username);
            
            //creates the DaemonMaster upon logging in
            DaemonMaster.getDaemonMaster();
            
            //loads the MainMenuUI and transfers control to its controller
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.hide();
            MainMenuController.getMainMenuController(stage);
        }
        else {
            //change status text and debug print
            statusText.setText("Authentication Failed");
            statusText.setTextFill(Color.RED);
            System.out.println("Authentication Failed");
        }

    }
    
}
