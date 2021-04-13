/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import MODEL.UtilityBill;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author cyberdraco
 */
public class UtilityBillDialogUIController implements Initializable{
            
    @FXML
    TextField dateField;
    @FXML
    ChoiceBox<String> typeSelection;
    @FXML
    TextField costField;
    
    private Stage dialogStage;
    private UtilityBill utilityBill;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeSelection.setItems(FXCollections.observableArrayList(
                "Electric", "Water", "Gas", "TV/Internet", "Other")
        );
    }
    
    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }
    
    public UtilityBill getUtilityBill() {
        return utilityBill;
    }
    
    public boolean isInputValid() {
        String errorMessage = "";
        
        if (dateField.getText().trim().length() == 0) {
            errorMessage += "The date field is empty!\n";
        } else {
            try {
                TimestampGuesser.guessDate(dateField.getText().trim());
            } catch (Exception e) {
                errorMessage += "The date format could not be parsed! Try YYYY-MM-DD\n";
            }
        }
        
        if (costField.getText().trim().length() == 0) {
            errorMessage += "The cost field is empty!\n";
        } else {
            try {
                Double d = new Double(costField.getText().trim());
            } catch (NumberFormatException e) {
                errorMessage += "The cost field is not a valid number!\n";
            }
        }
        
        if (typeSelection.getValue() == null) {
            errorMessage += "There is not type selected!\n";
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid input");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    
    @FXML
    public void handleOk(ActionEvent actionEvent) {
        if (isInputValid()) {
            try {
                utilityBill = new UtilityBill(TimestampGuesser.guessDate(dateField.getText().trim()), new Double(costField.getText().trim()));
                utilityBill.setName(typeSelection.getValue());
                System.out.println("New bill created!\n" + utilityBill);
                
                PersistentDataController.getPersistentDataController().getPersistentDataCollection().getUtilityCollection().getByName(utilityBill.getName()).add(utilityBill);
                PersistentDataController.getPersistentDataController().writeDataFile();
                dialogStage.close();
            } catch (Exception e) {
                System.err.println("There was a problem with adding input that was verified to be valid!");
                System.err.println(e.getMessage());
                System.err.println(e.getCause());
            }
        }
    }
    
    @FXML
    public void handleCancel(ActionEvent actionEvent) {
        dialogStage.close();
    }
}
