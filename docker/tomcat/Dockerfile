FROM local/c7-systemd
RUN yum -y install tomcat httpd; yum clean all; systemctl enable tomcat httpd
CMD ["/usr/sbin/init"]

# docker build --rm -t local/tomcat .
