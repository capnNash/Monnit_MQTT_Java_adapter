package com.iotright.imine.MonnitClearBlade;

import com.monnit.mine.MonnitMineAPI.iPersistSensorHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * TODO determine what this does vs. non Persist
 */

public class PersistSensorHandler implements iPersistSensorHandler {


    private static final Logger logger = LogManager.getLogger(PersistSensorHandler.class.getName());
    @Override
    public void ProcessPersistSensor(long SensorID) {
//        GUIListenerFunctions.print("Sensor " + SensorID + " has been updated.");
    }

}
