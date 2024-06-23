/*
 Navicat MySQL Data Transfer

 Source Server         : yu
 Source Server Type    : MySQL
 Source Server Version : 80034
 Source Host           : 114.132.67.226:3306
 Source Schema         : orientation

 Target Server Type    : MySQL
 Target Server Version : 80034
 File Encoding         : 65001

 Date: 03/04/2024 01:41:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user`  (
  `user_id` varbinary(255) NOT NULL COMMENT '管理员id',
  `teacher_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '教师学工号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `school` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学校',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '2为超级管理员，1为管理员，0为不可登录',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `preview_images` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'minio中头像的预览地址',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '后台管理员用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES (0x30383265666562612D333263352D346335382D623061342D323863373231323136653565, '12345678', '$2a$10$yTnv3pCGQEkUkxqcEri0QOuUWcioyeFGOFvY52p.5zssi4zLf/TDO', 'test', '广州商学院', '13612746972', 0, '男', '0375f1a8-b231-47a6-8d26-21a666e184d3.png', NULL);
INSERT INTO `admin_user` VALUES (0x30393534303562632D356431642D346262632D616239332D326566666364313830333438, '12345678', '$2a$10$vUJx7PZ18T2ARs9kihgdY.Q1w4IhHLc2avdGL0S5uLBb2IGSmQ.D.', 'zzz', 'sz', '18126507985', 0, '女', NULL, NULL);
INSERT INTO `admin_user` VALUES (0x30626438333930382D373236372D343762652D623634612D643132623536396637393165, '98745612', '$2a$10$yd/QihQxLrPMDhWVWqZsQOqTZ5xxpAjfXNyxWurRH/TCAr7t.hXAa', 'test2', '广州商学院', '13612746972', 0, '男', NULL, NULL);
INSERT INTO `admin_user` VALUES (0x31663631386462322D383331302D343730612D613138342D653134613037633130373063, '12345678', '$2a$10$aErLqeRXgLCmIDxR.y/WNu8k79PQjuG7cASPeJpp3effs9ui.cIMK', '广商', '广商', '18126507966', 1, '男', NULL, NULL);
INSERT INTO `admin_user` VALUES (0x32336330353165332D386435392D343331302D616261312D653963306133316537306336, '12345678', '$2a$10$8rxCwy3CVhT/KjNiKF9A1.lzsoSM45NYSnfGrBh8KbQXZ72/5d.ia', 'zzz', 'ui', '18126507985', 0, '男', NULL, NULL);
INSERT INTO `admin_user` VALUES (0x32363562396134312D303238622D343666372D386337362D363334666664396362656261, '12345678', '$2a$10$C9aVWDWXQVePhumuBpegee94dG39ShkXTI4KaygTfDpkwpNAEyd2a', '111', '111', '18126507985', 0, '女', NULL, NULL);
INSERT INTO `admin_user` VALUES (0x36356639346465352D323064632D343536362D386331382D663134323639623263366263, 'admin000', '$2a$10$eBKuBzTHHOduLt4BvDbC0e7rIVB.syydeZw2V46Hgduf4xhqHYKDi', '超级管理员', '广州商学院', '13612746972', 2, '女', '8a90bd91-6f48-4983-a90c-dfdcca8a55f5.png', NULL);
INSERT INTO `admin_user` VALUES (0x36663266643133612D343965312D346136652D613234302D366563313630336534656531, '12345678', '$2a$10$kwwhUKl3T.ystoCmX2ZhpeRr9UBhM7stmKZ5CxFTAJW4Jya6/fYWG', '123456', '123456', '16523625986', 1, '男', NULL, NULL);
INSERT INTO `admin_user` VALUES (0x61613036373736382D346635332D343830642D383365382D366334653137373263333165, '000001', '$2a$10$uK0MOV5LW3bfd3okSQmgDOhZ..WZ5SUV2XjTgOVLHJ13X7hXlEPj6', '测试添加以存在头像功能', '广商', '19822153436', 0, '女', 'b2fc57d3-678d-424e-b75c-620bdb04a7b1.png', NULL);
INSERT INTO `admin_user` VALUES (0x61613036373736382D346635332D343830642D383365382D366334653137373263333166, '000000', '$2a$10$30/FB9CwtO8kCT6amhat.uA22bi38p6o2q9vpZsed7Gkf29UPxk06', '测试添加功能', '广商', '19822153436', 1, '女', '3e2080f7-3781-4ad0-b498-f6ec048b5fea.png', NULL);
INSERT INTO `admin_user` VALUES (0x62373936346435632D663432652D343063362D623238612D376330613837366566333766, '87654321', '$2a$10$NDPO32.LBr4827q5PxmSMu4YAtS.aLeuVpdbkn.IFJ1e3f3dZz3dy', '测试1', '广州商学院', '13612746972', 1, '男', '29d93ca2-f224-4401-aef7-10b1452a1286.png', NULL);
INSERT INTO `admin_user` VALUES (0x62386565333864372D343537392D346633612D396638392D643763396664336331386133, '202206120101', '$2a$10$Q3.nYKBLzIyT12RE13QLBOcfk13vOt/wNUNNr7XrHvQXrucD4lSNi', 'test', '广商', '18172378542', 1, '男', 'c1b8f741-42d8-4438-9f46-855a976ebde6.png', NULL);
INSERT INTO `admin_user` VALUES (0x64383661643535612D366632302D343537662D393235382D636137386433633634363665, '202206120100', '$2a$10$EBE8YEUEjIW10AQJTzJiuOg6XpxfBoFRcEk.h8PsZ4GA7wdPZ5GMW', '小李', NULL, NULL, 0, NULL, NULL, NULL);
INSERT INTO `admin_user` VALUES (0x64646465343232662D616666392D343465342D623433652D373338346265303037383733, '12345678', '$2a$10$zAM6Wq03RGNEe3sHnTq8jukmA1O2V7SInA7gQn.Stk8uqRcI5rYOu', '6666', '广商', '18126507985', 0, '女', 'febf9446-6580-4e04-a64c-5bbe8681cf0d.png', NULL);
INSERT INTO `admin_user` VALUES (0x64656232396365352D646661632D346538302D626334372D303233343662646130303963, '12345678', '$2a$10$EBE8YEUEjIW10AQJTzJiuOg6XpxfBoFRcEk.h8PsZ4GA7wdPZ5GMW\r\n$2a$10$AuUc4DpimuH/nUNuzkZSMu1ghANQ9cr3GvnbC/oEt3EukAEtMYY46', 'test1', '广州商学院', '13612746972', 0, '男', 'b75123a6-09d3-44d4-b5df-2a2f8d67eca1.webp', NULL);

-- ----------------------------
-- Table structure for advertise
-- ----------------------------
DROP TABLE IF EXISTS `advertise`;
CREATE TABLE `advertise`  (
  `advertise_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '广告id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '广告标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '广告内容',
  `type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '广告类型',
  `target_person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目标人群',
  `key_words` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关键词',
  `costs` bigint(0) NULL DEFAULT NULL COMMENT '广告费用',
  `status` bigint(0) NULL DEFAULT NULL COMMENT '发布状态',
  `click_number` bigint(0) NULL DEFAULT 0 COMMENT '广告点击数量',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '位置信息',
  `company` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '提供广告的公司',
  `company_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '公司id',
  `image_video_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '广告图片或视频',
  `total_profit` bigint(0) NULL DEFAULT 0 COMMENT '总盈利',
  `add_status` bigint(0) NULL DEFAULT NULL COMMENT '0为草稿，1为已添加',
  `shelves_status` bigint(0) NULL DEFAULT NULL COMMENT '0为未上架到app，1为上架到app',
  PRIMARY KEY (`advertise_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of advertise
-- ----------------------------
INSERT INTO `advertise` VALUES ('047cbe7c-5fc0-48f2-a8d1-30d547793aaa', '能治此去所取气', '<p></p>', '交通类', 'wu', 'wu', 9, 1, 0, '广东汕头', 'wu', '047cbe7c-5fc0-48f2-a8d1-30d547793bbb', 'e6f030f2-d3bb-4bff-a982-fa94b76b1365.jpg', 0, 1, 1);
INSERT INTO `advertise` VALUES ('09433ca2-18cf-4f09-9484-433ee716d4f0', '2', '<p><strong>23<u>232</u></strong></p>', '交通类', '2', '2', 2, 0, 0, '广东', '2', NULL, 'c18d31b2-068c-4720-a948-c3e9971cc95d.png', 0, 1, 0);
INSERT INTO `advertise` VALUES ('0ec88bab-c50b-4947-8a31-354823cd55cf', '12', '<p></p>', '交通类', '12', '2', 12, 0, 0, '12', '21', NULL, 'e4b52296-65c3-4a17-b3c4-805cedf6d254.png', 0, 1, 0);
INSERT INTO `advertise` VALUES ('11d2949a-e88a-482f-b7b3-dac6d7a33e72', '456', '<p></p>', '交通类', '456', '456', 456, 0, 0, '456', '456', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('1c9895e3-5a99-4d62-ab7f-1cc92fcbe633', '西北', '', '交通类', '教师', '西北', 90, 0, 0, '广州', '未完', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('23d7c068-1417-4ca3-88c1-53c7e42abc00', '测试111', '<p></p>', '公益类', '测试111', '测试111', 111, 1, 0, '测试111', '测试111', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('24604dae-4d16-4efe-8720-143415421bc4', '000', '', '公益类', '000', '000', 0, 0, 0, '000', '000', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('28ba395a-116e-4b3e-99bb-9d3925d89555', '312', '<p><u>132</u></p>', '饮食类', '132', '123', 312, 0, 0, '321', '132', NULL, '0383ce39-d55c-4691-a937-73b04898acf1.png', 0, 1, 0);
INSERT INTO `advertise` VALUES ('2a1cdcba-367f-45d6-a58a-0e8e42c3ef69', '测试0', '<p><strong>123<u>213</u><s><u>213213123<mark>21321321</mark></u></s></strong></p>', '交通类', '测试0', '测试0', 100, 0, 0, '测试0', '测试0', NULL, 'cb159f46-04c5-4085-9850-641b27024686.png', 0, 1, 0);
INSERT INTO `advertise` VALUES ('2eb10cb8-ccbd-4ef3-8e2c-fd4af8cf36ea', '测试2', '<p><strong>测试的内容</strong></p><p><strong><em>测试的内容2</em></strong></p>', '交通类', '测试2', '测试2', 666, 0, 0, '测试2', '测试2', NULL, '2c83b590-3100-492c-be51-1edeb0ceaeaf.png', 0, 1, 0);
INSERT INTO `advertise` VALUES ('3053e74a-8019-4425-8b22-d23de1ab2a6c', '12', '', '公益类', '12', '12', 12, 0, 0, '12', '21', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('312e50cd-38fa-4b89-964a-39be2ed6792d', '777', '', '交通类', '777', '777', 777, 0, 0, '777', '777', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('3b5b3ead-e143-4554-8661-acfb75465bb5', '测试三', '<p><strong>23<u>1231</u><s><u>123</u></s></strong></p>', '交通类', '测试三', '测试三', 200, 0, 0, '测试三', '测试三', NULL, 'ea2b4fb6-531f-4be5-9ecb-87543646b881.png', 0, 1, 0);
INSERT INTO `advertise` VALUES ('3dde7493-4dd9-4e6b-9478-edcfcfdc0dc9', '32', '', '交通类', '33', '32', 333, 0, 0, '33', '3', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('431ee8d7-ad9a-41a0-bd2d-ac824070d3c6', '对对对', '', '交通类', '方法', '滴滴滴', 66, 1, 0, '请求', '问问', NULL, NULL, 0, 1, 1);
INSERT INTO `advertise` VALUES ('47b8b766-78d8-44bb-bb49-33b3be4c02f6', 'qqq', '<p>12<strong><em>12<u>333</u></em></strong></p>', '公益类', 'qqq', 'qqq', 90, 0, 0, 'qqq', 'qqq', NULL, '11051a7a-1994-4ff0-a0bd-e5fc6b0bceb9.png', 0, 1, 0);
INSERT INTO `advertise` VALUES ('51d7515f-d461-4e31-abdb-96b0f012792b', 'ce', '<p></p>', '交通类', 'ce', 'ce', 123, 0, 0, 'ce', 'ce', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('54d0a80b-43cc-407b-aab4-57ca16714d18', '11', '', '饮食类', '11', '11', 11, 0, 0, '11', '11', NULL, NULL, 0, 0, 0);
INSERT INTO `advertise` VALUES ('6b64b330-4a25-45b1-b6cb-da2b9b1d9415', '1', '', '公益类', '1', '1', 1, 0, 0, '1', '1', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('71b17882-ea74-4250-9b03-3db49466baa5', '草稿12', '<p></p>', '交通类', '草稿12', '草稿12', 6662, 0, 0, '草稿12', '草稿12', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('74f9070c-d442-4751-bcf9-e244ff3ce7f1', '231', '<p><em>123<u>213</u></em></p>', '交通类', '312', '312', 312, 0, 0, '132', '123', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('7772d182-abc5-4c70-94cc-d28151407bb1', '测试000', '<p><strong>123<em>1234</em></strong></p>', '公益类', '测试000', '测试000', 123, 0, 0, '测试000', '测试000', NULL, '6d66b3b0-02da-44c5-a226-698a45bf990d.png', 0, 1, 0);
INSERT INTO `advertise` VALUES ('78802fab-6228-4762-935b-e875ec7f3be6', '测试213', '<p>测试213</p>', '交通类', '测试213', '测试213', 10000, 0, 0, '测试213', '测试213', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('7cae084f-bbd0-4996-b61d-84ba0f8ab0b0', '123123', '', '饮食类', '123123', '123123', 12, 1, 0, '123123', '123123', NULL, '7fec061e-c2aa-48c1-986d-4172d152c5d8.png', 0, 1, 1);
INSERT INTO `advertise` VALUES ('8118ec3f-ea4f-4292-983d-b410a110527e', '777', '', '交通类', '777', '777', 777, 0, 0, '77', '777', NULL, '8931434a-82cd-4b1f-8eb2-5f2d078379b8.png', 0, 1, 0);
INSERT INTO `advertise` VALUES ('8bd94579-0257-4550-938b-1b692b8f51b2', '660', '', '公益类', '6660', '660', 66, 0, 0, '6006', '6060', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('8c76b6a7-9552-401b-bc2f-ca7bd2581934', '999', '', '公益类', '99988888', '999', 999, 0, 0, '999888', '999888', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('919469a6-4f5a-426b-911c-6d6d06180914', '11', '<p>456</p>', '交通类', '11', '11', 11, 0, 0, '11', '11', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('921b343e-ddcf-4ff4-9752-0221d58733d6', '5522', '', '交通类', '55', '5522', 5522, 0, 0, '5', '55', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('a186ef29-2ad4-43c9-b8b4-016ce95b8184', '新疆', '', '饮食类', '学生', '饮食', 100, 0, 0, '深圳', '华为', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('a5f6ee2a-5bbd-4ba8-81f1-9c46cea42951', 'ddd', '', '饮食类', 'ddd', 'ddd', 100, 0, 0, 'ddd', 'ddd', NULL, '3c85d5c7-173c-4e9f-88e0-38013c682c5d.mp4', 0, 1, 0);
INSERT INTO `advertise` VALUES ('a7ea01e1-6a52-4986-a0f9-231d3217d2f3', '测试测试', '<p>测试测试测试测试</p>', '交通类', '测试测试', '测试测试', 100, 0, 0, '测试测试', '测试测试', NULL, NULL, 0, 0, 0);
INSERT INTO `advertise` VALUES ('aa46d997-4563-4723-b868-12ad33818cf2', '江苏', '', '交通类', '校长', '222', 1000, 0, 0, '天安门', '北京北京', NULL, 'b966f988-510c-416b-8b58-f846c929f83e.png', 0, 1, 0);
INSERT INTO `advertise` VALUES ('ac036dc2-3a09-4fdd-97a2-bc5068a7503e', '98测试1', '<p><strong>231<u>123</u></strong></p>', '公益类', '98测试1', '98测试1', 98, 0, 0, '98测试1', '98测试1', NULL, 'e857e375-e890-4ee8-a652-fc09b6d74935.png', 0, 1, 0);
INSERT INTO `advertise` VALUES ('b76e249a-5346-4e08-b88e-a29d63c08b75', '添加', '<p>添加</p>', '饮食类', '添加', '添加', 333, 0, 0, '添加', '添加', NULL, NULL, 0, 0, 0);
INSERT INTO `advertise` VALUES ('bde35ac8-c248-4687-9fea-3d6e3e867851', '测试五', '<p>123<strong><em>2123</em></strong></p>', '交通类', '测试五', '测试五', 5, 0, 0, '测试五', '测试五', NULL, '55a062b5-2bfd-4498-80b6-34e9bafc54ac.png', 0, 1, 0);
INSERT INTO `advertise` VALUES ('c2650f1f-732b-436a-918e-1b888ce05486', 'ce', '<p><s>cececececece</s></p>', '交通类', 'ce', 'ce', 100, 0, 0, 'ce', 'ce', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('c74c3d1d-5b90-4b80-9998-d82c25d7a3d2', '测试7', '<p><strong>244234234<s>3242</s></strong></p>', '交通类', '测试7', '测试7', 777, 0, 0, '测试7', '测试7', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('ce9ea953-6459-4722-b35c-332a346294dd', 'ceshi', '<p><u>cesh</u>i</p>', '交通类', 'ceshi', 'ceshi', 100, 1, 0, 'ceshi', 'ceshi', NULL, 'd6f31f48-d97b-4901-b02e-1d5a5ee50a51.png', 0, 1, 0);
INSERT INTO `advertise` VALUES ('dd971e69-ac28-4c03-8b51-bbf30a0616ac', '发布', '<p>发布</p>', '交通类', '发布', '发布', 12, 0, 0, '发布', '发布', NULL, NULL, 0, 1, 0);
INSERT INTO `advertise` VALUES ('e069ecd6-a2f0-4281-8655-874f29b9c54d', 'zzzzzzz', '<p><em>zzzzzzz</em></p>', '交通类', 'zzzzzzz', 'zzzzzzz', 100, 1, 0, 'zzzzzzz', 'zzzzzzz', NULL, '0fe5252a-69cf-442f-b60f-86aaa8db0ca2.png', 0, 1, 0);
INSERT INTO `advertise` VALUES ('e73ddacc-da8c-4103-8a3f-008c50e9953f', '123333', '<p></p>', '交通类', '123333', '123333', 123333, 1, 0, '123333', '123333', NULL, NULL, 0, 1, 1);

-- ----------------------------
-- Table structure for advertise_image
-- ----------------------------
DROP TABLE IF EXISTS `advertise_image`;
CREATE TABLE `advertise_image`  (
  `advertise_image_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '广告_图片id(用于存放广告对应的多张图片）',
  `advertise_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '广告id',
  `media_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用于存放广告对应的图片或者视频',
  PRIMARY KEY (`advertise_image_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of advertise_image
-- ----------------------------
INSERT INTO `advertise_image` VALUES ('31662d42-8caf-418b-b268-724dbb6f26ed', '09433ca2-18cf-4f09-9484-433ee716d4f0', '6096859c-9ada-4b4a-bed8-dbd57df2ca4f.jpg');
INSERT INTO `advertise_image` VALUES ('5d288e51-080c-4a40-a874-0fd4af585dd2', '09433ca2-18cf-4f09-9484-433ee716d4f0', '2730791a-6b1d-47db-8d98-8442113b7bd3.png');
INSERT INTO `advertise_image` VALUES ('5d519dab-66af-4ef7-ba71-a70cbc6f97cf', '78802fab-6228-4762-935b-e875ec7f3be6', '104e9041-0888-4bd6-ba50-c0df44f3bbec.png');
INSERT INTO `advertise_image` VALUES ('ebe7967e-1cdb-4277-ac51-58bd286de528', '74f9070c-d442-4751-bcf9-e244ff3ce7f1', 'a67610de-0105-4bad-b514-85b7f1552628.png');

-- ----------------------------
-- Table structure for face_identify
-- ----------------------------
DROP TABLE IF EXISTS `face_identify`;
CREATE TABLE `face_identify`  (
  `face_identify_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `student_identity_card` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学生身份证',
  `student_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学生电话',
  `person_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '人员id',
  `group_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '人员对应的人员库',
  PRIMARY KEY (`face_identify_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of face_identify
-- ----------------------------

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow`  (
  `follow_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '关注id',
  `follower_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '关注者id',
  `following_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '博主id',
  `status` int(0) NULL DEFAULT NULL COMMENT '关注状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '关注时间',
  PRIMARY KEY (`follow_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of follow
-- ----------------------------
INSERT INTO `follow` VALUES ('047cbe7c-5fc0-48f2-a8d1-30d547793aaa', 'b184673b-4a7e-4b49-ab2c-08a3ad078888', '047cbe7c-5fc0-48f2-a8d1-30d547793afe', 1, '2011-08-05 14:12:00');
INSERT INTO `follow` VALUES ('047cbe7c-5fc0-48f2-a8d1-30d547793aae', '047cbe7c-5fc0-48f2-a8d1-30d547793afe', 'b184673b-4a7e-4b49-ab2c-08a3ad078888', 1, '2024-03-31 20:32:34');

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `post_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '朋友圈帖子id',
  `following_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发布者id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `media` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '媒体(图片or视频)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `likes` int(0) NULL DEFAULT NULL COMMENT '点赞数',
  `comments` int(0) NULL DEFAULT NULL COMMENT '评论数',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post
-- ----------------------------

-- ----------------------------
-- Table structure for post_comment
-- ----------------------------
DROP TABLE IF EXISTS `post_comment`;
CREATE TABLE `post_comment`  (
  `comment_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论id',
  `follower_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '粉丝id',
  `post_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '帖子id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post_comment
-- ----------------------------

-- ----------------------------
-- Table structure for post_likes
-- ----------------------------
DROP TABLE IF EXISTS `post_likes`;
CREATE TABLE `post_likes`  (
  `like_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '点赞id',
  `post_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '帖子id',
  `follower_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '粉丝id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '点赞时间',
  PRIMARY KEY (`like_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post_likes
-- ----------------------------

-- ----------------------------
-- Table structure for rankings
-- ----------------------------
DROP TABLE IF EXISTS `rankings`;
CREATE TABLE `rankings`  (
  `ranking_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '排行榜id',
  `student_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学生id',
  `ranking` int(0) NULL DEFAULT NULL COMMENT '学生对应的排行',
  `like_count` int(0) NULL DEFAULT NULL COMMENT '点赞数',
  `points` int(0) NULL DEFAULT NULL COMMENT '总积分'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rankings
-- ----------------------------
INSERT INTO `rankings` VALUES ('047cbe7c-5fc0-48f2-a8d1-30d547793afe', '047cbe7c-5fc0-48f2-a8d1-30d547793afe', 3, 3, 7);
INSERT INTO `rankings` VALUES ('2196aea4-868d-4298-a709-7dab1d6950ca', '2196aea4-868d-4298-a709-7dab1d6950ca', 5, 2, 2);
INSERT INTO `rankings` VALUES ('347dcc71-ec17-4dae-8a6c-59c1972f94dd', '347dcc71-ec17-4dae-8a6c-59c1972f94d3', 4, 1, 3);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `student_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `identity_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '身份证',
  `student_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `status` int(0) NULL DEFAULT NULL COMMENT '注册情况，0未注册，1已注册',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `is_delete` int(0) NULL DEFAULT NULL COMMENT '1为已删除,0为未删除',
  `sex` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号 ',
  `examinee_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '考生号',
  `dormitory_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '宿舍号',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `school` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学校',
  `college` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学院',
  `major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专业',
  `student_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '班级',
  `instructor` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '辅导员',
  `parents_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父母姓名',
  `parents_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父母电话',
  `graduation_school` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '毕业学校',
  `origin` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '籍贯',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `nation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '民族',
  `born_date` datetime(0) NULL DEFAULT NULL COMMENT '出生日期',
  `introduction` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '个人介绍',
  `expectation` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '大学期望',
  `self_assessment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '自我评价',
  `target` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '目标',
  `specialty` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '特长',
  `person_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '存储腾讯云人脸识别所需要的personId',
  PRIMARY KEY (`student_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('047cbe7c-5fc0-48f2-a8d1-30d547793afe', '440582200406202001', '202206120101', '$2a$10$jc/yYBe4hoSXqXN1L34jz.pY5N3..cO8F1xzWpxMaU3a9p2fBPU1q', 1, '小李', 0, '男', '17796224061', '202206120101', NULL, 'f5e5a3ba-294e-4bea-a504-0694c90cebca.png', '广商', '信息技术与工程学院', '软件工程', NULL, '李国祥', '小陈', '11111111111', '中学广商', '广东', '广东广州', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cfcd0fb7-8259-4f34-8639-7211d7918d0c');
INSERT INTO `student` VALUES ('2196aea4-868d-4298-a709-7dab1d6950ca', '440582200406202111', '202206120102', '$2a$10$pw87Y6tOOsMYVH8JseShV.ZYcoh2DcT2wGXZxqPa2fRrqRB0YVAAG', 1, 'test2', 1, '女', '17796224059', '202206120102', NULL, NULL, '广商', '信息技术与工程学院', '软件工程', NULL, '李国祥', '大李', '11111111111', '中学广商', '广东', '广东广州', '汉', '2003-01-01 00:00:00', NULL, NULL, NULL, NULL, 'wuwu', NULL);
INSERT INTO `student` VALUES ('282e6267-21b1-4222-9c8c-00691ec40f29', '440582200406202000', NULL, NULL, 1, 'xxx', 1, '男', '17796224060', '202206120100', NULL, NULL, '广商', '现代信息技术', '软件工程', NULL, '李国祥', 'xxx', '188888888888', '中学广商', '广东', '东莞', '汉', '2004-01-01 00:00:00', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `student` VALUES ('33fc8ca7-3782-4d6d-b067-13af9b749851', '111111111111111111', NULL, NULL, 0, 'zzz', 1, '男', '18166666666', '11111111111111', NULL, NULL, 'zzz', 'zzzz', 'zzz', NULL, 'zzz', 'zzz', '18166666666', 'zzz', 'zz', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `student` VALUES ('347dcc71-ec17-4dae-8a6c-59c1972f94d3', '130930200402273021', NULL, '$2a$10$y6mzElMgY2ZXMyyh0el1/.pirMJI0fGi2wag1Z5J6kTJFfVdW0owm', 1, 'z', 1, '女', '13612746972', '12345678912345', NULL, 'd886c417-0a8b-4032-ae94-b45d93e1a405.jpg', 'z', '在', '在', NULL, 'z', 'z', '18126507985', 'z', 'z', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `student` VALUES ('721fefae-fb09-4e5e-86c8-a3cab70dd1e7', '130930200402273022', NULL, NULL, 0, 'zzz', 1, 'z', '18126507985', '11111111111111', NULL, NULL, 'z', 'z', 'z', NULL, 'z', 'z', '18126507985', 'z', 'z', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `student` VALUES ('b184673b-4a7e-4b49-ab2c-08a3ad078888', '440582200406201222', '202206120100', '$2a$10$9CWWHtZVJ3kitDpmwrc8EOPoTW1aHLl32sYWsa5mvZxUoThTJDOhu', 1, '小朱', 1, '男', '17708216111', '202201', NULL, '452094bb-676a-4bb9-8a69-6ff0a8d1469d.jpg', '广商', '信院', '信管', NULL, '李国祥', '半风理入必真', '18685344725', '广商', '茂名', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '45dc3d40-abc8-43db-8b21-d92b60c5c4b7');
INSERT INTO `student` VALUES ('b184673b-4a7e-4b49-ab2c-08a3ad07888a', '440582200406208888', NULL, NULL, 0, '小朱', 1, '男', '18656772549', '202206', NULL, NULL, '广商', '信院', '信管', NULL, '李国祥', '半风理入必真', '18685344725', '广商', '茂名', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `student` VALUES ('b380a2e5-ea08-4874-ba59-a7802b36dcbb', '312963256235698417', NULL, NULL, 0, '12312', 0, 'ww132', '18163596532', '99999999895689', NULL, NULL, 'ww132', 'wwww', 'www', NULL, 'ww', 'www', '18163596532', 'w', 'w', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `student` VALUES ('c8689753-9681-4862-ab54-29278bea2158', '888888888888888888', NULL, NULL, 0, 'zzz', 1, 'nan', '18126555555', '66666666666666', NULL, NULL, 'zzz', 'www', 'www', NULL, 'www', 'www', '18126555555', '132', '1323', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `student` VALUES ('f9c4c18a-2c9f-46fb-941e-80e2e27f5994', '130930200402273023', NULL, NULL, 0, 'qqq', 1, '女', '18126507985', '11111111111111', NULL, NULL, 'a', '去', 's', NULL, 's', 's', '18126507985', 's', 's', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for student_task
-- ----------------------------
DROP TABLE IF EXISTS `student_task`;
CREATE TABLE `student_task`  (
  `student_task_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学生任务id',
  `student_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学生id',
  `task_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务id',
  `status` int(0) NULL DEFAULT NULL COMMENT '1未开始、0进行中、1已完成',
  `score` bigint(0) NULL DEFAULT NULL COMMENT '该任务对应的分数',
  PRIMARY KEY (`student_task_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_task
-- ----------------------------
INSERT INTO `student_task` VALUES ('047cbe7c-5fc0-48f2-a8d1-30d547793afw', '047cbe7c-5fc0-48f2-a8d1-30d547793afe', '047cbe7c-5fc0-48f2-a8d1-30d547793afa', 1, 2);
INSERT INTO `student_task` VALUES ('2196aea4-868d-4298-a709-7dab1d6950ww', '2196aea4-868d-4298-a709-7dab1d6950ca', '2196aea4-868d-4298-a709-7dab1d6950xx', 1, 2);
INSERT INTO `student_task` VALUES ('347dcc71-ec17-4dae-8a6c-59c1972f94dd', '347dcc71-ec17-4dae-8a6c-59c1972f94d3', '347dcc71-ec17-4dae-8a6c-59c1972f94dw', 1, 3);
INSERT INTO `student_task` VALUES ('5bfb3111-aef8-4445-866f-76921ff7ef2d', '282e6267-21b1-4222-9c8c-00691ec40f29', '00863ec1-cfc8-4859-9414-8fb2bb601c7e', 1, 132);
INSERT INTO `student_task` VALUES ('87d43941-34cd-4624-b2a2-c7753ba45c1c', 'b184673b-4a7e-4b49-ab2c-08a3ad078888', '047cbe7c-5fc0-48f2-a8d1-30d547793aed', 1, 5);
INSERT INTO `student_task` VALUES ('9717b927-9dab-4b80-8cd9-47023f2f8d86', 'b184673b-4a7e-4b49-ab2c-08a3ad078888', '047cbe7c-5fc0-48f2-a8d1-30d547793aed', 1, 5);
INSERT INTO `student_task` VALUES ('9adbe036-2731-44cc-9751-207942398873', '282e6267-21b1-4222-9c8c-00691ec40f29', '00863ec1-cfc8-4859-9414-8fb2bb601c7e', 1, 132);
INSERT INTO `student_task` VALUES ('b1c564b6-2846-4e72-8e90-080de8effd11', NULL, '047cbe7c-5fc0-48f2-a8d1-30d547793aed', 1, 5);
INSERT INTO `student_task` VALUES ('e433ff97-8965-4ba2-b5bb-fbecd3ca418c', 'b184673b-4a7e-4b49-ab2c-08a3ad078888', '047cbe7c-5fc0-48f2-a8d1-30d547793aed', 1, 5);
INSERT INTO `student_task` VALUES ('e50f4eda-abc8-4293-a0c2-42c114c5b1e9', '047cbe7c-5fc0-48f2-a8d1-30d547793afe', '047cbe7c-5fc0-48f2-a8d1-30d547793aed', 1, 5);

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `task_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务内容',
  `creator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `dead_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `school` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务对应的学校',
  `dependencies` int(0) NULL DEFAULT NULL COMMENT '1代表主线，2代表分线任务',
  `score` bigint(0) NULL DEFAULT NULL COMMENT '任务对应的分数',
  `status` int(0) NULL DEFAULT NULL COMMENT '已发布,未发布(草稿)',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务完成应对应的地址',
  `image_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务打卡图片',
  PRIMARY KEY (`task_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES ('00863ec1-cfc8-4859-9414-8fb2bb601c7e', '132', '<p><em>123</em></p>', '31', '1977-07-03 00:06:00', '1977-09-03 00:06:00', '132', 1, 132, 0, '312', 'acdc79c9-8e18-4edb-909b-a641eae9b403.png');
INSERT INTO `task` VALUES ('047cbe7c-5fc0-48f2-a8d1-30d547793aed', '开学任务', '拍照上传', 'xiao', '2024-03-01 02:37:04', '2024-03-26 19:16:45', '广商', 1, 5, 1, '广州', '6ae47c57-0dab-441f-b780-079cf361e3b9.png');
INSERT INTO `task` VALUES ('047cbe7c-5fc0-48f2-a8d1-30d547793aee', '革声史子', 'Lorem', '333', '1977-07-03 00:06:00', '1987-12-17 17:42:00', '222', 98, 34, 1, '111', NULL);
INSERT INTO `task` VALUES ('13386fb1-789c-49f2-9727-cb04f740e11b', '231', '<p><em>123<u>123</u></em></p>', '312', '2024-04-06 19:32:00', '2024-05-05 20:32:00', '123', 1, 123, 0, '123', NULL);
INSERT INTO `task` VALUES ('1f6c9183-c086-4f82-a0dd-ce8d73a120c9', 'ceshi', '<p>ceshi</p>', 'ceshi', '2024-03-03 00:00:00', '2024-03-31 00:00:00', 'ceshi', 1, 30, 0, 'ceshi', '6b68d375-83ec-40cc-bfc7-1433ba25bac8.png');
INSERT INTO `task` VALUES ('2e0674a5-40aa-451f-8671-12e8a383d21b', '312', '<p><u>132</u></p>', '132', '2024-04-07 00:00:00', '2024-05-05 00:00:00', '132', 1, 132, 1, '132', NULL);
INSERT INTO `task` VALUES ('622fa716-2ae9-4eb9-bae9-24a9183c5466', '测试777', '<p>测试777</p>', '测试777', '2024-03-03 00:00:00', '2024-03-31 00:00:00', '测试777', 2, 777, 1, '测试777', 'f1fbf0e8-6bea-4d81-b81b-b3de170a3f68.png');
INSERT INTO `task` VALUES ('6f8972e1-bc73-4cbf-a986-3b56dc37686e', '重新测试1', '<p>231</p>', '重新测试1', '2024-03-03 09:21:00', '2024-03-31 09:21:00', '重新测试1', 1, 100, 1, '重新测试1', '0624842c-5533-4cac-a6d0-ba748f6b5c25.png');
INSERT INTO `task` VALUES ('7d2beab3-5f81-4f5a-825a-30a6e01bf4bf', '453', '<p><u>435</u></p>', '34', '1987-12-17 17:42:00', '1987-12-17 17:42:00', '645', 1, 45, 1, '465', NULL);
INSERT INTO `task` VALUES ('aac656fe-9625-4b4c-84bc-cdca96a6e860', '123', '<p>123<s>213</s></p>', '321', '2024-03-29 10:19:00', '2024-03-31 00:00:00', '123', 1, 312, 1, '132', NULL);
INSERT INTO `task` VALUES ('bfb48b75-23a7-48a8-a04a-9477285ea34f', '测试56', '<p>56</p>', '测试56', '2024-03-31 16:41:00', '2024-03-31 16:41:00', '测试56', 1, 3, 1, '测试5', '03d8f182-f7e2-4476-8d94-0c0ac8b44484.png');
INSERT INTO `task` VALUES ('efb6de1d-f228-4798-9d51-fc387c84a9e6', '123', '<p><em>123</em></p>', '132', '2024-04-01 18:55:00', '2024-05-05 19:55:00', '132', 1, 132, 1, '132', NULL);
INSERT INTO `task` VALUES ('f4b0a352-1374-4d40-b116-f2bdd2df9beb', '666666662', '<p></p>', '测试6662', '2024-03-03 00:00:00', '2024-03-31 00:00:00', '测试666', 0, 16, 1, '测试666', '756d1c3a-3879-45e5-a7bd-ac96208b882f.png');

-- ----------------------------
-- Table structure for task_review
-- ----------------------------
DROP TABLE IF EXISTS `task_review`;
CREATE TABLE `task_review`  (
  `review_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
  `task_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '任务id',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学生相关信息',
  `review_image_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核的照片',
  `status` int(0) NULL DEFAULT NULL COMMENT '审核状态',
  PRIMARY KEY (`review_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of task_review
-- ----------------------------
INSERT INTO `task_review` VALUES ('2d57b91f-ca4d-4f09-9f04-77bc2bdda97e', '00863ec1-cfc8-4859-9414-8fb2bb601c7e', '17796224060', '6fd06e6f-b76c-4af4-8bef-417e49d4840c.jpg', 0);
INSERT INTO `task_review` VALUES ('3137bfc3-4a4d-430f-9668-bf339cf9c163', '047cbe7c-5fc0-48f2-a8d1-30d547793aed', '17708216111', '4f4f7a19-b287-468a-b7bf-cdd693f6644e.png', 1);
INSERT INTO `task_review` VALUES ('41565351-47ac-47e1-a720-8860b63ee6c9', '00863ec1-cfc8-4859-9414-8fb2bb601c7e', '17796224060', '686edfc1-c8c7-402b-8917-6e2c5a70438a.png', 1);
INSERT INTO `task_review` VALUES ('4fee5a1d-c082-49e1-a01b-f5f9d7f9696c', '00863ec1-cfc8-4859-9414-8fb2bb601c7e', '17796224060', 'd13bfa62-3283-494f-91d0-ff91f5c0c933.jpg', -1);
INSERT INTO `task_review` VALUES ('b8f330f9-e445-4071-875c-15b70a98607a', '00863ec1-cfc8-4859-9414-8fb2bb601c7e', '17796224060', '4796addf-7184-47f9-a4e8-0674eadd32d8.jpg', 1);

SET FOREIGN_KEY_CHECKS = 1;
