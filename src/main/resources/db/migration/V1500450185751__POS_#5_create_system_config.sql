DROP TABLE IF EXISTS `SystemConfig`;
CREATE TABLE IF NOT EXISTS `SystemConfig` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `companyName` varchar(255) DEFAULT NULL,
  `companyAddress` varchar(255) DEFAULT NULL,
  `companyTel` varchar(255) DEFAULT NULL,
  `companyEmail` varchar(255) DEFAULT NULL,
  `companyLogo` varchar(255) DEFAULT NULL,
  `companyCurrencyRate` decimal(10,2) DEFAULT NULL,
  `companyRule` varchar(255) DEFAULT NULL,
  `companyOtherInfo` varchar(255) DEFAULT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `createdBy` bigint(20) NOT NULL,
  `updatedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedBy` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;