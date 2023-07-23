CREATE SCHEMA order_service;
USE `order_service`;

DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `order_number` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_order_line_item`;
CREATE TABLE `t_order_line_item` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `sku_code` varchar(45) NOT NULL,
  `price` DECIMAL NOT NULL,
  `quantity` INT NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ORDER_idx` (`order_id`),
  CONSTRAINT `FK_ORDER` FOREIGN KEY (`order_id`) REFERENCES `t_order` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;