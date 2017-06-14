
CREATE SCHEMA `bulletin board`;

CREATE TABLE `bulletin board`.`users` (
 `id` INT NOT NULL AUTO_INCREMENT ,
 `login_id` VARCHAR(20) NOT NULL,
 `password` VARCHAR(255) NOT NULL ,
 `name` VARCHAR(10) NOT NULL ,
 `branch_id` INT NOT NULL ,
 `section_id` INT NOT NULL ,
 `is_working` TINYINT(1) NOT NULL ,
 `insert_date` TIMESTAMP NOT NULL ,
 `update_date` TIMESTAMP NOT NULL ,
 PRIMARY KEY (`id`) );



CREATE TABLE `bulletin board`.`posts` (
 `id` INT NOT NULL AUTO_INCREMENT ,
 `user_id` INT NOT NULL ,
 `title` VARCHAR(50) NOT NULL ,
 `text` VARCHAR(1000) NOT NULL ,
 `category` VARCHAR(45) NOT NULL ,
 `insert_date` TIMESTAMP NOT NULL ,
 `update_date` TIMESTAMP NOT NULL ,
PRIMARY KEY (`id`) );

CREATE TABLE `bulletin board`.`comments` (
 `id` INT NOT NULL AUTO_INCREMENT ,
 `user_id` INT NOT NULL ,
 `post_id` INT NOT NULL ,
 `text` VARCHAR(500) NOT NULL ,
 `insert_date` TIMESTAMP NOT NULL ,
 `update_date` TIMESTAMP NOT NULL ,
 PRIMARY KEY (`id`) );


CREATE TABLE `bulletin board`.`branches` (
 `id` INT NOT NULL AUTO_INCREMENT ,
 `name` VARCHAR(45) NOT NULL ,
 PRIMARY KEY (`id`) );

CREATE  TABLE `bulletin board`.`sections` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) );




CREATE VIEW `bulletin board`.`users_posts` AS
    (select 
		`bulletin board`.`posts`.`id` AS `id`,
        `bulletin board`.`posts`.`title` AS `title`,
		`bulletin board`.`posts`.`text` AS `text`,
        `bulletin board`.`users`.`id` AS `user_id`,
        `bulletin board`.`users`.`name` AS `name`,
		`bulletin board`.`users`.`branch_id` AS `branch_id`,
		`bulletin board`.`posts`.`insert_date` AS `insert_date`
    from
        (`bulletin board`.`users`
        join `bulletin board`.`posts`)
    where
        (`bulletin board`.`users`.`id` = `bulletin board`.`posts`.`user_id`)
    order by `bulletin board`.`posts`.`insert_date`);


CREATE VIEW `bulletin board`.`users_comments` AS
    (select 
		`bulletin board`.`comments`.`id` AS `id`,
		`bulletin board`.`users`.`id` AS `user_id`,
        `bulletin board`.`users`.`name` AS `name`,
		`bulletin board`.`users`.`branch_id` AS `branch_id`,
		`bulletin board`.`comments`.`post_id` AS `post_id`,
		`bulletin board`.`comments`.`text` AS `text`,
		`bulletin board`.`comments`.`insert_date` AS `insert_date`
    from
        (`bulletin board`.`users`
        join `bulletin board`.`comments`)
    where
        (`bulletin board`.`users`.`id` = `bulletin board`.`comments`.`user_id`)
    order by `bulletin board`.`comments`.`insert_date`);

