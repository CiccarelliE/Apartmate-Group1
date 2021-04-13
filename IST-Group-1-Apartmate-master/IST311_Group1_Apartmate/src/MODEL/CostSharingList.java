package MODEL;

import java.util.ArrayList;

public class CostSharingList extends ArrayList<CostSharing> {

    public CostSharingList() {
        super();
        addSampleData();
    }

    private void addSampleData() {
        System.out.println("Adding sample data for CostSharingList");
        User testCreator = new User("Test Creator", null);
        User testData = new User("ALL", null);
        try {
            add(new CostSharing(testCreator, testData, 50.34, "This is the main text that would describe the charge. This charge was created first!"));
            //delay between adding so that it can be sorted by time properly.
            Thread.sleep(500);
            add(new CostSharing(testCreator, testData, 25.00, "This is the main text that would describe the charge. This charge was created second..."));
            Thread.sleep(500);
            add(new CostSharing(testCreator, testData, 12.50, "This is the main text that would describe the charge. This charge was created third?"));
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

public void sortByTime(boolean ascendingOrder) {
        //lamda comparator, o1 and o2 are LocalDateTime objects
        super.sort((o1, o2) -> {
            if (ascendingOrder) {
                return o1.getTimestamp().compareTo(o2.getTimestamp());
            } else {
                return -1 * o1.getTimestamp().compareTo(o2.getTimestamp());
            }
        });
    }
}
