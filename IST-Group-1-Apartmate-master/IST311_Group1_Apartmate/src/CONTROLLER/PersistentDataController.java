package CONTROLLER;

import MODEL.PersistentDataCollection;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PersistentDataController 
{
    //default file path info
    private static final String ROOT_DATA_PATH = "";
    private static final String FILE_NAME = "ApartmateData.ser";

    private static PersistentDataCollection persistentDataCollection;
    private static PersistentDataController persistentDataController;
    
    private PersistentDataController() {
        readDataFile();
        if(persistentDataCollection==null) {
                System.err.println("Data file does not exist/there was an error. Rebuilding the data!");
                persistentDataCollection = PersistentDataCollection.getPersistentDataCollection();
                writeDataFile();
                readDataFile();
        }
    }
    
    public static PersistentDataController getPersistentDataController() {
        if (persistentDataController==null) {
            persistentDataController = new PersistentDataController();
        }
        return persistentDataController;
    }
    
    public PersistentDataCollection getPersistentDataCollection() {
        return persistentDataCollection;
    }
    
    protected void readDataFile() {
        String absFilePath = ROOT_DATA_PATH+FILE_NAME;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(absFilePath));
            persistentDataCollection = (PersistentDataCollection) ois.readObject();
            ois.close();
            
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("There was an error reading data file at " + absFilePath);
        }
    }
    
    protected void writeDataFile() {
        String absFilePath = ROOT_DATA_PATH+FILE_NAME;
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(absFilePath));
            oos.writeObject(persistentDataCollection);
            oos.close();
            if(LoginUIController.getAuthenticatedUser()!=null)
                DaemonMaster.getDaemonMaster().updateDaemons(); //if the PersistentData is written, and any user is authenticated, 
            System.out.println("Done writing application data to " + absFilePath);
        } catch (IOException ex) {
            System.err.println("There was an error writing to " + absFilePath);
        }
        
    }
}