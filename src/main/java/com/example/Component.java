package com.example;

/**
 * This class represents a component instance inside array components in a
 * topology.
 * 
 * @see Topology
 */

public class Component {
    private String type;
    private String id;
    private String parameterName;
    private String parameters;
    private String netlist;

    /**
     * 
     * @param type          type of the component (e.g.:"resistor", "capacitor",
     *                      "nmos")
     * @param id            unique id of the component
     * @param parameterName name of the component
     * @param parameters    parameters object (key-value pairs) for the component
     *                      (e.g.:"default"="1.0", "min"="0.0", "max"="10.0")
     * @param netlist       netlist attached with this component
     */

    public Component(String type, String id, String parameterName, String parameters, String netlist) {
        this.type = type;
        this.id = id;
        this.parameterName = parameterName;
        this.parameters = parameters;
        this.netlist = netlist;
    }

    /*
     * Getters and setters
     */

    /**
     * for parameters details
     * 
     * @see Component
     */
    public void setComponent(String type, String id, String parameterName, String parameters, String netlist) {
        this.type = type;
        this.id = id;
        this.parameterName = parameterName;
        this.parameters = parameters;
        this.netlist = netlist;
    }

    /**
     * 
     * @return id of the component
     */

    public String getId() {
        return id;
    }

    /**
     * 
     * @return type of the component
     */

    public String getType() {
        return type;
    }

    /**
     * 
     * @return netlist attached with this component
     */

    public String getNetlist() {
        return netlist;
    }

    /**
     * 
     * @return parameters object (key-value pairs) for the component
     *         (e.g.:"default"="1.0", "min"="0.0", "max"="10.0")
     */

    public String getParameters() {
        return parameters;
    }

    /**
     * 
     * @return name of the component used to index parameters
     */

    public String getParameterName() {
        return parameterName;
    }

}
