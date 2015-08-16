#!/bin/sh

APP_HOME=`dirname $0`

java -classpath $APP_HOME/resources/:$APP_HOME/lib/* beeriprint.todo.gui.MainFrame
