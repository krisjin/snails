-- MySQL dump 10.13  Distrib 5.5.20, for Win64 (x86)
--
-- Host: localhost    Database: dmcp
-- ------------------------------------------------------
-- Server version	5.5.20


--
-- Table structure for table `funcgroup_func`
--

DROP TABLE IF EXISTS `funcgroup_func`;

CREATE TABLE `funcgroup_func` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `functionId` int(11) NOT NULL,
  `functionGroupId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `function`
--

DROP TABLE IF EXISTS `function`;

CREATE TABLE `function` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `funcName` varchar(100) NOT NULL,
  `funcDesc` varchar(500) DEFAULT NULL,
  `actionUrl` varchar(200) NOT NULL,
  `param` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `functiongroup`
--

DROP TABLE IF EXISTS `functiongroup`;

CREATE TABLE `functiongroup` (
  `id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(100) DEFAULT NULL,
  `actionUrl` varchar(100) DEFAULT NULL,
  `functions` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `keyword`
--

DROP TABLE IF EXISTS `keyword`;

CREATE TABLE `keyword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `content` text,
  `status` int(11) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;

CREATE TABLE `news` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `newsTitle` varchar(200) DEFAULT NULL,
  `newsMedia` varchar(300) DEFAULT NULL,
  `newsUrl` varchar(200) DEFAULT NULL,
  `newsContent` longtext,
  `newsPosttime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=gbk;


--
-- Table structure for table `news_content`
--

DROP TABLE IF EXISTS `news_content`;

CREATE TABLE `news_content` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `newsId` bigint(11) NOT NULL,
  `content` mediumtext,
  PRIMARY KEY (`id`),
  KEY `newsId_content` (`newsId`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=4309936 DEFAULT CHARSET=gbk;


--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `roleName` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `roleDesc` varchar(512) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `role_func`
--

DROP TABLE IF EXISTS `role_func`;

CREATE TABLE `role_func` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) DEFAULT NULL,
  `funcId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `tech_article`
--

DROP TABLE IF EXISTS `tech_article`;

CREATE TABLE `tech_article` (
  `article_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `article_title` varchar(500) NOT NULL,
  `article_post_date` datetime DEFAULT NULL,
  `article_url` varchar(500) DEFAULT NULL,
  `article_tag` varchar(500) DEFAULT NULL,
  `article_content` mediumtext,
  `article_site` int(11) DEFAULT NULL,
  `article_category` int(11) DEFAULT NULL,
  `aritcle_read_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`article_id`),
  KEY `artilce_ids` (`article_id`) USING BTREE,
  KEY `artilce_titles` (`article_title`(255)) USING BTREE,
  KEY `article_postdate` (`article_post_date`) USING BTREE,
  KEY `article_site` (`article_site`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=4247380 DEFAULT CHARSET=utf8;


--
-- Table structure for table `tech_news`
--

DROP TABLE IF EXISTS `tech_news`;

CREATE TABLE `tech_news` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tag` varchar(1024) DEFAULT NULL,
  `news_url` varchar(1024) DEFAULT NULL,
  `news_title` varchar(1024) DEFAULT NULL,
  `news_author` varchar(1024) DEFAULT NULL,
  `news_content` text,
  `news_post_date` datetime DEFAULT NULL,
  `news_insert_date` datetime DEFAULT NULL,
  `news_comment_id` bigint(20) DEFAULT NULL,
  `news_media` varchar(1024) DEFAULT NULL,
  `news_media_code` varchar(255) DEFAULT NULL,
  `source_news_media` varchar(255) DEFAULT NULL,
  `source_news_media_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `news_postdate` (`news_post_date`) USING BTREE,
  KEY `news_title` (`news_title`(255)) USING BTREE,
  KEY `media` (`news_media`(255)) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=125 DEFAULT CHARSET=utf8;


--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userId` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `email` varchar(45) DEFAULT '' COMMENT '邮件地址',
  `name` varchar(50) DEFAULT '' COMMENT '用户名称',
  `password` varchar(32) DEFAULT '' COMMENT '用户密码',
  `status` int(2) DEFAULT NULL COMMENT '状态：0 隐藏 1 显示',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `idx_email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'dmcp@126.com', 'dmcp', '21232f297a57a5a743894a0e4a801fc3', '1', '2015-01-15 21:07:14');
--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `useId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `company`
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `companyName` varchar(40) DEFAULT NULL,
  `industry` varchar(100) DEFAULT NULL,
  `scale` varchar(50) DEFAULT NULL,
  `site` varchar(100) DEFAULT NULL,
  `financing` varchar(50) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `logo` varchar(100) DEFAULT NULL,
  `map` varchar(100) DEFAULT NULL,
  `manager` varchar(50) DEFAULT NULL,
  `managerAvatar` varchar(100) DEFAULT NULL,
  `introduction` text,
  `location` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-01-20  0:24:12
