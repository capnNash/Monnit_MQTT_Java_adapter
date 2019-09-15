# MineCustomClearblade_1
First mod of the provided iMine GUI with the Mine software, including the ClearBlade java SDK 

1. This project uses another project as a dependency, the project is in the repository ClearBladeJavaSdkModified. Please add as a library in your project.
There should be a jar, CB_java_sdk_mod.jar in the libs directory of this project that handles this now. 
2. Please put your email and password in the code to authenticate the Clearblade API. (Messaging and collection). When running this within a ClearBlade Edge, you'll
need to make sure that you've deployed the Edge to the gateway, and that the user you want to authenticate as is synced to the Edge, BEFORE attempting
any ClearBlade.initialize... calls. See more below about deploying on a ClearBlade Edge



Real quick guide to understand the changes to integrate with CB platform:::
>>>Server is started using iMine Lib- has a bunch of pre-defined handlers.
>>>We need to have 3 handlers:: sensormessage handler, gateway message handler and CB portal messages handler(done thru a separate thread process currently)
>>>New classes added:: 
MessageParse_mod- for parsing
Logger- for creating daily logs
headless_guiList- to read a param file with Gateway and its sensor list, starting the server and receiving CB portal messages. 

might need to change the clientid in the code

# Deploying this application to a gateway, running a ClearBlade Edge

The main purpose of this application is to act as a psuedo Adapter between a Monnit gateway, and a ClearBlade Edge. The basic goal is to recieve 
messages from the Monnit gateway, and then forward those to the ClearBlade Edge's MQTT broker, which is available at tcp://localhost:1883. Per the 
ClearBlade's tech recommendations (Michael Sprague):

The Java SDK will use the ClearBlade Messaging broker defined in the messagingURL used when you call clearBlade.initialize(). You will also need to point your platformURL to the edge as well. 
This is because user/device/developer sessions/tokens are not synced between the platform and the edge. 
So if you want to be able to use the MQTT broker on the edge, you will need to authenticate as your user/device against the edge rather than the platform. 
For the Java SDK you’ll want to set the platformURL to http://localhost:9000 and the messagingURL to tcp://localhost:1883. 
Finally, you’ll need to ensure your user or device you are authenticating as is deployed to your edge via a Deployment.

Steps to deploy:
1. First, deploy the corresponding ClearBlade Edge to your gateway environment, following the ClearBlade docs process
2. Copy the monnit_service.sh script, in this Deployment directory, to /etc/init.d/ and make sure you replace the variables at the top of this script with appropriate values. Also, make sure this file is executable
3. Copy the built jar for this app to /opt/app/<whatever>.jar, and make sure the permissions are set appropriately. 
4. Before you build the jar and copy it into the gateway, make sure you've modified the environment variables in the src/resources/app.properties, and filled in the src/resources/devices.json as needed.



