#!/bin/sh

#  validate user input
if ! [ $# -eq 2 ]; then
    echo "Usage:"
    echo "$0 job-name path/to/input/file"
    echo "Example:"
    echo "$0 job1 path/to/input/file"
    exit 1
fi

# exit on first error
set -e

# init variables
BATCH_HOME=/opt/spring/dist
CURRENT_DIR=`dirname $0`
CP=$CURRENT_DIR/resources/:$CURRENT_DIR/lib/*:$BATCH_HOME/resources/:$BATCH_HOME/lib/*
MAIN=org.springframework.batch.core.launch.support.CommandLineJobRunner
ARGS="classpath:/$1.xml $1 inputFile=file:$2"

# run the job
java -cp $CP $MAIN $ARGS
