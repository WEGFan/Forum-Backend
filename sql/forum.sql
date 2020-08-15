/*
 Target Server Type    : MySQL 5.7.28
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 2020-08-13 21:11:21
*/

CREATE USER IF NOT EXISTS 'forum'@'localhost' IDENTIFIED BY 'forum.%{^_%u<_l,mQnUbyXYwr0QvU:,FHBJ,6"Xg7ff^19Mc<tcCGS2p!ia@F52Gpw3%mUt,1A_*w~3dOd#A';
GRANT ALTER, CREATE, CREATE TEMPORARY TABLES, DELETE, DROP, INSERT, LOCK TABLES, SELECT, SHOW VIEW, UPDATE ON forum.* TO 'forum'@'localhost';
CREATE DATABASE IF NOT EXISTS forum DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;

USE forum;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attachment
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment`
(
    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '附件编号',
    `topic_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '所属主题编号',
    `board_id` bigint(20) UNSIGNED NOT NULL COMMENT '所属板块编号',
    `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '原始文件名',
    `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件路径',
    `file_size` int(11) UNSIGNED NOT NULL COMMENT '文件大小',
    `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '附件描述 200字符内',
    `download_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '下载地址',
    `download_count` bigint(20) UNSIGNED NOT NULL COMMENT '下载次数',
    `upload_time` datetime(0) NOT NULL COMMENT '上传（创建）时间',
    `uploader_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '上传者用户编号',
    `uploader_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '上传者ip',
    `delete_time` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `attachment_ibfk_topic_id` (`topic_id`) USING BTREE,
    INDEX `attachment_ibfk_uploader_user_id` (`uploader_user_id`) USING BTREE,
    INDEX `attachment_ibfk_board_id` (`board_id`) USING BTREE,
    CONSTRAINT `attachment_ibfk_board_id` FOREIGN KEY (`board_id`) REFERENCES `board` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `attachment_ibfk_topic_id` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `attachment_ibfk_uploader_user_id` FOREIGN KEY (`uploader_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '附件表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of attachment
-- ----------------------------

-- ----------------------------
-- Table structure for board
-- ----------------------------
DROP TABLE IF EXISTS `board`;
CREATE TABLE `board`
(
    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '板块编号',
    `category_id` bigint(20) UNSIGNED NOT NULL COMMENT '所属分区编号',
    `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '板块名称 最大30字符',
    `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '板块描述 最大200字符',
    `visible` tinyint(1) UNSIGNED NOT NULL COMMENT '是否显示',
    `order` tinyint(4) UNSIGNED NOT NULL COMMENT '顺序 小的先显示 1-100',
    `create_time` datetime(0) NOT NULL COMMENT '创建时间',
    `update_time` datetime(0) NOT NULL COMMENT '更新时间',
    `delete_time` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '板块表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of board
-- ----------------------------

-- ----------------------------
-- Table structure for board_admin
-- ----------------------------
DROP TABLE IF EXISTS `board_admin`;
CREATE TABLE `board_admin`
(
    `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户编号',
    `board_id` bigint(20) UNSIGNED NOT NULL COMMENT '板块编号',
    PRIMARY KEY (`user_id`) USING BTREE,
    INDEX `board_admin_ibfk_board_id` (`board_id`) USING BTREE,
    CONSTRAINT `board_admin_ibfk_board_id` FOREIGN KEY (`board_id`) REFERENCES `board` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `board_admin_ibfk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '板块管理表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of board_admin
-- ----------------------------

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`
(
    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分区编号',
    `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分区名称 最大30字符',
    `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分区描述 最大200字符',
    `visible` tinyint(1) UNSIGNED NOT NULL COMMENT '是否显示',
    `order` tinyint(4) UNSIGNED NOT NULL COMMENT '顺序 小的先显示 1-100',
    `create_time` datetime(0) NOT NULL COMMENT '创建时间',
    `update_time` datetime(0) NOT NULL COMMENT '更新时间',
    `delete_time` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '分区表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------

-- ----------------------------
-- Table structure for category_admin
-- ----------------------------
DROP TABLE IF EXISTS `category_admin`;
CREATE TABLE `category_admin`
(
    `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '用户编号',
    `category_id` bigint(20) UNSIGNED NOT NULL COMMENT '分区编号',
    PRIMARY KEY (`user_id`) USING BTREE,
    INDEX `category_admin_ibfk_category_id` (`category_id`) USING BTREE,
    CONSTRAINT `category_admin_ibfk_category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `category_admin_ibfk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '分区管理表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category_admin
-- ----------------------------

-- ----------------------------
-- Table structure for link
-- ----------------------------
DROP TABLE IF EXISTS `link`;
CREATE TABLE `link`
(
    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '友情链接编号',
    `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称 最大30字符',
    `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '网址',
    `icon_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标地址',
    `order` tinyint(4) UNSIGNED NOT NULL COMMENT '顺序 小的先显示 1-100',
    `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述 最大200字符',
    `create_time` datetime(0) NOT NULL COMMENT '创建时间',
    `update_time` datetime(0) NOT NULL COMMENT '更新时间',
    `delete_time` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '友情链接表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of link
-- ----------------------------

-- ----------------------------
-- Table structure for operation_log
-- ----------------------------
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log`
(
    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '操作编号',
    `operator_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '操作者用户编号',
    `operator_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作者ip',
    `item_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作对象类型 attachment/board/category/reply/topic/user',
    `item_id` bigint(20) UNSIGNED NOT NULL COMMENT '操作对象编号',
    `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作类型',
    `detail` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '详细信息',
    `operate_time` datetime(0) NOT NULL COMMENT '操作时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `operation_log_ibfk_operator_user_id` (`operator_user_id`) USING BTREE,
    CONSTRAINT `operation_log_ibfk_operator_user_id` FOREIGN KEY (`operator_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '全站操作日志表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`
(
    `user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '用户编号',
    `board_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '板块编号',
    `ban_visit` tinyint(1) UNSIGNED NOT NULL COMMENT '禁止访问',
    `ban_create_topic` tinyint(1) UNSIGNED NOT NULL COMMENT '禁止发表主题',
    `ban_reply` tinyint(1) UNSIGNED NOT NULL COMMENT '禁止回复',
    `ban_upload_attachment` tinyint(1) UNSIGNED NOT NULL COMMENT '禁止上传附件',
    `ban_download_attachment` tinyint(1) UNSIGNED NOT NULL COMMENT '禁止下载附件',
    INDEX `permission_ibfk_user_id` (`user_id`) USING BTREE,
    INDEX `permission_ibfk_board_id` (`board_id`) USING BTREE,
    CONSTRAINT `permission_ibfk_board_id` FOREIGN KEY (`board_id`) REFERENCES `board` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `permission_ibfk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '权限表（用户论坛权限、用户板块权限、板块内普通用户权限）'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permission
-- ----------------------------

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply`
(
    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '回复编号',
    `topic_id` bigint(20) UNSIGNED NOT NULL COMMENT '所属主题编号',
    `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复内容 150000字符内',
    `short_content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短内容 纯文字',
    `reply_time` datetime(0) NOT NULL COMMENT '回复时间',
    `replier_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '回复者用户编号',
    `replier_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复者ip',
    `edit_time` datetime(0) NULL DEFAULT NULL COMMENT '最后编辑时间',
    `editor_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '最后编辑者用户编号',
    `editor_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后编辑者ip',
    `delete_time` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `reply_ibfk_topic_id` (`topic_id`) USING BTREE,
    INDEX `reply_ibfk_replier_user_id` (`replier_user_id`) USING BTREE,
    INDEX `reply_ibfk_editor_user_id` (`editor_user_id`) USING BTREE,
    FULLTEXT INDEX `reply_idx_short_content` (`short_content`) WITH PARSER `ngram`,
    CONSTRAINT `reply_ibfk_editor_user_id` FOREIGN KEY (`editor_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `reply_ibfk_replier_user_id` FOREIGN KEY (`replier_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `reply_ibfk_topic_id` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '回帖表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of reply
-- ----------------------------

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic`
(
    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主题编号',
    `category_id` bigint(20) UNSIGNED NOT NULL COMMENT '所在分区编号',
    `board_id` bigint(20) UNSIGNED NOT NULL COMMENT '所在板块编号',
    `type` tinyint(4) UNSIGNED NOT NULL COMMENT '主题类型 0-普通主题 1-公告',
    `title` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题 120字符内',
    `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容 150000字符内',
    `short_content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短内容 纯文字',
    `submit_time` datetime(0) NOT NULL COMMENT '发布时间',
    `submitter_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '发布者用户编号',
    `submitter_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发布者ip',
    `view_count` bigint(20) UNSIGNED NOT NULL COMMENT '浏览次数',
    `reply_count` bigint(20) UNSIGNED NOT NULL COMMENT '回复总数',
    `last_reply_time` datetime(0) NOT NULL COMMENT '最后回复时间（没回复就是发帖时间 排序方便）',
    `last_replier_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '最后回复者用户编号',
    `pinned` tinyint(1) UNSIGNED NOT NULL COMMENT '是否置顶',
    `featured` tinyint(1) UNSIGNED NOT NULL COMMENT '是否精华',
    `edit_time` datetime(0) NULL DEFAULT NULL COMMENT '最后编辑时间',
    `editor_user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '最后编辑者用户编号',
    `editor_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后编辑者ip',
    `delete_time` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `topic_ibfk_category_id` (`category_id`) USING BTREE,
    INDEX `topic_ibfk_board_id` (`board_id`) USING BTREE,
    INDEX `topic_ibfk_submitter_user_id` (`submitter_user_id`) USING BTREE,
    INDEX `topic_ibfk_last_replier_user_id` (`last_replier_user_id`) USING BTREE,
    INDEX `topic_ibfk_editor_user_id` (`editor_user_id`) USING BTREE,
    FULLTEXT INDEX `topic_idx_title` (`title`) WITH PARSER `ngram`,
    CONSTRAINT `topic_ibfk_board_id` FOREIGN KEY (`board_id`) REFERENCES `board` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `topic_ibfk_category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `topic_ibfk_editor_user_id` FOREIGN KEY (`editor_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `topic_ibfk_last_replier_user_id` FOREIGN KEY (`last_replier_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `topic_ibfk_submitter_user_id` FOREIGN KEY (`submitter_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '主题表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of topic
-- ----------------------------

-- ----------------------------
-- Table structure for topic_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `topic_operation_log`;
CREATE TABLE `topic_operation_log`
(
    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主题操作编号',
    `topic_id` bigint(20) UNSIGNED NOT NULL COMMENT '主题编号',
    `operator_user_id` bigint(20) UNSIGNED NOT NULL COMMENT '操作者用户编号',
    `operator_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作者ip',
    `reason` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作原因',
    `operate_time` datetime(0) NOT NULL COMMENT '操作时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `topic_operation_log_ibfk_topic_id` (`topic_id`) USING BTREE,
    INDEX `topic_operation_log_ibfk_operator_user_id` (`operator_user_id`) USING BTREE,
    CONSTRAINT `topic_operation_log_ibfk_operator_user_id` FOREIGN KEY (`operator_user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `topic_operation_log_ibfk_topic_id` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '主题操作日志表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of topic_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户编号',
    `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名 最大30字符',
    `password` char(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'bcrypt加密后的密码',
    `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称 最大30字符',
    `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
    `email_verified` tinyint(1) UNSIGNED NOT NULL COMMENT '是否已激活邮箱',
    `sex` tinyint(4) UNSIGNED NOT NULL COMMENT '性别 0-男 1-女 2-保密',
    `signature` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个人签名 最大500字符',
    `super_board_admin` tinyint(1) UNSIGNED NOT NULL COMMENT '是否为超级版主',
    `admin` tinyint(1) UNSIGNED NOT NULL COMMENT '是否为管理员',
    `avatar_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '头像文件地址',
    `topic_count` bigint(20) UNSIGNED NOT NULL COMMENT '主题帖总数',
    `reply_count` bigint(20) UNSIGNED NOT NULL COMMENT '回复总数',
    `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '上次登录时间',
    `last_login_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上次登录ip',
    `register_time` datetime(0) NOT NULL COMMENT '注册时间',
    `update_time` datetime(0) NOT NULL COMMENT '更新时间',
    `delete_time` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '用户表'
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
