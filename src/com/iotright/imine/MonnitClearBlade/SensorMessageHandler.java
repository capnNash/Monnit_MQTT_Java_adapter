package com.iotright.imine.MonnitClearBlade;

import java.util.List;

//import com.monnit.mine.BaseApplication.Datum;
import com.monnit.mine.MonnitMineAPI.Gateway;
import com.monnit.mine.MonnitMineAPI.SensorMessage;
import com.monnit.mine.MonnitMineAPI.iSensorMessageHandler;
import com.clearblade.java.api.ClearBlade;
import com.clearblade.java.api.ClearBladeException;
import com.clearblade.java.api.InitCallback;

//import com.clearblade.java.api.Collection;
//import com.clearblade.java.api.Query;
//import com.clearblade.java.api.Item;
//import com.clearblade.java.api.DataCallback;

import com.clearblade.java.api.User;
import com.clearblade.java.api.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class SensorMessageHandler implements iSensorMessageHandler {

	private static final Logger logger = LogManager.getLogger(SensorMessageHandler.class.getName());

	@Override
    public void ProcessSensorMessages(List<SensorMessage> sensorMessageList, Gateway gateway) throws Exception {
        for (SensorMessage msg : sensorMessageList) {
           
           
			//refactored out to get rid of ui specific code
//            if (msg.ProfileID > 65000) {
//                Sensor sens = GUIListenerFunctions.FindSensorBySensorID(msg.SensorID);
//                msg.ProfileID = sens.MonnitApplication.Value();
//            }
         logger.debug("ProcessSensorMessages, Sensor Messsage..........................."+msg.toString());
         
		//////////////////////////////CB auth
		////////////////////////NA
         
         JSONObject sensorMessage = MessageParser.parseStringToJson(msg.toString());
         handleEachMessage(sensorMessage);
         
        }
    }
    
	
	private void handleEachMessage(JSONObject sensMessage) {

        try {
            logger.debug("SensorMessageHandler:handleEachMessage sensMessage: " + sensMessage.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

		ClearBladeManager.getInstance().initializePlatform();
		
		String clientID = PropertiesManager.getProperty(PropertiesManager.CLIENT_ID);
		Message message = new Message(clientID);
		
		try {
			Thread.sleep(5000);
		} catch(InterruptedException ex) {
			logger.error("SensorMessageHandler sleep before message.publish error: " + ex.getMessage());
		}
		
		try {
			
			String sensID = sensMessage.getString(PropertiesManager.SENSORID);
			String sensType = sensMessage.getString("type");
            String topic = PropertiesManager.getProperty(PropertiesManager.SENSOR_TOPIC) + "/" + sensID + "/_platform";
            logger.debug("The topic path we're publishing to: " + topic);
			message.publish(topic, sensMessage.toString());
			
		} catch(Exception ex) {
            logger.error("SensorMessageHandler handleEachMessage ClearBlade message.publish error: " + ex.getMessage());
		}
		
		try {
			Thread.sleep(5000);
		} catch(InterruptedException ex) {
            logger.error(ex.getMessage());
		}
		
		message.disconnect();
		
		logoutUser();
	}
	
	private void logoutUser() {
		
		User currentUser = ClearBlade.getCurrentUser();
		currentUser.logout(new InitCallback() {

			@Override
			public void done(boolean results) {
				
				logger.debug("User logged out");
			}
			@Override
			public void error(ClearBladeException exception) {
				logger.error("Logout failed " + exception.getMessage());
			}
			
		});
	}
         

}
