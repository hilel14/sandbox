# On db host:
# mysqladmin -u root -p create bugs
# mysql -u root -p -e "GRANT ALL ON bugs.* TO 'bugzilla'@'172.17.%.%' IDENTIFIED BY '123456';"

yum -y install epel-release
yum -y install wget httpd mod_perl mod_perl-devel httpd-devel gd-devel mysql-devel graphviz patchutils gcc 'perl(Apache2::SizeLimit)' 'perl(Authen::Radius)' 'perl(Authen::SASL)' 'perl(Cache::Memcached)' 'perl(CGI)' 'perl(Chart::Lines)' 'perl(Daemon::Generic)' 'perl(Date::Format)' 'perl(DateTime)' 'perl(DateTime::TimeZone)' 'perl(DBI)' 'perl(Digest::SHA)' 'perl(Email::MIME)' 'perl(Email::Reply)' 'perl(Email::Sender)' 'perl(Encode)' 'perl(Encode::Detect)' 'perl(File::MimeInfo::Magic)' 'perl(File::Slurp)' 'perl(GD)' 'perl(GD::Graph)' 'perl(GD::Text)' 'perl(HTML::FormatText::WithLinks)' 'perl(HTML::Parser)' 'perl(HTML::Scrubber)' 'perl(IO::Scalar)' 'perl(JSON::RPC)' 'perl(JSON::XS)' 'perl(List::MoreUtils)' 'perl(LWP::UserAgent)' 'perl(Math::Random::ISAAC)' 'perl(MIME::Parser)' 'perl(mod_perl2)' 'perl(Net::LDAP)' 'perl(Net::SMTP::SSL)' 'perl(PatchReader)' 'perl(SOAP::Lite)' 'perl(Template)' 'perl(Template::Plugin::GD::Image)' 'perl(Test::Taint)' 'perl(TheSchwartz)' 'perl(URI)' 'perl(XMLRPC::Lite)' 'perl(XML::Twig)'

yum clean all
systemctl enable httpd

wget https://ftp.mozilla.org/pub/mozilla.org/webtools/bugzilla-5.0.3.tar.gz
tar xvf bugzilla-5.0.3.tar.gz
mv bugzilla-5.0.3 /var/www/bugzilla
mv bugzilla.httpd.conf /etc/httpd/conf.d/
/var/www/bugzilla/install-module.pl --all
