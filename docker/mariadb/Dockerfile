FROM local/c7-systemd

RUN yum -y install mariadb-server; yum clean all; systemctl enable mariadb

EXPOSE 3306

CMD ["/usr/sbin/init"]

# docker build --rm -t local/mariadb .
# docker run -tid  --name mariadb -v /sys/fs/cgroup:/sys/fs/cgroup:ro -v mariadb:/var/lib/mysql -p 3306:3306 local/mariadb
