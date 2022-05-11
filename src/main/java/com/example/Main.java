package com.example;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        try {

            TopologyAPI topologyAPI = new TopologyAPI();
            topologyAPI.readJSON("topology.json");
            topologyAPI.readJSON("topology2.json");
            System.out.println("************Topology 1: ***********************");
            topologyAPI.printTopology("top1");

            System.out.println("************Topology 2: ***********************");
            topologyAPI.printTopology("top2");

            Component[] components = topologyAPI.queryDevices("top1");

            ArrayList<Component> components0 = topologyAPI.queryDevicesWithNetlistNode("top1",
                    components[0].getNetlist());
            System.out.println(
                    "************Components connected to node " + components[0].getNetlist()
                            + ": ***********************");
            for (Component c : components0) {
                System.out.println(c.getId());
            }

            System.out.println("Writing to JSON file ...");
            topologyAPI.writeJSON("top1");

            System.out.println("Writing to JSON file ...");
            topologyAPI.writeJSON("top2");

            System.out.println("Deleting topology ...");
            topologyAPI.deleteTopology("top1");
            topologyAPI.printTopology("top1");

            System.out.println("Deleting topology ...");
            topologyAPI.deleteTopology("top2");
            topologyAPI.printTopology("top2");

        } catch (

        Exception e) {
            Logger logger = Logger.getLogger(Main.class.getName());
            logger.log(java.util.logging.Level.SEVERE, null, e);
        }
    }

}
