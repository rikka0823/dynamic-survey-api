SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

CREATE TABLE `account` (
  `email` varchar(50) NOT NULL,
  `password` varchar(512) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `feedback` (
  `quiz_id` int NOT NULL,
  `ques_id` int NOT NULL,
  `answer` varchar(200) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `age` int DEFAULT '0',
  `fillin_date` date DEFAULT NULL,
  PRIMARY KEY (`quiz_id`,`email`,`ques_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `ques` (
  `quiz_id` int NOT NULL,
  `ques_id` int NOT NULL,
  `ques_name` varchar(45) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `required` tinyint DEFAULT '0',
  `options` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`quiz_id`,`ques_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `quiz` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `published` tinyint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
