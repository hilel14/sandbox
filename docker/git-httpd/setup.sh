#!/bin/sh

# install packages
yum -y install httpd git gitweb
yum clean all

# enable services
systemctl enable httpd.service

# Configure git over http
cd /root
mkdir /var/lib/git
mv git-http-backend.conf /etc/httpd/conf.d/
