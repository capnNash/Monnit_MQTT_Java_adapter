package com.iotright.imine.MonnitClearBlade;

import com.monnit.mine.MonnitMineAPI.iPersistGatewayHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TODO determine what this does vs. non Persist
 */
public class PersistGatewayHandler implements iPersistGatewayHandler {

    private static final Logger logger = LogManager.getLogger(PersistGatewayHandler.class.getName());

    @Override
    public void ProcessPersistGateway(long GatewayID) {
        logger.debug("ProcessPersistGateway for gateway with id: " + GatewayID);
//        GUIListenerFunctions.print("Gateway " + GatewayID + " has been updated.");
    }

}
