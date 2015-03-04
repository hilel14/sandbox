#!/bin/sh

#  make sure version argument exist
if ! [ $# -eq 3 ]; then
	echo "Usage:"
	echo "$0 in_file out_file run_id"
	echo "Example:"
	echo "$0 in.txt out.txt 1001"
	exit 1
fi

# exit on first error
set -e

# init variables

BATCH_HOME=/opt/spring/dist
APP_HOME=`dirname $0`
CP=$APP_HOME/resources/:$APP_HOME/lib/*:$BATCH_HOME/resources/:$BATCH_HOME/lib/*
MAIN=org.springframework.batch.core.launch.support.CommandLineJobRunner

# set job parameters

# Key names that are prefixed with a - are considered non-identifying and will not contribute to the identity of a JobInstance. 
# Key names ending with "(<type>)" where type is one of string, date, long are converted to the corresponding type. 
# The default type is string. E.g.
#    schedule.date(date)=2007/12/11
#    department.id(long)=2345
# See org.springframework.batch.core.converter.DefaultJobParametersConverter for details

PARAMS="-input_file="file:$1" -output_file="file:$2" run.id=$3"

java -cp $CP $MAIN classpath:/delimited.xml job1 $PARAMS

