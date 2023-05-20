/*
 Navicat Premium Data Transfer

 Source Server         : pei
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : bms

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 06/04/2023 16:11:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `aid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `mail` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`aid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', 'admin', '超级管理员', '18888888888', '666666@666.com');
INSERT INTO `admin` VALUES (3, 'test', '123456', '测试', '13415646135', '416584156@qq.com');
INSERT INTO `admin` VALUES (10, 'Lu', '123456', '毛璐', '76937623983', 'mlu@gmail.com');
INSERT INTO `admin` VALUES (11, 'Yunxi', '123456', '何云熙', '15018663208', 'yunxhe915@icloud.com');
INSERT INTO `admin` VALUES (12, 'Lu', '123456', '杨璐', '17210487347', 'yang79@mail.com');
INSERT INTO `admin` VALUES (13, 'Zitao', '123456', '邓子韬', '15080829783', 'zitaoden@hotmail.com');
INSERT INTO `admin` VALUES (14, 'Anqi', '123456', '钟安琪', '16391058398', 'anqi314@yahoo.com');
INSERT INTO `admin` VALUES (15, 'Zitao', '123456', '何子韬', '218731521', 'hzi1109@gmail.com');
INSERT INTO `admin` VALUES (16, 'Xiaoming', '123456', '邹晓明', '2199587088', 'zoux408@icloud.com');
INSERT INTO `admin` VALUES (17, 'Anqi', '123456', '傅安琪', '16891680931', 'fu41@gmail.com');
INSERT INTO `admin` VALUES (18, 'Lan', '123456', '邱岚', '16361675626', 'qiulan4@mail.com');
INSERT INTO `admin` VALUES (19, 'Jialun', '123456', '董嘉伦', '75519460310', 'jialun1117@yahoo.com');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `mail` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `idCard` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `money` double(20, 2) NOT NULL DEFAULT 0.00,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `user_pk`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'zhangsan', '123456', '张三', '17216835574', 'zhangsan@163.com', '455215198806211216', 2150.00);
INSERT INTO `user` VALUES (2, 'lisi', '123456', '李四', '16833544883', 'lisi@163.com', '488953199007133619', 750.00);
INSERT INTO `user` VALUES (3, 'wangwu', '123456', '王五', '15487625485', '15487625485@163.com', '528766199311213519', 0.00);

SET FOREIGN_KEY_CHECKS = 1;
