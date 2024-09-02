-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: frg
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `ask`
--

DROP TABLE IF EXISTS `ask`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ask` (
  `anum` int NOT NULL,
  `aTitle` varchar(100) NOT NULL,
  `aContents` varchar(4000) NOT NULL,
  `aDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `afile` varchar(255) DEFAULT NULL,
  `id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`anum`),
  KEY `id` (`id`),
  CONSTRAINT `ask_ibfk_1` FOREIGN KEY (`id`) REFERENCES `usertbl` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ask`
--

LOCK TABLES `ask` WRITE;
/*!40000 ALTER TABLE `ask` DISABLE KEYS */;
/*!40000 ALTER TABLE `ask` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `cnum` int NOT NULL,
  `cCount` int DEFAULT NULL,
  `id` varchar(50) DEFAULT NULL,
  `pnum` int DEFAULT NULL,
  PRIMARY KEY (`cnum`),
  KEY `id` (`id`),
  KEY `cart_ibfk_2` (`pnum`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`id`) REFERENCES `usertbl` (`id`),
  CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`pnum`) REFERENCES `product` (`pNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `catenum` int NOT NULL AUTO_INCREMENT,
  `catename` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`catenum`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'영양제'),(2,'다이어트 식품'),(3,'운동 보조 기구');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dorder`
--

DROP TABLE IF EXISTS `dorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dorder` (
  `donum` int NOT NULL AUTO_INCREMENT,
  `DOcount` int DEFAULT '0',
  `doprice` int NOT NULL DEFAULT '0',
  `onum` int DEFAULT NULL,
  `pnum` int DEFAULT NULL,
  PRIMARY KEY (`donum`),
  KEY `dorder_ibfk_2` (`pnum`),
  KEY `dorder_ibfk_1` (`onum`),
  CONSTRAINT `dorder_ibfk_1` FOREIGN KEY (`onum`) REFERENCES `ordertbl` (`onum`),
  CONSTRAINT `dorder_ibfk_2` FOREIGN KEY (`pnum`) REFERENCES `product` (`pNum`)
) ENGINE=InnoDB AUTO_INCREMENT=100007 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dorder`
--

LOCK TABLES `dorder` WRITE;
/*!40000 ALTER TABLE `dorder` DISABLE KEYS */;
INSERT INTO `dorder` VALUES (100000,3,43200,30001,5),(100001,8,80000,30003,1),(100002,6,154800,30000,6),(100003,2,18000,30002,7),(100004,1,10000,30004,1),(100005,3,147000,30008,4),(100006,4,644000,30009,2);
/*!40000 ALTER TABLE `dorder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manswer`
--

DROP TABLE IF EXISTS `manswer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manswer` (
  `mContents` varchar(4000) NOT NULL,
  `mDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `mid` varchar(50) DEFAULT NULL,
  `anum` int DEFAULT NULL,
  KEY `anum` (`anum`),
  KEY `manswer_ibfk_1` (`mid`),
  CONSTRAINT `manswer_ibfk_1` FOREIGN KEY (`mid`) REFERENCES `master` (`mid`),
  CONSTRAINT `manswer_ibfk_2` FOREIGN KEY (`anum`) REFERENCES `ask` (`anum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manswer`
--

LOCK TABLES `manswer` WRITE;
/*!40000 ALTER TABLE `manswer` DISABLE KEYS */;
/*!40000 ALTER TABLE `manswer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `master`
--

DROP TABLE IF EXISTS `master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `master` (
  `mid` varchar(255) NOT NULL,
  `mpwd` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `master`
--

LOCK TABLES `master` WRITE;
/*!40000 ALTER TABLE `master` DISABLE KEYS */;
/*!40000 ALTER TABLE `master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordertbl`
--

DROP TABLE IF EXISTS `ordertbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ordertbl` (
  `onum` int NOT NULL AUTO_INCREMENT,
  `odate` datetime(6) DEFAULT NULL,
  `id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`onum`),
  KEY `id` (`id`),
  CONSTRAINT `ordertbl_ibfk_1` FOREIGN KEY (`id`) REFERENCES `usertbl` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30010 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordertbl`
--

LOCK TABLES `ordertbl` WRITE;
/*!40000 ALTER TABLE `ordertbl` DISABLE KEYS */;
INSERT INTO `ordertbl` VALUES (30000,'2024-08-10 00:00:00.000000','이용자01'),(30001,'2024-08-05 00:00:00.000000','이용자02'),(30002,'2024-08-11 00:00:00.000000','이용자03'),(30003,'2024-08-05 00:00:00.000000','이용자04'),(30004,'2024-08-14 00:00:00.000000','이용자05'),(30005,'2024-08-20 00:00:00.000000','이용자05'),(30008,'2024-08-24 00:00:00.000000','이용자08'),(30009,'2024-08-20 00:00:00.000000','이용자09');
/*!40000 ALTER TABLE `ordertbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `ponum` int NOT NULL AUTO_INCREMENT,
  `potitle` varchar(255) DEFAULT NULL,
  `pocontents` varchar(255) DEFAULT NULL,
  `poDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `poFile` varchar(255) DEFAULT NULL,
  `powarning` int NOT NULL,
  `viewcnt` int DEFAULT '0',
  `replycnt` int DEFAULT '0',
  `id` varchar(255) NOT NULL,
  PRIMARY KEY (`ponum`),
  KEY `post_ibfk_1` (`id`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`id`) REFERENCES `usertbl` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=259 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'제목01','내용01','2024-08-14 01:55:53',NULL,0,0,0,'이용자01'),(2,'제목02','내용02','2024-08-14 01:55:53',NULL,0,0,0,'이용자02'),(3,'제목03','내용03','2024-08-14 01:55:53',NULL,0,0,0,'이용자03'),(4,'제목04','내용04','2024-08-14 01:55:53',NULL,0,0,0,'이용자04'),(5,'제목05','내용05','2024-08-14 01:55:53',NULL,0,0,0,'이용자05'),(6,'제목01','내용01','2024-08-14 01:56:10',NULL,0,0,0,'이용자01'),(7,'제목02','내용02','2024-08-14 01:56:10',NULL,0,0,0,'이용자02'),(8,'제목03','내용03','2024-08-14 01:56:10',NULL,0,0,0,'이용자03'),(9,'제목04','내용04','2024-08-14 01:56:10',NULL,0,0,0,'이용자04'),(10,'제목05','내용05','2024-08-14 01:56:10',NULL,0,0,0,'이용자05'),(13,'제목01','내용01','2024-08-14 01:56:12',NULL,0,0,0,'이용자01'),(14,'제목02','내용02','2024-08-14 01:56:12',NULL,0,0,0,'이용자02'),(15,'제목03','내용03','2024-08-14 01:56:12',NULL,0,0,0,'이용자03'),(16,'제목04','내용04','2024-08-14 01:56:12',NULL,0,0,0,'이용자04'),(17,'제목05','내용05','2024-08-14 01:56:12',NULL,0,0,0,'이용자05'),(18,'제목01','내용01','2024-08-14 01:56:12',NULL,0,0,0,'이용자01'),(19,'제목02','내용02','2024-08-14 01:56:12',NULL,0,0,0,'이용자02'),(20,'제목03','내용03','2024-08-14 01:56:12',NULL,0,0,0,'이용자03'),(21,'제목04','내용04','2024-08-14 01:56:12',NULL,0,0,0,'이용자04'),(22,'제목05','내용05','2024-08-14 01:56:12',NULL,0,0,0,'이용자05'),(28,'제목01','내용01','2024-08-14 01:56:13',NULL,0,0,0,'이용자01'),(29,'제목02','내용02','2024-08-14 01:56:13',NULL,0,0,0,'이용자02'),(30,'제목03','내용03','2024-08-14 01:56:13',NULL,0,0,0,'이용자03'),(31,'제목04','내용04','2024-08-14 01:56:13',NULL,0,0,0,'이용자04'),(32,'제목05','내용05','2024-08-14 01:56:13',NULL,0,0,0,'이용자05'),(33,'제목01','내용01','2024-08-14 01:56:13',NULL,0,0,0,'이용자01'),(34,'제목02','내용02','2024-08-14 01:56:13',NULL,0,0,0,'이용자02'),(35,'제목03','내용03','2024-08-14 01:56:13',NULL,0,0,0,'이용자03'),(36,'제목04','내용04','2024-08-14 01:56:13',NULL,0,0,0,'이용자04'),(37,'제목05','내용05','2024-08-14 01:56:13',NULL,0,0,0,'이용자05'),(38,'제목01','내용01','2024-08-14 01:56:13',NULL,0,0,0,'이용자01'),(39,'제목02','내용02','2024-08-14 01:56:13',NULL,0,0,0,'이용자02'),(40,'제목03','내용03','2024-08-14 01:56:13',NULL,0,0,0,'이용자03'),(41,'제목04','내용04','2024-08-14 01:56:13',NULL,0,0,0,'이용자04'),(42,'제목05','내용05','2024-08-14 01:56:13',NULL,0,0,0,'이용자05'),(43,'제목01','내용01','2024-08-14 01:56:13',NULL,0,0,0,'이용자01'),(44,'제목02','내용02','2024-08-14 01:56:13',NULL,0,0,0,'이용자02'),(45,'제목03','내용03','2024-08-14 01:56:13',NULL,0,0,0,'이용자03'),(46,'제목04','내용04','2024-08-14 01:56:13',NULL,0,0,0,'이용자04'),(47,'제목05','내용05','2024-08-14 01:56:13',NULL,0,0,0,'이용자05'),(59,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(60,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(61,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(62,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(63,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(64,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(65,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(66,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(67,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(68,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(69,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(70,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(71,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(72,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(73,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(74,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(75,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(76,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(77,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(78,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(79,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(80,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(81,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(82,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(83,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(84,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(85,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(86,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(87,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(88,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(89,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(90,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(91,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(92,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(93,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(94,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(95,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(96,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(97,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(98,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(122,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(123,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(124,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(125,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(126,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(127,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(128,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(129,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(130,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(131,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(132,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(133,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(134,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(135,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(136,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(137,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(138,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(139,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(140,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(141,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(142,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(143,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(144,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(145,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(146,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(147,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(148,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(149,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(150,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(151,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(152,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(153,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(154,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(155,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(156,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(157,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(158,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(159,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(160,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(161,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(162,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(163,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(164,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(165,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(166,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(167,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(168,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(169,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(170,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(171,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(172,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(173,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(174,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(175,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(176,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(177,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(178,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(179,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(180,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(181,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(182,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(183,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(184,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(185,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(186,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(187,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(188,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(189,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(190,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(191,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(192,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(193,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(194,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(195,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(196,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(197,'제목01','내용01','2024-08-14 01:56:14',NULL,0,0,0,'이용자01'),(198,'제목02','내용02','2024-08-14 01:56:14',NULL,0,0,0,'이용자02'),(199,'제목03','내용03','2024-08-14 01:56:14',NULL,0,0,0,'이용자03'),(200,'제목04','내용04','2024-08-14 01:56:14',NULL,0,0,0,'이용자04'),(201,'제목05','내용05','2024-08-14 01:56:14',NULL,0,0,0,'이용자05'),(254,'제목06','내용6','2024-08-29 08:10:57',NULL,0,0,0,'이용자06'),(255,'제목07','내용07','2024-08-29 08:10:57',NULL,0,0,0,'이용자07'),(256,'제목08','내용08','2024-08-29 08:10:57',NULL,0,0,0,'이용자08'),(257,'제목09','내용09','2024-08-29 08:10:57',NULL,1,0,0,'이용자09'),(258,'제목10','내용10','2024-08-29 08:10:57',NULL,1,0,0,'이용자10');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `pNum` int NOT NULL AUTO_INCREMENT,
  `pname` varchar(255) DEFAULT NULL,
  `pPrice` int NOT NULL,
  `pCount` int NOT NULL,
  `pImg` varchar(255) DEFAULT NULL,
  `pDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `pintro` varchar(255) DEFAULT NULL,
  `pcate` int DEFAULT NULL,
  `pImgUrl` varchar(255) DEFAULT NULL,
  `pDetailImgUrl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pNum`),
  KEY `FKqirxcssbwsmqlue4n0mjlpkcf` (`pcate`),
  CONSTRAINT `FKqirxcssbwsmqlue4n0mjlpkcf` FOREIGN KEY (`pcate`) REFERENCES `category` (`catenum`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'여성 홈트 기구',10000,10,'/images/p1.jpg','2024-07-31 15:00:00','Introduction 1',3,'/images/p1.jpg','/images/p1dt.jpg'),(2,'필라테스 기구',161000,15,'/images/p2.jpg','2024-08-01 15:00:00','Introduction 2',3,'/images/p2.jpg','/images/p2dt.jpg'),(3,'체지방 커팅 영양제',32400,8,'/images/p3.jpg','2024-08-02 15:00:00','Introduction 3',1,'/images/p3.jpg','/images/p3dt.jpg'),(4,'운동 부스팅 아르기닌',49000,20,'/images/p4.jpg','2024-08-03 15:00:00','Introduction 4',2,'/images/p4.jpg','/images/p4dt.jpg'),(5,'프로틴 스파클링',14400,5,'/images/p5.jpg','2024-08-04 15:00:00','Introduction 5',2,'/images/p5.jpg',NULL),(6,'지방분해 효소',25800,12,'/images/p6.jpg','2024-08-05 15:00:00','Introduction 6',2,'/images/p6.jpg',NULL),(7,'수분충전 활력음료',9000,7,'/images/p7.jpg','2024-08-06 15:00:00','Introduction 7',2,'/images/p7.jpg',NULL),(8,'식사대용 단백질 쉐이크',41000,18,'/images/p8.jpg','2024-08-07 15:00:00','Introduction 8',2,'/images/p8.jpg',NULL),(9,'아르기닌 15일분',36000,2,'/images/p9.jpg','2024-08-08 15:00:00','Introduction 9',2,'/images/p9.jpg',NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qna`
--

DROP TABLE IF EXISTS `qna`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qna` (
  `qnum` int NOT NULL AUTO_INCREMENT,
  `qcate` varchar(255) NOT NULL,
  `question` varchar(255) DEFAULT NULL,
  `qanswer` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`qnum`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qna`
--

LOCK TABLES `qna` WRITE;
/*!40000 ALTER TABLE `qna` DISABLE KEYS */;
INSERT INTO `qna` VALUES (1,'주문/결제','주문 내역은 어떻게 확인할 수 있나요?','로그인 후 마이페이지에서 주문 내역을 확인할 수 있습니다.'),(2,'재고확인','특정 상품의 입고날짜는 어떻게 확인할 수 있나요?','트레이너에게 직접 문의하시면 됩니다.'),(3,'취소/환불','주문 취소는 어떻게 하나요?','마이페이지에서 주문을 선택하고 취소 버튼을 클릭하면 됩니다.');
/*!40000 ALTER TABLE `qna` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reply`
--

DROP TABLE IF EXISTS `reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reply` (
  `rpnum` int NOT NULL,
  `rpDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `rpContents` varchar(300) NOT NULL,
  `rpWarning` tinyint(1) NOT NULL DEFAULT '0',
  `id` varchar(50) DEFAULT NULL,
  `ponum` int DEFAULT NULL,
  PRIMARY KEY (`rpnum`),
  KEY `id` (`id`),
  KEY `reply_ibfk_2` (`ponum`),
  CONSTRAINT `reply_ibfk_1` FOREIGN KEY (`id`) REFERENCES `usertbl` (`id`),
  CONSTRAINT `reply_ibfk_2` FOREIGN KEY (`ponum`) REFERENCES `post` (`ponum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reply`
--

LOCK TABLES `reply` WRITE;
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
/*!40000 ALTER TABLE `reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `rvnum` int NOT NULL,
  `star` int DEFAULT NULL,
  `rvDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `rvContents` varchar(1000) DEFAULT NULL,
  `rvWarning` tinyint(1) NOT NULL DEFAULT '0',
  `id` varchar(50) DEFAULT NULL,
  `pnum` int DEFAULT NULL,
  PRIMARY KEY (`rvnum`),
  KEY `id` (`id`),
  KEY `review_ibfk_2` (`pnum`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`id`) REFERENCES `usertbl` (`id`),
  CONSTRAINT `review_ibfk_2` FOREIGN KEY (`pnum`) REFERENCES `product` (`pNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usertbl`
--

DROP TABLE IF EXISTS `usertbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usertbl` (
  `id` varchar(50) NOT NULL,
  `pwd` varchar(50) NOT NULL,
  `name` varchar(10) NOT NULL,
  `phone` varchar(30) NOT NULL,
  `sex` varchar(10) NOT NULL,
  `height` int DEFAULT NULL,
  `weight` int DEFAULT NULL,
  `birth` datetime(6) DEFAULT NULL,
  `firstday` datetime(6) DEFAULT NULL,
  `restday` int DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `sessionkey` varchar(50) DEFAULT NULL,
  `sessionlimit` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertbl`
--

LOCK TABLES `usertbl` WRITE;
/*!40000 ALTER TABLE `usertbl` DISABLE KEYS */;
INSERT INTO `usertbl` VALUES ('이용자01','0001','홍길동','010-0000-0001','남',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('이용자02','0002','김길동','010-0000-0002','남',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('이용자03','0003','박길동','010-0000-0003','남',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('이용자04','0004','홍길순','010-0000-0004','여',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('이용자05','0005','홍길이','010-0000-0005','여',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('이용자06','6666','홍길동','010-0606-6666','여',165,70,'1997-06-06 00:00:00.000000','2024-05-22 00:00:00.000000',NULL,NULL,NULL,NULL),('이용자07','7777','김길동','010-0707-7777','남',185,80,'1999-07-17 00:00:00.000000','2024-07-20 00:00:00.000000',NULL,NULL,NULL,NULL),('이용자08','8888','박길동','010-0808-8888','여',160,50,'1998-08-08 00:00:00.000000','2024-03-12 00:00:00.000000',NULL,NULL,NULL,NULL),('이용자09','9999','황길동','010-0909-9999','남',175,75,'1999-09-09 00:00:00.000000','2024-04-26 00:00:00.000000',NULL,NULL,NULL,NULL),('이용자10','1010','짐길동','010-1010-1010','여',155,40,'2000-10-10 00:00:00.000000','2024-02-22 00:00:00.000000',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `usertbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warningtbl`
--

DROP TABLE IF EXISTS `warningtbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warningtbl` (
  `wnum` int NOT NULL,
  `ponum` int DEFAULT NULL,
  `rpnum` int DEFAULT NULL,
  `rvnum` int DEFAULT NULL,
  PRIMARY KEY (`wnum`),
  KEY `rpnum` (`rpnum`),
  KEY `rvnum` (`rvnum`),
  KEY `warningtbl_ibfk_1` (`ponum`),
  CONSTRAINT `warningtbl_ibfk_1` FOREIGN KEY (`ponum`) REFERENCES `post` (`ponum`),
  CONSTRAINT `warningtbl_ibfk_2` FOREIGN KEY (`rpnum`) REFERENCES `reply` (`rpnum`),
  CONSTRAINT `warningtbl_ibfk_3` FOREIGN KEY (`rvnum`) REFERENCES `review` (`rvnum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warningtbl`
--

LOCK TABLES `warningtbl` WRITE;
/*!40000 ALTER TABLE `warningtbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `warningtbl` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-02 10:06:12
