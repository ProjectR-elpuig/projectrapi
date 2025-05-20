-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         11.4.2-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.7.0.6850
-- --------------------------------------------------------

-- Volcando estructura de base de datos para projectr
CREATE DATABASE IF NOT EXISTS `projectr` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;
USE `projectr`;

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
