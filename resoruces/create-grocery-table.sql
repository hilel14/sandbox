DROP TABLE IF EXISTS grocery;

CREATE TABLE grocery  (
    id INT(11) NOT NULL AUTO_INCREMENT,
    product VARCHAR(50),
    quantity INT,
    price DECIMAL(10,2),    
    expiration DATE,
    PRIMARY KEY (id)    
) CHARACTER SET utf8 COLLATE utf8_bin;

INSERT INTO grocery (product, quantity, price, expiration) VALUES ('cheese', 2, 9.95, '1966-10-23');
