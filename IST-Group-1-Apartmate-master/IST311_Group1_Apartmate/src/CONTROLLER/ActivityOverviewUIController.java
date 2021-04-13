package CONTROLLER;

import MODEL.Activity;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

//Code based on example from https://code.makery.ch/library/javafx-tutorial/
public class ActivityOverviewUIController {

    private Stage stage;

    @FXML
    private TableView<Activity> activityTable;
    @FXML
    private TableColumn<Activity, String> userColumn;
    @FXML
    private TableColumn<Activity, String> titleColumn;

    @FXML
    private ObservableList<Activity> observableActivityList;

    @FXML
    private Label userLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label messageLabel;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ActivityOverviewUIController() {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        List<Activity> dataList = (List<Activity>) PersistentDataController.getPersistentDataController().getPersistentDataCollection().getActivityList();
        observableActivityList = FXCollections.observableList(dataList);
        // Initialize the person table with the two columns.
        userColumn.setCellValueFactory(new PropertyValueFactory<>("User"));

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));

        activityTable.setItems(observableActivityList);

        // Clear person details.
        showActivityDetails(null);

        // Listen for selection changes and show the person details when changed.
        activityTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showActivityDetails(newValue));
    }

    private void refreshTable() {
        List<Activity> dataList = (List<Activity>) PersistentDataController.getPersistentDataController().getPersistentDataCollection().getActivityList();
        observableActivityList = FXCollections.observableList(dataList);
        activityTable.setItems(observableActivityList);
        activityTable.refresh();
    }

    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    private void showActivityDetails(Activity activity) {
        if (activity != null) {
            // Fill the labels with info from the person object.
            userLabel.setText(activity.getUser().getUserName());
            titleLabel.setText(activity.getTitle());
            dateLabel.setText(activity.getFormattedTimestamp());
            messageLabel.setText(activity.getBody());
        } else {
            // user is null, remove all the text.
            userLabel.setText("");
            titleLabel.setText("");
            dateLabel.setText("");
            messageLabel.setText("");
        }
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteActivity() {
        int selectedIndex = activityTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            activityTable.getItems().remove(selectedIndex);
            PersistentDataController.getPersistentDataController().writeDataFile();
            refreshTable();
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(stage);
            alert.setTitle("No Selection");
            alert.setHeaderText("No user Selected");
            alert.setContentText("Please select a user in the table.");

            alert.showAndWait();
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new user.
     */
    @FXML
    private void handleNewActivity() {
        Activity temp = new Activity(LoginUIController.getAuthenticatedUser(), "", "");
        boolean okClicked = ActivityUsecaseController.getActivityUsecaseController(stage).showActivityEditDialog(temp);
        if (okClicked) {
            PersistentDataController.getPersistentDataController().getPersistentDataCollection().getActivityList().add(temp);
            PersistentDataController.getPersistentDataController().writeDataFile();
            refreshTable();
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected user.
     */
    @FXML
    private void handleEditActivity() {
        Activity selectedActivity = activityTable.getSelectionModel().getSelectedItem();
        if (selectedActivity != null) {
            boolean okClicked = ActivityUsecaseController.getActivityUsecaseController(stage).showActivityEditDialog(selectedActivity);
            if (okClicked) {
                showActivityDetails(selectedActivity);
                PersistentDataController.getPersistentDataController().writeDataFile();
                refreshTable();
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(stage);
            alert.setTitle("No Selection");
            alert.setHeaderText("No activity Selected");
            alert.setContentText("Please select an item in the table.");

            alert.showAndWait();
        }
    }
}
