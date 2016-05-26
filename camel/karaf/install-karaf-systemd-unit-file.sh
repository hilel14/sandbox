# useradd --system  --home-dir=/opt/apache/karaf karaf
# chown -R karaf /opt/apache/karaf/data/

cat <<EOF > /etc/systemd/system/karaf.service

[Unit]
Description=Apache Karaf OSGi container
After=syslog.target network.target

[Service]
Type=forking
PIDFile=/opt/apache/karaf/data/karaf.localhost.pid
ExecStart=/opt/apache/karaf/bin/karaf-service start
#ExecStop=/opt/apache/karaf/bin/karaf-service stop
User=karaf
Group=karaf

[Install]
WantedBy=multi-user.target

EOF
