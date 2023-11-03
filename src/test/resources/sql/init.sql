USE test;

CREATE TABLE IF NOT EXISTS `test`.`users` (
    `no` int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` varchar(20) NOT NULL
);

INSERT INTO `test`.`users` (user_id) VALUES ('user1');
INSERT INTO `test`.`users` (user_id) VALUES ('user2');