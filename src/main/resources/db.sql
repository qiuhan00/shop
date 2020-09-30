-- 创建并使用database
create database if not exists shop;
use shop;
-- 建表
drop table if exists tbl_cart;
CREATE TABLE `tbl_cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_code` int DEFAULT NULL,
  `product_attrs` varchar(50) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists tbl_catalog;
CREATE TABLE `tbl_catalog` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  `code` varchar(10) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `order1` int DEFAULT NULL,
  `show_in_nav` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `parent_code` varchar(10) DEFAULT NULL,
  `node_level` int DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists tbl_city;
CREATE TABLE `tbl_city` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `city_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `ad_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `province_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists tbl_county;
CREATE TABLE `tbl_county` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `ad_code` varchar(10) DEFAULT NULL,
  `city_code` varchar(10) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists tbl_message;
CREATE TABLE `tbl_message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `message` varchar(500) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `user_code` varchar(10) DEFAULT NULL,
  `user_name` varchar(10) DEFAULT NULL,
  `reply_message` varchar(500) DEFAULT NULL,
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists tbl_order;
CREATE TABLE `tbl_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_no` varchar(20) DEFAULT NULL,
  `user_code` varchar(20) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `refund_status` char(1) DEFAULT NULL,
  `pay_status` char(1) DEFAULT NULL,
  `low_stocks` char(1) DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `fee` decimal(10,2) DEFAULT NULL,
  `ptotal` decimal(10,2) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `express_code` varchar(10) DEFAULT NULL,
  `express_name` varchar(20) DEFAULT NULL,
  `express_no` varchar(20) DEFAULT NULL,
  `express_company_name` varchar(20) DEFAULT NULL,
  `confirm_send_product_remark` varchar(20) DEFAULT NULL,
  `other_requirement` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `closed_comment` varchar(50) DEFAULT NULL,
  `score` int DEFAULT NULL,
  `address_id` int DEFAULT NULL,
  `pay_channel_id` int DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists tbl_order_detail;
CREATE TABLE `tbl_order_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_no` varchar(20) DEFAULT NULL,
  `product_code` int DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `fee` decimal(10,2) DEFAULT NULL,
  `product_name` varchar(20) DEFAULT NULL,
  `total0` decimal(10,2) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists tbl_pay_channel;
CREATE TABLE `tbl_pay_channel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `pay_name` varchar(20) DEFAULT NULL,
  `pay_type` char(1) DEFAULT NULL,
  `image_url` varchar(50) DEFAULT NULL,
  `status` char(1) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists tbl_product;
CREATE TABLE `tbl_product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `product_code` bigint NOT NULL,
  `introduce` varchar(100) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `price_unit` varchar(10) DEFAULT NULL,
  `now_price` decimal(10,2) DEFAULT NULL,
  `product_html` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  `picture` varchar(50) DEFAULT NULL,
  `max_picture` varchar(50) DEFAULT NULL,
  `images` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `unit` varchar(10) DEFAULT NULL,
  `sellcount` int DEFAULT '0',
  `stock` int DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  `is_time_promotion` char(1) DEFAULT 'n',
  `is_hot_search` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'n',
  `is_recommend` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'n',
  `status` int DEFAULT NULL,
  `show_in_nav` char(1) DEFAULT 'n',
  `catalog_id` int DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists tbl_province;
CREATE TABLE `tbl_province` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `ad_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists tbl_town;
CREATE TABLE `tbl_town` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `ad_code` varchar(10) DEFAULT NULL,
  `county_code` varchar(10) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists tbl_user;
CREATE TABLE `tbl_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `true_name` varchar(10) DEFAULT NULL COMMENT '真实名',
  `phone` varchar(15) DEFAULT NULL,
  `card_no` varchar(20) DEFAULT NULL COMMENT '证件号',
  `email` varchar(20) DEFAULT NULL,
  `grade` int DEFAULT NULL COMMENT '等级',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists tbl_user_address;
CREATE TABLE `tbl_user_address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `province_code` varchar(10) DEFAULT NULL,
  `province_name` varchar(20) DEFAULT NULL,
  `city_code` varchar(10) DEFAULT NULL,
  `city_name` varchar(100) DEFAULT NULL,
  `county_code` varchar(10) DEFAULT NULL,
  `county_name` varchar(100) DEFAULT NULL,
  `town_code` varchar(10) DEFAULT NULL,
  `town_name` varchar(100) DEFAULT NULL,
  `address_detail` varchar(100) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `type` char(1) DEFAULT NULL,
  `consignee` varchar(10) DEFAULT NULL,
  `consignee_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `post_code` varchar(10) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_usercode_type` (`type`,`consignee_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists tbl_user_login;
CREATE TABLE `tbl_user_login` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_code` varchar(20) DEFAULT NULL,
  `login_ts` datetime DEFAULT NULL,
  `login_bit` bit(64) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





