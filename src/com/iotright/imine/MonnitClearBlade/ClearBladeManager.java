package com.iotright.imine.MonnitClearBlade;

import com.clearblade.java.api.ClearBlade;
import com.clearblade.java.api.ClearBladeException;
import com.clearblade.java.api.InitCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;


public class ClearBladeManager {

    private static final Logger logger = LogManager.getLogger(ClearBladeManager.class.getName());

    private static ClearBladeManager cbInstance = new ClearBladeManager();



    public static ClearBladeManager getInstance() {

        if(cbInstance == null) {
            cbInstance = new ClearBladeManager();
        }

        return cbInstance;
    }

    public void initializePlatform() {

        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put(PropertiesManager.USER_EMAIL, PropertiesManager.getProperty(PropertiesManager.USER_EMAIL));
        options.put(PropertiesManager.USER_PASSWORD, PropertiesManager.getProperty(PropertiesManager.USER_PASSWORD));
        options.put(PropertiesManager.PLATFORM_URL, PropertiesManager.getProperty(PropertiesManager.PLATFORM_URL));
        options.put(PropertiesManager.MESSAGING_URL, PropertiesManager.getProperty(PropertiesManager.MESSAGING_URL));
        options.put("logging", true);

        ClearBlade.initialize(PropertiesManager.getProperty(PropertiesManager.SYSTEM_KEY), PropertiesManager.getProperty(PropertiesManager.SYSTEM_SECRET), options, new InitCallback() {

            @Override
            public void done(boolean results) {

                logger.info("ClearBlade platform initialized, with results: " + results);
                //isInit = true;
            }

            @Override
            public void error(ClearBladeException error) {
                logger.error("The CB init... error message: " + error.getMessage());
            }
        });
    }

    private ClearBladeManager() {

    }
}
