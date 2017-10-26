FROM local/c7-systemd

RUN yum -y install mariadb-server; yum clean all; systemctl enable mariadb

EXPOSE 3306

CMD ["/usr/sbin/init"]

# docker build --rm --tag local/mariadb .
# docker network create --driver=bridge --subnet=172.18.0.0/16 --gateway=172.18.0.1 devops
# docker run --tty --interactive --detach --name=mariadb --network=devops --ip=172.18.0.2 --volume=/sys/fs/cgroup:/sys/fs/cgroup:ro --volume=mariadb:/var/lib/mysql --publish=3306:3306 local/mariadb
