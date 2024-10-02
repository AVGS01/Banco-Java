DROP DATABASE IF EXISTS `locadoraveiculos`;
CREATE DATABASE IF NOT EXISTS `locadoraveiculos`;
USE `locadoraveiculos`;

CREATE TABLE `locatarios` (
  `idlocatarios` int unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `data_nasc` date NOT NULL,
  `endereço` varchar(45) NOT NULL,
  `telefone` varchar(11) NOT NULL,
  `cpf` varchar(11) NOT NULL,
  `cnh` varchar(25) NOT NULL,
  PRIMARY KEY (`idlocatarios`)
);

CREATE TABLE `veiculos` (
  `idveiculo` int unsigned NOT NULL AUTO_INCREMENT, -- Correção aqui
  `placa` varchar(7) NOT NULL,
  `marca` varchar(35) NOT NULL,
  `modelo` varchar(35) NOT NULL,
  `ano` int(4) NOT NULL,
  `disponibilidade` varchar(15) NOT NULL,
  `valor_diaria` float NOT NULL,
  PRIMARY KEY (`idveiculo`) -- Correção aqui
);

CREATE TABLE `locacoes` (
  `idlocacoes` int unsigned NOT NULL AUTO_INCREMENT,
  `idlocatario` int unsigned NOT NULL,
  `idveiculo` int unsigned NOT NULL,
  `data_inicio` date NOT NULL,
  `data_termino` date NOT NULL,
  `status_locacao` varchar(35) NOT NULL,
  PRIMARY KEY (`idlocacoes`),
  FOREIGN KEY (`idlocatario`) REFERENCES `locatarios`(`idlocatarios`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`idveiculo`) REFERENCES `veiculos`(`idveiculo`) ON DELETE CASCADE ON UPDATE CASCADE -- Correção aqui
);

CREATE TABLE `devolucoes` (
  `iddevolucoes` int unsigned NOT NULL AUTO_INCREMENT,
  `idlocacoes` int unsigned NOT NULL,
  `data_devolucao` date NOT NULL,
  `quilometragem` float NOT NULL,
  `danos` varchar(250) NOT NULL,
  `multa_atraso` float NOT NULL,
  PRIMARY KEY (`iddevolucoes`),
  FOREIGN KEY (`idlocacoes`) REFERENCES `locacoes`(`idlocacoes`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `pagamentos` (
  `idpagamentos` int unsigned NOT NULL AUTO_INCREMENT,
  `idlocacoes` int unsigned NOT NULL,
  `valor_total` float NOT NULL,
  `data_pagamento` date NOT NULL,
  `metodo_pagamento` int NOT NULL,
  `status_pagamento` varchar(15) NOT NULL,
  PRIMARY KEY (`idpagamentos`),
  FOREIGN KEY (`idlocacoes`) REFERENCES `locacoes`(`idlocacoes`) ON DELETE CASCADE ON UPDATE CASCADE
);
.
