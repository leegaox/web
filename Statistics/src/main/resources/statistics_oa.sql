/*
 Navicat Premium Data Transfer

 Source Server         : 10.16.33.51
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 10.16.33.51:3306
 Source Schema         : statistics_oa

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 10/03/2020 14:32:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_employee
-- ----------------------------
DROP TABLE IF EXISTS `t_employee`;
CREATE TABLE `t_employee`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '姓名',
  `job_id` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '工号',
  `department` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_employee
-- ----------------------------
INSERT INTO `t_employee` VALUES (1, '高翔', '1270', '机车公共技术平台');
INSERT INTO `t_employee` VALUES (2, 'Lee', '1111', '人事部');
INSERT INTO `t_employee` VALUES (6, 'TEST人物', '1280', '行政');
INSERT INTO `t_employee` VALUES (8, '李明', '1214', '行政部');
INSERT INTO `t_employee` VALUES (9, '李明', '1215', '行政部');

-- ----------------------------
-- Table structure for t_record
-- ----------------------------
DROP TABLE IF EXISTS `t_record`;
CREATE TABLE `t_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '其他、备注',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `employee_id` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT ' 员工id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_record
-- ----------------------------
INSERT INTO `t_record` VALUES (1, 'Android Studio,Intellij IDEA', '2020-03-09 14:22:08', '1');
INSERT INTO `t_record` VALUES (11, 'test remark', '2020-03-10 09:39:36', '6');
INSERT INTO `t_record` VALUES (13, 'VS', '2020-03-10 10:23:44', '8');
INSERT INTO `t_record` VALUES (14, 'VS', '2020-03-10 10:48:10', '9');

-- ----------------------------
-- Table structure for t_record_common_soft
-- ----------------------------
DROP TABLE IF EXISTS `t_record_common_soft`;
CREATE TABLE `t_record_common_soft`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `record_id` int(11) DEFAULT NULL COMMENT '填表记录id',
  `software_id` int(11) DEFAULT NULL COMMENT '常用软件id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_record_common_soft
-- ----------------------------
INSERT INTO `t_record_common_soft` VALUES (1, 1, 1);
INSERT INTO `t_record_common_soft` VALUES (2, 1, 2);
INSERT INTO `t_record_common_soft` VALUES (3, 1, 3);
INSERT INTO `t_record_common_soft` VALUES (4, 2, 2);
INSERT INTO `t_record_common_soft` VALUES (17, 11, 4);
INSERT INTO `t_record_common_soft` VALUES (18, 11, 5);
INSERT INTO `t_record_common_soft` VALUES (19, 13, 1);
INSERT INTO `t_record_common_soft` VALUES (20, 13, 5);
INSERT INTO `t_record_common_soft` VALUES (21, 14, 1);
INSERT INTO `t_record_common_soft` VALUES (22, 14, 5);

-- ----------------------------
-- Table structure for t_software
-- ----------------------------
DROP TABLE IF EXISTS `t_software`;
CREATE TABLE `t_software`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `software_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT ' 软件名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_software
-- ----------------------------
INSERT INTO `t_software` VALUES (1, 'Office');
INSERT INTO `t_software` VALUES (2, 'Photoshop');
INSERT INTO `t_software` VALUES (3, '美图秀秀');
INSERT INTO `t_software` VALUES (4, 'TeamViewer');
INSERT INTO `t_software` VALUES (5, 'Xmind');

SET FOREIGN_KEY_CHECKS = 1;
