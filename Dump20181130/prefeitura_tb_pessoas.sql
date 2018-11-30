-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: prefeitura
-- ------------------------------------------------------
-- Server version	8.0.12

--
-- Table structure for table `tb_pessoas`
--

DROP TABLE IF EXISTS `tb_pessoas`;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_pessoas` (
  `nome` varchar(200) NOT NULL,
  `pis_pasep` varchar(100) NOT NULL,
  PRIMARY KEY (`pis_pasep`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dump completed on 2018-11-30 11:03:42
