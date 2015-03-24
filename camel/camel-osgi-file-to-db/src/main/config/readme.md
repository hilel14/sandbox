feature:repo-add camel 2.13.2
feature:install camel-blueprint camel-jdbc camel-bindy

feature:repo-add mvn:org.ops4j.pax.jdbc/pax-jdbc-features/0.5.0/xml/features
feature:install transaction jndi pax-jdbc-mysql pax-jdbc-pool-dbcp2 pax-jdbc-config

feature:install jdbc

service:list DataSourceFactory

copy org.ops4j.datasource-product.cfg to <karaf-home>/etc

service:list DataSource

jdbc:datasources
jdbc:tables product
jdbc:execute product SELECT * FROM product

bundle:install --start mvn:my.sandbox/camel-osgi-file-to-db/1.0-SNAPSHOT