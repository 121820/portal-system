# Portal System - 门户网站系统

## 项目结构
```
portal-system/
├── portal-api/        # 后端服务 (Spring Boot 3.x + MyBatis-Plus)
├── portal-web/        # 门户前端 (Nuxt3 SSR)
├── portal-admin/      # 管理后台 (Vue3 + Element Plus)
└── portal-app/        # 移动端 (UniApp)
```

## 技术栈
- **后端**: Spring Boot 3.2.0 + MyBatis-Plus 3.5.5
- **数据库**: MySQL 8.0 + Redis
- **前端门户**: Nuxt3 (SSR)
- **管理后台**: Vue3 + Element Plus
- **移动端**: UniApp

## 开发计划
### Phase 1: 项目骨架 + 公共模块 + 用户模块
- ✅ 创建 Git 仓库
- ✅ 初始化 Maven 多模块项目
- ✅ 配置 Spring Boot 基础依赖
- ✅ 集成 MyBatis-Plus
- 🔄 配置 MySQL + Redis 连接
- 🔄 创建用户模块基础表结构

### Phase 2: 博客模块 + 门户前端
### Phase 3: 管理后台基础框架  
### Phase 4: 内容管理 + 文件上传 + 接口管理
### Phase 5: 移动端适配 + 部署优化

## 部署路径
- **项目路径**: /app/portal-system
- **部署路径**: /app/portal-system
- **运行端口**: 8080

## 数据库初始化
需要先创建 `portal_system` 数据库，然后执行用户模块相关表的 SQL 脚本。

---
创建时间: 2026-04-03