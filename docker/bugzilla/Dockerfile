FROM local/c7-systemd

RUN /root/setup.sh

EXPOSE 80
EXPOSE 443

CMD ["/usr/sbin/init"]

# docker build --rm -t local/bugzilla .
# docker run -tid -v /sys/fs/cgroup:/sys/fs/cgroup:ro -v bugzilla:/var/www/bugzilla -p 80:80 -p 443:443 local/bugzilla
