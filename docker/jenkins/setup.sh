#!/bin/sh

yum -y install tomcat wget
yum clean all
systemctl enable tomcat

cd /root
wget http://mirrors.jenkins.io/war-stable/latest/jenkins.war
mv jenkins.war /var/lib/tomcat/webapps
