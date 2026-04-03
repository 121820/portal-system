-- 博客模块表结构

-- 栏目表
CREATE TABLE IF NOT EXISTS `cms_category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '栏目ID',
  `name` varchar(100) NOT NULL COMMENT '栏目名称',
  `description` varchar(255) DEFAULT NULL COMMENT '栏目描述',
  `parent_id` bigint DEFAULT '0' COMMENT '父栏目ID',
  `sort` int DEFAULT '0' COMMENT '排序',
  `status` tinyint DEFAULT '1' COMMENT '状态（1-正常 0-停用）',
  `created_by` bigint DEFAULT NULL COMMENT '创建者',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_at` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='栏目表';

-- 文章表
CREATE TABLE IF NOT EXISTS `cms_article` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `summary` varchar(500) DEFAULT NULL COMMENT '摘要',
  `content` longtext COMMENT '内容',
  `cover_image` varchar(255) DEFAULT NULL COMMENT '封面图片',
  `category_id` bigint NOT NULL COMMENT '栏目ID',
  `author_id` bigint NOT NULL COMMENT '作者ID',
  `status` tinyint DEFAULT '0' COMMENT '状态（1-发布 0-草稿）',
  `view_count` int DEFAULT '0' COMMENT '浏览量',
  `created_by` bigint DEFAULT NULL COMMENT '创建者',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_at` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_author_id` (`author_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章表';

-- 标签表
CREATE TABLE IF NOT EXISTS `cms_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `name` varchar(50) NOT NULL COMMENT '标签名称',
  `status` tinyint DEFAULT '1' COMMENT '状态（1-正常 0-停用）',
  `created_by` bigint DEFAULT NULL COMMENT '创建者',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_at` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签表';

-- 文章标签关联表
CREATE TABLE IF NOT EXISTS `cms_article_tag` (
  `article_id` bigint NOT NULL COMMENT '文章ID',
  `tag_id` bigint NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`article_id`,`tag_id`),
  KEY `idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章标签关联表';