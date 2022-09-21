-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: sistemavoto
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `candidati`
--

DROP TABLE IF EXISTS `candidati`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidati` (
  `idCandidati` varchar(100) NOT NULL,
  `idElezione` varchar(100) NOT NULL,
  PRIMARY KEY (`idCandidati`,`idElezione`),
  KEY `idElezione_idx` (`idElezione`),
  CONSTRAINT `idElezione` FOREIGN KEY (`idElezione`) REFERENCES `elezioni` (`idElezioni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidati`
--

LOCK TABLES `candidati` WRITE;
/*!40000 ALTER TABLE `candidati` DISABLE KEYS */;
INSERT INTO `candidati` VALUES ('ducati','ranking moto'),('honda','ranking moto'),('kawasaki','ranking moto'),('ktm','ranking moto'),('triumph','ranking moto'),('yamaha','ranking moto'),('test1','testCategorico'),('test2','testCategorico'),('test3','testCategorico'),('test1','testPreferenziale'),('test2','testPreferenziale'),('test3','testPreferenziale'),('contro','testReferendum'),('pro','testReferendum');
/*!40000 ALTER TABLE `candidati` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `elezioni`
--

DROP TABLE IF EXISTS `elezioni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `elezioni` (
  `idElezioni` varchar(100) NOT NULL,
  `attiva` tinyint NOT NULL,
  `tipoElezione` varchar(100) DEFAULT NULL,
  `conclusa` tinyint DEFAULT NULL,
  PRIMARY KEY (`idElezioni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `elezioni`
--

LOCK TABLES `elezioni` WRITE;
/*!40000 ALTER TABLE `elezioni` DISABLE KEYS */;
INSERT INTO `elezioni` VALUES ('ranking moto',0,'preferenziale  maggioranza | non assoluta',1),('testCategorico',0,'categorico  maggioranza | non assoluta',0),('testPreferenziale',0,'preferenziale  maggioranza | non assoluta',0),('testReferendum',0,'referendum  con quorum',0);
/*!40000 ALTER TABLE `elezioni` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utenti`
--

DROP TABLE IF EXISTS `utenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utenti` (
  `username` varchar(15) NOT NULL,
  `password` char(43) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `cognome` varchar(45) NOT NULL,
  `dataDiNascita` date NOT NULL,
  `nazionedinascita` varchar(5) NOT NULL,
  `comunedinascita` varchar(45) DEFAULT NULL,
  `isAdmin` tinyint(1) NOT NULL,
  `codiceFiscale` char(16) NOT NULL,
  `sesso` char(1) NOT NULL DEFAULT 'M',
  PRIMARY KEY (`username`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utenti`
--

LOCK TABLES `utenti` WRITE;
/*!40000 ALTER TABLE `utenti` DISABLE KEYS */;
INSERT INTO `utenti` VALUES ('alexAdmin','2VCBIs0UPWnfIpvzYkt7yyuKyB7SEKDJJkVe8RnBKr0','aleksandar','manasiev','1999-06-10','IT','milano',1,'MNSLSN99E10F205V','M'),('alexander','2fDRTzm3Zl2yy/1grYYSoGupkQGJblvAcs5BWyhO+Ig','aleksandar','manasiev','1999-06-10','IT','lodi',0,'MNSLSN99E10E205V','M'),('testAdmin1','oUDAwe2i3vK4MDY7o2KqTX0lXCYpYFRIIfVW4WZhtv8','admin','admin','1999-06-15','IT','crema',1,'DMNDMN99E15D142Q','M'),('testUser1','G08OmFGXGZjnMgeFRMlrNsPQHO33yqMyNZ1vHYNWcBQ','mario','kart','1999-06-11','IT','bergamo',0,'KRTMRA99E11A794G','M'),('testUser2','YDA64iuZiGG847KPM+7BvnWKITyGyTwHbb6fVYwRx1I','luigi','kart','1999-06-12','IT','roma',0,'KRTLGU99E12H501W','M'),('testUser3','/WGgOvT3fYcPwh4F5+gGeAlcktgIz7O1wnnuBMdKyhM','lalala','nonloso','1999-06-13','GER',NULL,0,'NNLLLL99E13Z112T','M'),('testUser4','pOYk1obgPtJ2fAq9hcFEJrCxFX0s6B0nu0/k9vAdaIo','lululu','nonloso2','1999-06-14','FR',NULL,0,'NNLLLL99E14Z110R','M');
/*!40000 ALTER TABLE `utenti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voti`
--

DROP TABLE IF EXISTS `voti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voti` (
  `idElezione` varchar(100) NOT NULL,
  `idCandidato` varchar(100) NOT NULL,
  `peso` int NOT NULL,
  `idElettore` varchar(100) NOT NULL,
  PRIMARY KEY (`idElezione`,`idCandidato`,`peso`,`idElettore`),
  KEY `idElettore_idx` (`idCandidato`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voti`
--

LOCK TABLES `voti` WRITE;
/*!40000 ALTER TABLE `voti` DISABLE KEYS */;
INSERT INTO `voti` VALUES ('ranking moto','ducati',1,'dc8DkRy39H+b513toYWtw4fI/kure6qXwisEcVxgDzw'),('ranking moto','ducati',2,'bB4GWH5/z7Ose0Llmsw2cNIoyUmfIz01Oe4fVCKfpIc'),('ranking moto','ducati',6,'Ircf0iNMkhCcnk8CPLtGa/oaC9pGgnyBtOifIziDtPo'),('ranking moto','honda',4,'bB4GWH5/z7Ose0Llmsw2cNIoyUmfIz01Oe4fVCKfpIc'),('ranking moto','honda',5,'dc8DkRy39H+b513toYWtw4fI/kure6qXwisEcVxgDzw'),('ranking moto','honda',5,'Ircf0iNMkhCcnk8CPLtGa/oaC9pGgnyBtOifIziDtPo'),('ranking moto','kawasaki',3,'bB4GWH5/z7Ose0Llmsw2cNIoyUmfIz01Oe4fVCKfpIc'),('ranking moto','kawasaki',3,'dc8DkRy39H+b513toYWtw4fI/kure6qXwisEcVxgDzw'),('ranking moto','kawasaki',4,'Ircf0iNMkhCcnk8CPLtGa/oaC9pGgnyBtOifIziDtPo'),('ranking moto','ktm',1,'bB4GWH5/z7Ose0Llmsw2cNIoyUmfIz01Oe4fVCKfpIc'),('ranking moto','ktm',3,'Ircf0iNMkhCcnk8CPLtGa/oaC9pGgnyBtOifIziDtPo'),('ranking moto','ktm',6,'dc8DkRy39H+b513toYWtw4fI/kure6qXwisEcVxgDzw'),('ranking moto','triumph',2,'Ircf0iNMkhCcnk8CPLtGa/oaC9pGgnyBtOifIziDtPo'),('ranking moto','triumph',4,'dc8DkRy39H+b513toYWtw4fI/kure6qXwisEcVxgDzw'),('ranking moto','triumph',5,'bB4GWH5/z7Ose0Llmsw2cNIoyUmfIz01Oe4fVCKfpIc'),('ranking moto','yamaha',1,'Ircf0iNMkhCcnk8CPLtGa/oaC9pGgnyBtOifIziDtPo'),('ranking moto','yamaha',2,'dc8DkRy39H+b513toYWtw4fI/kure6qXwisEcVxgDzw'),('ranking moto','yamaha',6,'bB4GWH5/z7Ose0Llmsw2cNIoyUmfIz01Oe4fVCKfpIc');
/*!40000 ALTER TABLE `voti` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-21 19:29:12
