#!/usr/bin/env bash


mkdir /opt/conf
mkdir /opt/iotright

mv ./devices.json /opt/conf/
mv ./app.properties /opt/conf/
mv ./Main.jar /opt/iotright/

mv ./iotright.sh /usr/local/bin/
chmod 755 /usr/local/bin/iotright.sh
mv ./iotright.service /etc/systemd/system/
chmod 755 /etc/systemd/system/iotright.service

