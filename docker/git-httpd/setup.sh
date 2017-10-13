#!/bin/sh

# install packages
yum -y install httpd git
yum clean all

# enable services
systemctl enable httpd.service

# Configure git over http
mkdir /var/www/git
mv /root/git-http-backend.conf /etc/httpd/conf.d/
