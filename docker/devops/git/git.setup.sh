#!/bin/sh

# install packages
yum -y install git
yum clean all

# Configure git over http
mkdir /var/lib/tomcat/git
cd /root/git
mv git.httpd.conf /etc/httpd/conf.d/git.conf

# Post build tasks:

# htpasswd -c /var/lib/tomcat/git/.htpasswd user1
# git init --bare /var/lib/tomcat/git/project-one.git
# chown -R apache /var/lib/tomcat/git/project-one.git
