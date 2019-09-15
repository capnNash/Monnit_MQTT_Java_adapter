package com.iotright.imine.MonnitClearBlade;

import com.clearblade.java.api.ClearBladeException;
import com.clearblade.java.api.Message;
import com.clearblade.java.api.MessageCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.monnit.mine.MonnitMineAPI.Gateway;
import com.monnit.mine.MonnitMineAPI.MineServer;
import com.monnit.mine.MonnitMineAPI.Sensor;
import com.monnit.mine.MonnitMineAPI.enums.eFirmwareGeneration;
import com.monnit.mine.MonnitMineAPI.enums.eGatewayType;
import com.monnit.mine.MonnitMineAPI.enums.eSensorApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;


import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;


////////////////////////////////////
///////
//* server: port, IP and protocol
//* gateway: id and type---register, find, remove, reform, pointtoImonnit
//* sensor: id and type1, type2---register, find, remove, update- config1, config 2.

public class IoTRiMineCB {

    private static final Logger logger = LogManager.getLogger(IoTRiMineCB.class.getName());
    private static MineServer _Server;
    private static Gateway gateway;


    public static void startServer() throws Exception {

        _Server = IoTRMineServer.getMonnitServer();
        logger.debug("Constructed Server.");
        _Server.StartServer();
        logger.debug("Server started.");
       

        if (_Server.addGatewayDataProcessingHandler(new GatewayMessageHandler())) {
            logger.debug("Added GatewayHandler.");
        } else {
            logger.debug("Failed to add handler.");
        }
        if (_Server.addSensorDataProcessingHandler(new SensorMessageHandler())) {
            logger.debug("Added SensorHandler.");
        } else {
            logger.debug("Failed to add handler.");
        }
        if (_Server.addExceptionProcessingHandler(new ExceptionHandler())) {
            logger.debug("Added ExceptionHandler.");
        } else {
            logger.debug("Failed to add handler.");
        }
        //TODO what is the difference between Persist and normal?
        if (_Server.addPersistSensorHandler(new PersistSensorHandler())) {
            logger.debug("Added PersistSensorHandler.");
        } else {
            logger.debug("Failed to add handler.");
        }
        if (_Server.addPersistGatewayHandler(new PersistGatewayHandler())) {
            logger.debug("Added PersistGatewayHandler.");
        } else {
            logger.debug("Failed to add handler.");
        }
        if (_Server.addUnknownGatewayHandler(new UnknownGatewayHandler())) {
            logger.debug("Added UnknownGatewayHandler.");
        } else {
            logger.debug("Failed to add handler.");
        }
        
        ResponseHandler responseHandler = new ResponseHandler();
        _Server.addGatewayResponseHandler(responseHandler);

        logger.debug("Finished all inits");
        logger.debug(MineServer.getVersion());
        
        try {
			Thread.sleep(5000);
		} catch(InterruptedException ex) {
			ex.getMessage();
		}
    }

    private static int getPort() {
        return PropertiesManager.getPort();
    }

    public static Gateway registerGateway(MonnitGateway monnitGateway) throws Exception {

        logger.debug("registerGateway: " + monnitGateway.getGatewayID() + ", egt: " + eGatewayType.valueOf(monnitGateway.getGatewayType()) + ", gfv: " + monnitGateway.getGatewayFirmwareVersion() + ", rfv: " + monnitGateway.getRadioFirmwareVersion() + ", host addy: " + monnitGateway.getHostAddress() + ", port: " + getPort());
        if (monnitGateway.getGatewayID() > 0) {
            eGatewayType egt = eGatewayType.valueOf(monnitGateway.getGatewayType());
            Gateway gate = new Gateway(
                    monnitGateway.getGatewayID(),
                    egt,
                    monnitGateway.getGatewayFirmwareVersion(),
                    monnitGateway.getRadioFirmwareVersion(),
                    monnitGateway.getHostAddress(),
                    getPort()
            );
            gate.IsDirty = false;
            monnitGateway.setGateway(gate);
            _Server.RegisterGateway(gate);

           // gate.UpdateReportInterval(.5);            
            logger.debug("Gateway " + monnitGateway.getGatewayID() + " registered.");
            return gate;
        }

        return null;
    }

