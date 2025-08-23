-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema my_pantry
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `my_pantry` ;

-- -----------------------------------------------------
-- Schema my_pantry
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `my_pantry` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
SHOW WARNINGS;
USE `my_pantry` ;

-- -----------------------------------------------------
-- Table `my_pantry`.`account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_pantry`.`account` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `my_pantry`.`account` (
  `user_name` VARCHAR(64) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `email` VARCHAR(64) NOT NULL,
  `cell_number` VARCHAR(10) NULL DEFAULT NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `my_pantry`.`brands`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_pantry`.`brands` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `my_pantry`.`brands` (
  `brand_id` INT NOT NULL AUTO_INCREMENT,
  `brand_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`brand_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE UNIQUE INDEX `brand_name` ON `my_pantry`.`brands` (`brand_name` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `my_pantry`.`categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_pantry`.`categories` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `my_pantry`.`categories` (
  `category_id` INT NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`category_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE UNIQUE INDEX `category_name` ON `my_pantry`.`categories` (`category_name` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `my_pantry`.`units`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_pantry`.`units` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `my_pantry`.`units` (
  `unit_id` INT NOT NULL AUTO_INCREMENT,
  `unit_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`unit_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE UNIQUE INDEX `unit_name` ON `my_pantry`.`units` (`unit_name` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `my_pantry`.`items`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_pantry`.`items` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `my_pantry`.`items` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `barcode` VARCHAR(13) NULL DEFAULT NULL COMMENT '-  Use VARCHAR(13) to accommodate UPC-A (12 digits) and EAN-13 (13 digits).\n- If some items (like produce) don’t have barcodes, allow NULL.\n',
  `item_name` VARCHAR(100) NOT NULL,
  `quantity` DECIMAL(10,2) NOT NULL,
  `use_by` DATE NOT NULL,
  `unit_id` INT NULL DEFAULT NULL,
  `category_id` INT NULL DEFAULT NULL,
  `brand_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  CONSTRAINT `items_ibfk_1`
    FOREIGN KEY (`unit_id`)
    REFERENCES `my_pantry`.`units` (`unit_id`),
  CONSTRAINT `items_ibfk_2`
    FOREIGN KEY (`category_id`)
    REFERENCES `my_pantry`.`categories` (`category_id`),
  CONSTRAINT `items_ibfk_3`
    FOREIGN KEY (`brand_id`)
    REFERENCES `my_pantry`.`brands` (`brand_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE UNIQUE INDEX `barcode_UNIQUE` ON `my_pantry`.`items` (`barcode` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `my_pantry`.`stock`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_pantry`.`stock` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `my_pantry`.`stock` (
  `stock_id` INT NOT NULL AUTO_INCREMENT,
  `item_id` INT NOT NULL,
  `purchase_date` DATE NOT NULL,
  `remaining_quantity` DECIMAL(10,2) NOT NULL,
  `use_by` DATE NOT NULL,
  PRIMARY KEY (`stock_id`),
  CONSTRAINT `stock_ibfk_1`
    FOREIGN KEY (`item_id`)
    REFERENCES `my_pantry`.`items` (`item_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
