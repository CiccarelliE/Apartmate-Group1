package MODEL;

import CONTROLLER.TimestampGuesser;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Cole Daubenspeck Dec 6, 2018
 */
public class UtilityCollection implements Serializable {

    private UtilityHistory electric, water, gas, cable, other;

    public UtilityCollection() {
        this.electric = new UtilityHistory("Electric");
        this.water = new UtilityHistory("Water");
        this.gas = new UtilityHistory("Gas");
        this.cable = new UtilityHistory("TV/Internet");
        this.other = new UtilityHistory("Other");
        
        addSampleData();
    }

    public UtilityHistory getByName(String name) {
        switch (name) {
            case "Electric":
                return getElectric();
            case "Water":
                return getWater();
            case "Gas":
                return getGas();
            case "TV/Internet":
                return getCable();
            case "Other":
                return getOther();
        }
        return null;
    }

    public UtilityHistory getElectric() {
        return electric;
    }

    public UtilityHistory getWater() {
        return water;
    }

    public UtilityHistory getGas() {
        return gas;
    }

    public UtilityHistory getCable() {
        return cable;
    }

    public UtilityHistory getOther() {
        return other;
    }

    public void addSampleData() {
        System.out.println("Adding sample data for UtilityCollection");
        try {
            LocalDate date1 = TimestampGuesser.guessDate("2018-01-01");
            electric.add(new UtilityBill(date1, 111.11));
            water.add(new UtilityBill(date1, 65.59));
            gas.add(new UtilityBill(date1, 23.67));
            cable.add(new UtilityBill(date1, 90));
            other.add(new UtilityBill(date1, 0));
            
            LocalDate date2 = TimestampGuesser.guessDate("2018-02-01");
            electric.add(new UtilityBill(date2, 135.19));
            water.add(new UtilityBill(date2, 72.59));
            gas.add(new UtilityBill(date2, 27.67));
            cable.add(new UtilityBill(date2, 90));
            other.add(new UtilityBill(date2, 0));
            
            LocalDate date3 = TimestampGuesser.guessDate("2018-03-01");
            electric.add(new UtilityBill(date3, 127.51));
            water.add(new UtilityBill(date3, 72.59));
            gas.add(new UtilityBill(date3, 27.67));
            cable.add(new UtilityBill(date3, 90));
            other.add(new UtilityBill(date3, 0));
            
            LocalDate date4 = TimestampGuesser.guessDate("2018-04-01");
            electric.add(new UtilityBill(date4, 135.11));
            water.add(new UtilityBill(date4, 61.97));
            gas.add(new UtilityBill(date4, 27.67));
            cable.add(new UtilityBill(date4, 90));
            other.add(new UtilityBill(date4, 0));
            
            LocalDate date5 = TimestampGuesser.guessDate("2018-05-01");
            electric.add(new UtilityBill(date5, 165.11));
            water.add(new UtilityBill(date5, 46.71));
            gas.add(new UtilityBill(date5, 27.67));
            cable.add(new UtilityBill(date5, 110));
            other.add(new UtilityBill(date5, 0));
            
            LocalDate date6 = TimestampGuesser.guessDate("2018-06-01");
            electric.add(new UtilityBill(date6, 168.11));
            water.add(new UtilityBill(date6, 42.56));
            gas.add(new UtilityBill(date6, 19.32));
            cable.add(new UtilityBill(date6, 110));
            other.add(new UtilityBill(date6, 0));
        } catch (Exception e) {
            System.err.println("Someone messed up coding the sample data for UtilityCollection... :/");
        }
    }
}
