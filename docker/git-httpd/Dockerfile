FROM local/c7-systemd

ADD . /root

RUN /root/setup.sh

EXPOSE 80
EXPOSE 443

CMD ["/usr/sbin/init"]
