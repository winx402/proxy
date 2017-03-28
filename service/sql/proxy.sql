CREATE TABLE `proxy_ip` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `ip` VARCHAR(15) NOT NULL DEFAULT '' COMMENT 'ip',
  `port` INT(5) NOT NULL DEFAULT '0' COMMENT '端口',
  `available` TINYINT(2) NOT NULL DEFAULT '0' COMMENT '可用程度',
  `proxy_type` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '类型',
  `score` TINYINT(3) NOT NULL DEFAULT '0' COMMENT '分数',
  `create_time` DATE NOT NULL DEFAULT '2000-01-01' COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_ip_port` (`ip`,`port`),
  KEY `idx_available_score` (`available`, `score`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='代理ip';