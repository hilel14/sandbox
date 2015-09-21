#!/bin/sh

#  make sure version argument exist
if ! [ $# -eq 3 ]; then
	echo "Usage:"
	echo "$0 in-file out-file intance-id"
	echo "Example:"
	echo "$0 in.txt out.txt Jan-2014"
	exit 1
fi

# exit on first error
set -e

# init variables
BATCH_HOME=/opt/springsource/spring-batch-2.2.0.RELEASE/dist
CURRENT_DIR=`dirname $0`
VM_OPTIONS="-Dlog4j.configuration=file:$CURRENT_DIR/config/log4j.properties"
CLASSPATH=$CURRENT_DIR/resources/:$CURRENT_DIR/lib/*:$BATCH_HOME/resources/:$BATCH_HOME/lib/*
MAIN=org.springframework.batch.core.launch.support.CommandLineJobRunner
ARGS="classpath:/my-job-1.xml my-job-1 inputFile=file:$1 outputFile=file:$2 cycle.date=$3"

# run the job
java $VM_OPTIONS -cp $CLASSPATH $MAIN $ARGS
