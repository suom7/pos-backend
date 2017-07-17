DROP TABLE IF EXISTS `Invoice`;
CREATE TABLE IF NOT EXISTS `Invoice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `invoiceNo` varchar(255) DEFAULT NULL,
  `InvoiceDate` timestamp NOT NULL DEFAULT  '0000-00-00 00:00:00',
  `subtotal` decimal(10,2) DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  `state` int(11) DEFAULT '1',
  `createdDate` timestamp NOT NULL DEFAULT  '0000-00-00 00:00:00',
  `createdByUserId` bigint(20) NOT NULL,
  `updatedDate` timestamp NOT NULL DEFAULT  '0000-00-00 00:00:00',
  `updatedByUserId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `InvoiceDetail`;
CREATE TABLE IF NOT EXISTS `InvoiceDetail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `invId` int(11) DEFAULT NULL,
  `pId` int(11) DEFAULT NULL,
  `qty` decimal(10,2) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  `state` int(11) DEFAULT '1',
  `createdDate` timestamp NOT NULL DEFAULT  '0000-00-00 00:00:00',
  `createdByUserId` bigint(20) NOT NULL,
  `updatedDate` timestamp NOT NULL DEFAULT  '0000-00-00 00:00:00',
  `updatedByUserId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `Product`;
CREATE TABLE IF NOT EXISTS `Product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `categoryId` int(11) DEFAULT NULL,
  `categoryName` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT '1',
  `note` varchar(255) DEFAULT NULL,
  `createdDate` timestamp NOT NULL DEFAULT  '0000-00-00 00:00:00',
  `createdByUserId` bigint(20) NOT NULL,
  `updatedDate` timestamp NOT NULL DEFAULT  '0000-00-00 00:00:00',
  `updatedByUserId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `Category`;
CREATE TABLE IF NOT EXISTS `Category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `state` bigint(11) DEFAULT '1',
  `createdDate` timestamp NOT NULL DEFAULT  '0000-00-00 00:00:00',
  `createdByUserId` bigint(20) NOT NULL,
  `updatedDate` timestamp NOT NULL DEFAULT  '0000-00-00 00:00:00',
  `updatedByUserId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `Price`;
CREATE TABLE IF NOT EXISTS `Price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `productId` int(11) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT '1',
  `createdDate` timestamp NOT NULL DEFAULT  '0000-00-00 00:00:00',
  `createdByUserId` bigint(20) NOT NULL,
  `updatedDate` timestamp NOT NULL DEFAULT  '0000-00-00 00:00:00',
  `updatedByUserId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


