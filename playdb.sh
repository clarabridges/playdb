#!/bin/sh

if [ "$1" = "run-tests" ]; then
    mvn test
else
    java -jar ./target/playdb.jar
fi