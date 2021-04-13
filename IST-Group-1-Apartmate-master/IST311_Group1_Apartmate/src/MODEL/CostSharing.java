package MODEL;

import CONTROLLER.TimestampGuesser;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CostSharing implements Serializable
{
    
    private User user;
    private User chargedTo;
    private LocalDateTime timestamp;
    private String body;
    private double cost; 
    
    public CostSharing(User creator, User chargedTo, double cost, String body) {
        this.user = creator;
        this.chargedTo = chargedTo;
        this.timestamp = LocalDateTime.now();
        this.cost = cost;
        this.body = body;
    }
    
    public CostSharing(User creator, User chargedTo, double cost, String body, LocalDateTime timestamp) {
        this.user = creator;
        this.chargedTo = chargedTo;
        this.timestamp = timestamp;
        this.cost = cost;
        this.body = body;
    }

    public User getCreator() {
        return user;
    }

    public void setCreator(User user) {
        this.user = user;
    }
    
    public User getChargedTo() {
        return chargedTo;
    }
    
    public void setChargedTo(User chargedTo) {
        this.chargedTo = chargedTo;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TimestampGuesser.UNIFORM_TIMESTAMP);
        return formatter.format(timestamp);
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
}
