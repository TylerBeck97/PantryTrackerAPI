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
-- Table `my_pantry`.`USERS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_pantry`.`USERS` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `my_pantry`.`USERS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `USERNAME` VARCHAR(64) NOT NULL,
  `PASSWORD` VARCHAR(32) NOT NULL,
  `EMAIL` VARCHAR(64) NOT NULL,
  `PHONE_NUMBER` VARCHAR(10) NULL DEFAULT NULL,
  `CREATE_TIME` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `my_pantry`.`BRANDS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_pantry`.`BRANDS` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `my_pantry`.`BRANDS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `BRAND_NAME` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `my_pantry`.`CATEGORIES`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_pantry`.`CATEGORIES` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `my_pantry`.`CATEGORIES` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `CATEGORY_NAME` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `my_pantry`.`UNITS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_pantry`.`UNITS` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `my_pantry`.`UNITS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `UNIT_NAME` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `my_pantry`.`INVENTORY_ITEM`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_pantry`.`INVENTORY_ITEM` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `my_pantry`.`INVENTORY_ITEM` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `BARCODES` INT NULL DEFAULT NULL COMMENT '-  Use VARCHAR(13) to accommodate UPC-A (12 digits) and EAN-13 (13 digits).\n- If some items (like produce) don’t have barcodes, allow NULL.\n',
  `DESCRIPTION` VARCHAR(100) NOT NULL,
  `QUANTITY` DECIMAL(10,2) NOT NULL,
  `EXPIRATION_DATE` DATE NOT NULL,
  `BRANDS_ID` INT NOT NULL,
  `CATEGORIES_ID` INT NOT NULL,
  `UNITS_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `fk_INVENTORY_ITEM_BRANDS1`
    FOREIGN KEY (`BRANDS_ID`)
    REFERENCES `my_pantry`.`BRANDS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_INVENTORY_ITEM_CATEGORIES1`
    FOREIGN KEY (`CATEGORIES_ID`)
    REFERENCES `my_pantry`.`CATEGORIES` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_INVENTORY_ITEM_UNITS1`
    FOREIGN KEY (`UNITS_ID`)
    REFERENCES `my_pantry`.`UNITS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;
CREATE UNIQUE INDEX `BARCODE_UNIQUE` ON `my_pantry`.`INVENTORY_ITEM` (`BARCODES` ASC) VISIBLE;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `my_pantry`.`STOCK`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_pantry`.`STOCK` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `my_pantry`.`STOCK` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `PURCHASE_DATE` DATE NOT NULL,
  `REMAINING_QUANTITY` DECIMAL(10,2) NOT NULL,
  `USE_BY` DATE NOT NULL,
  `INVENTORY_ITEM_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `fk_STOCK_INVENTORY_ITEM1`
    FOREIGN KEY (`INVENTORY_ITEM_ID`)
    REFERENCES `my_pantry`.`INVENTORY_ITEM` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `my_pantry`.`ROLE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_pantry`.`ROLE` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `my_pantry`.`ROLE` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `my_pantry`.`USERS_ROLES`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_pantry`.`USERS_ROLES` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `my_pantry`.`USERS_ROLES` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ROLE_ID` INT NOT NULL,
  `USERS_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `fk_USERS_ROLES_ROLE1`
    FOREIGN KEY (`ROLE_ID`)
    REFERENCES `my_pantry`.`ROLE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USERS_ROLES_USERS1`
    FOREIGN KEY (`USERS_ID`)
    REFERENCES `my_pantry`.`USERS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
