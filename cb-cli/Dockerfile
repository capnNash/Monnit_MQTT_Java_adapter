FROM ubuntu:latest

RUN apt-get update \
    && apt-get install -y git \
    && apt-get install -y wget \
    && apt-get install -y software-properties-common

RUN apt-add-repository ppa:mosquitto-dev/mosquitto-ppa && apt-get update && apt-get install -y mosquitto mosquitto-clients

WORKDIR /usr/myapp

ENV PATH "$PATH:/usr/myapp"

RUN wget https://github.com/ClearBlade/cb-cli/releases/download/5.1.4/cb-cli-linux-amd64.tar.gz
RUN tar -zxvf cb-cli-linux-amd64.tar.gz