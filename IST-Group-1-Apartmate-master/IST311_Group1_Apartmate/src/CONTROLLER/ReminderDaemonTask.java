package CONTROLLER;

import MODEL.Reminder;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

/**
 * @author Cole Daubenspeck Nov 29, 2018
 */
public class ReminderDaemonTask extends Task {

    private final Reminder reminder;

    private Alert alert;

    public ReminderDaemonTask(Reminder reminder) {
        alert = null;
        this.reminder = reminder;
        setOnSucceeded((event) -> {
            alert.show();
        });
    }

    public void showAlert() {
        alert.show();
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void execute() {

    }

    @Override
    protected Alert call() throws Exception {
        long millisToWait = reminder.getTriggerDateTime().toInstant(OffsetDateTime.now().getOffset()).toEpochMilli() - Instant.now().toEpochMilli();
        try {
            if (reminder.isTriggerable()) {
                if (millisToWait > 0) {
                    TimeUnit.MILLISECONDS.sleep(millisToWait);
                }

                //using Platform.runlater() will cause the code in the lambda function to execute of the JavaFX Application Thread
                Platform.runLater(() -> {
                    System.out.println("Reminder triggered: \"" + reminder.getBody() + "\"");

                    if (millisToWait <= 0) { //if the reminder was during a time when the Daemon wasn't running (its not really a daemon lol)
                        alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Missed reminder!\n\n" + reminder.getBody());
                    } else {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText(reminder.getBody());
                    }

                    alert.initModality(Modality.NONE); //doesn't block input while showing
                    alert.setTitle("Reminder for: " + TimestampGuesser.formatTimestamp(reminder.getTriggerDateTime()));
                    alert.setHeaderText(reminder.getTitle()); //so the title is easy to read.

                    
                    //Setting the buton options
                    ButtonType remindLater = new ButtonType("Tomorrow...");
                    ButtonType ok = new ButtonType("Okay");
                    alert.getButtonTypes().setAll(remindLater, ok);
                    
                    //makes it so it doesn't trigger again in the future, depending on which button was pressed
                    Optional<ButtonType> result = alert.showAndWait();
                    if (!result.isPresent()); else if (result.get() == remindLater) {
                        reminder.setTriggerable(true); //double checking. It shouldn't have changed... but...
                        reminder.setTriggerDateTime(LocalDateTime.now().plusDays(1));
                        PersistentDataController.getPersistentDataController().writeDataFile();
                        System.out.println("Reminding later at: " + TimestampGuesser.formatTimestamp(reminder.getTriggerDateTime()));
                    } else if (result.get() == ok) {
                        reminder.setTriggerable(false);
                    }
                });
                return alert;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(ReminderDaemonTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new Exception("There was an issue when creaing the Reminder alert..."); //this shouldn't every happen, but...
    }
}
