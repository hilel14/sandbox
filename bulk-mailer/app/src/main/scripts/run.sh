#!/bin/sh

APP_HOME=`dirname $0`
CLASS_PATH=$APP_HOME/resources/:$APP_HOME/lib/*
MAIN_CLASS=beeriprint.newsletter.batch.mailer.Main

java -cp $CLASS_PATH $MAIN_CLASS $1 $2
