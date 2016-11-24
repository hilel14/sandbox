DROP TABLE IF EXISTS people;

CREATE TABLE people (
    id INT NOT NULL, 
    first_name VARCHAR(255), 
    birth_date TIMESTAMP,
    height DECIMAL(20,2),
    PRIMARY KEY (id)
);

INSERT INTO people (id, first_name, birth_date, height) VALUES (1, 'אברהם' , '1955-12-17', 1.67);
INSERT INTO people (id, first_name, birth_date, height) VALUES (2, 'יצחק' , '1972-10-1', 1.82);
INSERT INTO people (id, first_name, birth_date, height) VALUES (3, 'יוסף סטלין' , '1864-2-25', 1.45);