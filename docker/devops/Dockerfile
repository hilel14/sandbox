FROM local/tomcat

ADD . /root

RUN /root/jenkins/jenkins.setup.sh
RUN /root/archiva/archiva.setup.sh

EXPOSE 80
EXPOSE 443

CMD ["/usr/sbin/init"]

# docker build --rm -t local/devops .
# docker run -tid -v /sys/fs/cgroup:/sys/fs/cgroup:ro -v devops:/var/lib/tomcat -p 80:80 -p 443:443 local/devops
