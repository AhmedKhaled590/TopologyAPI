package com.example;

/**
 * This class represents a topology instance consisting of a unique id and array
 * of components.
 */
public class Topology {

    private String id;
    private Component[] components;

    public Topology(String id, Component[] components) {
        this.id = id;
        this.components = components;
    }

    /**
     * 
     * @return id of the topology
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @return array of components in the topology
     */

    public Component[] getComponents() {
        return components;
    }
}
