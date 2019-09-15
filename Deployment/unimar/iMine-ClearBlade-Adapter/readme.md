# Deploying the iMine application as a ClearBlade Adapter

All the files in this directory are used to create the CB Adapter. For more information on ClearBlade Adapters, see documentation here:
https://docs.clearblade.com/v/4/adapters/

The deploy.sh script in this directory moves all the other associated files into place on the gateway/edge device. The strategy is to 
set the application up as a systemd controlled service, and then place the configuration files - app.properties and devices.json, under /opt/config on the gateway. 


The other required commands defined within the CB Adapter, are all just calling the systemd start|stop|restart|status commands