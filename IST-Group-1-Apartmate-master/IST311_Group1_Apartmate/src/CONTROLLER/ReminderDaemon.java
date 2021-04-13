package CONTROLLER;

import MODEL.Reminder;
import MODEL.ReminderList;
import java.util.ArrayList;

/**
 * @author Cole Daubenspeck Nov 29, 2018
 */
public class ReminderDaemon extends Thread {

    //references to the core data, and what that data is being used for
    private ReminderList reminderList;
    private ArrayList<ReminderDaemonTask> taskList;

    public ReminderDaemon() {
        this(PersistentDataController.getPersistentDataController().getPersistentDataCollection().getReminderList());
    }

    public ReminderDaemon(ReminderList rList) {
        this.reminderList = rList;
        taskList = new ArrayList<>();
        setDaemon(true); //makes it so this Thread doesn't keep the program alive
        for (Reminder reminder : rList) {
            if (reminder.isTriggerable()) {
                ReminderDaemonTask rdt = new ReminderDaemonTask(reminder);
                taskList.add(rdt);
            }
        }
    }

    public void updateTasks() {
        System.out.println("ReminderDaemon is now updating");
        for (Reminder reminder : reminderList) {
            boolean needsAdded = true;
            if (reminder.isTriggerable()) { //if reminder should be triggered in the future
                for (ReminderDaemonTask rdt : taskList) {
                    if (reminder == rdt.getReminder()) { //if the reminder is already scheduled
                        needsAdded = false;
                        break;
                    }
                }
                if (needsAdded) {
                    ReminderDaemonTask rdt = new ReminderDaemonTask(reminder);
                    taskList.add(rdt);
                    run();
                }
            }
        }
    }

    @Override
    public void run() {
        int count = 0;
        for (ReminderDaemonTask rdt : taskList) {
            if (rdt.getReminder().isTriggerable()) {
                Thread taskThread = new Thread(rdt); //tasks are concurrent
                taskThread.setDaemon(true);
                taskThread.start();
                count++;
            } else {
                System.err.println("Error in the daemon casued by a reminder that isn't triggerable!");
            }
        }
        System.out.println(count + " new reminders were scheduled");
        System.out.println("Done scheduling new Reminders");
    }

}
