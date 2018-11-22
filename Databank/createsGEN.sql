-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema ID222177_g52
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ID222177_g52
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ID222177_g52` DEFAULT CHARACTER SET latin1 ;
USE `ID222177_g52` ;

-- -----------------------------------------------------
-- Table `ID222177_g52`.`Speler`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ID222177_g52`.`Speler` (
  `naam` VARCHAR(50) NOT NULL,
  `geboortejaar` INT(11) NOT NULL,
  `krediet` INT(11) NOT NULL,
  PRIMARY KEY (`naam`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `ID222177_g52`.`Kaarttype`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ID222177_g52`.`Kaarttype` (
  `kaarttype` VARCHAR(10) NOT NULL,
  `waarde` INT(11) NOT NULL,
  `omschrijving` VARCHAR(10) NOT NULL,
  `startstapel` TINYINT(1) NOT NULL,
  `prijs` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`omschrijving`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `ID222177_g52`.`Kaart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ID222177_g52`.`Kaart` (
  `spelerNaam` VARCHAR(50) NOT NULL,
  `omschrijving` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`spelerNaam`, `omschrijving`),
  CONSTRAINT `Kaart_ibfk_1`
    FOREIGN KEY (`spelerNaam`)
    REFERENCES `ID222177_g52`.`Speler` (`naam`),
  CONSTRAINT `Kaart_ibfk_2`
    FOREIGN KEY (`omschrijving`)
    REFERENCES `ID222177_g52`.`Kaarttype` (`omschrijving`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE INDEX `omschrijving` ON `ID222177_g52`.`Kaart` (`omschrijving` ASC);


-- -----------------------------------------------------
-- Table `ID222177_g52`.`Wedstrijd`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ID222177_g52`.`Wedstrijd` (
  `naam` VARCHAR(50) NOT NULL,
  `aantalGespeeldeSets` INT(1) NOT NULL,
  PRIMARY KEY (`naam`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `ID222177_g52`.`WedstrijdKaartSpeler`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ID222177_g52`.`WedstrijdKaartSpeler` (
  `naamWedstrijd` VARCHAR(50) NOT NULL,
  `naamSpeler` VARCHAR(50) NOT NULL,
  `omschrijving` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`naamWedstrijd`, `naamSpeler`, `omschrijving`),
  CONSTRAINT `WedstrijdKaartSpeler_ibfk_1`
    FOREIGN KEY (`naamSpeler`)
    REFERENCES `ID222177_g52`.`Speler` (`naam`),
  CONSTRAINT `WedstrijdKaartSpeler_ibfk_2`
    FOREIGN KEY (`omschrijving`)
    REFERENCES `ID222177_g52`.`Kaarttype` (`omschrijving`),
  CONSTRAINT `WedstrijdKaartSpeler_ibfk_3`
    FOREIGN KEY (`naamWedstrijd`)
    REFERENCES `ID222177_g52`.`Wedstrijd` (`naam`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE INDEX `naamSpeler` ON `ID222177_g52`.`WedstrijdKaartSpeler` (`naamSpeler` ASC);

CREATE INDEX `omschrijving` ON `ID222177_g52`.`WedstrijdKaartSpeler` (`omschrijving` ASC);


-- -----------------------------------------------------
-- Table `ID222177_g52`.`WedstrijdSpeler`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ID222177_g52`.`WedstrijdSpeler` (
  `naamWedstrijd` VARCHAR(50) NOT NULL,
  `naamSpeler` VARCHAR(50) NOT NULL,
  `aantalGewonnenSets` INT(1) NOT NULL,
  PRIMARY KEY (`naamWedstrijd`, `naamSpeler`),
  CONSTRAINT `WedstrijdSpeler_ibfk_1`
    FOREIGN KEY (`naamWedstrijd`)
    REFERENCES `ID222177_g52`.`Wedstrijd` (`naam`),
  CONSTRAINT `WedstrijdSpeler_ibfk_2`
    FOREIGN KEY (`naamSpeler`)
    REFERENCES `ID222177_g52`.`Speler` (`naam`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

CREATE INDEX `naamSpeler` ON `ID222177_g52`.`WedstrijdSpeler` (`naamSpeler` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
