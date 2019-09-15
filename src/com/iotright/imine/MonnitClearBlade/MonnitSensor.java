package com.iotright.imine.MonnitClearBlade;

import com.monnit.mine.MonnitMineAPI.Sensor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MonnitSensor {

    private static final Logger logger = LogManager.getLogger(MonnitSensor.class.getName());

    private int sensorID;
    private String sensorType;
    private MonnitGateway gateway;
    private Sensor sensor;

    private String firmwareVersion;

    public MonnitSensor(MonnitGateway gateway, int sensId, String senType) {

        super();
        setGateway(gateway);
        setSensorType(senType);
        setSensorID(sensId);
        try {
            setSensor(IoTRMineServer.findSensorBySensorID(getSensorID()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Double getReportInterval() {
       return  (sensor != null) ? getSensor().getReportInterval() : null;
    }

    public Double getActiveStateInterval() {
        return  (sensor != null) ? getSensor().getActiveStateInterval() : null;
    }

    public Integer getMeasurementsPerTransmission() {
        return  (sensor != null) ? getSensor().getMeasurementsPerTransmission() : null;
    }

    public Long getMinimumThreshold() {
        return  (sensor != null) ? getSensor().getMinimumThreshold() : null;
    }

    public Long getMaximumThreshold() {
        return  (sensor != null) ? getSensor().getMaximumThreshold() : null;
    }

    public Long getHysteresis() {
        return  (sensor != null) ? getSensor().getHysteresis() : null;
    }

    public Integer getRecovery() {
        return  (sensor != null) ? getSensor().getRecovery() : null;
    }


    public int getSensorID() {
        return sensorID;
    }

    public void setSensorID(int sensorID) {
        this.sensorID = sensorID;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public MonnitGateway getGateway() {
        return gateway;
    }

    public void setGateway(MonnitGateway gateway) {
        this.gateway = gateway;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

}
