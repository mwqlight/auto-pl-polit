-- Python语言学习驾驶舱系统数据库初始化脚本（简化版）
-- 创建时间: 2024年
-- 数据库: MySQL 8.0+

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `python_learning` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `python_learning`;

-- 学习模块表
CREATE TABLE IF NOT EXISTS `learning_modules` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '模块ID',
    `title` VARCHAR(100) NOT NULL COMMENT '模块标题',
    `description` TEXT COMMENT '模块描述',
    `module_type` ENUM('BASIC_SYNTAX', 'OOP', 'FUNCTIONAL_PROGRAMMING', 'EXCEPTION_HANDLING', 'MODULES_PACKAGES', 'ADVANCED_FEATURES') DEFAULT 'BASIC_SYNTAX' COMMENT '模块类型',
    `difficulty_level` ENUM('BEGINNER', 'INTERMEDIATE', 'ADVANCED') DEFAULT 'BEGINNER' COMMENT '难度级别',
    `estimated_duration` INT DEFAULT 60 COMMENT '预计学习时长(分钟)',
    `prerequisites` TEXT COMMENT '前置要求',
    `learning_objectives` TEXT COMMENT '学习目标',
    `icon_url` VARCHAR(255) COMMENT '图标URL',
    `thumbnail_url` VARCHAR(255) COMMENT '缩略图URL',
    `is_active` BOOLEAN DEFAULT TRUE COMMENT '是否激活',
    `is_published` BOOLEAN DEFAULT FALSE COMMENT '是否发布',
    `sort_order` INT DEFAULT 0 COMMENT '排序索引',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_module_type` (`module_type`),
    INDEX `idx_difficulty` (`difficulty_level`),
    INDEX `idx_sort_order` (`sort_order`),
    INDEX `idx_published` (`is_published`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学习模块表';

-- 课程表
CREATE TABLE IF NOT EXISTS `lessons` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '课程ID',
    `module_id` BIGINT NOT NULL COMMENT '所属模块ID',
    `title` VARCHAR(100) NOT NULL COMMENT '课程标题',
    `description` TEXT COMMENT '课程描述',
    `content` LONGTEXT COMMENT '课程内容(HTML格式)',
    `video_url` VARCHAR(255) COMMENT '视频URL',
    `duration` INT DEFAULT 15 COMMENT '课程时长(分钟)',
    `sort_order` INT DEFAULT 0 COMMENT '排序索引',
    `is_free` BOOLEAN DEFAULT TRUE COMMENT '是否免费',
    `prerequisites` TEXT COMMENT '前置要求',
    `learning_objectives` TEXT COMMENT '学习目标',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`module_id`) REFERENCES `learning_modules`(`id`) ON DELETE CASCADE,
    INDEX `idx_module_id` (`module_id`),
    INDEX `idx_sort_order` (`sort_order`),
    INDEX `idx_free` (`is_free`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='课程表';

-- 代码示例表
CREATE TABLE IF NOT EXISTS `code_examples` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '示例ID',
    `lesson_id` BIGINT NOT NULL COMMENT '所属课程ID',
    `title` VARCHAR(100) NOT NULL COMMENT '示例标题',
    `description` TEXT COMMENT '示例描述',
    `java_code` LONGTEXT COMMENT 'Java代码',
    `python_code` LONGTEXT COMMENT 'Python代码',
    `explanation` TEXT COMMENT '代码解释',
    `difficulty_level` ENUM('EASY', 'MEDIUM', 'HARD') DEFAULT 'EASY' COMMENT '难度级别',
    `sort_order` INT DEFAULT 0 COMMENT '排序索引',
    `is_interactive` BOOLEAN DEFAULT FALSE COMMENT '是否可交互',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`lesson_id`) REFERENCES `lessons`(`id`) ON DELETE CASCADE,
    INDEX `idx_lesson_id` (`lesson_id`),
    INDEX `idx_difficulty` (`difficulty_level`),
    INDEX `idx_sort_order` (`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='代码示例表';

-- 系统配置表
CREATE TABLE IF NOT EXISTS `system_configs` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '配置ID',
    `config_key` VARCHAR(100) NOT NULL UNIQUE COMMENT '配置键',
    `config_value` TEXT NOT NULL COMMENT '配置值',
    `description` VARCHAR(255) COMMENT '配置描述',
    `config_type` ENUM('STRING', 'NUMBER', 'BOOLEAN', 'JSON') DEFAULT 'STRING' COMMENT '配置类型',
    `is_public` BOOLEAN DEFAULT FALSE COMMENT '是否公开',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_config_key` (`config_key`),
    INDEX `idx_config_key` (`config_key`),
    INDEX `idx_public` (`is_public`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';