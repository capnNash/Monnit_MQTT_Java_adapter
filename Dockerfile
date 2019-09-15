FROM java:8

WORKDIR /opt/monnit

ADD build/artifacts/Main.jar Main.jar

COPY Deployment/unimar/iMine-ClearBlade-Adapter/app.properties /opt/conf/app.properties

COPY Deployment/unimar/iMine-ClearBlade-Adapter/devices.json /opt/conf/devices.json

EXPOSE 3000

CMD java -jar -Dlog4j.configurationFile=log4j2.json -Dproperties=/opt/conf/app.properties -Ddevices=/opt/conf/devices.json Main.jar