package MODEL;

import CONTROLLER.TimestampGuesser;
import java.io.Serializable;
import java.security.Timestamp;
import java.time.LocalDateTime;

public class Reminder implements Serializable {

    private String title;
    private String details;
    private LocalDateTime reminderDateTime;
    
    boolean triggerable; //true if the reminder needs to be triggered in the future

    public Reminder(String title, String details, LocalDateTime dateTime) {
        this.title = title;
        this.details = details;
        this.reminderDateTime = dateTime;
        this.triggerable = true;

    }
    
    public LocalDateTime getTriggerDateTime() {
        return reminderDateTime;
    }
    
    public void setTriggerDateTime(LocalDateTime ldt) {
        this.reminderDateTime = ldt;
    }

    public String getBody() {
        return details;
    }

    public void setBody(String details) {
        this.details = details;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }
    
    public boolean isTriggerable() {
        return triggerable;
    }

    public void setTriggerable(boolean willTrigger) {
        this.triggerable = willTrigger;
    }
    
    @Override
    public String toString() {
        return TimestampGuesser.formatTimestamp(reminderDateTime) + " - " + title;
    }
}
