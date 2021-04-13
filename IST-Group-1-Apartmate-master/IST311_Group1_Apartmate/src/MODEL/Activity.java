package MODEL;

import CONTROLLER.TimestampGuesser;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Activity implements Serializable
{
    
    private User user;
    private LocalDateTime timestamp;
    private String title, body;
    
    public Activity(User creator, String title, String body) {
        this.user = creator;
        this.timestamp = LocalDateTime.now();
        this.title = title;
        this.body = body;
    }
    
    public Activity(User creator, String title, String body, LocalDateTime timestamp) {
        this.user = creator;
        this.timestamp = timestamp;
        this.title = title;
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    
}
