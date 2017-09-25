FROM centos:6

LABEL name="CentOS Base Image" \
    vendor="CentOS" \
    license="GPLv2" \
    build-date="20170801"
    
RUN yum -y install httpd
RUN chkconfig httpd on
RUN chkconfig iptables off

EXPOSE 80

# CMD ["/bin/bash"]
ENTRYPOINT ["/sbin/init"]

# Usefull commands:
# sudo docker build -t local/c6 .
# sudo docker run -d -p 4000:80 local/c6
# sudo docker ps
# sudo docker exec -it $CONTAINER_ID /bin/bash
