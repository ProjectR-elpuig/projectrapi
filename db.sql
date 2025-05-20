-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         11.4.2-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.7.0.6850
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para projectr
CREATE DATABASE IF NOT EXISTS `projectr` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;
USE `projectr`;

-- Volcando estructura para tabla projectr.productos
CREATE TABLE IF NOT EXISTS `productos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `precio` double NOT NULL,
  `stock` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Volcando datos para la tabla projectr.productos: ~50 rows (aproximadamente)
INSERT INTO `productos` (`id`, `nombre`, `descripcion`, `precio`, `stock`) VALUES
	(1, 'Laptop', 'Laptop de última generación', 1200.99, 10),
	(2, 'Teléfono', 'Smartphone avanzado', 799.5, 25),
	(3, 'Tablet', 'Tablet de 10 pulgadas', 299.99, 15),
	(7, 'Laptop', 'Laptop de última generación', 1200.99, 10),
	(8, 'Teléfono', 'Smartphone avanzado', 799.5, 25),
	(9, 'Tablet', 'Tablet de 10 pulgadas', 299.99, 15),
	(10, 'Laptop', 'Laptop de última generación', 1200.99, 10),
	(11, 'Teléfono', 'Smartphone avanzado', 799.5, 25),
	(12, 'Tablet', 'Tablet de 10 pulgadas', 299.99, 15),
	(13, 'Laptop', 'Laptop de última generación', 1200.99, 10),
	(14, 'Teléfono', 'Smartphone avanzado', 799.5, 25),
	(15, 'Tablet', 'Tablet de 10 pulgadas', 299.99, 15),
	(16, 'Laptop', 'Laptop de última generación', 1200.99, 10),
	(17, 'Teléfono', 'Smartphone avanzado', 799.5, 25),
	(18, 'Tablet', 'Tablet de 10 pulgadas', 299.99, 15),
	(19, 'Laptop', 'Laptop de última generación', 1200.99, 10),
	(20, 'Teléfono', 'Smartphone avanzado', 799.5, 25),
	(21, 'Tablet', 'Tablet de 10 pulgadas', 299.99, 15),
	(22, 'Laptop', 'Laptop de última generación', 1200.99, 10),
	(23, 'Teléfono', 'Smartphone avanzado', 799.5, 25),
	(24, 'Tablet', 'Tablet de 10 pulgadas', 299.99, 15),
	(25, 'Laptop', 'Laptop de última generación', 1200.99, 10),
	(26, 'Teléfono', 'Smartphone avanzado', 799.5, 25),
	(27, 'Tablet', 'Tablet de 10 pulgadas', 299.99, 15),
	(28, 'Laptop', 'Laptop de última generación', 1200.99, 10),
	(29, 'Teléfono', 'Smartphone avanzado', 799.5, 25),
	(30, 'Tablet', 'Tablet de 10 pulgadas', 299.99, 15),
	(31, 'Laptop', 'Laptop de última generación', 1200.99, 10),
	(32, 'Teléfono', 'Smartphone avanzado', 799.5, 25),
	(33, 'Tablet', 'Tablet de 10 pulgadas', 299.99, 15),
	(34, 'Laptop', 'Laptop de última generación', 1200.99, 10),
	(35, 'Teléfono', 'Smartphone avanzado', 799.5, 25),
	(36, 'Tablet', 'Tablet de 10 pulgadas', 299.99, 15),
	(37, 'Laptop', 'Laptop de última generación', 1200.99, 10),
	(38, 'Teléfono', 'Smartphone avanzado', 799.5, 25),
	(39, 'Tablet', 'Tablet de 10 pulgadas', 299.99, 15),
	(40, 'Laptop', 'Laptop de última generación', 1200.99, 10),
	(41, 'Teléfono', 'Smartphone avanzado', 799.5, 25),
	(42, 'Tablet', 'Tablet de 10 pulgadas', 299.99, 15),
	(43, 'Laptop', 'Laptop de última generación', 1200.99, 10),
	(44, 'Teléfono', 'Smartphone avanzado', 799.5, 25),
	(115, 'Laptop', 'Laptop de última generación', 1200.99, 10),
	(116, 'Teléfono', 'Smartphone avanzado', 799.5, 25),
	(117, 'Tablet', 'Tablet de 10 pulgadas', 299.99, 15),
	(118, 'Laptop', 'Laptop de última generación', 1200.99, 10),
	(119, 'Teléfono', 'Smartphone avanzado', 799.5, 25),
	(120, 'Tablet', 'Tablet de 10 pulgadas', 299.99, 15),
	(121, 'Laptop', 'Laptop de última generación', 1200.99, 10),
	(122, 'Teléfono', 'Smartphone avanzado', 799.5, 25),
	(123, 'Tablet', 'Tablet de 10 pulgadas', 299.99, 15),
	(124, 'Laptop', 'Laptop de última generación', 1200.99, 10),
	(125, 'Teléfono', 'Smartphone avanzado', 799.5, 25),
	(126, 'Tablet', 'Tablet de 10 pulgadas', 299.99, 15);

-- Volcando estructura para tabla projectr.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `citizenid` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `pwd` varchar(255) DEFAULT NULL,
  `img` mediumblob DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`citizenid`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `phone_number` (`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla projectr.usuarios: ~4 rows (aproximadamente)
INSERT INTO `usuarios` (`citizenid`, `username`, `pwd`, `img`, `phone_number`) VALUES
	('58a570d8-6b21-4ae6-a2ff-7dda8a06e28e', 'alex', '$2a$10$F4c955TRSduP35pw7k.nuOg0c4bbcO/swpYvjvkhtpBYkel6pWCvW', NULL, '604863373'),
	('7ac9fee4-9abe-4fec-a1cf-71ec8cbb4cab', 'davide', '$2a$10$obS2kNoPN/xgnPG/BPfXCeKbd5/sYG6van9ULL.Ohe0o.sbEf88YC', NULL, '660596259'),
	('ID123', 'usuario1', '$2a$10$88DPJWxR8/UfI1B7/hM4J.F.pRxDD00ECOXEGILitWC/1LCNroReu', NULL, '5551234'),
	('ID456', 'usuario2', '$2a$10$izMsDGQxstLDNjQBHCn9mu1/l2Lc8iLP5.pq3oshplxNFFJkv.O32', NULL, '5555678');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;


-- Volcando estructura para tabla projectr.contacts
CREATE TABLE IF NOT EXISTS `contacts` (
                                          `contactid` int(11) NOT NULL AUTO_INCREMENT,
    `citizenid` varchar(255) DEFAULT NULL,
    `phone_number` varchar(255) DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`contactid`),
    UNIQUE KEY `UC_Contact` (`citizenid`,`phone_number`),
    KEY `phone_number` (`phone_number`),
    CONSTRAINT `contacts_ibfk_1` FOREIGN KEY (`citizenid`) REFERENCES `usuarios` (`citizenid`),
    CONSTRAINT `contacts_ibfk_2` FOREIGN KEY (`phone_number`) REFERENCES `usuarios` (`phone_number`)
    ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla projectr.contacts: ~2 rows (aproximadamente)
INSERT INTO `contacts` (`contactid`, `citizenid`, `phone_number`, `name`) VALUES
                                                                              (2, '58a570d8-6b21-4ae6-a2ff-7dda8a06e28e', '660596259', 'Davide desde alex :)'),
                                                                              (8, '58a570d8-6b21-4ae6-a2ff-7dda8a06e28e', '5551234', 'ausuario1 desde alex :)');
