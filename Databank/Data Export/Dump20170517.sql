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

--
-- Table structure for table `Kaarttype`
--

DROP TABLE IF EXISTS `Kaarttype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Kaarttype` (
  `kaarttype` varchar(10) NOT NULL,
  `waarde` int(11) NOT NULL,
  `omschrijving` varchar(10) NOT NULL,
  `startstapel` tinyint(1) NOT NULL,
  `prijs` int(11) DEFAULT NULL,
  PRIMARY KEY (`omschrijving`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Kaarttype`
--

LOCK TABLES `Kaarttype` WRITE;
/*!40000 ALTER TABLE `Kaarttype` DISABLE KEYS */;
INSERT INTO `Kaarttype` VALUES ('+',1,'+1',0,5),('+/-',1,'+1/-1',1,10),('+',2,'+2',1,5),('+/-',2,'+2/-2',0,10),('+',3,'+3',0,5),('+/-',3,'+3/-3',1,10),('+',4,'+4',1,5),('+/-',4,'+4/-4',0,10),('+',5,'+5',1,5),('+/-',5,'+5/-5',0,10),('+',6,'+6',1,5),('+/-',6,'+6/-6',0,10),('-',1,'-1',1,5),('-',2,'-2',1,5),('-',3,'-3',1,5),('-',4,'-4',0,5),('-',5,'-5',1,5),('-',6,'-6',0,5),('+',1,'1',0,0),('x+/-y',1,'1+/-2',0,100),('+',10,'10',0,0),('xT',1,'1T',0,20),('+',2,'2',0,0),('x&y',2,'2&4',0,50),('+',3,'3',0,0),('x&y',3,'3&6',0,50),('+',4,'4',0,0),('+',5,'5',0,0),('+',6,'6',0,0),('+',7,'7',0,0),('+',8,'8',0,0),('+',9,'9',0,0),('D',0,'D',0,30);
/*!40000 ALTER TABLE `Kaarttype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Speler`
--

DROP TABLE IF EXISTS `Speler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Speler` (
  `naam` varchar(50) NOT NULL,
  `geboortejaar` int(11) NOT NULL,
  `krediet` int(11) NOT NULL,
  PRIMARY KEY (`naam`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Speler`
--

LOCK TABLES `Speler` WRITE;
/*!40000 ALTER TABLE `Speler` DISABLE KEYS */;
INSERT INTO `Speler` VALUES ('Jan',1994,0),('Simon',1994,500);
/*!40000 ALTER TABLE `Speler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Wedstrijd`
--

DROP TABLE IF EXISTS `Wedstrijd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Wedstrijd` (
  `naam` varchar(50) NOT NULL,
  `aantalGespeeldeSets` int(1) NOT NULL,
  PRIMARY KEY (`naam`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Wedstrijd`
--

LOCK TABLES `Wedstrijd` WRITE;
/*!40000 ALTER TABLE `Wedstrijd` DISABLE KEYS */;
/*!40000 ALTER TABLE `Wedstrijd` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WedstrijdKaartSpeler`
--

DROP TABLE IF EXISTS `WedstrijdKaartSpeler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WedstrijdKaartSpeler` (
  `naamWedstrijd` varchar(50) NOT NULL,
  `naamSpeler` varchar(50) NOT NULL,
  `omschrijving` varchar(10) NOT NULL,
  PRIMARY KEY (`naamWedstrijd`,`naamSpeler`,`omschrijving`),
  KEY `naamSpeler` (`naamSpeler`),
  KEY `omschrijving` (`omschrijving`),
  CONSTRAINT `WedstrijdKaartSpeler_ibfk_1` FOREIGN KEY (`naamSpeler`) REFERENCES `Speler` (`naam`),
  CONSTRAINT `WedstrijdKaartSpeler_ibfk_2` FOREIGN KEY (`omschrijving`) REFERENCES `Kaarttype` (`omschrijving`),
  CONSTRAINT `WedstrijdKaartSpeler_ibfk_3` FOREIGN KEY (`naamWedstrijd`) REFERENCES `Wedstrijd` (`naam`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WedstrijdKaartSpeler`
--

LOCK TABLES `WedstrijdKaartSpeler` WRITE;
/*!40000 ALTER TABLE `WedstrijdKaartSpeler` DISABLE KEYS */;
/*!40000 ALTER TABLE `WedstrijdKaartSpeler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `WedstrijdSpeler`
--

DROP TABLE IF EXISTS `WedstrijdSpeler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `WedstrijdSpeler` (
  `naamWedstrijd` varchar(50) NOT NULL,
  `naamSpeler` varchar(50) NOT NULL,
  `aantalGewonnenSets` int(1) NOT NULL,
  PRIMARY KEY (`naamWedstrijd`,`naamSpeler`),
  KEY `naamSpeler` (`naamSpeler`),
  CONSTRAINT `WedstrijdSpeler_ibfk_1` FOREIGN KEY (`naamWedstrijd`) REFERENCES `Wedstrijd` (`naam`),
  CONSTRAINT `WedstrijdSpeler_ibfk_2` FOREIGN KEY (`naamSpeler`) REFERENCES `Speler` (`naam`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `WedstrijdSpeler`
--

LOCK TABLES `WedstrijdSpeler` WRITE;
/*!40000 ALTER TABLE `WedstrijdSpeler` DISABLE KEYS */;
/*!40000 ALTER TABLE `WedstrijdSpeler` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-17 17:36:01
