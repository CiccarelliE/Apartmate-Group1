/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

/**
 *
 * @author cyberdraco
 */
public final class DaemonMaster {

    private static DaemonMaster daemonMaster;

    private final ReminderDaemon reminderDaemon;

    public DaemonMaster() {
        System.out.println("DaemonMaster created");

        reminderDaemon = new ReminderDaemon();

        initiate();
    }

    /**
     * This getter follows the singleton design pattern and is responsible for
     * giving access to the Daemon Master.
     *
     * @return The static DaemonMaster
     */
    public static DaemonMaster getDaemonMaster() {
        if (daemonMaster == null) {
            daemonMaster = new DaemonMaster();
        }

        return daemonMaster;
    }

    public void updateDaemons() {
        System.out.println("DaemonMaster is now updating the daemons");
        reminderDaemon.updateTasks();
    }

    /**
     * Since all Daemons are Thread objects, they need to be started to
     * function. This method goes through and starts all Daemons.
     */
    private void initiate() {
        System.out.println("DaemonMaster initiated");
        reminderDaemon.start();
    }

}
