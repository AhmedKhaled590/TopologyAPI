package com.example;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        try {

            boolean readState1 = TopologyAPI.readJSON("topology.json");
            checkReadState(readState1);

            boolean readState2 = TopologyAPI.readJSON("topology.json");
            checkReadState(readState2);

            boolean readState3 = TopologyAPI.readJSON("topology2.json");
            checkReadState(readState3);

            System.out.println("************Topology 1: ***********************");
            TopologyAPI.printTopology("top1");

            System.out.println("************Topology 2: ***********************");
            TopologyAPI.printTopology("top2");

            Component[] components = TopologyAPI.queryDevices("top1");

            ArrayList<Component> components0 = TopologyAPI.queryDevicesWithNetlistNode("top1",
                    components[0].getNetlist());
            System.out.println(
                    "************Components connected to node " + components[0].getNetlist()
                            + ": ***********************");
            for (Component c : components0) {
                System.out.println(c.getId());
            }

            System.out.println("Writing to JSON file ...");
            TopologyAPI.writeJSON("top1");

            System.out.println("Writing to JSON file ...");
            TopologyAPI.writeJSON("top2");

            System.out.println("Deleting topology ...");
            TopologyAPI.deleteTopology("top1");
            TopologyAPI.printTopology("top1");

            System.out.println("Deleting topology ...");
            TopologyAPI.deleteTopology("top2");
            TopologyAPI.printTopology("top2");

        } catch (

        Exception e) {
            Logger logger = Logger.getLogger(Main.class.getName());
            logger.log(java.util.logging.Level.SEVERE, null, e);
        }
    }

    private static void checkReadState(boolean state) {
        if (state) {
            System.out.println("Topology read successfully");
        } else {
            System.out.println("Topology read failed");
        }

    }

}
