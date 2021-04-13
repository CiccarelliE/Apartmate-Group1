package CONTROLLER;

import MODEL.CostSharing;
import java.util.ArrayList;
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
public class CostSharingOverviewUIController {
    
    private Stage stage;
    
    @FXML
    private TableView<CostSharing> costSharingTable;
    @FXML
    private TableColumn<CostSharing, String> userColumn;
    @FXML
    private TableColumn<CostSharing, String> costColumn;
    
    @FXML
    private ObservableList<CostSharing> observableCostSharingList;

    @FXML
    private Label userLabel;
    @FXML
    private Label costLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label messageLabel;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public CostSharingOverviewUIController() {
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
        List<CostSharing> dataList = (List<CostSharing>)PersistentDataController.getPersistentDataController().getPersistentDataCollection().getCostSharingList();

        
        // Initialize the person table with the two columns.
        userColumn.setCellValueFactory(new PropertyValueFactory<>("Creator"));
        
        costColumn.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        
        //only share YOUR charges
        observableCostSharingList = FXCollections.observableList(new ArrayList<>());
        for(CostSharing cs : dataList) {
            if(cs.getChargedTo().getUserName().equals("ALL") || cs.getChargedTo() == LoginUIController.getAuthenticatedUser()) {
                observableCostSharingList.add(cs);
            }
        }
        costSharingTable.setItems(observableCostSharingList);
        
        // Clear person details.
        showCostSharingDetails(null);

        // Listen for selection changes and show the person details when changed.
        costSharingTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCostSharingDetails(newValue));
    }
    
    private void refreshTable() {
        List<CostSharing> dataList = (List<CostSharing>)PersistentDataController.getPersistentDataController().getPersistentDataCollection().getCostSharingList();
        
        //only share YOUR charges
        observableCostSharingList = FXCollections.observableList(new ArrayList<>());
        for(CostSharing cs : dataList) {
            if(cs.getChargedTo().getUserName().equals("ALL") || cs.getChargedTo() == LoginUIController.getAuthenticatedUser()) {
                observableCostSharingList.add(cs);
            }
        }
        
        costSharingTable.setItems(observableCostSharingList);
        costSharingTable.refresh();
    }
    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     * 
     * @param person the person or null
     */
    private void showCostSharingDetails(CostSharing costSharing) {
        if (costSharing != null) {
            // Fill the labels with info from the person object.
            userLabel.setText(costSharing.getCreator().getUserName());
            costLabel.setText(costSharing.getCost().toString());
            dateLabel.setText(costSharing.getFormattedTimestamp());
            messageLabel.setText(costSharing.getBody());
        } else {
            // user is null, remove all the text.
            userLabel.setText("");
            costLabel.setText("");
            dateLabel.setText("");
            messageLabel.setText("");
        }
    }
    
    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteCostSharing() {
        int selectedIndex = costSharingTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            costSharingTable.getItems().remove(selectedIndex);
            PersistentDataController.getPersistentDataController().writeDataFile();
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(stage);
            alert.setTitle("No Selection");
            alert.setHeaderText("No activity Selected");
            alert.setContentText("Please select an activity in the table.");
            
            alert.showAndWait();
        }
    }
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new user.
     */
    @FXML
    private void handleNewCostSharing() {
        CostSharing temp = new CostSharing(LoginUIController.getAuthenticatedUser(), LoginUIController.getAuthenticatedUser(), 0, "");
        boolean okClicked = CostSharingUsecaseController.getCostSharingUsecaseController(stage).showCostSharingEditDialog(temp);
        if (okClicked) {
            PersistentDataController.getPersistentDataController().getPersistentDataCollection().getCostSharingList().add(temp);
            PersistentDataController.getPersistentDataController().writeDataFile();
            refreshTable();
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected user.
     */
    @FXML
    private void handleEditCostSharing() {
        CostSharing selectedCostSharing = costSharingTable.getSelectionModel().getSelectedItem();
        if (selectedCostSharing != null) {
            boolean okClicked = CostSharingUsecaseController.getCostSharingUsecaseController(stage).showCostSharingEditDialog(selectedCostSharing);
            if (okClicked) {
                showCostSharingDetails(selectedCostSharing);
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