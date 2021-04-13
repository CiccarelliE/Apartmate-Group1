package CONTROLLER;

import MODEL.Reminder;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Eric Ciccarelli Nov 29, 2018
 */
public class ReminderUsecaseController {

    private Stage stage;
    private BorderPane rootLayout;
    private static ReminderUsecaseController reminderUsecaseController;

    private ReminderUsecaseController(Stage stage) {
        this.stage = stage;
        setupView();
        stage.show();
    }

    public static ReminderUsecaseController getReminderUsecaseController(Stage stage) {
        if (reminderUsecaseController == null) {
            reminderUsecaseController = new ReminderUsecaseController(stage);
        }
        return reminderUsecaseController;
    }
    
    public void setupView() {
        try {
            rootLayout = (BorderPane)FXMLLoader.load(getClass().getResource("/VIEW/RootLayout.fxml"));
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            showReminderOverview();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void show() {
        stage.show();
    }
    
    public void showReminderOverview() {
        try {
            // Load activity overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/VIEW/ReminderOverview.fxml"));
            Parent overview = loader.load();
            rootLayout.setCenter(overview);
            
            ((ReminderOverviewUIController)loader.getController()).setStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    public boolean showReminderEditDialog(Reminder toEdit) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/VIEW/ReminderEditDialog.fxml"));
            Parent parent = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Reminder");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            
            Scene scene = new Scene(parent);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ReminderEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setReminder(toEdit);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
