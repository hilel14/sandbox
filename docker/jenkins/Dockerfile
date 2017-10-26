FROM local/c7-systemd
ADD . /root
RUN /root/setup.sh
EXPOSE 8080
CMD ["/usr/sbin/init"]

# docker build --rm -t local/jenkins .
# docker run -tid --name=jenkins --network=devops --ip=172.18.0.6 -v /sys/fs/cgroup:/sys/fs/cgroup:ro -v jenkins:/var/lib/tomcat/webapps -p 8004:8080 local/jenkins
