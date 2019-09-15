package com.iotright.imine.MonnitClearBlade;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

/**
 * this class parses the Monnit message from the gateway and converts it to json
 * TODO cleanup and document
 */

public class MessageParser {

	private static final Logger logger = LogManager.getLogger(MessageParser.class.getName());


//		public static void main(String[] args) {
//			// TODO Auto-generated method stub
//			String msg = "Wed Apr 17 2019 02:53:48 AM SensorID: 450796 State: 0 Voltage: 3.41 Signal: 62 Data: X-Axis Speed: 2.9 X-Axis Frequency: 12.0 Y-Axis Speed: 5.8 Y-Axis Frequency: 12.0 Z-Axis Speed: 6.9 Z-Axis Frequency: 12.0 Duty Cycle: 100.0";
//		    //String msg = "Wed Apr 17 2019 02:53:38 AM SensorID: 450791 State: 0 Voltage: 3.35 Signal: 48 Data: Pascal: -640.0";
//		    //String msg = "Wed Apr 17 2019 02:54:43 AM SensorID: 450784 State: 96 Voltage: 2.9 Signal: 100 Data: Micrograms: 0.0";
//			parseString(msg);
//		}
		
		public static JSONObject parseStringToJson(String msg) {

			logger.debug("MessageParser parseStringToJson raw msg string: " + msg);
			
			JSONObject jsonsensConf = new JSONObject();
			
			int ind1 = indexOfAM_PM(msg)+2;
			//logger.debug(ind1);
			int ind2 = msg.indexOf("Signal: ")+11;
			//logger.debug(ind2);
			
			char char1 = msg.charAt(ind2-1);
			if(char1 == ' ')ind2 = ind2-1;
			if(char1 == 'D')ind2 = ind2-2; 
			
			
			String dateTime = msg.substring(0, ind1);		
			//String dateTime = msg.substring(0, (indexOfAM_PM(msg)+2));
			String sensConf = msg.substring(ind1+1, ind2);
			String sensData = msg.substring(ind2+7);
//			logger.debug(dateTime);
//			logger.debug(sensConf);
//			logger.debug(sensData);
			
			try {
				
				jsonsensConf = splitDataJsonify(sensConf);
				JSONObject jsonsensData = splitDataJsonify(sensData);
				String sensType =  identifySensorType(sensData);
//				logger.debug(sensType);
				jsonsensConf.put("type", sensType);
				jsonsensConf.put("data", jsonsensData);
				jsonsensConf.put("datatimestamp", dateTime);
				logger.debug("The converted json from raw: " + jsonsensConf.toString());
				
			}catch(Exception rex) {}
			
			return jsonsensConf;
			
			//logger.debug(sensConf);
			//logger.debug(sensData);
//			String result[] = sub2.split("\\s*,\\s*");
//			logger.debug(result);
		}
		
		public static JSONObject splitDataJsonify(String msg) {
			
			JSONObject json=new JSONObject();
			
			try {		
				ArrayList<String> finres = new ArrayList<String>();
				String res[] = msg.split("[:]");
				//for() {}
				////logger.debug(res.length+"..........len");
				
				
				//json.put(key,value);
				//return json.toString();
				
				////logger.debug(json);
				
				//JSONObject json=new JSONObject();
				//json.put(key,value);
				//return json.toString();
				for(int i=0;i<res.length;i++) {
					////logger.debug(res[i]);
					res[i] = res[i].trim();				 
					//logger.debug(res[i]);
					
					if(Character.isDigit(res[i].charAt(0)) || res[i].charAt(0)=='-') {
						
						int ind = res[i].indexOf(' ');
						if(ind == -1)finres.add(res[i]);
						else {
							String intValue = res[i].substring(0, ind);
							String keyValue=  res[i].substring(ind+1);
							finres.add(intValue);
							finres.add(keyValue);
						}
						
						////logger.debug("digit row...");
						
					}else {
						
						////logger.debug("non digit row...");
						finres.add(res[i]);
					}
					
					////logger.debug(res[i]);
					//json.put(res[i],res[j]);
				}
				
				////logger.debug(finres.size());
				
				for(int i = 0; i<finres.size() ;i+=2) {
						
					json.put(finres.get(i).replaceAll("\\s+","").toLowerCase(),finres.get(i+1).replaceAll("\\s+","").toLowerCase());
				}
				
			}catch(Exception ex) {
				//logger.debug(ex.getMessage());
			}

			return json;
				
		}
		
		public static int indexOfAM_PM(String msg){
			
			String subAM = "AM";
			String subPM = "PM";
			
			int ind = msg.indexOf(subAM);
			
			if(ind == -1) {
				
				ind = msg.indexOf(subPM);
			}
			
			return ind;
			
		}

		//TODO fix up for ThreePhaseCurrentMeter sensor
		public static String identifySensorType(String msg) {
			
			int ind = msg.indexOf(':');
			String s = msg.substring(0, ind).replaceAll("\\s+","");
			//s = s.replaceAll("\\s+","");
			//logger.debug(s);
			
			if(s.equalsIgnoreCase("Micrograms")) {
				
				return "AirQuality";
				
			}else if(s.equalsIgnoreCase("Probe1Temperature")){
				
				return "QuadTemperature";
				
			}else if(s.equalsIgnoreCase("X-AxisSpeed")){
				
				return "Vibration";
				
			}else if(s.equalsIgnoreCase("Pascal")){
				
				return "DifferentialPressure";

			}else if(s.equalsIgnoreCase("phase1average")){

				return "ThreePhaseCurrentMeter";

			}
			else if(s.equalsIgnoreCase("instantaneous")){
				return "CO2Meter";
			}
			return s;
		}

}//end of class