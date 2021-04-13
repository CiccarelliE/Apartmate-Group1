package MODEL;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Cole Daubenspeck Dec 6, 2018
 */

public class UtilityBill implements Serializable
{
    private String name; //used internally to match bill with the collection
    private LocalDate date;
    private double cost;
    
    public UtilityBill(LocalDate date, double cost) {
        this.name = "Other";
        this.date = date;
        this.cost = cost;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public double getCost() {
        return cost;
    }
    
    public void setCost(double cost) {
        this.cost = cost;
    }
    
    @Override
    public String toString() {
        return name + String.format(": $%.2f", cost);
    }
}
