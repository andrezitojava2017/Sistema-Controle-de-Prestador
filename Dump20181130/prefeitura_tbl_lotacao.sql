-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: prefeitura
-- ------------------------------------------------------
-- Server version	8.0.12

--
-- Table structure for table `tbl_lotacao`
--

DROP TABLE IF EXISTS `tbl_lotacao`;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_lotacao` (
  `codigo_dotacao` int(11) NOT NULL,
  `descricao` varchar(200) NOT NULL,
  PRIMARY KEY (`codigo_dotacao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_lotacao`
--

-- Dump completed on 2018-11-30 11:03:41
