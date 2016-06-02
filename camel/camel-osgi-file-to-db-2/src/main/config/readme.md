feature:repo-add camel 2.17.1
feature:install camel-blueprint camel-jdbc camel-bindy

feature:repo-add pax-jdbc
feature:install transaction jndi pax-jdbc-mariadb pax-jdbc-pool-dbcp2 pax-jdbc-config jdbc

copy configuration files to etc folder

bundle:install --start mvn:my.sandbox/camel-osgi-file-to-db-2/1.0-SNAPSHOT
bundle:uninstall camel-osgi-file-to-db-2