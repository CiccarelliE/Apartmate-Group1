package MODEL;

import java.util.ArrayList;

public class UserList extends ArrayList<User> {

    public UserList() {
        super();
        addDefaultAdminUser();
        addSampleData();
    }
    
    private void addDefaultAdminUser() {
        add(new User("admin", "changeme"));
    }
    
    private void addSampleData() {
        System.out.println("Adding sample data for UserList");
        add(new User("test", "test"));
        add(new User("hello there", "general kenobi"));
    }

    public boolean authenticate(String username, String password) {
        //for every user in this list
        for (User user : this) {
            //check to see if there is a matching username and password
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    //this prevents users with matching usernames from being added to the list
    @Override
    public boolean add(User newUser) {
        for (User user : this) {
            if (user.getUserName().equals(newUser.getUserName())) {
                return false;
            }
        }
        return super.add(newUser);
    }
    
    public User getUser(String username) {
        for(User user : this) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        String result = "User List Contains: \n";
        for (User u : this) {
            result += u.getUserName() + " (" + u.getPassword() + ")\n";
        }
        return result.trim();
    }
}
