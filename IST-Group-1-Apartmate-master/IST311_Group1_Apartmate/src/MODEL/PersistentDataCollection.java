package MODEL;

import java.io.Serializable;

/**
 * @author Cole Daubenspeck Nov 5, 2018
 */

public class PersistentDataCollection implements Serializable
{
    //static variable for singleton pattern
    private static PersistentDataCollection dataCollection;
    
    private UserList userList;
    private ActivityList activityList;
    private ReminderList reminderList;
    private UtilityCollection utilityCollection;
    private CostSharingList costSharingList;
    
    private PersistentDataCollection() {
        //constructor will create all models if they don't already exist
        if(userList==null) {
            userList = new UserList();
        }
        if(activityList==null) {
            activityList = new ActivityList();
        }
        if(reminderList == null){
           reminderList = new ReminderList();
        }
        if(utilityCollection==null) {
            utilityCollection = new UtilityCollection();
        }
        if(costSharingList == null){
           costSharingList = new CostSharingList();
        }
    }
    
    //method to handle singleton design pattern
    public static PersistentDataCollection getPersistentDataCollection() {
        if (dataCollection == null)
            dataCollection = new PersistentDataCollection();
        return dataCollection;
    }
    
    public UserList getUserList() {
        return userList;
    }
    
    public ActivityList getActivityList() {
        return activityList;
    }
    
    public ReminderList getReminderList() {
        return reminderList;
    }
    
    public UtilityCollection getUtilityCollection() {
        return utilityCollection;
    }
    
    public CostSharingList getCostSharingList() {
        return costSharingList;
    }
}
