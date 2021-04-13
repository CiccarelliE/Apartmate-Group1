package CONTROLLER;

import MODEL.UtilityBill;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author cyberdraco
 */
public final class UtilityUsecaseController {

    private Stage stage;
    private BorderPane rootLayout;
    private static UtilityUsecaseController utilityUsecaseController;

    private UtilityUsecaseController(Stage stage) {
        this.stage = stage;
        setupView();
        stage.show();
    }

    public static UtilityUsecaseController getUtilityUsecaseController(Stage stage) {
        if (utilityUsecaseController == null) {
            utilityUsecaseController = new UtilityUsecaseController(stage);
        }
        return utilityUsecaseController;
    }
    
    public void setupView() {
        try {
            rootLayout = (BorderPane)FXMLLoader.load(getClass().getResource("/VIEW/RootLayout.fxml"));
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            showUtilityOverview();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void show() {
        stage.show();
    }
    
    public void showUtilityOverview() {
        try {
            // Load activity overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/VIEW/UtilityOverviewUI.fxml"));
            Parent overview = loader.load();
            rootLayout.setCenter(overview);
            
            ((UtilityOverviewUIController)loader.getController()).setStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    public UtilityBill showNewUtilityBillDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/VIEW/NewUtilityBillDialog.fxml"));
            Parent parent = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Utility Bill");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            
            Scene scene = new Scene(parent);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            UtilityBillDialogUIController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.getUtilityBill();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
