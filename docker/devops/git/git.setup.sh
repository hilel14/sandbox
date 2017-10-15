#!/bin/sh

# install packages
yum -y install git
yum clean all

# enable services
systemctl enable httpd.service

# Configure git over http
mkdir /var/www/git
cd /root/git
mv git.httpd.conf /etc/httpd/conf.d/git.conf
