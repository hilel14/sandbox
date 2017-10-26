FROM local/c7-systemd

ADD . /root

RUN /root/setup.sh

EXPOSE 80
#EXPOSE 443

CMD ["/usr/sbin/init"]

# docker build --rm -t local/git .
# docker run -tid --name=git --network=devops --ip=172.18.0.4 -v /sys/fs/cgroup:/sys/fs/cgroup:ro -v git:/var/lib/git -p 8002:80 local/git
