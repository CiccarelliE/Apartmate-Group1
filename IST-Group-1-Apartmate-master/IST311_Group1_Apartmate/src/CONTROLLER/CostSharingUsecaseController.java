package CONTROLLER;

import MODEL.CostSharing;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Cole Daubenspeck Nov 11, 2018
 */
public final class CostSharingUsecaseController {

    private Stage stage;
    private BorderPane rootLayout;
    private static CostSharingUsecaseController costSharingUsecaseController;

    private CostSharingUsecaseController(Stage stage) {
        this.stage = stage;
        setupView();
        stage.show();
    }

    public static CostSharingUsecaseController getCostSharingUsecaseController(Stage stage) {
        if (costSharingUsecaseController == null) {
            costSharingUsecaseController = new CostSharingUsecaseController(stage);
        }
        return costSharingUsecaseController;
    }
    
    public void setupView() {
        try {
            rootLayout = (BorderPane)FXMLLoader.load(getClass().getResource("/VIEW/RootLayout.fxml"));
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            showCostSharingOverview();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void show() {
        stage.show();
    }
    
    public void showCostSharingOverview() {
        try {
            // Load activity overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/VIEW/CostSharingOverview.fxml"));
            Parent overview = loader.load();
            rootLayout.setCenter(overview);
            
            ((CostSharingOverviewUIController)loader.getController()).setStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    public boolean showCostSharingEditDialog(CostSharing toEdit) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/VIEW/CostSharingEditDialog.fxml"));
            Parent parent = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New CostSharing");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            
            Scene scene = new Scene(parent);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            CostSharingEditDialogUIController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCostSharing(toEdit);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
