CREATE USER 'debezium'@'%' IDENTIFIED BY 'dbz';
GRANT SELECT, RELOAD, SHOW DATABASES, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'debezium'@'%';
FLUSH PRIVILEGES;


CREATE DATABASE inventory;
USE inventory;

CREATE TABLE products (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255),
                          description TEXT,
                          quantity INT
);

INSERT INTO products(name, description, quantity) VALUES ('gadget', 'cool device', 100);