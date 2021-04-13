package MODEL;

import java.util.ArrayList;

public class ActivityList extends ArrayList<Activity>
{
    public ActivityList() {
        super();
        addSampleData();
    }
    
    private void addSampleData() {
        try {
            System.out.println("Adding sample data for ActivityList");
            User nobody = new User("nobody", "");
            add(new Activity(nobody, "Test Activity 1", "This is the main text that would describe the activity. This activity was created first!"));
            //delay between adding so that it can be sorted by time properly.
            Thread.sleep(1000);
            add(new Activity(nobody, "Test Activity 2", "This is the main text that would describe the activity. This activity was created second..."));
            Thread.sleep(1000);
            add(new Activity(nobody, "Test Activity 3", "This is the main text that would describe the activity. This activity was created third?"));
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    public void sortByTime(boolean ascendingOrder) {
        //lamda comparator, o1 and o2 are LocalDateTime objects
        super.sort((o1, o2) -> {
            if(ascendingOrder)
                return o1.getTimestamp().compareTo(o2.getTimestamp());
            else
                return -1 * o1.getTimestamp().compareTo(o2.getTimestamp());
        });
    }
}
