/*
 Navicat Premium Data Transfer

 Source Server         : Lissandra(Local)
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : db_jax

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 10/05/2019 13:51:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `course_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '课程id，主键自增长',
  `course_name` varchar(255) NOT NULL DEFAULT '' COMMENT '课程名称',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除，0-存在，1-已被删除',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`course_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
BEGIN;
INSERT INTO `course` VALUES (1, '高等数学', 0, '2019-04-27 13:03:11', '2019-04-27 13:03:11');
INSERT INTO `course` VALUES (2, '高等英语', 0, '2019-04-27 13:03:17', '2019-04-27 13:03:17');
INSERT INTO `course` VALUES (3, '高等语文', 0, '2019-04-27 13:03:23', '2019-04-27 13:03:23');
INSERT INTO `course` VALUES (4, '大学物理', 0, '2019-04-27 13:03:33', '2019-04-27 13:03:33');
INSERT INTO `course` VALUES (5, '形式与政策', 0, '2019-04-27 13:04:07', '2019-04-27 13:04:07');
INSERT INTO `course` VALUES (6, '大学生心理健康教育', 0, '2019-04-27 13:04:17', '2019-04-27 13:04:17');
INSERT INTO `course` VALUES (7, 'Java基础课程', 0, '2019-04-27 13:04:40', '2019-04-27 13:04:55');
INSERT INTO `course` VALUES (8, 'C语言基础课程', 0, '2019-04-27 13:04:49', '2019-04-27 13:04:49');
INSERT INTO `course` VALUES (9, 'Delphi基础', 0, '2019-04-27 13:05:45', '2019-04-27 13:05:45');
INSERT INTO `course` VALUES (10, 'Linux详解', 0, '2019-04-27 13:05:53', '2019-04-27 13:05:53');
INSERT INTO `course` VALUES (11, '计算机操作系统', 0, '2019-04-27 13:06:00', '2019-04-27 13:06:00');
INSERT INTO `course` VALUES (12, '大学体育', 0, '2019-04-27 13:07:06', '2019-04-27 13:07:06');
COMMIT;

-- ----------------------------
-- Table structure for mark
-- ----------------------------
DROP TABLE IF EXISTS `mark`;
CREATE TABLE `mark` (
  `mark_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '评分id，主键，自增长',
  `mark_user_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '评分人id',
  `mark_rated_user_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '被评分人id',
  `mark_score` int(255) unsigned NOT NULL DEFAULT '0' COMMENT '评分分数',
  `mark_course_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '课程id',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除，0-存在，1-已被删除',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `mark_type` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '评分类型，1-学生评分，2-教师评分，3-专家评分',
  PRIMARY KEY (`mark_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mark
-- ----------------------------
BEGIN;
INSERT INTO `mark` VALUES (1, 2, 3, 80, 3, 0, '2019-04-27 13:14:15', '2019-05-06 10:18:01', 1);
INSERT INTO `mark` VALUES (2, 4, 3, 70, 3, 0, '2019-04-27 13:15:03', '2019-05-05 22:45:23', 3);
INSERT INTO `mark` VALUES (3, 5, 3, 50, 3, 0, '2019-05-06 10:17:59', '2019-05-06 10:18:03', 2);
INSERT INTO `mark` VALUES (4, 2, 3, 80, 4, 0, '2019-04-27 13:14:15', '2019-05-06 10:18:01', 1);
INSERT INTO `mark` VALUES (6, 2, 3, 40, 2, 0, '2019-05-08 10:50:23', '2019-05-08 10:50:23', 1);
INSERT INTO `mark` VALUES (9, 2, 3, 88, 1, 0, '2019-05-08 15:05:50', '2019-05-08 15:05:50', 1);
INSERT INTO `mark` VALUES (10, 13, 3, 30, 3, 0, '2019-05-10 11:10:19', '2019-05-10 11:10:19', 1);
COMMIT;

-- ----------------------------
-- Table structure for mark_engine
-- ----------------------------
DROP TABLE IF EXISTS `mark_engine`;
CREATE TABLE `mark_engine` (
  `mark_engine_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键自增长',
  `student_weight` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '学生评分权重，百分比',
  `teacher_weight` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '教师评分权重，百分比',
  `expert_weight` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '专家评分权重，百分比',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除，0-存在，1-已被删除',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`mark_engine_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mark_engine
-- ----------------------------
BEGIN;
INSERT INTO `mark_engine` VALUES (1, 30, 30, 40, 0, '2019-04-27 13:13:03', '2019-05-06 14:19:38');
COMMIT;

-- ----------------------------
-- Table structure for teacher_course_ref
-- ----------------------------
DROP TABLE IF EXISTS `teacher_course_ref`;
CREATE TABLE `teacher_course_ref` (
  `teacher_course_ref_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '引用主键，自增长',
  `teacher_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '用户id',
  `course_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '课程id',
  `is_delete` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除，0-存在，1-已被删除',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_marked` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否被评分过，0-没有被评分，1-已经被评分',
  PRIMARY KEY (`teacher_course_ref_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher_course_ref
-- ----------------------------
BEGIN;
INSERT INTO `teacher_course_ref` VALUES (1, 5, 1, 0, '2019-04-27 13:09:32', '2019-05-10 10:29:37', 0);
INSERT INTO `teacher_course_ref` VALUES (2, 5, 2, 0, '2019-04-27 13:09:47', '2019-05-10 10:29:38', 0);
INSERT INTO `teacher_course_ref` VALUES (3, 5, 4, 0, '2019-04-27 13:09:56', '2019-05-10 10:29:39', 0);
INSERT INTO `teacher_course_ref` VALUES (4, 3, 1, 0, '2019-04-27 13:10:00', '2019-05-07 11:30:31', 0);
INSERT INTO `teacher_course_ref` VALUES (5, 3, 3, 0, '2019-04-27 13:10:00', '2019-05-07 11:30:32', 0);
INSERT INTO `teacher_course_ref` VALUES (6, 3, 4, 0, '2019-04-27 13:10:00', '2019-05-07 11:30:33', 0);
INSERT INTO `teacher_course_ref` VALUES (7, 14, 1, 0, '2019-05-10 11:46:11', '2019-05-10 11:46:11', 0);
INSERT INTO `teacher_course_ref` VALUES (8, 14, 4, 0, '2019-05-10 11:46:11', '2019-05-10 11:46:11', 0);
INSERT INTO `teacher_course_ref` VALUES (9, 14, 7, 0, '2019-05-10 11:46:11', '2019-05-10 11:46:11', 0);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名称',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '登录密码',
  `permission` tinyint(4) unsigned NOT NULL DEFAULT '1' COMMENT '角色权限，0-超级管理员，1-学生，2-教师，3-专家',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除，0-存在，1-已被删除',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'admin', '111', 0, 0, '2019-01-29 13:59:09', '2019-01-30 17:20:47');
INSERT INTO `user` VALUES (2, '石傻傻', '111', 1, 0, '2019-02-03 21:26:35', '2019-04-27 13:08:16');
INSERT INTO `user` VALUES (3, '王老师', '111', 2, 0, '2019-04-27 13:08:36', '2019-04-27 13:08:36');
INSERT INTO `user` VALUES (4, '专家老王', '111', 3, 0, '2019-04-27 13:08:55', '2019-04-27 13:08:55');
INSERT INTO `user` VALUES (5, '李老师', '111', 2, 0, '2019-05-06 10:17:27', '2019-05-06 10:17:27');
INSERT INTO `user` VALUES (6, '3333', '111', 1, 0, '2019-05-10 10:27:08', '2019-05-10 10:27:08');
INSERT INTO `user` VALUES (7, '3333', '111', 1, 0, '2019-05-10 10:31:01', '2019-05-10 10:31:01');
INSERT INTO `user` VALUES (8, '44444', '111', 1, 0, '2019-05-10 10:34:14', '2019-05-10 10:34:14');
INSERT INTO `user` VALUES (9, '22222', '111', 1, 0, '2019-05-10 10:51:05', '2019-05-10 10:51:05');
INSERT INTO `user` VALUES (10, '3333', '111', 1, 0, '2019-05-10 10:52:40', '2019-05-10 10:52:40');
INSERT INTO `user` VALUES (11, '555555', '555', 1, 0, '2019-05-10 10:58:55', '2019-05-10 10:58:55');
INSERT INTO `user` VALUES (13, 'eee', '111', 1, 0, '2019-05-10 11:06:36', '2019-05-10 11:06:36');
INSERT INTO `user` VALUES (14, '陈海老师', '111444', 2, 0, '2019-05-10 11:45:45', '2019-05-10 13:25:25');
INSERT INTO `user` VALUES (15, '郑涛', '111222', 1, 0, '2019-05-10 11:46:41', '2019-05-10 13:24:56');
COMMIT;

-- ----------------------------
-- Table structure for user_course_ref
-- ----------------------------
DROP TABLE IF EXISTS `user_course_ref`;
CREATE TABLE `user_course_ref` (
  `user_course_ref_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '引用主键，自增长',
  `user_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '用户id',
  `course_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '课程id',
  `course_teacher_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '课程老师id',
  `is_delete` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除，0-存在，1-已被删除',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_marked` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否被评分过，0-没有被评分，1-已经被评分',
  PRIMARY KEY (`user_course_ref_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_course_ref
-- ----------------------------
BEGIN;
INSERT INTO `user_course_ref` VALUES (1, 2, 1, 3, 0, '2019-04-27 13:09:32', '2019-05-08 14:07:00', 1);
INSERT INTO `user_course_ref` VALUES (2, 2, 2, 3, 0, '2019-04-27 13:09:47', '2019-05-07 11:30:30', 1);
INSERT INTO `user_course_ref` VALUES (3, 2, 4, 3, 0, '2019-04-27 13:09:56', '2019-05-07 15:23:30', 1);
INSERT INTO `user_course_ref` VALUES (4, 4, 1, 3, 0, '2019-04-27 13:10:00', '2019-05-09 09:21:56', 0);
INSERT INTO `user_course_ref` VALUES (5, 5, 3, 3, 0, '2019-04-27 13:10:00', '2019-05-09 09:21:58', 0);
INSERT INTO `user_course_ref` VALUES (6, 5, 4, 3, 0, '2019-04-27 13:10:00', '2019-05-09 09:21:59', 0);
INSERT INTO `user_course_ref` VALUES (9, 13, 3, 3, 0, '2019-05-10 11:07:05', '2019-05-10 11:07:05', 1);
INSERT INTO `user_course_ref` VALUES (10, 13, 2, 5, 0, '2019-05-10 11:07:05', '2019-05-10 11:07:05', 0);
INSERT INTO `user_course_ref` VALUES (11, 13, 1, 5, 0, '2019-05-10 11:07:05', '2019-05-10 11:07:05', 0);
INSERT INTO `user_course_ref` VALUES (12, 15, 7, 14, 0, '2019-05-10 11:46:48', '2019-05-10 11:46:48', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
