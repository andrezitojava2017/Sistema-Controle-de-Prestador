-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: prefeitura
-- ------------------------------------------------------
-- Server version	8.0.12

--
-- Table structure for table `tbl_servicos`
--

DROP TABLE IF EXISTS `tbl_servicos`;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tbl_servicos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `competencia` varchar(10) NOT NULL,
  `empenho` int(11) NOT NULL,
  `fonte` int(8) NOT NULL,
  `pisPasep` varchar(20) NOT NULL,
  `inss_retido` decimal(10,2) DEFAULT NULL,
  `inss_patronal` decimal(10,2) DEFAULT NULL,
  `salario_base` decimal(10,2) DEFAULT NULL,
  `cod_dotacao` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_servicos`
--

-- Dump completed on 2018-11-30 11:03:42
