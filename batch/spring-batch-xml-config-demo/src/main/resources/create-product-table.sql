CREATE TABLE product  (
    id int(11) NOT NULL AUTO_INCREMENT,
    description VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_bin,
    price DECIMAL(10,2),
    purchase_date DATE,
    PRIMARY KEY (id)
);
