package CONTROLLER;

import MODEL.User;
import MODEL.UserList;
import java.awt.Color;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

//Code based on example from https://code.makery.ch/library/javafx-tutorial/
public class UserManagementUIController {

    private Stage stage;

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> userColumn;

    @FXML
    private ObservableList<User> observableUserList;

    @FXML
    private Label currentUsername;
    @FXML
    private Label messageLabel;

    @FXML
    private TextField userField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private PasswordField enterPasswordField;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public UserManagementUIController() {
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
        List<User> dataList = (List<User>) PersistentDataController.getPersistentDataController().getPersistentDataCollection().getUserList();
        observableUserList = FXCollections.observableList(dataList);
        // Initialize the person table with the two columns.
        userColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        userTable.setItems(observableUserList);

        // Clear person details.
        showUserDetails(null);

        // Listen for selection changes and show the person details when changed.
        userTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showUserDetails(newValue));
    }

    private void refreshTable() {
        List<User> dataList = (List<User>) PersistentDataController.getPersistentDataController().getPersistentDataCollection().getUserList();
        observableUserList = FXCollections.observableList(dataList);
        userTable.setItems(observableUserList);
        userTable.refresh();
    }

    private boolean authenticateForChanges() {
        UserList uList = PersistentDataController.getPersistentDataController().getPersistentDataCollection().getUserList();
        boolean result = uList.authenticate(LoginUIController.getAuthenticatedUser().getUserName(), enterPasswordField.getText());
        enterPasswordField.setText(null);
        if (!result) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(stage);
            alert.setTitle("Can't Authenticate!");
            alert.setHeaderText("Bad login info!");
            alert.setContentText("Your password is incorrect. You need to authenticate to make changes!");

            alert.showAndWait();
        }
        return result;
    }

    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    private void showUserDetails(User user) {
        currentUsername.setText(LoginUIController.getAuthenticatedUser().getUserName());
        if (user != null) {
            // Fill the labels with info from the person object.
            userField.setText(user.getUserName());
            String pText = user.getPassword();
            passwordField.setText(pText);
            repeatPasswordField.setText(pText);
        } else {
            // user is null, remove all the text.
            userField.setText("");
            passwordField.setText("");
            repeatPasswordField.setText("");
        }
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteUser() {
        int selectedIndex = userTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            if (authenticateForChanges()) {
                userTable.getItems().remove(selectedIndex);
                PersistentDataController.getPersistentDataController().writeDataFile();

                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.initOwner(stage);
                alert.setTitle("Done");
                alert.setHeaderText("User Deleted");
                alert.setContentText("The user has been deleted and the data was saved.");

                alert.showAndWait();
            }
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
    private void handleNewUser() {
        UserList uList = PersistentDataController.getPersistentDataController().getPersistentDataCollection().getUserList();
        User blankUser = new User("new user", "");
        uList.add(blankUser);
        refreshTable();
        showUserDetails(blankUser);
        userTable.getSelectionModel().select(blankUser);

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initOwner(stage);
        alert.setTitle("Done");
        alert.setHeaderText("User Created");
        alert.setContentText("User created. Go ahead and change their username and password now!");

        alert.showAndWait();
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected user.
     */
    @FXML
    private void handleEditUser() {

        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            if (authenticateForChanges()) {
                UserList uList = PersistentDataController.getPersistentDataController().getPersistentDataCollection().getUserList();
                boolean doesntExist = true;
                for(User u : uList) {
                    if (userField.getText().trim().equals(u.getUserName())) {
                        doesntExist = false;
                    }
                }
                if (doesntExist && passwordField.getText().equals(repeatPasswordField.getText())) {
                    System.out.println("User infomation was: ");
                    System.out.println("\t---> User: " + selectedUser.getUserName());
                    System.out.println("\t---> Pass: " + selectedUser.getPassword());

                    selectedUser.setUserName(userField.getText());
                    selectedUser.setPassword(passwordField.getText());
                    PersistentDataController.getPersistentDataController().writeDataFile();
                    refreshTable();

                    System.out.println("User infomation is now: ");
                    System.out.println("\t---> User: " + selectedUser.getUserName());
                    System.out.println("\t---> Pass: " + selectedUser.getPassword());

                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.initOwner(stage);
                    alert.setTitle("Done");
                    alert.setHeaderText("User Changed");
                    alert.setContentText("The user information has been changed and the data has been saved.");

                    alert.showAndWait();

                } else {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.initOwner(stage);
                    alert.setTitle("Error changing info!");
                    if(doesntExist) {
                        alert.setHeaderText("Passwords don't match!");
                        alert.setContentText("Make sure both passwords are typed correctly");
                    }
                    else {
                        alert.setHeaderText("Username is already taken");
                        alert.setContentText("The username already is in use by another user.");
                    }

                    alert.showAndWait();
                }
            }

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
}
