# Portal 系统部署指南

## 部署环境要求
- **Node.js**: v16.0.0 或更高版本
- **npm**: v8.0.0 或更高版本
- **NAS 存储**: `/vol1/1000/Photos/portal-system` 目录已创建并有写权限
- **后端 API**: Spring Boot 服务运行在 `http://localhost:8080` (可配置)

## 前端部署步骤

### 1. 克隆代码仓库
```bash
# 私有仓库（推荐）
git clone https://github.com/121820/portal-system.git

# 或公共仓库
git clone https://github.com/121820/openclaw-portal-system.git

cd portal-system
```

### 2. 安装依赖
```bash
cd portal-admin
npm install
```

### 3. 配置环境变量
创建 `.env` 文件：
```bash
cp .env.example .env
```

编辑 `.env` 文件，配置后端 API 地址：
```env
VUE_APP_API_BASE_URL=http://localhost:8080/api/v1
```

### 4. 开发环境启动
```bash
npm run serve
```
访问 `http://localhost:8080` 查看开发版本

### 5. 生产环境构建
```bash
npm run build
```
构建产物位于 `dist/` 目录

### 6. 生产环境部署
将 `dist/` 目录部署到 Web 服务器（Nginx/Apache/Tomcat）

**Nginx 配置示例**：
```nginx
server {
    listen 80;
    server_name portal.yourdomain.com;
    
    root /path/to/portal-system/portal-admin/dist;
    index index.html;
    
    location / {
        try_files $uri $uri/ /index.html;
    }
    
    # 代理 API 请求到后端
    location /api/ {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

## NAS 文件存储配置
确保 NAS 目录有正确权限：
```bash
# 创建目录
mkdir -p /vol1/1000/Photos/portal-system

# 设置权限（根据实际用户调整）
chmod 755 /vol1/1000/Photos/portal-system
chown www-data:www-data /vol1/1000/Photos/portal-system
```

## 后端 API 要求
Portal 前端需要以下 API 接口：

### 文章管理
- `GET /api/v1/articles` - 获取文章列表
- `GET /api/v1/articles/{id}` - 获取文章详情  
- `POST /api/v1/articles` - 创建文章
- `PUT /api/v1/articles/{id}` - 更新文章
- `DELETE /api/v1/articles/{id}` - 删除文章

### 栏目管理
- `GET /api/v1/categories` - 获取栏目树
- `POST /api/v1/categories` - 创建栏目
- `PUT /api/v1/categories/{id}` - 更新栏目
- `DELETE /api/v1/categories/{id}` - 删除栏目

### 标签管理  
- `GET /api/v1/tags` - 获取标签列表
- `POST /api/v1/tags` - 创建标签
- `PUT /api/v1/tags/{id}` - 更新标签
- `DELETE /api/v1/tags/{id}` - 删除标签

### 用户管理
- `GET /api/v1/users` - 获取用户列表
- `GET /api/v1/roles` - 获取角色列表
- `POST /api/v1/users` - 创建用户
- `PUT /api/v1/users/{id}` - 更新用户
- `DELETE /api/v1/users/{id}` - 删除用户
- `POST /api/v1/users/{id}/reset-password` - 重置密码

### 文件上传
- `POST /api/v1/files/upload` - 上传文件到 NAS

## 环境变量配置
| 变量名 | 默认值 | 说明 |
|--------|--------|------|
| VUE_APP_API_BASE_URL | http://localhost:8080/api/v1 | 后端 API 基础 URL |
| VUE_APP_UPLOAD_MAX_SIZE | 10485760 | 上传文件最大大小 (10MB) |
| VUE_APP_ALLOWED_FILE_TYPES | image/jpeg,image/png,image/gif,image/webp | 允许的文件类型 |

## 故障排除

### 常见问题
1. **页面空白**: 检查 API 地址配置是否正确
2. **文件上传失败**: 检查 NAS 目录权限和磁盘空间
3. **路由 404**: 确保 Web 服务器配置了 SPA 路由回退
4. **样式错乱**: 清除浏览器缓存或检查 Element Plus 版本

### 日志查看
- 浏览器开发者工具 Console 查看前端错误
- Web 服务器日志查看部署问题
- 后端服务日志查看 API 错误

## 维护和更新
- 定期 `git pull` 获取最新代码
- `npm update` 更新依赖包
- 监控 NAS 存储空间使用情况
- 备份重要数据和配置文件