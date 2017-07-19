DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `categoryId` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `qty` bigint(11) NOT NULL,
  `price_1` FLOAT NOT NULL,
  `price_2` FLOAT NOT NULL,
  `currency` bigint(11) NOT NULL,
  `state` bigint(11) DEFAULT NULL,
  `version` bigint(11) DEFAULT NULL,
  `createdDate` timestamp NOT NULL DEFAULT  '0000-00-00 00:00:00',
  `createdBy` varchar(100) NULL,
  `updatedDate` timestamp NOT NULL DEFAULT  '0000-00-00 00:00:00',
  `updatedBy` varchar(100) NULL,
  CONSTRAINT constraint_code UNIQUE (code),
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `state` bigint(11) DEFAULT NULL,
  `version` bigint(11) DEFAULT NULL,
  `createdDate` timestamp NOT NULL DEFAULT  '0000-00-00 00:00:00',
  `createdBy` varchar(100) NULL,
  `updatedDate` timestamp NOT NULL DEFAULT  '0000-00-00 00:00:00',
  `updatedBy` varchar(100) NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;




