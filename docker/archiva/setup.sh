#!/bin/sh

yum -y install java-1.8.0-openjdk wget
yum clean all

cd /root
wget http://apache.mivzakim.net/archiva/2.2.3/binaries/apache-archiva-2.2.3-bin.tar.gz
tar xf apache-archiva-2.2.3-bin.tar.gz
mv apache-archiva-2.2.3 /opt/archiva
useradd --system --home-dir=/opt/archiva archiva
chown -R archiva:archiva /opt/archiva
mv systemd.archiva.service /etc/systemd/system/archiva.service
systemctl enable archiva
