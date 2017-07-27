
DROP TABLE IF EXISTS `Session`;
CREATE TABLE `Session` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accessToken` varchar(500) DEFAULT NULL,
  `apiInvocationCount` bigint(20) DEFAULT NULL,
  `UserAccountId` bigint(20) DEFAULT NULL,
  `expirePolicy` int(11) DEFAULT NULL,
  `expiresTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `createdDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `lastAccessTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `roles` varchar(500) DEFAULT NULL,
  `csrfToken` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `INDEX_expiresTime` (`expiresTime`),
  KEY `INDEX_accessToken` (`accessToken`(255)),
  KEY `INDEX_UserAccountId` (`UserAccountId`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;

--
-- Table structure for table `UserAccount`
--

DROP TABLE IF EXISTS `UserAccount`;
CREATE TABLE `UserAccount` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `clientId` varchar(255) NOT NULL,
  `clientSecret` varchar(256) DEFAULT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  `passwordResetExpiresTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `passwordResetToken` varchar(256) DEFAULT NULL,
  `roles` varchar(256) NOT NULL,
  `socialProvider` varchar(256) DEFAULT NULL,
  `socialProviderUserId` varchar(256) DEFAULT NULL,
  `expirePolicy` int(11) DEFAULT NULL,
  `expireInSeconds` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `activateAccountToken` varchar(256) DEFAULT NULL,
  `createdBy` varchar(256) DEFAULT NULL,
  `createdDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updatedBy` varchar(256) DEFAULT NULL,
  `updatedDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `numberLoginFailure` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `clientId` (`clientId`)
) ENGINE=InnoDB AUTO_INCREMENT=500 DEFAULT CHARSET=utf8;