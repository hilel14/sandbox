FROM local/c7-systemd

ADD . /root

RUN /root/setup.sh

EXPOSE 80
#EXPOSE 443

CMD ["/usr/sbin/init"]

# docker build --rm -t local/bugzilla .
# docker run -tid --name=bugzilla --network=devops --ip=172.18.0.3 -v /sys/fs/cgroup:/sys/fs/cgroup:ro -v bugzilla:/var/www/bugzilla -p 8001:80 local/bugzilla
