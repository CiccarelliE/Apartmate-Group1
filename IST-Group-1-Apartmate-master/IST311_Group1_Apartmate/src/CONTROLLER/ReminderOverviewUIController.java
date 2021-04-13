package CONTROLLER;

import MODEL.Reminder;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
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
public class ReminderOverviewUIController {

    private Stage stage;

    @FXML
    private TableView<Reminder> reminderTable;
    @FXML
    private TableColumn<Reminder, String> titleColumn;
    @FXML
    private TableColumn<Reminder, String> timestampColumn;

    @FXML
    private ObservableList<Reminder> observableActivityList;

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
    public ReminderOverviewUIController() {
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
        List<Reminder> dataList = (List<Reminder>) PersistentDataController.getPersistentDataController().getPersistentDataCollection().getReminderList();
        observableActivityList = FXCollections.observableList(dataList);
        // Initialize the person table with the two columns.
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));

        //reformats the LocalDateTime as a String using the universal format rather than just relying on the raw value of the reminder's "triggerDateTime"
        timestampColumn.setCellValueFactory((item) -> new SimpleStringProperty(TimestampGuesser.formatTimestamp(item.getValue().getTriggerDateTime())));

        reminderTable.setItems(observableActivityList);

        // Clear person details.
        showReminderDetails(null);

        // Listen for selection changes and show the person details when changed.
        reminderTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showReminderDetails(newValue));
    }

    private void refreshTable() {
        List<Reminder> dataList = (List<Reminder>) PersistentDataController.getPersistentDataController().getPersistentDataCollection().getReminderList();
        observableActivityList = FXCollections.observableList(dataList);
        reminderTable.setItems(observableActivityList);
        reminderTable.refresh();
    }

    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    private void showReminderDetails(Reminder reminder) {
        if (reminder != null) {
            // Fill the labels with info from the person object.
            //userLabel.setText(reminder.getUser().getUserName());
            titleLabel.setText(reminder.getTitle());
            dateLabel.setText(TimestampGuesser.formatTimestamp(reminder.getTriggerDateTime()));
            messageLabel.setText(reminder.getBody());
        } else {
            // user is null, remove all the text.
            //userLabel.setText("");
            titleLabel.setText("");
            dateLabel.setText("");
            messageLabel.setText("");
        }
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteReminder() {
        int selectedIndex = reminderTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            reminderTable.getItems().remove(selectedIndex);
            PersistentDataController.getPersistentDataController().writeDataFile();
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(stage);
            alert.setTitle("No Selection");
            alert.setHeaderText("No reminder Selected");
            alert.setContentText("Please select a reminder in the table.");

            alert.showAndWait();
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new user.
     */
    @FXML
    private void handleNewReminder() {
        Reminder temp = new Reminder(null, null, null);
        boolean okClicked = ReminderUsecaseController.getReminderUsecaseController(stage).showReminderEditDialog(temp);
        if (okClicked) {
            PersistentDataController.getPersistentDataController().getPersistentDataCollection().getReminderList().add(temp);
            PersistentDataController.getPersistentDataController().writeDataFile();
            refreshTable();
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected user.
     */
    @FXML
    private void handleEditReminder() {
        Reminder selectedReminder = reminderTable.getSelectionModel().getSelectedItem();
        if (selectedReminder != null) {
            boolean okClicked = ReminderUsecaseController.getReminderUsecaseController(stage).showReminderEditDialog(selectedReminder);
            if (okClicked) {
                showReminderDetails(selectedReminder);
                PersistentDataController.getPersistentDataController().writeDataFile();
                refreshTable();
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(stage);
            alert.setTitle("No Selection");
            alert.setHeaderText("No reminder Selected");
            alert.setContentText("Please select an item in the table.");

            alert.showAndWait();
        }
    }
}