    public static void registerSensor(MonnitSensor monnitSensor) throws Exception {

        if (monnitSensor.getSensorID() > 0) {
            eSensorApplication esa = null;
            
            for (Map.Entry<Integer,String> entry : eSensorApplication.getStringValues().entrySet())
            {         
                if (monnitSensor.getSensorType().equals(entry.getValue()))
                {
                    esa = eSensorApplication.getEnum(entry.getKey());
                    break;
                }
            }

//            logger.debug("The derived esa: " + esa + " for monnit sensor: " + monnitSensor.getSensorType());
            
            eFirmwareGeneration platform = eFirmwareGeneration.valueOf("Alta");
            
            if(esa !=null && platform !=null) {
                Sensor sens = null;
                try {
//                    logger.debug("Making a new Sensor for sensor with id: " + Integer.toUnsignedLong(monnitSensor.getSensorID()) + " and esa: " + esa + ", with firmware version: " + monnitSensor.getFirmwareVersion() + " on platform: " + platform);
//                    logger.debug("The sensor id is type: " + monnitSensor.getSensorID());
                    sens = new Sensor(Integer.toUnsignedLong(monnitSensor.getSensorID()), esa, monnitSensor.getFirmwareVersion(), platform);
//                    logger.debug("The monnit sensor: " + sens.SensorID);
                } catch (Exception e) {
                    logger.error("ERROR creating new Sensor object: " + e.toString());
                    e.printStackTrace();
                }
                monnitSensor.setSensor(sens);
                try {
                    if (monnitSensor.getSensorID() > 0) {
                        _Server.RegisterSensor(Integer.toUnsignedLong(monnitSensor.getGateway().getGatewayID()), sens);
                        logger.debug("Sensor " + monnitSensor.getSensorID() + " registered.");
                        updateSensorHeartbeat(PropertiesManager.getProperty(PropertiesManager.DEFAULT_SENSOR_HEARTBEAT), monnitSensor.getSensorID()  );
                    } else {
                        logger.warn("Invalid GatewayID: " + monnitSensor.getSensorID());
                    }

                } catch (Exception e) {
                    logger.error("Failed to register sensor: \n" + e.toString());
                    e.printStackTrace();
                }
            }


        }
    }

    
    public static void registerMonnitDevices() {


        try  {

            logger.debug("Parsing json device info from filename: " + System.getProperty("devices"));

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            
            InputStreamReader reader = new InputStreamReader(new FileInputStream(System.getProperty("devices")), "UTF-8");

            MonnitGateway monnitGateway = gson.fromJson(reader, MonnitGateway.class);

            gateway = registerGateway(monnitGateway);

            Thread.sleep(3000);

            for(MonnitSensor sensor : monnitGateway.getSensors()) {
                sensor.setGateway(monnitGateway);
                registerSensor(sensor);
                Thread.sleep(1500);
            }

            logger.debug("Number of connected sensors for this gateway:::\n" + gateway.getRegisteredSensors().size());

        } catch (Exception ex) {

            logger.error("Problem parsing device json: " + ex.getMessage());
        }
    }
    
    public static void updateSensorHeartbeat(String doubleHeartbeat, long sensorId) {
        
    	try{
            logger.debug("Updating Sensor Heartbeat...."+ sensorId);
            if (sensorId > 0) {
                Sensor sens = _Server.FindSensor(sensorId);
                sens.UpdateReportInterval(Double.parseDouble(doubleHeartbeat));
            }
    	} catch (Exception e) {
			logger.error(e.getMessage());
		}
    }
   
private static void subscribeToTopics(){
		
		Thread t1 = new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	
		        // code goes here.
				logger.debug("Subscribe handler waiting>>>>");

				ClearBladeManager.getInstance().initializePlatform();

				// might need to change this, as when you disconnect improperly, leaving the mqtt broker running, in which case, you can't rerun with this clientid and need to change it
                String clientID = PropertiesManager.getProperty(PropertiesManager.CLIENT_ID);
				Message message = new Message(clientID);
				
				try {
					Thread.sleep(3000);
				} catch(InterruptedException ex) {
					logger.error(ex.getMessage());
				}
				
				MessageCallback messageCallback = new MessageCallback() {
					
					@Override
					public void done(String topic, String message){
						try {
							JSONObject obj = new JSONObject(message);
							String sensorHeartBeat = obj.getString("sensHeartBeat");
                            String sensorID = obj.getString("sensID");
                            String sensorType = obj.getString("sensType");
							logger.debug("Topic: " + topic +" Message received: " + message);
							logger.debug("The heartbeat:" + obj.getString("sensHeartBeat"));

							updateSensorHeartbeat(sensorHeartBeat, Long.parseLong(sensorID));

						}catch(Exception ex) {
							logger.error(ex.getMessage());
						}
					}
					
					@Override
					public void error(ClearBladeException exception) {
						
						String message = exception.getLocalizedMessage();
						logger.debug("CB Subscribe Exception: " + message);
					}
				};
				
				message.subscribe(PropertiesManager.getProperty(PropertiesManager.SENSOR_UPDATE_TOPIC), messageCallback);
				
				try {
					Thread.sleep(5000);
				} catch(InterruptedException ex) {
					logger.error(ex.getMessage());
				}
		    	
		    }
		});
		
		t1.setDaemon(true);
		t1.start();	
	}
    
    
    public static void main(String args[]) {
        //arg[0] is the name of the devices file to parse out, and should be in a java resources directory
    	try{
    		startServer();
            registerMonnitDevices();
    		subscribeToTopics();
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
