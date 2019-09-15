# Info: process and deployment history

## Process:

There are a number of systems that this app can be deployed in; servers, small to large gateway devices, docker containers, and even raspberry pi's. The one constant is that the platform just needs to support java.
Typically, these contexts are going to be some flavor of a linux distro, and specific details about how to deploy this application are dependent on the distro. For example, the traditional systemctl means of specifying 
control scripts in /etc/init.d/... has been largely deprecated in desktop/server variants, in favor of using systemd. Either way, the goal is the same: install and configure the application as a system level service that 
will be monitored and controlled using whatever feature for maintaining running services is available within the deployment context. 

### Configuring a systemd based service

You'll need to create directories to keep the java jar application, and the two configuration files needed by the application. These are arbitrary, but are referenced in the start and restart command sections of the control script, so if you 
change them from the default /opt/conf (configuration files directory) and /opt/iotright (where the jar app lives), be sure to update the references. You'll also need to create the service file, and the control script. These steps are outlined below:

1. create two directories under /opt; conf and iotright
2. create app.properties and devices.json under the conf directory you just created
3. place the app jar file under the iotright directory you just created
4. create a service file at /etc/systemd/system/iotright.service (use previous deployments, or the example as a guide to what should go in this file)
5. create a control script at /usr/local/bin/iotright.sh (or if changed, be sure to update the service file with it's location)
6. ensure executable permissions for both the java jar app, and the iotright.sh control script

With these in place you can now start, stop, get status on, etc, the iotright service, using the standard systemctl commands available.

1. reload the systemctl so it'll pick up our newly defined server: sudo systemctl daemon-reload
2. start the application: sudo systemctl start iotright.service
3. get a status on the running application: sudo systemctl status iotright.service (always do this after starting, to confirm the application is now running)
4. To ensure the application starts on boot, and systemctl keeping it alive: sudo systemctl enable iotright.service

### Installing aws cli tools, and CloudWatch logging agent
This isn't required, but more of an example for how to pipe the application logs from the gateway to an aws cloudwatch log group and stream

Steps:
1. Install the AWS cli tools on the server or gateway, following info here: https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-install.html
2. Install the aws CloudWatch agent on the server or gateway, following here: https://docs.aws.amazon.com/AmazonCloudWatch/latest/monitoring/install-CloudWatch-Agent-on-premise.html and: https://docs.aws.amazon.com/AmazonCloudWatch/latest/monitoring/download-cloudwatch-agent-commandline.html#download-CloudWatch-Agent-on-EC2-Instance-commandline-first
3. Configure the aws cloudwatch agent, following info here: https://docs.aws.amazon.com/AmazonCloudWatch/latest/monitoring/CloudWatch-Agent-Configuration-File-Details.html. Basically, create a json file similar to the one under Deployment/unimar and copy it to /opt/aws/amazon-cloudwatch-agent/etc/amazon-cloudwatch-agent.json on the gateway/server
4. Make sure to modify the log4j.json configuration in src/resources, and set the default appender to be File. In development, it's usually set to STDOUT. Rebuild the jar if needed.

