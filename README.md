# Portal 系统管理后台

![Portal Admin](https://via.placeholder.com/800x400?text=Portal+Admin+Dashboard)

一个基于 Vue 3 + Element Plus 的现代化内容管理系统，支持文章管理、栏目管理、标签管理和用户权限控制。

## 🚀 功能特性

### 📝 内容管理
- **文章管理**: 完整的 CRUD 操作，富文本编辑器支持
- **栏目管理**: 树形结构，支持多级嵌套和排序
- **标签管理**: 标签创建、编辑、删除，关联文章统计
- **文件上传**: 图片上传到 NAS 存储，支持多种格式

### 👥 用户权限
- **用户管理**: 用户创建、编辑、删除、状态控制
- **角色分配**: 基于角色的权限管理
- **密码重置**: 安全的密码重置功能
- **状态控制**: 启用/禁用用户账户

### 🎨 技术特性
- **前端框架**: Vue 3 + Composition API
- **UI 组件库**: Element Plus
- **富文本编辑器**: WangEditor
- **路由管理**: Vue Router
- **HTTP 客户端**: Axios
- **响应式设计**: 适配桌面和移动设备

## 📦 快速开始

### 环境要求
- Node.js v16.0.0+
- npm v8.0.0+
- NAS 存储空间 (用于文件上传)

### 安装依赖
```bash
git clone https://github.com/121820/portal-system.git
cd portal-system/portal-admin
npm install
```

### 开发环境
```bash
npm run serve
```
访问 `http://localhost:8080` 查看开发版本

### 生产构建
```bash
npm run build
```
构建产物位于 `dist/` 目录

## 🗂️ 项目结构
```
portal-system/
├── portal-admin/           # 前端管理后台
│   ├── dist/              # 构建产物
│   ├── public/            # 静态资源
│   ├── src/               # 源代码
│   │   ├── components/    # 公共组件
│   │   ├── views/         # 页面组件
│   │   ├── router/        # 路由配置
│   │   ├── api/           # API 接口
│   │   ├── App.vue        # 根组件
│   │   └── main.js        # 入口文件
│   ├── package.json       # 依赖配置
│   └── vue.config.js      # Vue 配置
├── TEST_PLAN.md           # 测试计划
├── DEPLOYMENT.md          # 部署指南
└── README.md             # 项目说明
```

## 🔧 API 接口

系统需要以下后端 API 接口：

| 模块 | 接口 | 方法 | 描述 |
|------|------|------|------|
| 文章管理 | `/api/v1/articles` | GET | 获取文章列表 |
| 文章管理 | `/api/v1/articles/{id}` | GET | 获取文章详情 |
| 文章管理 | `/api/v1/articles` | POST | 创建文章 |
| 文章管理 | `/api/v1/articles/{id}` | PUT | 更新文章 |
| 文章管理 | `/api/v1/articles/{id}` | DELETE | 删除文章 |
| 栏目管理 | `/api/v1/categories` | GET | 获取栏目树 |
| 栏目管理 | `/api/v1/categories` | POST | 创建栏目 |
| 栏目管理 | `/api/v1/categories/{id}` | PUT | 更新栏目 |
| 栏目管理 | `/api/v1/categories/{id}` | DELETE | 删除栏目 |
| 标签管理 | `/api/v1/tags` | GET | 获取标签列表 |
| 标签管理 | `/api/v1/tags` | POST | 创建标签 |
| 标签管理 | `/api/v1/tags/{id}` | PUT | 更新标签 |
| 标签管理 | `/api/v1/tags/{id}` | DELETE | 删除标签 |
| 用户管理 | `/api/v1/users` | GET | 获取用户列表 |
| 用户管理 | `/api/v1/users` | POST | 创建用户 |
| 用户管理 | `/api/v1/users/{id}` | PUT | 更新用户 |
| 用户管理 | `/api/v1/users/{id}` | DELETE | 删除用户 |
| 用户管理 | `/api/v1/users/{id}/reset-password` | POST | 重置密码 |
| 文件上传 | `/api/v1/files/upload` | POST | 上传文件 |

## 📁 文件存储

上传的文件存储在 NAS 的以下路径：
```
/vol1/1000/Photos/portal-system/
```

确保该目录有写权限：
```bash
mkdir -p /vol1/1000/Photos/portal-system
chmod 755 /vol1/1000/Photos/portal-system
```

## 🧪 测试

### 单元测试
```bash
npm run test:unit
```

### 端到端测试
```bash
npm run test:e2e
```

### 手动测试
参考 `TEST_PLAN.md` 进行完整的功能测试。

## 🚀 部署

详细的部署步骤请参考 `DEPLOYMENT.md` 文件。

## 📄 许可证

MIT License

## 🙏 致谢

- Vue.js - 渐进式 JavaScript 框架
- Element Plus - Vue 3 组件库  
- WangEditor - 富文本编辑器
- Axios - HTTP 客户端

---

**注意**: 本项目需要配合后端 API 服务使用，确保后端服务正常运行后再部署前端。