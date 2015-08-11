DROP TABLE IF EXISTS category;
CREATE TABLE category(
    id int PRIMARY KEY,
    description  VARCHAR(255)
);
INSERT INTO category (id, description) VALUES(0, 'ללא');
INSERT INTO category (id, description) VALUES(1, 'איביל');
INSERT INTO category (id, description) VALUES(2, 'רשת');
INSERT INTO category (id, description) VALUES(3, 'תוכנה');

DROP TABLE IF EXISTS status;
CREATE TABLE status(
    id int PRIMARY KEY,
    description  VARCHAR(255)
);
INSERT INTO status (id, description) VALUES(1, 'פעיל');
INSERT INTO status (id, description) VALUES(2, 'פתוח');
INSERT INTO status (id, description) VALUES(3, 'סגור');

DROP TABLE IF EXISTS project;
CREATE TABLE project(
--  id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id INT AUTO_INCREMENT PRIMARY KEY,
    title  VARCHAR(255),
    description VARCHAR(1024),
    start_date DATE DEFAULT NOW(),
    end_date DATE,
    priority INT DEFAULT 1,
    category INT DEFAULT 0,
    status INT DEFAULT 2,
--  on_desktop BOOLEAN DEFAULT false,
--  active BOOLEAN DEFAULT true,
    FOREIGN KEY (category) REFERENCES category(id),
    FOREIGN KEY (status) REFERENCES status(id)
);
INSERT INTO project (title) VALUES('Project one');
INSERT INTO project (title) VALUES('Project two');
INSERT INTO project (title) VALUES('Project tree');

DROP TABLE IF EXISTS task;
CREATE TABLE task(
    id INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT,
    description  VARCHAR(255),
    completed BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (project_id) REFERENCES project(id)
);
INSERT INTO task (project_id, description) VALUES(1, 'Buy milk');

