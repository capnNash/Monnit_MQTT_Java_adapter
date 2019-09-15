package com.iotright.imine.MonnitClearBlade;

import com.monnit.mine.MonnitMineAPI.Gateway;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MonnitGateway {

    private static final Logger logger = LogManager.getLogger(MonnitGateway.class.getName());

    private int gatewayID;
    private String gatewayType;
    private ArrayList<MonnitSensor> sensors;
    private String gatewayFirmwareVersion;
    private String radioFirmwareVersion;
    private String hostAddress;
    private Gateway gateway;

    public MonnitGateway(int gatewayID, String gatewayType, ArrayList<MonnitSensor> sensors, Gateway gateway) {
        this.gatewayID = gatewayID;
        this.gatewayType = gatewayType;
        this.sensors = sensors;
        this.gateway = gateway;
    }

    public void addSensor(MonnitSensor sensor) {
        getSensors().add(sensor);
    }

    public void removeSensor(MonnitSensor sensor) {
        getSensors().remove(sensor);
    }

    public int getGatewayID() {
        return gatewayID;
    }

    public void setGatewayID(int gatewayID) {
        this.gatewayID = gatewayID;
    }

    public String getGatewayType() {
        return gatewayType;
    }

    public void setGatewayType(String gatewayType) {
        this.gatewayType = gatewayType;
    }

    public ArrayList<MonnitSensor> getSensors() {
        return sensors;
    }

    public void setSensors(ArrayList<MonnitSensor> sensors) {
        this.sensors = sensors;
    }

    public Gateway getGateway() {
        return gateway;
    }

    public void setGateway(Gateway gateway) {
        this.gateway = gateway;
    }

    public String getGatewayFirmwareVersion() {
        return gatewayFirmwareVersion;
    }

    public void setGatewayFirmwareVersion(String gatewayFirmwareVersion) {
        this.gatewayFirmwareVersion = gatewayFirmwareVersion;
    }

    public String getRadioFirmwareVersion() {
        return radioFirmwareVersion;
    }

    public void setRadioFirmwareVersion(String radioFirmwareVersion) {
        this.radioFirmwareVersion = radioFirmwareVersion;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }
}
