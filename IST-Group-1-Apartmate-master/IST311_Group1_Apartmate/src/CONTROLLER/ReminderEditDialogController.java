package CONTROLLER;

import MODEL.Reminder;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//Code based on example from https://code.makery.ch/library/javafx-tutorial/
public class ReminderEditDialogController {

    @FXML
    private TextField timestampField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField bodyField;

    private Stage dialogStage;
    private Reminder reminder;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param person
     */
    public void setReminder(Reminder reminder) {
        this.reminder = reminder;

        //userField.setText(reminder.getUser().getUserName());
        if (reminder.getTriggerDateTime() != null)
            timestampField.setText(TimestampGuesser.formatTimestamp(reminder.getTriggerDateTime()));
        titleField.setText(reminder.getTitle());
        bodyField.setText(reminder.getBody());
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            PersistentDataController pdc = PersistentDataController.getPersistentDataController();
            //String usernameText = userField.getText();
            //if(pdc.getPersistentDataCollection().getUserList().getUser(usernameText)==null) {
            //    reminder.setUser(LoginUIController.getAuthenticatedUser());
            //}
            //else {
            //    reminder.setUser(pdc.getPersistentDataCollection().getUserList().getUser(usernameText));
            //}
            try {
                reminder.setTriggerDateTime(TimestampGuesser.guessTimestamp(timestampField.getText()));
            } catch (Exception e) {
                System.err.println("There was an error when parsing the timestamp for a second time?: " + timestampField.getText());
            }

            reminder.setTitle(titleField.getText());
            reminder.setBody(bodyField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        //PersistentDataCollection pdc = PersistentDataController.getPersistentDataController().getPersistentDataCollection();
        ///if (userField.getText() == null || userField.getText().length() == 0 || pdc.getUserList().getUser(userField.getText()) == null) {
        //    errorMessage += "Not a valid username!\n"; 
        //}

        if (timestampField.getText() == null || timestampField.getText().length() == 0) {
            errorMessage += "No valid text for a timestamp!\n";
        }

        try {
            TimestampGuesser.guessTimestamp(timestampField.getText());
        } catch (Exception ex) {
            errorMessage += "Time format not parsable (try YYYY-MM-DD HH:MM)\n";
        }

        if (titleField.getText() == null || titleField.getText().length() == 0) {
            errorMessage += "No valid title!\n";
        }

        if (bodyField.getText() == null || bodyField.getText().length() == 0) {
            errorMessage += "No valid body!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}
