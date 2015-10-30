DROP TABLE IF EXISTS contacts;

CREATE TABLE contacts (
	ID INT auto_increment PRIMARY KEY, 
	first_name VARCHAR(255),
	last_name VARCHAR(255),
	phone VARCHAR(255),
	email VARCHAR(255)
);

/*

INSERT INTO contacts (email) VALUES( 'a@b.c');

Another option: Import data from file us-500.csv (source: https://www.briandunning.com/sample-data)

*/