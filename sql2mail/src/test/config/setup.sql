/* 
 * Start H2 database, by running the following command 
 * (replace /var/opt/h2 with a folder on your machine):
 * java -cp  ~/.m2/repository/com/h2database/h2/1.4.192/h2-1.4.192.jar org.h2.tools.Server -tcp -web -baseDir /var/opt/h2
 */

CREATE TABLE people (id INT PRIMARY KEY, first_name VARCHAR(255), birth_date DATE, height DECIMAL(20, 2));

INSERT INTO people (id, first_name, birth_date, height) VALUES (1, 'James', '1974-11-17', 2.07);
INSERT INTO people (id, first_name, birth_date, height) VALUES (2, 'Josephine', '1984-03-13', 1.64);
INSERT INTO people (id, first_name, birth_date, height) VALUES (3, 'Simona', '1992-01-01', 1.73);
INSERT INTO people (id, first_name, birth_date, height) VALUES (4, 'Kris', '2002-07-13', 1.80);
INSERT INTO people (id, first_name, birth_date, height) VALUES (5, 'Veronika', '2016-04-01', 1.62);
