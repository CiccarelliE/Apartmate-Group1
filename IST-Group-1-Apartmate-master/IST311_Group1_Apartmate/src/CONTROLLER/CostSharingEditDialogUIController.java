package CONTROLLER;

import MODEL.CostSharing;
import MODEL.PersistentDataCollection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//Code based on example from https://code.makery.ch/library/javafx-tutorial/
public class CostSharingEditDialogUIController {

    @FXML
    private TextField userField;
    @FXML
    private TextField timestampField;
    @FXML
    private TextField costField;

    @FXML
    private TextField bodyField;

    private Stage dialogStage;
    private CostSharing costSharing;
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
     * Sets the CostSharing to be edited in the dialog.
     *
     * @param costSharing
     */
    public void setCostSharing(CostSharing costSharing) {
        this.costSharing = costSharing;

        userField.setText(costSharing.getCreator().getUserName());
        timestampField.setText(costSharing.getTimestamp().toString());
        costField.setText(costSharing.getCost().toString());
        bodyField.setText(costSharing.getBody());
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
            
            String usernameText = userField.getText();
            costSharing.setCreator(LoginUIController.getAuthenticatedUser());
            costSharing.setChargedTo(pdc.getPersistentDataCollection().getUserList().getUser(usernameText));

            try {
                costSharing.setTimestamp(TimestampGuesser.guessTimestamp(timestampField.getText().trim()));
            } catch (Exception e) {
            }
            
            costSharing.setCost(new Double(costField.getText().trim()));
            costSharing.setBody(bodyField.getText().trim());

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
        PersistentDataCollection pdc = PersistentDataController.getPersistentDataController().getPersistentDataCollection();
        if (userField.getText() == null || userField.getText().trim().length() == 0 || pdc.getUserList().getUser(userField.getText()) == null) {
            errorMessage += "Not a valid username!\n";
        }

        if (timestampField.getText() == null || timestampField.getText().trim().length() == 0) {
            errorMessage += "No valid text for a timestamp!\n";
        }
        boolean unparsable = true;
        try {
            TimestampGuesser.guessTimestamp(timestampField.getText().trim());
            unparsable = false;
        } catch (Exception ex) {
        }
        if (unparsable) {
            errorMessage += "Time format not parsable (use YYYY-MM-DD HH:MM)\n";
        }

        if (costField.getText() == null || costField.getText().trim().length() == 0) {
            errorMessage += "No cost entered!\n";
        } else {
            try {
                Double d = new Double(costField.getText().trim());
            } catch (NumberFormatException e) {
                errorMessage += "Cost is not a number!\n";
            }
        }

        if (bodyField.getText() == null || bodyField.getText().trim().length() == 0) {
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
