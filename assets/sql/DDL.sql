DROP DATABASE IF EXISTS fitnessbet;
CREATE DATABASE fitnessbet;
USE fitnessbet;

DROP TABLE IF EXISTS `USER`;

CREATE TABLE `USER` (
   `id` varchar(15) NOT NULL,
   `pw` varchar(100) NOT NULL,
   `name` varchar(50) NOT NULL,
   `campus` varchar(20) NOT NULL,
   `class_num` int NOT NULL,
   `current_point` int NOT NULL DEFAULT '0',
   `total_point` int NOT NULL DEFAULT '0',
   `visited` date DEFAULT NULL COMMENT '저장되어 있는 데이터가 현재 날짜 이전이면 출석 전 (CURDATE())사용',
   `gender` tinyint(1) NOT NULL COMMENT '여성 T / 남 F',
   `admin` tinyint(1) NOT NULL DEFAULT '0',
   `accept` tinyint(1) NOT NULL DEFAULT '0',
   `phone` varchar(20) NOT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `REVIEW`;

CREATE TABLE `REVIEW` (
	`id`	int	NOT NULL auto_increment,
	`writer`	VARCHAR(15)	NOT NULL	COMMENT 'USER 테이블의 id컬럼 참조',
	`content`	VARCHAR(500)	NOT NULL,
	`reg_date`	date	NOT NULL,
	`mod_date`	date	NULL,
	`betting_id`	int	NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `BETTING`;

CREATE TABLE `BETTING` (
	`id`	int	NOT NULL auto_increment,
	`challenger`	VARCHAR(15)	NOT NULL,
	`mission_id`	int	NOT NULL	COMMENT 'auto_increment',
    `mission_cnt` int	NOT NULL,
	`success_cnt`	int	NOT NULL	DEFAULT 0	COMMENT '미션 성공 배팅 수',
	`fail_cnt`	int	NOT NULL	DEFAULT 0	COMMENT '미션 실패 배팅 수',
	`success_point`	int	NOT NULL	DEFAULT 0,
	`fail_point`	int	NOT NULL	DEFAULT 0,
	`result`	int	DEFAULT 0,
	`reg_date`	date	NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `MISSION`;

CREATE TABLE `MISSION` (
	`id`	int	NOT NULL auto_increment,
	`content`	VARCHAR(100)	NOT NULL,
	`min_m`	int	NOT NULL,
	`max_m`	int	NOT NULL,
	`min_w`	int	NOT NULL,
	`max_w`	int	NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `BETTING_HISTORY`;

CREATE TABLE `BETTING_HISTORY` (
	`id`	int	NOT NULL auto_increment,
	`betting_id`	int	NOT NULL	COMMENT 'betting 테이블 id 참조',
	`player`	VARCHAR(15)	NOT NULL	COMMENT 'user테이블 id 참조',
	`point`	int	NOT NULL,
	`choice` int	NOT NULL,
    `prize` int,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `PRODUCT`;

CREATE TABLE `PRODUCT` (
	`id`	int	NOT NULL auto_increment,
	`name`	VARCHAR(100)	NOT NULL,
	`price`	int	NOT NULL,
	`img`	VARCHAR(2000)	NULL,
	`del_yn`	boolean	NOT NULL	DEFAULT false,
	`point`	int	NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `POINT_HISTORY`;

CREATE TABLE `POINT_HISTORY` (
	`id`	int	NOT NULL auto_increment,
    `user_id` varchar(15) NOT NULL,
    `product_id` int,
    `betting_id` int,
    `category` int NOT NULL,
    `record_date` date NOT NULL,
    `point` int NOT NULL,
    PRIMARY KEY (`id`)
);


ALTER TABLE `REVIEW` ADD CONSTRAINT `FK_USER_TO_REVIEW_1` FOREIGN KEY (
	`writer`
)
REFERENCES `USER` (
	`id`
);



ALTER TABLE `REVIEW` ADD CONSTRAINT `FK_BETTING_TO_REVIEW_1` FOREIGN KEY (
	`betting_id`
)
REFERENCES `BETTING` (
	`id`
);

ALTER TABLE `BETTING` ADD CONSTRAINT `FK_USER_TO_BETTING_1` FOREIGN KEY (
	`challenger`
)
REFERENCES `USER` (
	`id`
);

ALTER TABLE `BETTING` ADD CONSTRAINT `FK_MISSION_TO_BETTING_1` FOREIGN KEY (
	`mission_id`
)
REFERENCES `MISSION` (
	`id`
);

ALTER TABLE `BETTING_HISTORY` ADD CONSTRAINT `FK_BETTING_TO_BETTING_HISTORY_1` FOREIGN KEY (
	`betting_id`
)
REFERENCES `BETTING` (
	`id`
);

ALTER TABLE `BETTING_HISTORY` ADD CONSTRAINT `FK_USER_TO_BETTING_HISTORY_1` FOREIGN KEY (
	`player`
)
REFERENCES `USER` (
	`id`
);

ALTER TABLE `POINT_HISTORY` ADD CONSTRAINT `FK_USER_TO_POINT_HISTORY_1` FOREIGN KEY (
	`user_id`
)
REFERENCES `USER` (
	`id`
);

ALTER TABLE `POINT_HISTORY` ADD CONSTRAINT `FK_PRODUCT_TO_POINT_HISTORY_2` FOREIGN KEY (
	`product_id`
)
REFERENCES `PRODUCT` (
	`id`
);

ALTER TABLE `POINT_HISTORY` ADD CONSTRAINT `FK_BETTING_TO_POINT_HISTORY_3` FOREIGN KEY (
	`betting_id`
)
REFERENCES `BETTING` (
	`id`
);