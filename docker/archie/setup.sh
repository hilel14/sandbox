yum -y install httpd tomcat6 wget git lsof ImageMagick java-1.8.0-openjdk-devel

mkdir /opt/apache
mkdir -p /var/opt/archie/{assetstore,preview,backup}
chown tomcat /var/opt/archie/assetstore
cd /root/work

# download software
#wget http://apache.spd.co.il/lucene/solr/6.6.1/solr-6.6.1.tgz
#wget http://apache.mivzakim.net/maven/maven-3/3.5.0/binaries/apache-maven-3.5.0-bin.tar.g
git clone https://github.com/hilel14/archie.git archie-src

# configure apache httpd and tomcat, deploy archie gui
cp archie-src/archie-cli/src/config/setup/httpd/archie.conf /etc/httpd/conf.d/
cp archie-src/archie-ws/src/main/config/archie-ws.properties /usr/share/tomcat6/lib
cp -R archie-src/archie-gui/public_html /var/www/html/archie

# install and configure maven
tar xvf apache-maven-3.5.0-bin.tar.gz
mv apache-maven-3.5.0 /opt/apache/maven

# install and configure solr
./install_solr_service.sh solr-6.6.1.tgz -i /opt/apache -d /var/opt/solr
su solr -c "/opt/apache/solr/bin/solr create_core -c archie -d basic_configs"
curl http://localhost:8983/solr/archie/config -H 'Content-type:application/json' -d @solrconfig.json
curl http://localhost:8983/solr/archie/schema -H 'Content-type:application/json' -d @managed-schema.json

# build and deploy archie-ws
cd archie-ws
mvn install
mv target/archie-ws.war /var/lib/tomcat6/webapps

# enable and disable services
chkconfig httpd on
chkconfig tomcat6 on
chkconfig solr on
chkconfig iptables off
