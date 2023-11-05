-- MySQL Script generated by MySQL Workbench
-- Thu Nov  2 00:13:16 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema sis-cripto
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sis-cripto
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sis-cripto` DEFAULT CHARACTER SET utf8 ;
USE `sis-cripto` ;

-- -----------------------------------------------------
-- Table `sis-cripto`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sis-cripto`.`User` (
  `dni_user` VARCHAR(10) NOT NULL,
  `name_user` VARCHAR(60) NOT NULL,
  `surname_user` VARCHAR(50) NOT NULL,
  `gender_user` VARCHAR(40) NOT NULL,
  `email_user` VARCHAR(30) NOT NULL,
  `tel_user` VARCHAR(10) NULL,
  PRIMARY KEY (`dni_user`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sis-cripto`.`Wallet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sis-cripto`.`Wallet` (
  `id_wallet` BINARY(16) NOT NULL,
  `User_dni_user` VARCHAR(10) NOT NULL,
  `balance_wallet` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id_wallet`),
  INDEX `fk_Billetera_Usuario1_idx` (`User_dni_user` ASC) VISIBLE,
  CONSTRAINT `fk_Billetera_Usuario1`
    FOREIGN KEY (`User_dni_user`)
    REFERENCES `sis-cripto`.`User` (`dni_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sis-cripto`.`Currency`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sis-cripto`.`Currency` (
  `ticker_currency` VARCHAR(10) NOT NULL,
  `name_currency` VARCHAR(40) NOT NULL,
  `value_currency` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`ticker_currency`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sis-cripto`.`Transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sis-cripto`.`Transaction` (
  `id_transaction` BIGINT auto_increment,
  `datetime_transaction` DATETIME NOT NULL,
  `type_transaction` VARCHAR(30) NOT NULL,
  `Currency_ticker_currency_o` VARCHAR(10) NULL,
  `Currency_ticker_currency_d` VARCHAR(10) NOT NULL,
  `Wallet_id_wallet_o` BINARY(16) NULL,
  `Wallet_id_wallet_d` BINARY(16) NOT NULL,
  `amount_transaction_o` DECIMAL(10,2) NULL,
  `amount_transaction_d` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id_transaction`),
  INDEX `fk_Transaccion_Divisa1_idx` (`Currency_ticker_currency_o` ASC) VISIBLE,
  INDEX `fk_Transaccion_Divisa2_idx` (`Currency_ticker_currency_d` ASC) VISIBLE,
  INDEX `fk_Transaccion_Billetera1_idx` (`Wallet_id_wallet_o` ASC) VISIBLE,
  INDEX `fk_Transaccion_Billetera2_idx` (`Wallet_id_wallet_d` ASC) VISIBLE,
  CONSTRAINT `fk_Transaccion_Divisa1`
    FOREIGN KEY (`Currency_ticker_currency_o`)
    REFERENCES `sis-cripto`.`Currency` (`ticker_currency`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Transaccion_Divisa2`
    FOREIGN KEY (`Currency_ticker_currency_d`)
    REFERENCES `sis-cripto`.`Currency` (`ticker_currency`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Transaccion_Billetera1`
    FOREIGN KEY (`Wallet_id_wallet_o`)
    REFERENCES `sis-cripto`.`Wallet` (`id_wallet`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Transaccion_Billetera2`
    FOREIGN KEY (`Wallet_id_wallet_d`)
    REFERENCES `sis-cripto`.`Wallet` (`id_wallet`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sis-cripto`.`Holding`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sis-cripto`.`Holding` (
  `Wallet_id_wallet` BINARY(16) NOT NULL,
  `Currency_ticker_currency` VARCHAR(10) NOT NULL,
  `amount` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`Wallet_id_wallet`, `Currency_ticker_currency`),
  INDEX `fk_Billetera_has_Divisa_Divisa1_idx` (`Currency_ticker_currency` ASC) VISIBLE,
  INDEX `fk_Billetera_has_Divisa_Billetera1_idx` (`Wallet_id_wallet` ASC) VISIBLE,
  CONSTRAINT `fk_Billetera_has_Divisa_Billetera1`
    FOREIGN KEY (`Wallet_id_wallet`)
    REFERENCES `sis-cripto`.`Wallet` (`id_wallet`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Billetera_has_Divisa_Divisa1`
    FOREIGN KEY (`Currency_ticker_currency`)
    REFERENCES `sis-cripto`.`Currency` (`ticker_currency`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



