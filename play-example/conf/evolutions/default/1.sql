-- Bot configuration schema

# --- !Ups

CREATE TABLE users (
  id           VARCHAR(255) NOT NULL,
  first_name   VARCHAR(255) NOT NULL,
  last_name    VARCHAR(255) NOT NULL,
  email        VARCHAR(255) NOT NULL
);

# --- !Downs

DROP TABLE if EXISTS users;
