CREATE TABLE reports
(
  id    bigint(20)  NOT NULL AUTO_INCREMENT,
  date  varchar(10) NOT NULL,
  count int(11)     NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_date (date)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;