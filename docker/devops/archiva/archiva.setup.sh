#!/bin/sh

mkdir /var/lib/tomcat/{conf,data,archiva}
echo 'CATALINA_OPTS="-Dappserver.home=/usr/share/tomcat -Dappserver.base=/var/lib/tomcat"' >> /etc/sysconfig/tomcat
ln -s /usr/share/java/javamail/mail.jar /usr/share/java/tomcat/mail.jar

cd /root/archiva
mv archiva.httpd.conf /etc/httpd/conf.d/archiva.conf
mv archiva.war /var/lib/tomcat/archiva
mv derby-10.10.1.1.jar /usr/share/java/tomcat
mv tomcat-dbcp.jar /usr/share/java/tomcat
mv archiva.xml /etc/tomcat/Catalina/localhost/

chown -R tomcat.tomcat /var/lib/tomcat
