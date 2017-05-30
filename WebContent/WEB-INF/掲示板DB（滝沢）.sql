
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

CREATE TABLE `bulletin board`.`messages` (
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
 `message_id` INT NOT NULL ,
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
