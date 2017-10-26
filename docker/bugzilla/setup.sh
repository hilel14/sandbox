# Pre (on db host):
# mysqladmin -u root -p create bugs
# mysql -u root -p -e "GRANT ALL ON bugs.* TO 'bugzilla'@'172.18.%.%' IDENTIFIED BY '123456';"

yum -y install epel-release
yum -y install wget httpd mod_perl mod_perl-devel httpd-devel gd-devel mysql-devel graphviz patchutils gcc make perl-CPAN

yum clean all
systemctl enable httpd

cd /root
wget https://ftp.mozilla.org/pub/mozilla.org/webtools/bugzilla-5.0.3.tar.gz
tar xvf bugzilla-5.0.3.tar.gz
mv bugzilla-5.0.3 /var/www/bugzilla
mv bugzilla.httpd.conf /etc/httpd/conf.d/bugzilla.conf
cd /var/www/bugzilla/
./install-module.pl --all

# Post:
# cd /var/www/bugzilla/ 
# ./checksetup.pl
# Edit localconfig (db_host, db_name, db_user, db_pass)
# ./checksetup.pl
# ./testserver.pl http://localhost/bugzilla
