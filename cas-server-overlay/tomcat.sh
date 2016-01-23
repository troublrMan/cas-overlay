#!/bin/bash

TOMCAT_DIR=~/software/apache-tomcat-7.0.52

$TOMCAT_DIR/bin/shutdown.sh

rm -rf $TOMCAT_DIR/logs/*
echo $TOMCAT_DIR/logs/* "cleaned"

rm -f $TOMCAT_DIR/webapps/cas.war
echo $TOMCAT_DIR/webapps/cas.war "deleted"


rm -rf $TOMCAT_DIR/webapps/cas
echo $TOMCAT_DIR/webapps/cas "deleted"

rm -rf $TOMCAT_DIR/work/*
echo $TOMCAT_DIR/work/* "cleaned"

mvn clean package
cp -v target/cas.war $TOMCAT_DIR/webapps


