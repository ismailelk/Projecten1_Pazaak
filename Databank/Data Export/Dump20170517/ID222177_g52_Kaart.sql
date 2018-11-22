CREATE DATABASE  IF NOT EXISTS `ID222177_g52` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `ID222177_g52`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: ID222177_g52.db.webhosting.be    Database: ID222177_g52
-- ------------------------------------------------------
-- Server version	5.7.14-8

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Kaart`
--

DROP TABLE IF EXISTS `Kaart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Kaart` (
  `spelerNaam` varchar(50) NOT NULL,
  `omschrijving` varchar(10) NOT NULL,
  PRIMARY KEY (`spelerNaam`,`omschrijving`),
  KEY `omschrijving` (`omschrijving`),
  CONSTRAINT `Kaart_ibfk_1` FOREIGN KEY (`spelerNaam`) REFERENCES `Speler` (`naam`),
  CONSTRAINT `Kaart_ibfk_2` FOREIGN KEY (`omschrijving`) REFERENCES `Kaarttype` (`omschrijving`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Kaart`
--

LOCK TABLES `Kaart` WRITE;
/*!40000 ALTER TABLE `Kaart` DISABLE KEYS */;
INSERT INTO `Kaart` VALUES ('Jan','+1/-1'),('Simon','+1/-1'),('Jan','+2'),('Simon','+2'),('Jan','+3/-3'),('Simon','+3/-3'),('Jan','+4'),('Simon','+4'),('Jan','+5'),('Simon','+5'),('Jan','+6'),('Simon','+6'),('Jan','-1'),('Simon','-1'),('Jan','-2'),('Simon','-2'),('Jan','-3'),('Simon','-3'),('Jan','-5'),('Simon','-5');
/*!40000 ALTER TABLE `Kaart` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-17 17:38:12
