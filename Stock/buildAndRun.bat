@echo off
call mvn clean package
call docker build -t com.mycompany/Stock .
call docker rm -f Stock
call docker run -d -p 9080:9080 -p 9443:9443 --name Stock com.mycompany/Stock