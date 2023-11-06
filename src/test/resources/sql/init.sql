USE test;

CREATE TABLE IF NOT EXISTS `test`.`users` (
    `no` int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` varchar(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS `test`.`goods` (
    `no` int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_no` int(10) NOT NULL,
    `name` varchar(20) NOT NULL
);

INSERT INTO `test`.`users` (user_id) VALUES ('user1');
INSERT INTO `test`.`users` (user_id) VALUES ('user2');

INSERT INTO `test`.`goods` (user_no, name) VALUES (1, 'goods1');
INSERT INTO `test`.`goods` (user_no, name) VALUES (1, 'goods2');
INSERT INTO `test`.`goods` (user_no, name) VALUES (2, 'goods3');