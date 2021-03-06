CREATE TABLE `proxy_ip` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `ip` VARCHAR(15) NOT NULL DEFAULT '' COMMENT 'ip',
  `port` INT(5) NOT NULL DEFAULT '0' COMMENT '端口',
  `available` TINYINT(2) NOT NULL DEFAULT '0' COMMENT '可用程度',
  `proxy_type` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '类型',
  `score` TINYINT(3) NOT NULL DEFAULT '0' COMMENT '分数',
  `create_time` DATE NOT NULL DEFAULT '2000-01-01' COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_ip_port_type` (`ip`,`port`,`proxy_type`),
  KEY `idx_available_score` (`available`, `score`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='代理ip';



CREATE TABLE `source_web` (
  `id` INT(4) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `web` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '网站首页，入口',
  `entrance` VARCHAR(200) NOT NULL DEFAULT '' COMMENT '是否访问',
  `should_visit` VARCHAR(200) NOT NULL DEFAULT '' COMMENT '是否爬取',
  `line_type` VARCHAR(10) NOT NULL DEFAULT '' COMMENT '数据格式',
  `ip_expression` VARCHAR(10) NOT NULL DEFAULT '' COMMENT 'ip获取',
  `port_expression` VARCHAR(10) NOT NULL DEFAULT '' COMMENT '端口获取',
  `type_expression` VARCHAR(10) NOT NULL DEFAULT '' COMMENT '代理类型获取',
  `cycle` INT(4) NOT NULL DEFAULT '24' COMMENT '执行周期',
  `last_time` DATETIME NOT NULL DEFAULT '2000-01-01 00:00:01' COMMENT '上次执行时间',
  `ban_times` INT(3) NOT NULL DEFAULT '0' COMMENT '禁止访问次数',
  `status` INT(1) NOT NULL DEFAULT '1' COMMENT '状态，是否生效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='代理网站，爬取ip';