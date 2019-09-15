package com.iotright.imine.MonnitClearBlade;

import com.monnit.mine.MonnitMineAPI.enums.eSensorApplication;
import com.monnit.mine.MonnitMineAPI.iUnknownSensorHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UnknownSensorHandler implements iUnknownSensorHandler {

    private static final Logger logger = LogManager.getLogger(UnknownSensorHandler.class.getName());
    @Override
    public void ProcessUnknownSensor(long SensorID, eSensorApplication type) {
        eSensorApplication esensortype = type;
        try {
            //TODO finish this
//            GUIListenerFunctions.print("Unregistered Type:" + esensortype.toString() + ", SensorID: " + SensorID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ProcessUnknownSensor(int arg0) {
        // TODO Auto-generated method stub

    }
}
