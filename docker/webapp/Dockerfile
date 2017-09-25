FROM centos:6

LABEL name="Java Web application"
  
RUN yum -y install httpd tomcat6
RUN chkconfig httpd on
RUN chkconfig tomcat6 on
RUN chkconfig iptables off

ADD ./index.jsp /var/lib/tomcat6/webapps

EXPOSE 80

# CMD ["/bin/bash"]
ENTRYPOINT ["/sbin/init"]
