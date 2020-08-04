#!/bin/sh
set -e
docker run --name test-mysql -p 3306 -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:latest
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '1234';
mysqladmin -u root -p create mydb
GRANT ALL PRIVILEGES ON mydb.* TO 'admin'@'%';
mysql --port 32768 --protocol=tcp -u root -p
