#!/bin/bash

TOMCAT_DIR=~/software/apache-tomcat-7.0.52

$TOMCAT_DIR/bin/shutdown.sh

rm -rf $TOMCAT_DIR/logs/*
echo $TOMCAT_DIR/logs/* "cleaned"

rm -f $TOMCAT_DIR/webapps/cas-management.war
echo $TOMCAT_DIR/webapps/cas-management.war "deleted"


rm -rf $TOMCAT_DIR/webapps/cas-management
echo $TOMCAT_DIR/webapps/cas-management "deleted"

rm -rf $TOMCAT_DIR/work/*
echo $TOMCAT_DIR/work/* "cleaned"

echo "building war"
mvn clean package

echo "copying war"
cp -v target/cas-management.war $TOMCAT_DIR/webapps


