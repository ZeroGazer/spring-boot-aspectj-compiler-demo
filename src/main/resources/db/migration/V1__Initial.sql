DROP TABLE IF EXISTS USER;

CREATE TABLE USER(
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(20) NOT NULL
);

INSERT INTO USER(username) VALUES ('demo1');
INSERT INTO USER(username) VALUES ('demo2');