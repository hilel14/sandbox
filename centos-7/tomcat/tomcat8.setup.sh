#!/bin/sh

set -e

TOMCAT_HOME=/opt/apache/tomcat
TOMCAT_BASE=/var/opt/tomcat
SYSTEMD_UNIT_FILE=/etc/systemd/system/tomcat8.service

parseUserInput() {
	echo "parse user input..."
	if [ -z $2 ]; then
	   echo "Usage: $0 apache-tomcat-x.y.z.tar.gz systemd-unit-file.service" >&2
	   exit 1
	fi
}

checkPrerequisites() {
	
	echo "check prerequisites..."

	if [ ! -d `dirname $TOMCAT_HOME` ]; then
	   echo "parent directory `dirname $TOMCAT_HOME` must exist" >&2
	   exit 1
	fi

	if [ -d $TOMCAT_HOME ]; then
	   echo "tomcat home directory $TOMCAT_HOME already exist" >&2
	   exit 1
	fi

	if [ -d $TOMCAT_BASE ]; then
	   echo "tomcat base directory $TOMCAT_BASE already exist" >&2
	   exit 1
	else
	   mkdir $TOMCAT_BASE
	fi

	if [ -f $SYSTEMD_UNIT_FILE ]; then
	   echo "systemd unit file $SYSTEMD_UNIT_FILE already exist" >&2
	   exit 1
	fi

}

installApplication() {
	echo "install application..."	
	tar xf $1
	PACKAGE_FOLDER=`basename $1 .tar.gz`
	mv $PACKAGE_FOLDER $TOMCAT_HOME
	pushd $TOMCAT_HOME
		mv logs temp webapps work $TOMCAT_BASE
		mv conf $TOMCAT_BASE
	#	mv conf /etc/tomcat8 && ln -s /etc/tomcat8/ $TOMCAT_BASE/conf
		mkdir $TOMCAT_BASE/lib
		echo "CATALINA_HOME=$TOMCAT_HOME" > bin/setenv.sh
		echo "CATALINA_BASE=$TOMCAT_BASE" >> bin/setenv.sh
	popd
}

installService() {
	echo "install service..."
	sudo cp $1 $SYSTEMD_UNIT_FILE
	sudo systemctl daemon-reload
}

parseUserInput "$1" "$2"
checkPrerequisites
installApplication "$1"
installService "$2"

echo "the operation completed successfully"
