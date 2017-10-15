#!/bin/sh

cd /root/jenkins
mv jenkins.httpd.conf /etc/httpd/conf.d/jenkins.conf
mv jenkins.war /var/lib/tomcat/webapps
