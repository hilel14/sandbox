FROM local/c7-systemd
ADD . /root
RUN /root/setup.sh
EXPOSE 8080
# CMD ["/opt/archiva/bin/archiva start"]
CMD ["/usr/sbin/init"]

# docker build --rm -t local/archiva .
# docker run -tid --name=archiva --network=devops --ip=172.18.0.5 -v /sys/fs/cgroup:/sys/fs/cgroup:ro -v archiva:/opt/archiva -p 8003:8080 local/archiva
