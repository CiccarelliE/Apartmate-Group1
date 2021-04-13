package CONTROLLER;

import MODEL.Activity;
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
public final class ActivityUsecaseController {

    private Stage stage;
    private BorderPane rootLayout;
    private static ActivityUsecaseController activityUsecaseController;

    private ActivityUsecaseController(Stage stage) {
        this.stage = stage;
        setupView();
        stage.show();
    }

    public static ActivityUsecaseController getActivityUsecaseController(Stage stage) {
        if (activityUsecaseController == null) {
            activityUsecaseController = new ActivityUsecaseController(stage);
        }
        return activityUsecaseController;
    }
    
    public void setupView() {
        try {
            rootLayout = (BorderPane)FXMLLoader.load(getClass().getResource("/VIEW/RootLayout.fxml"));
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            showActivityOverview();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void show() {
        stage.show();
    }
    
    public void showActivityOverview() {
        try {
            // Load activity overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/VIEW/ActivityOverview.fxml"));
            Parent overview = loader.load();
            rootLayout.setCenter(overview);
            
            ((ActivityOverviewUIController)loader.getController()).setStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    public boolean showActivityEditDialog(Activity toEdit) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/VIEW/ActivityEditDialog.fxml"));
            Parent parent = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Activity");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            
            Scene scene = new Scene(parent);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ActivityEditDialogUIController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setActivity(toEdit);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
