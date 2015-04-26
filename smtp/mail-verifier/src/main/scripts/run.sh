#!/bin/sh

export JAVA_HOME=/Library/Internet\ Plug-Ins/JavaAppletPlugin.plugin/Contents/Home
export PATH=$JAVA_HOME:$PATH

java -version

# slf4j-jdk14-1.7.7.jar
# slf4j-simple-1.7.7.jar

sudo java -classpath mail-verifier-1.0-SNAPSHOT.jar:slf4j-simple-1.7.7.jar:lib/activation-1.1.1.jar:lib/commons-email-1.3.jar:lib/jcharset.jar:lib/jsr305-1.3.9.jar:lib/jtds-1.3.1.jar:lib/mail-1.4.4.jar:lib/slf4j-api-1.6.1.jar:lib/subethasmtp-3.1.7.jar -Dorg.slf4j.simpleLogger.defaultLogLevel=DEBUG beeriprint.mail.verifier.Main