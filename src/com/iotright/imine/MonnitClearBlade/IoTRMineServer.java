package com.iotright.imine.MonnitClearBlade;

import com.monnit.mine.MonnitMineAPI.MineServer;
import com.monnit.mine.MonnitMineAPI.Sensor;
import com.monnit.mine.MonnitMineAPI.enums.eMineListenerProtocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IoTRMineServer {

    private static final Logger logger = LogManager.getLogger(IoTRMineServer.class.getName());

    private MineServer monnitServer;

    private static IoTRMineServer mineServer = new IoTRMineServer();

    public static IoTRMineServer getInstance() {

        if(mineServer == null) {
            mineServer = new IoTRMineServer();
        }

        if(mineServer.monnitServer == null) {

            InetAddress ip = null;                                             //stringToByteIP(gui.ipDropdown.getSelectedItem().toString()));
            try {
                ip = InetAddress.getByName("0.0.0.0");
            } catch (UnknownHostException e) {
                e.printStackTrace();
                return null;
            }

            eMineListenerProtocol Protocol;
            try {
                Protocol = eMineListenerProtocol.valueOf("TCPAndUDP");
            } catch (Exception e) {
                logger.error("Invalid Protocol: \n" + e.getMessage());
                return null;
            }

            try {
                mineServer.monnitServer = new MineServer(Protocol, ip, PropertiesManager.getPort());
            } catch (IOException e) {
                logger.error("Failed to init MinServer: \n" + e.getMessage());
                return null;
            }

        }



        return mineServer;
    }

    public static MineServer getMonnitServer() {
        return getInstance().monnitServer;
    }

    public static Sensor findSensorBySensorID(int sid) throws InterruptedException {
        Sensor sens = getInstance().monnitServer.FindSensor(sid);
        return sens;
    }

    private IoTRMineServer() {
    }
}
