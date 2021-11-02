#!/bin/sh
mvn clean package && docker build -t com.mycompany/Stock .
docker rm -f Stock || true && docker run -d -p 9080:9080 -p 9443:9443 --name Stock com.mycompany/Stock