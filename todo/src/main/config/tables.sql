DROP TABLE IF EXISTS category;
CREATE TABLE category(
    id int PRIMARY KEY,
    description  VARCHAR(255)
);
INSERT INTO category (id, description) VALUES(0, 'None');
INSERT INTO category (id, description) VALUES(1, 'Ebill');
INSERT INTO category (id, description) VALUES(2, 'Grafika');
INSERT INTO category (id, description) VALUES(3, 'Printdomain');

DROP TABLE IF EXISTS status;
CREATE TABLE status(
    id int PRIMARY KEY,
    description  VARCHAR(255)
);
INSERT INTO status (id, description) VALUES(0, 'Not started');
INSERT INTO status (id, description) VALUES(1, 'In progress');
INSERT INTO status (id, description) VALUES(9, 'Close');

DROP TABLE IF EXISTS project;
CREATE TABLE project(
--  id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id INT AUTO_INCREMENT PRIMARY KEY,
    title  VARCHAR(255),
    description VARCHAR(1024),
    start_date DATE DEFAULT NOW(),
    end_date DATE,
    priority INT DEFAULT 0,
    on_desktop BOOLEAN DEFAULT false,
    active BOOLEAN DEFAULT true,
    category INT DEFAULT 0,
    FOREIGN KEY (category) REFERENCES category(id)
);
INSERT INTO project (title) VALUES('Project one');
INSERT INTO project (title) VALUES('Project two');
INSERT INTO project (title) VALUES('Project tree');

DROP TABLE IF EXISTS note;
CREATE TABLE note(
    id INT AUTO_INCREMENT PRIMARY KEY,
    project_id int,
    description  VARCHAR(255),
    FOREIGN KEY (project_id) REFERENCES project(id)
);
INSERT INTO note (project_id, description) VALUES(1, 'Very important project');

DROP TABLE IF EXISTS task;
CREATE TABLE task(
    id INT AUTO_INCREMENT PRIMARY KEY,
    project_id int,
    description  VARCHAR(255),
    status INT DEFAULT 0,
    FOREIGN KEY (project_id) REFERENCES project(id),
    FOREIGN KEY (status) REFERENCES status(id)
);
INSERT INTO task (project_id, description) VALUES(1, 'Buy milk');

