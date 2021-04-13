package MODEL;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReminderList extends ArrayList<Reminder> {

    public ReminderList() {
        super();
        addSampleData();
    }

    private void addSampleData() {
        System.out.println("Adding sample data for ReminderList");
        try {
            //reminder in the far past
            add(new Reminder("First Launch?", "This reminder is set in the far past and should always trigger at first launch",  CONTROLLER.TimestampGuesser.guessTimestamp("1970-01-01T00:00:01")));
            //reminder in the future
            add(new Reminder("It's 2019!", "Happy new year!", CONTROLLER.TimestampGuesser.guessTimestamp("2019-01-01 00:00:01")));
            //reminder in the near future
            add(new Reminder("", "This is a test reminder which should trigger 15 seconds after login", LocalDateTime.now().plusSeconds(15)));
        } catch (Exception e) {
            System.err.println("Timestamps in the sample Reminder are not valid!");
            System.err.println(e.getMessage());
        }
    }

}