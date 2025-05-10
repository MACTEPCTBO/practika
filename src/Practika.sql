-- MySQL Script for 'practika' Database
-- Created: May 2025
-- Character set: UTF-8mb4
-- Collation: utf8mb4_unicode_ci

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema practika
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `practika`;
CREATE SCHEMA IF NOT EXISTS `practika` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `practika`;

-- -----------------------------------------------------
-- Table `practika`.`city`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `practika`.`city` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `region` VARCHAR(100) NULL,
  `country` VARCHAR(100) NOT NULL DEFAULT 'Россия',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `city_name_unique` (`name`, `region`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `practika`.`office`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `practika`.`office` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `short_name` VARCHAR(20) NULL,
  `description` TEXT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `office_name_unique` (`name`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `practika`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `practika`.`address` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `office_id` INT NOT NULL,
  `city_id` INT NOT NULL,
  `street` VARCHAR(200) NOT NULL,
  `building` VARCHAR(20) NOT NULL,
  `apartment` VARCHAR(20) NULL,
  `postal_code` VARCHAR(20) NULL,
  `is_primary` TINYINT(1) NOT NULL DEFAULT 0,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_address_office_idx` (`office_id` ASC),
  INDEX `fk_address_city_idx` (`city_id` ASC),
  CONSTRAINT `fk_address_office`
    FOREIGN KEY (`office_id`)
    REFERENCES `practika`.`office` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_address_city`
    FOREIGN KEY (`city_id`)
    REFERENCES `practika`.`city` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `practika`.`floor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `practika`.`floor` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `office_id` INT NOT NULL,
  `number` INT NOT NULL,
  `description` VARCHAR(200) NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_floor_office_idx` (`office_id` ASC),
  CONSTRAINT `fk_floor_office`
    FOREIGN KEY (`office_id`)
    REFERENCES `practika`.`office` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `practika`.`cabinet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `practika`.`cabinet` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `floor_id` INT NOT NULL,
  `number` VARCHAR(20) NOT NULL,
  `description` VARCHAR(200) NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_cabinet_floor_idx` (`floor_id` ASC),
  CONSTRAINT `fk_cabinet_floor`
    FOREIGN KEY (`floor_id`)
    REFERENCES `practika`.`floor` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `practika`.`manufacturer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `practika`.`manufacturer` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NOT NULL,
  `website` VARCHAR(255) NULL,
  `country` VARCHAR(100) NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `manufacturer_name_unique` (`name`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `practika`.`seller`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `practika`.`seller` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `legal_name` VARCHAR(200) NULL,
  `phone` VARCHAR(20) NOT NULL,
  `email` VARCHAR(100) NULL,
  `rating` DECIMAL(3,2) NULL,
  `website` VARCHAR(255) NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `seller_name_unique` (`name`),
  UNIQUE INDEX `seller_phone_unique` (`phone`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `practika`.`component_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `practika`.`component_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `description` TEXT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `component_type_name_unique` (`name`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `practika`.`component`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `practika`.`component` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cabinet_id` INT NOT NULL,
  `type_id` INT NOT NULL,
  `manufacturer_id` INT NOT NULL,
  `seller_id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `model` VARCHAR(100) NULL,
  `serial_number` VARCHAR(100) NOT NULL,
  `inventory_number` VARCHAR(100) NOT NULL,
  `purchase_date` DATE NOT NULL,
  `warranty_end_date` DATE NULL,
  `price` DECIMAL(10,2) NULL,
  `status` ENUM('active', 'in_repair', 'decommissioned', 'lost') NOT NULL DEFAULT 'active',
  `notes` TEXT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `serial_number_unique` (`serial_number`),
  UNIQUE INDEX `inventory_number_unique` (`inventory_number`),
  INDEX `fk_component_cabinet_idx` (`cabinet_id` ASC),
  INDEX `fk_component_type_idx` (`type_id` ASC),
  INDEX `fk_component_manufacturer_idx` (`manufacturer_id` ASC),
  INDEX `fk_component_seller_idx` (`seller_id` ASC),
  CONSTRAINT `fk_component_cabinet`
    FOREIGN KEY (`cabinet_id`)
    REFERENCES `practika`.`cabinet` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_component_type`
    FOREIGN KEY (`type_id`)
    REFERENCES `practika`.`component_type` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_component_manufacturer`
    FOREIGN KEY (`manufacturer_id`)
    REFERENCES `practika`.`manufacturer` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_component_seller`
    FOREIGN KEY (`seller_id`)
    REFERENCES `practika`.`seller` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `practika`.`component_document`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `practika`.`component_document` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `component_id` INT NOT NULL,
  `document_type` ENUM('invoice', 'warranty', 'manual', 'certificate', 'other') NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `file_path` VARCHAR(512) NOT NULL,
  `file_size` INT NOT NULL,
  `upload_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `notes` TEXT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_component_document_component_idx` (`component_id` ASC),
  CONSTRAINT `fk_component_document_component`
    FOREIGN KEY (`component_id`)
    REFERENCES `practika`.`component` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `practika`.`employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `practika`.`employee` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `last_name` VARCHAR(100) NOT NULL,
  `first_name` VARCHAR(100) NOT NULL,
  `middle_name` VARCHAR(100) NULL,
  `position` VARCHAR(100) NOT NULL,
  `department` VARCHAR(100) NULL,
  `phone` VARCHAR(20) NULL,
  `email` VARCHAR(100) NULL,
  `is_active` TINYINT(1) NOT NULL DEFAULT 1,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `employee_email_unique` (`email`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `practika`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `practika`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `employee_id` INT NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  `password_hash` VARCHAR(255) NOT NULL,
  `role` ENUM('admin', 'manager', 'technician', 'viewer') NOT NULL DEFAULT 'viewer',
  `last_login` DATETIME NULL,
  `is_active` TINYINT(1) NOT NULL DEFAULT 1,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_unique` (`username`),
  INDEX `fk_user_employee_idx` (`employee_id` ASC),
  CONSTRAINT `fk_user_employee`
    FOREIGN KEY (`employee_id`)
    REFERENCES `practika`.`employee` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `practika`.`component_history`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `practika`.`component_history` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `component_id` INT NOT NULL,
  `changed_by` INT NOT NULL,
  `change_type` ENUM('create', 'update', 'delete', 'status_change', 'location_change') NOT NULL,
  `field_name` VARCHAR(50) NULL,
  `old_value` TEXT NULL,
  `new_value` TEXT NULL,
  `change_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `notes` TEXT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_component_history_component_idx` (`component_id` ASC),
  INDEX `fk_component_history_user_idx` (`changed_by` ASC),
  CONSTRAINT `fk_component_history_component`
    FOREIGN KEY (`component_id`)
    REFERENCES `practika`.`component` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_component_history_user`
    FOREIGN KEY (`changed_by`)
    REFERENCES `practika`.`user` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `practika`.`component_maintenance`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `practika`.`component_maintenance` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `component_id` INT NOT NULL,
  `maintenance_type` ENUM('preventive', 'repair', 'upgrade', 'inspection') NOT NULL,
  `performed_by` INT NULL,
  `start_date` DATETIME NOT NULL,
  `end_date` DATETIME NULL,
  `description` TEXT NOT NULL,
  `result` TEXT NULL,
  `cost` DECIMAL(10,2) NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_component_maintenance_component_idx` (`component_id` ASC),
  INDEX `fk_component_maintenance_employee_idx` (`performed_by` ASC),
  CONSTRAINT `fk_component_maintenance_component`
    FOREIGN KEY (`component_id`)
    REFERENCES `practika`.`component` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_component_maintenance_employee`
    FOREIGN KEY (`performed_by`)
    REFERENCES `practika`.`employee` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;