CREATE TABLE campaign (
    id INT NOT NULL AUTO_INCREMENT,
    template_name varchar(50) NOT NULL,
    list_file varchar(50) NOT NULL,
    campaign_date date NOT NULL COMMENT 'campaign launch date',
    PRIMARY KEY (id),
    CONSTRAINT unique_campaign UNIQUE (campaign_date, template_name, list_file)
) ENGINE=InnoDB;

CREATE TABLE email (
    id varchar(50) NOT NULL,
    campaign_id INT NOT NULL,
    recipient_name varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
    user_part varchar(255) DEFAULT NULL,
    domain_part varchar(255) DEFAULT NULL,
    action_code varchar(20) DEFAULT  NULL COMMENT 'eSMTP action code. Examples: relayed, delayed, failed',
    status_code varchar(20) DEFAULT NULL COMMENT 'eSMTP status code. Examples: 550 or 5.1.1',
    diagnostic_code varchar(512) DEFAULT NULL COMMENT 'eSMTP diagnostic code - human readable messages',
    created datetime NOT NULL,
    modified timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX campaign_id_idx (campaign_id), 
    CONSTRAINT campaign_id_fk FOREIGN KEY (campaign_id) REFERENCES campaign (id),
    INDEX domain_part_idx (domain_part)
) ENGINE=InnoDB;

CREATE TABLE activity (
    id INT NOT NULL AUTO_INCREMENT,
    email_id varchar(50) NOT NULL,
    activity_time datetime NOT NULL,
    activity_type varchar(50) NOT NULL COMMENT 'opened, clicked',
    resource_id SMALLINT NOT NULL COMMENT 'See file targets.tab',
    PRIMARY KEY (id),
    INDEX email_id_idx (email_id), 
    CONSTRAINT email_id_fk FOREIGN KEY (email_id) REFERENCES email (id)
) ENGINE=InnoDB;
