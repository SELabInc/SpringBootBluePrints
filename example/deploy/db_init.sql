-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema SpringBootBluePrintsDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema SpringBootBluePrintsDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `SpringBootBluePrintsDB` DEFAULT CHARACTER SET utf8 ;
USE `SpringBootBluePrintsDB` ;

-- -----------------------------------------------------
-- Table `SpringBootBluePrintsDB`.`UserGroup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SpringBootBluePrintsDB`.`UserGroup` (
  `Id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `UK_5k805tordnv062yboxhx5w3rb` (`Name` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `SpringBootBluePrintsDB`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SpringBootBluePrintsDB`.`User` (
  `Id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `AccountNonExpired` BIT(1) NOT NULL,
  `AccountNonLocked` BIT(1) NOT NULL,
  `CredentialsNonExpired` BIT(1) NOT NULL,
  `Enabled` BIT(1) NOT NULL,
  `Password` VARCHAR(255) NOT NULL,
  `RegisterDate` DATETIME(6) NOT NULL,
  `Username` VARCHAR(255) NOT NULL,
  `UserGroupId` BIGINT(20) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `UK_seh7nteifndaopocsq9f1w8ia` (`Username` ASC),
  INDEX `FKq015opena7gk2cwth9uw400ta` (`UserGroupId` ASC),
  CONSTRAINT `FKq015opena7gk2cwth9uw400ta`
    FOREIGN KEY (`UserGroupId`)
    REFERENCES `SpringBootBluePrintsDB`.`UserGroup` (`Id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `SpringBootBluePrintsDB`.`UserGroupAuth`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SpringBootBluePrintsDB`.`UserGroupAuth` (
  `Id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `Auth` NVARCHAR(45) NOT NULL,
  `UserGroupId` BIGINT(20) NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `FK24md3ac19rn09ewh0959o0vr7` (`UserGroupId` ASC),
  CONSTRAINT `FK24md3ac19rn09ewh0959o0vr7`
    FOREIGN KEY (`UserGroupId`)
    REFERENCES `SpringBootBluePrintsDB`.`UserGroup` (`Id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- -----------------------------------------------------
-- INSERT defult values
-- -----------------------------------------------------

INSERT INTO `SpringBootBluePrintsDB`.`UserGroup` (Name)
VALUES ('None')
;

INSERT INTO `SpringBootBluePrintsDB`.`UserGroup` (Name)
VALUES ('Member')
;

INSERT INTO `SpringBootBluePrintsDB`.`UserGroup` (Name)
VALUES ('Admin')
;

INSERT INTO `SpringBootBluePrintsDB`.`UserGroupAuth` (UserGroupId, Auth)
SELECT id, 'MEMBER'
FROM `SpringBootBluePrintsDB`.`UserGroup`
WHERE Name = 'Admin'
;

INSERT INTO `SpringBootBluePrintsDB`.`UserGroupAuth` (UserGroupId, Auth)
SELECT id, 'ADMIN'
FROM `SpringBootBluePrintsDB`.`UserGroup`
WHERE Name = 'Admin'
;

INSERT INTO `SpringBootBluePrintsDB`.`UserGroupAuth` (UserGroupId, Auth)
SELECT id, 'MEMBER'
FROM `SpringBootBluePrintsDB`.`UserGroup`
WHERE Name = 'Member'
;

INSERT INTO `SpringBootBluePrintsDB`.`User` (AccountNonExpired, AccountNonLocked, CredentialsNonExpired, Enabled, Password, RegisterDate, Username, UserGroupId)
SELECT 1, 1, 1, 1, '$2a$12$/kwx9QzXMuR0WU.H88uq5OM0d91GMhnimG7WDPXfHFz9Yt7kUgSym', now(), 'selabdev', id
FROM `SpringBootBluePrintsDB`.`UserGroup`
WHERE Name = 'Admin'
;

INSERT INTO `SpringBootBluePrintsDB`.`User` (AccountNonExpired, AccountNonLocked, CredentialsNonExpired, Enabled, Password, RegisterDate, Username, UserGroupId)
SELECT 1, 1, 1, 1, '$2a$12$/kwx9QzXMuR0WU.H88uq5OM0d91GMhnimG7WDPXfHFz9Yt7kUgSym', now(), 'selebadmin', id
FROM `SpringBootBluePrintsDB`.`UserGroup`
WHERE Name = 'Admin'
;

INSERT INTO `SpringBootBluePrintsDB`.`User` (AccountNonExpired, AccountNonLocked, CredentialsNonExpired, Enabled, Password, RegisterDate, Username, UserGroupId)
SELECT 1, 1, 1, 1, '$2a$12$/kwx9QzXMuR0WU.H88uq5OM0d91GMhnimG7WDPXfHFz9Yt7kUgSym', now(), 'selebmember', id
FROM `SpringBootBluePrintsDB`.`UserGroup`
WHERE Name = 'Member'
;

INSERT INTO `SpringBootBluePrintsDB`.`User` (AccountNonExpired, AccountNonLocked, CredentialsNonExpired, Enabled, Password, RegisterDate, Username, UserGroupId)
SELECT 1, 1, 1, 1, '$2a$12$/kwx9QzXMuR0WU.H88uq5OM0d91GMhnimG7WDPXfHFz9Yt7kUgSym', now(), 'selebregister', id
FROM `SpringBootBluePrintsDB`.`UserGroup`
WHERE Name = 'None'
;

-- -----------------------------------------------------
-- INSERT smaple values
-- -----------------------------------------------------

