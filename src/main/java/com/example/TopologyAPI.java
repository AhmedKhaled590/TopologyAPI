package com.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

/**
 * Topology API class
 */

class TopologyAPI {
    private static ArrayList<Topology> topologies = new ArrayList<Topology>();
    private static final String COMPONENTS = "components";
    private static final String NETLIST = "netlist";

    /**
     * @param topologyId
     * @return topology object with the given id or null if not found
     */

    private static Topology getTopologyByID(String topologyId) {
        for (Topology topology : topologies) {
            if (topology.getId().equals(topologyId))
                return topology;
        }
        return null;
    }

    /**
     * 
     * @param fileName name of the file to read
     * @return content of the file as a string
     * @throws IOException
     */

    private static String parseJsonFile(String fileName) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder stringBuilder = new StringBuilder();
            String str = bufferedReader.readLine();
            while (str != null) {
                stringBuilder.append(str);
                str = bufferedReader.readLine();
            }
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found");
        }
    }

    /**
     * as parameter name is the only index changing, this method is used to get it.
     * 
     * @param jsonObject toplogy object containing array of components
     * @param index      index of the component in the array
     * @return name of the component
     */

    private static String getParameterName(JSONObject jsonObject, int index) throws JSONException {
        Iterator<String> set = jsonObject.getJSONArray(COMPONENTS).getJSONObject(index).keys();
        String str = "";
        while (set.hasNext()) {
            str = set.next();
            if (!str.equals("type") && !str.equals("id") && !str.equals(NETLIST)) {
                return str;
            }
        }
        return str;
    }

    /**
     * This function is used to create a new component in the topology
     * 
     * @param topologyJsonObject topology object containing array of components
     * @param component          component object
     * @param index              index of the component in the array
     * @return new created component object
     */

    private static Component createComponent(JSONObject topologyJsonObject, JSONObject component, int index)
            throws JSONException {
        String str1 = getParameterName(topologyJsonObject, index);
        String str2 = component.getString("type");
        String str3 = component.getString("id");
        String str4 = component.getJSONObject(str1).toString();
        String str5 = component.getJSONObject(NETLIST).toString();
        return new Component(str2, str3, str1, str4, str5);
    }

    /**
     * Constructor for TopologyAPI class initializing topologies array
     */

    public TopologyAPI() {
        // topologies = new ArrayList<>();
    }

    /**
     * This function is used to read a topology from a json file and add it to the
     * topologies array
     * e.g.: readJSON("topology.json")
     * 
     * @param fileName name of the file to read
     * @return true if file is successfully read, false otherwise
     * @throws IOException, JSONException
     */

    public static boolean readJSON(String fileName) throws JSONException, IOException {
        try {
            String fileContent = parseJsonFile(fileName);
            JSONObject jSONObject = new JSONObject(fileContent);
            String topologyId = jSONObject.getString("id");

            if (getTopologyByID(topologyId) != null) {
                return false;
            }

            Component[] arrayOfComponent = new Component[jSONObject.getJSONArray(COMPONENTS).length()];
            for (byte b = 0; b < arrayOfComponent.length; b++) {
                JSONObject jSONObject1 = jSONObject.getJSONArray(COMPONENTS).getJSONObject(b);
                arrayOfComponent[b] = createComponent(jSONObject, jSONObject1, b);
            }
            topologies.add(new Topology(topologyId, arrayOfComponent));
            return true;
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println(fileNotFoundException.getMessage());
            return false;
        } catch (IOException iOException) {
            System.out.println(iOException.getMessage());
            return false;
        } catch (JSONException jsonException) {
            System.out.println(jsonException.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static void writeToJsonFile(String fileName, JSONObject jsonObject) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName + ".json"))) {
            printWriter.write(jsonObject.toString());
        } catch (Exception exception) {
            throw new IOException(exception.getMessage());
        }
    }

    /**
     * This function is used to write a topology to a json file.
     * e.g.: writeJSON("top1")
     * 
     * @param topologyId id of the topology to be written,also the name of the file
     *                   to be written
     * @return true if file is successfully written, false otherwise
     * @throws IOException
     */

    public static boolean writeJSON(String topologyId) throws IOException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", topologyId);
            JSONArray jSONArray = new JSONArray();
            Topology topology = getTopologyByID(topologyId);
            if (topology == null)
                return false;
            for (Component component : topology.getComponents()) {
                JSONObject jSONObject1 = new JSONObject();
                String str = component.getParameterName();
                jSONObject1.put("type", component.getType());
                jSONObject1.put("id", component.getId());
                jSONObject1.put(str, new JSONObject(component.getParameters()));
                jSONObject1.put(NETLIST, new JSONObject(component.getNetlist()));
                jSONArray.put(jSONObject1);
            }
            jSONObject.put(COMPONENTS, jSONArray);
            writeToJsonFile(topologyId, jSONObject);
            return true;
        } catch (Exception exception) {
            throw new IOException(exception.getMessage());
        }
    }

    /**
     * This function is used to get the topology with the given id
     * 
     * @param topologyId id of the topology to be deleted
     * @return Topology object with the given id or null if not found
     */

    public static Topology queryTopology(String topologyId) {
        return getTopologyByID(topologyId);
    }

    /**
     * This function is used to delete a topology with the given id
     * 
     * @param topologyId id of the topology to be deleted
     * @return true if topology is successfully deleted, false otherwise
     */

    public static boolean deleteTopology(String topologyId) {
        Topology topology = getTopologyByID(topologyId);
        if (topology != null) {
            ArrayList<Topology> arrayList = new ArrayList<>(topologies.size() - 1);
            for (Topology topology1 : topologies) {
                if (!topology1.getId().equals(topologyId))
                    arrayList.add(topology1);
            }
            topologies = arrayList;
            return true;
        }
        return false;
    }

    /**
     * This function is used to get the components in the topology with the given id
     * 
     * @param topologyId
     * @return array of components in the topology with the given id or null if not
     *         found
     */

    public static Component[] queryDevices(String topologyId) {
        Topology topology = getTopologyByID(topologyId);
        if (topology != null)
            return topology.getComponents();
        return null;
    }

    /**
     * This function is used to query about which devices are connected to a give
     * netlist node in a given topology.
     * 
     * @param topologyId id of the topology
     * @param netlist    netlist node
     * @return array of components connected to the netlist node or null if not
     *         found
     */

    public static ArrayList<Component> queryDevicesWithNetlistNode(String topologyId, String netlist) {
        Topology topology = getTopologyByID(topologyId);
        if (topology != null) {
            Component[] arrayOfComponent = topology.getComponents();
            ArrayList<Component> arrayList = new ArrayList<>();
            for (Component component : arrayOfComponent) {
                if (component.getNetlist().equals(netlist))
                    arrayList.add(component);
            }
            return arrayList;
        }
        return null;
    }

    /**
     * This function is used to print topology with the given id
     * 
     * @param topologyId
     */

    public static void printTopology(String topologyId) {
        Topology topology = getTopologyByID(topologyId);
        if (topology != null) {
            System.out.println("TopologyId: " + topology.getId());
            for (Component component : topology.getComponents()) {
                System.out.println("ComponentId: " + component.getId());
                System.out.println("ComponentType: " + component.getType());
                System.out.println("{" + component.getParameters() + "}");
                System.out.println("Netlist: " + component.getNetlist());
            }
        } else {
            System.out.println("Topology not found");
        }
    }

    public static int getTopologiesSize() {
        return topologies.size();
    }

}
