package com.iotright.imine.MonnitClearBlade;

import com.monnit.mine.MonnitMineAPI.enums.eGatewayType;
import com.monnit.mine.MonnitMineAPI.iUnknownGatewayHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UnknownGatewayHandler implements iUnknownGatewayHandler {

    private static final Logger logger = LogManager.getLogger(UnknownGatewayHandler.class.getName());

    @Override
    public void ProcessUnknownGateway(long GatewayID, eGatewayType type) {
        eGatewayType egatewaytype = type;
        try {
            //TODO finish this
//            GUIListenerFunctions.print("Unregistered Type:" + egatewaytype.toString() + ", GatewayID: " + GatewayID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ProcessUnknownGateway(int arg0) {
        // TODO Auto-generated method stub

    }
}
