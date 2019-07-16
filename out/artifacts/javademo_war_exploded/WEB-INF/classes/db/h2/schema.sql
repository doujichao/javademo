CREATE TABLE `singer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(60) ,
  `last_name` varchar(40) ,
  `birth_date` date ,
  `version` int not null default 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

CREATE TABLE `album` (
  `id` int NOT NULL AUTO_INCREMENT,
  `singer_id` int(11) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `release_date` date DEFAULT NULL,
  `version` int not null default 0,
  PRIMARY KEY (`id`),
  CONSTRAINT `album_ibfk_1` FOREIGN KEY (`singer_id`) REFERENCES `singer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `instrument` (
  `instrument_id` varchar(20) NOT NULL,
  PRIMARY KEY (`instrument_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `singer_instrument` (
  `singer_id` int(11) NOT NULL,
  `instrument_id` varchar(20) DEFAULT NULL,
  CONSTRAINT `fk_singer_instrument_1` FOREIGN KEY (`singer_id`) REFERENCES `singer` (`id`),
  CONSTRAINT `fk_singer_instrument_2` FOREIGN KEY (`instrument_id`) REFERENCES `instrument` (`instrument_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;