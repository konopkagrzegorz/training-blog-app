INSERT INTO `roles` (`name`) VALUES ('ROLE_ADMIN');
INSERT INTO `roles` (`name`) VALUES ('ROLE_MODERATOR');
INSERT INTO `roles` (`name`) VALUES ('ROLE_USER');

INSERT INTO `users` (`username`, `first_name`, `last_name`, `password`, `email`, `role_id`)
VALUES ('gkonopka','Grzegorz','Konopka','$2y$12$mWI3bRSpWyds3GkkTITmFuLCI7Eyiov7GON/9NTFkJxqQX4lWQgra','konopkagrzeg@gmail.com','1');

INSERT INTO `users` (`username`, `first_name`, `last_name`, `password`, `email`, `role_id`)
VALUES ('mkomenda','Martyna','Komenda','$2y$12$YRNUnzw/yNuPaBYXP9m6VO48SuukLRkgobaHXL4j23Ey4/Xi9tv0W','martynakomenda@gmail.com','2');

INSERT INTO `users` (`username`, `first_name`, `last_name`, `password`, `email`, `role_id`)
VALUES ('rkonopka','Ryszard','Konopka','$2y$12$pgO84pmff4nn0pRtw52sx.zei0UviGruBYJZ21Qq6JBogt83sZocK','ryszardk@gmail.com','3');

-- CREATE TABLE `users`
-- (`id` INT(20) NOT NULL,
-- `username` VARCHAR(64) NOT NULL,
-- `first_name` VARCHAR(64) NOT NULL,
-- `last_name` VARCHAR(64) NOT NULL,
-- `password` VARCHAR(64) NOT NULL,
-- `email` VARCHAR(64) NOT NULL,
--   PRIMARY KEY (`id`)) engine=InnoDB;

--   CREATE TABLE `users` (
--   `id` int(11) NOT NULL AUTO_INCREMENT,
--   `username` varchar(45) NOT NULL,
--   `first_name` varchar(64) NOT NULL,
--   `last_name` varchar(64) NOT NULL,
--   `password` varchar(64) NOT NULL,
--   `email` varchar(64) NOT NULL,
--   PRIMARY KEY (`id`)
-- );

-- CREATE TABLE `roles`
-- (`id` INT(20) NOT NULL AUTO_INCEREMENT,
-- `name` VARCHAR(64) NOT NULL,
--   PRIMARY KEY (`id`)) engine=InnoDB;
--
-- ALTER TABLE `roles` add constraint `users_fk_key` foreign key (`user_id`) references users (`id`);