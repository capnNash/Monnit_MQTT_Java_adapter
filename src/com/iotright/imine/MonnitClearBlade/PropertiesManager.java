/**
 * singleton class for managing access to all application and environment properties required for the application
 */

package com.iotright.imine.MonnitClearBlade;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {

    private static final Logger logger = LogManager.getLogger(PropertiesManager.class.getName());

    public static final String SYSTEM_KEY = "systemKey";
    public static final String SYSTEM_SECRET = "systemSecret";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String PLATFORM_URL = "platformURL";
    public static final String MESSAGING_URL = "messagingURL";
    public static final String SENSORID = "sensorid";
    public static final String CLIENT_ID = "clientID";
    public static final String SENSOR_TOPIC = "sensorTopicPath";
    public static final String SENSOR_UPDATE_TOPIC = "updateSensorTopicPath";
    public static final String MONNIT_PORT = "monnitPort";
    public static final String DEFAULT_SENSOR_HEARTBEAT = "defaultSensorHeartbeat";

    private static PropertiesManager singleInstance = new PropertiesManager();

    private Properties props = null;

    public static PropertiesManager getInstance() {

        if(singleInstance == null) {
            singleInstance = new PropertiesManager();
        }
        return singleInstance;
    }

    private PropertiesManager() {

        this.props = new Properties();

        try {
            logger.debug("The properties path env var: " + System.getProperty("properties"));
            this.props.load(new FileInputStream(System.getProperty("properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getProperty(String propertyKey) {

        return singleInstance.props.getProperty(propertyKey);
    }

    public static int getPort() {
        return Integer.parseInt(singleInstance.props.getProperty(MONNIT_PORT));
    }
}
