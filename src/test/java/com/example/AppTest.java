package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.nio.file.Files;

import org.junit.Test;

/**
 * Unit test for simple App.
 */

public class AppTest {
    @Test
    public void readJsonFileSuccessfuly() throws Exception {
        TopologyAPI.deleteTopology("top1");
        assertEquals(0, TopologyAPI.getTopologiesSize());
        TopologyAPI.readJSON("topology.json");
        assertEquals(1, TopologyAPI.getTopologiesSize());
    }

    @Test
    public void readJsonFileWithError() throws Exception {
        TopologyAPI.deleteTopology("top1");
        assertEquals(0, TopologyAPI.getTopologiesSize());
        assertEquals(false, TopologyAPI.readJSON("topology_error.json"));
        assertEquals(0, TopologyAPI.getTopologiesSize());
    }

    @Test
    public void readJsonFileWithWrongFormat() throws Exception {
        TopologyAPI.deleteTopology("top1");
        assertEquals(0, TopologyAPI.getTopologiesSize());
        assertEquals(false, TopologyAPI.readJSON("t.json"));
        assertEquals(0, TopologyAPI.getTopologiesSize());
    }

    @Test
    public void writeJSONFileSuccessfuly() throws Exception {
        TopologyAPI.deleteTopology("top1");
        // check if file exists before writing
        File jsonFile = new File("top1.json");
        boolean fileExists = jsonFile.exists();
        if (fileExists) {
            jsonFile.delete();
        }
        assertEquals(false, jsonFile.exists());
        TopologyAPI.readJSON("topology.json");
        assertEquals(true, TopologyAPI.writeJSON("top1"));
        assertEquals(true, jsonFile.exists());
    }

    @Test
    public void writeJSONFileWithError() throws Exception {
        TopologyAPI.deleteTopology("top1");
        File jsonFile = new File("top1.json");
        boolean fileExists = jsonFile.exists();
        if (fileExists) {
            jsonFile.delete();
        }
        assertEquals(false, jsonFile.exists());
        assertEquals(false, TopologyAPI.writeJSON("top1"));
        assertEquals(false, jsonFile.exists());
    }

    @Test
    public void queryTopologySuccessfuly() throws Exception {
        TopologyAPI.readJSON("topology.json");
        assertNotNull(TopologyAPI.queryTopology("top1"));
    }

    @Test
    public void queryNotFoundTopology() throws Exception {
        TopologyAPI.readJSON("topology.json");
        assertNull(TopologyAPI.queryTopology("top2"));
    }

    @Test
    public void deleteTopologySuccessfuly() throws Exception {
        TopologyAPI.readJSON("topology.json");
        assertEquals(1, TopologyAPI.getTopologiesSize());
        TopologyAPI.deleteTopology("top1");
        assertEquals(0, TopologyAPI.getTopologiesSize());
    }

    @Test
    public void deleteNotFoundTopology() throws Exception {
        TopologyAPI.readJSON("topology.json");
        assertEquals(1, TopologyAPI.getTopologiesSize());
        TopologyAPI.deleteTopology("top2");
        assertEquals(1, TopologyAPI.getTopologiesSize());
    }

    @Test
    public void queryDevicesSuccessfuly() throws Exception {
        TopologyAPI.deleteTopology("top1");
        TopologyAPI.readJSON("topology.json");
        assertNotNull(TopologyAPI.queryDevices("top1"));
    }

    @Test
    public void queryNotFoundDevices() throws Exception {
        TopologyAPI.deleteTopology("top1");
        TopologyAPI.readJSON("topology.json");
        assertNull(TopologyAPI.queryDevices("top2"));
    }

    @Test
    public void queryDevicesWithNetlistSuccessfuly() throws Exception {
        TopologyAPI.deleteTopology("top1");
        TopologyAPI.readJSON("topology.json");
        assertNotNull(TopologyAPI.queryDevicesWithNetlistNode("top1",
                TopologyAPI.queryTopology("top1").getComponents()[0].getNetlist()));
    }

    @Test
    public void queryNotFoundDevicesWithNetlist() throws Exception {
        TopologyAPI.deleteTopology("top1");
        TopologyAPI.readJSON("topology.json");
        assertNull(TopologyAPI.queryDevicesWithNetlistNode("top2",
                TopologyAPI.queryTopology("top1").getComponents()[0].getNetlist()));
    }

}
