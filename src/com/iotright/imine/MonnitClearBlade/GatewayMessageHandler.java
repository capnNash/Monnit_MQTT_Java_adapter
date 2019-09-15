package com.iotright.imine.MonnitClearBlade;

import com.monnit.mine.MonnitMineAPI.GatewayMessage;
import com.monnit.mine.MonnitMineAPI.iGatewayMessageHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import com.clearblade.java.api.Query;


//TODO finish this class, needs parsing for the gateway message to json

public class GatewayMessageHandler implements iGatewayMessageHandler {

    private static final Logger logger = LogManager.getLogger(GatewayMessageHandler.class.getName());

    @Override
    public void ProcessGatewayMessage(GatewayMessage gatewayMessage)
            throws Exception {
    	
    	logger.debug("ProcessGatewayMessage constructor-GatewayMessage arg: " + gatewayMessage.toString());

    	//DOCS this message looks like this: ProcessGatewayMessage constructor-GatewayMessage arg: Wed May 29 2019 06:01:25 PM GatewayID: 940597 Type: Data MessageCount: 2 Gateway Power: 0.0 Gateway Parameters:


        String modString = gatewayMessage.toString();
//        GUIListenerFunctions.print("The gateway message handler.............."+modString);
        
        
        //send raw modstring to clearblade
        
        //1. authen
//        String SYSTEM_KEY = "a8ec94c80ba2fba4d4e5c2d1f0f701";
//        String SYSTEM_SECRET = "A8EC94C80BCA8BFBD28AECD6CFA801";
//        
//        HashMap<String, Object> initOptions = new HashMap<String, Object>();
//        
//        InitCallback initCallback = new InitCallback(){
//            @Override
//            public void done(boolean results){
//        	    //initialization successful
//            	logger.debug("Hello2");
//        	}
//        	@Override
//        	public void error(ClearBladeException exception){ 
//        	   //initialization failed, given a ClearBladeException with the cause
//               // Log.i("Failed init", "holy cow!!" + exception.getLocalizedMessage());
//        		logger.debug("Hello2.......error"+exception);
//            }
//        };
//
//        initOptions.put("platformURL", "https://iotright.clearblade.com");
//        initOptions.put("email", "nagrawal@unimar.com");
//        initOptions.put("password", "uniBladeNA@123");
//
//        ClearBlade.initialize(SYSTEM_KEY, SYSTEM_SECRET, initOptions, initCallback);
//        
//        String collectionID = "d2ec94c80ba89887c0ddabb3b50e";
//        Collection collection = new Collection(collectionID);
//        
//        String column = "{\"gatewaymsgstring\":\""+modString+"\"}";
//        collection.create(column, new DataCallback() {
//            @Override
//            public void done(Item[] response) {
//                // Query successful
//            	logger.debug("Hello3");
//            }
//            @Override
//            public void error(ClearBladeException exception) {
//                // Query unsuccessful
//            	logger.debug("Hello3.......error"+exception);
//            }
//        });
//        
//        logger.debug("Hello4");
        
    }

}
