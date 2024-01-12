#!/bin/sh
mvn clean install -Dmaven.test.skip=true
docker-compose -f docker-compose.yml up --build -d