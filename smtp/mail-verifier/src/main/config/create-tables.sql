CREATE TABLE customer_domain (
    id INT IDENTITY(1,1),
    customer_code varchar(3),
    customer_domain varchar(255)
);

CREATE TABLE sender_white_list (
    id INT IDENTITY(1,1),
    email varchar(255)
);

CREATE TABLE domain_white_list (
    id INT IDENTITY(1,1),
    domain_name varchar(255)
);

CREATE TABLE special_attachment_names (
    id INT IDENTITY(1,1),
    attachment_file_name varchar(255)
);
