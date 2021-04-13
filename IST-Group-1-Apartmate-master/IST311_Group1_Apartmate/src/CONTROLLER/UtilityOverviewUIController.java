/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import MODEL.UtilityBill;
import MODEL.UtilityCollection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Cole Daubenspeck
 */
public class UtilityOverviewUIController implements Initializable {

    @FXML
    LineChart<String, Number> utilityChart;

    Stage stage;

    public UtilityOverviewUIController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        utilityChart.setAnimated(false);
        utilityChart.getXAxis().setLabel("Date");
        utilityChart.getYAxis().setLabel("Cost");
        utilityChart.getYAxis().setAutoRanging(true);
        utilityChart.setCursor(Cursor.CROSSHAIR);
        refresh();
    }

    public void refresh() {
        utilityChart.getData().clear();
        
        UtilityCollection utilityCollection = PersistentDataController.getPersistentDataController().getPersistentDataCollection().getUtilityCollection();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimestampGuesser.UNIFORM_DATE);
        
        Series<String, Number> electricBills = new Series<>();
        electricBills.setName(utilityCollection.getElectric().getName());
        for (UtilityBill ub : utilityCollection.getElectric()) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(ub.getDate().format(formatter), ub.getCost());
            data.setNode(new HoverDisplay(ub.getCost()));
            //System.out.println(data + " = " + data.getXValue() + " " + data.getYValue());
            electricBills.getData().add(data);
        }
//        electricBills.getData().sort((o1, o2) -> {
//            LocalDate ld1 = LocalDate.parse(o1.getXValue(), formatter);
//            LocalDate ld2 = LocalDate.parse(o2.getXValue(), formatter);
//            return ld1.compareTo(ld2);
//        });
        utilityChart.getData().add(electricBills);
        
        Series<String, Number> waterBills = new Series<>();
        waterBills.setName(utilityCollection.getWater().getName());
        for (UtilityBill ub : utilityCollection.getWater()) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(ub.getDate().format(formatter), ub.getCost());
            data.setNode(new HoverDisplay(ub.getCost()));
            //System.out.println(data + " = " + data.getXValue() + " " + data.getYValue());
            waterBills.getData().add(data);
        }
//        waterBills.getData().sort((o1, o2) -> {
//            LocalDate ld1 = LocalDate.parse(o1.getXValue(), formatter);
//            LocalDate ld2 = LocalDate.parse(o2.getXValue(), formatter);
//            return ld1.compareTo(ld2);
//        });
        utilityChart.getData().add(waterBills);
        
        Series<String, Number> gasBills = new Series<>();
        gasBills.setName(utilityCollection.getGas().getName());
        for (UtilityBill ub : utilityCollection.getGas()) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(ub.getDate().format(formatter), ub.getCost());
            data.setNode(new HoverDisplay(ub.getCost()));
            //System.out.println(data + " = " + data.getXValue() + " " + data.getYValue());
            gasBills.getData().add(data);
        }
//        gasBills.getData().sort((o1, o2) -> {
//            LocalDate ld1 = LocalDate.parse(o1.getXValue(), formatter);
//            LocalDate ld2 = LocalDate.parse(o2.getXValue(), formatter);
//            return ld1.compareTo(ld2);
//        });
        utilityChart.getData().add(gasBills);
        
        Series<String, Number> cableBills = new Series<>();
        cableBills.setName(utilityCollection.getCable().getName());
        for (UtilityBill ub : utilityCollection.getCable()) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(ub.getDate().format(formatter), ub.getCost());
            data.setNode(new HoverDisplay(ub.getCost()));
            //System.out.println(data + " = " + data.getXValue() + " " + data.getYValue());
            cableBills.getData().add(data);
        }
//        cableBills.getData().sort((o1, o2) -> {
//            LocalDate ld1 = LocalDate.parse(o1.getXValue(), formatter);
//            LocalDate ld2 = LocalDate.parse(o2.getXValue(), formatter);
//            return ld1.compareTo(ld2);
//        });
        utilityChart.getData().add(cableBills);
        
        Series<String, Number> otherBills = new Series<>();
        otherBills.setName(utilityCollection.getOther().getName());
        for (UtilityBill ub : utilityCollection.getOther()) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(ub.getDate().format(formatter), ub.getCost());
            data.setNode(new HoverDisplay(ub.getCost()));
            //System.out.println(data + " = " + data.getXValue() + " " + data.getYValue());
            otherBills.getData().add(data);
        }
//        otherBills.getData().sort((o1, o2) -> {
//            LocalDate ld1 = LocalDate.parse(o1.getXValue(), formatter);
//            LocalDate ld2 = LocalDate.parse(o2.getXValue(), formatter);
//            return ld1.compareTo(ld2);
//        });
        utilityChart.getData().add(otherBills);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void addNewUtilityBill(ActionEvent e) {
        UtilityBill ub = UtilityUsecaseController.getUtilityUsecaseController(stage).showNewUtilityBillDialog();
        if (ub != null) {
            PersistentDataController.getPersistentDataController().getPersistentDataCollection().getUtilityCollection().getByName(ub.getName()).add(ub);
            refresh();
        }
    }
    
    private class HoverDisplay extends StackPane {
        public HoverDisplay(Double value) {
            setPrefSize(15, 15);
            
            final Label label = new Label("$" + String.format("%.2f", value));
            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
            label.setStyle("-fx-font-size: 12; -fx-font-weight: bold; -fx-border-color: BLACK;");
            
            setOnMouseEntered((event) -> {
                getChildren().setAll(label);
                setCursor(Cursor.NONE);
                toFront();
            });
            
            setOnMouseExited((event) -> {
                getChildren().clear();
            });
        }
    }
}
