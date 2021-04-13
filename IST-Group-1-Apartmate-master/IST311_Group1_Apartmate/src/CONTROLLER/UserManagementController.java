package CONTROLLER;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author Cole Daubenspeck Nov 29, 2018
 */

public final class UserManagementController 
{
    private static UserManagementController userManagementController;
    private static Stage stage;
    private BorderPane rootLayout;
    
    private UserManagementController(Stage stage) {
        this.stage = stage;
        setupView();
        stage.show();
    }
    
    public void setupView() {
        try {
            rootLayout = (BorderPane)FXMLLoader.load(getClass().getResource("/VIEW/RootLayout.fxml"));
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
        } catch (IOException ex) {
            System.out.println("!!! - Crashed at root layout loading");
            ex.printStackTrace();
        }
        try {
            Parent overview = FXMLLoader.load(getClass().getResource("/VIEW/UserManagementUI.fxml"));
            rootLayout.setCenter(overview);
            
            //((UserManagementUIController)loader.getController()).setStage(stage);
        } catch (IOException ex) {
            System.out.println("!!! - Crashed at view loading");
            ex.printStackTrace();
        }
    }

    public void show() {
        stage.show();
    }
    
    public static UserManagementController getUserManagementController(Stage s) {
        if (userManagementController == null) {
            userManagementController = new UserManagementController(s);
        }
        return userManagementController;
    }
}
