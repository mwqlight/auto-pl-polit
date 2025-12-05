# Python语言学习驾驶舱系统

> 基于SpringBoot + Vue3的高科技Python学习平台，提供Java到Python代码转换、智能学习路径、实时代码执行等核心功能

## 🚀 项目简介

Python语言学习驾驶舱系统是一个现代化的全栈学习平台，采用前后端分离架构，融合了代码转换、智能学习、实时交互等先进功能。

### ✨ 核心特性

- **🔗 智能代码转换**：Java到Python代码自动转换引擎
- **📚 个性化学习**：基于AI的智能学习路径推荐
- **💻 实时代码执行**：在线Python代码编辑和执行环境
- **🎯 学习进度跟踪**：可视化学习进度和成绩分析
- **🔐 统一身份认证**：支持多种认证方式的用户管理系统

## 🏗️ 技术架构

### 后端技术栈
- **框架**：Spring Boot 3.2.0 + Java 17
- **数据库**：MySQL 8.0 + Redis 7.0
- **安全**：Spring Security + JWT
- **文档**：SpringDoc OpenAPI 3.0
- **代码转换**：Jython + 自定义转换引擎

### 前端技术栈
- **框架**：Vue 3.3 + TypeScript 5.0
- **构建工具**：Vite 4.4
- **UI组件**：Element Plus 2.3
- **状态管理**：Pinia 2.1
- **路由**：Vue Router 4.2
- **图表**：ECharts 5.4
- **代码编辑器**：Monaco Editor 0.40

## 📁 项目结构

```
auto-pl-polit/
├── python-learning-backend/     # 后端SpringBoot项目
│   ├── src/main/java/com/company/pythonlearning/
│   │   ├── config/              # 配置类
│   │   ├── controller/          # 控制器层
│   │   ├── service/             # 业务逻辑层
│   │   ├── repository/          # 数据访问层
│   │   ├── entity/              # 实体类
│   │   ├── dto/                 # 数据传输对象
│   │   └── util/                # 工具类
│   ├── src/main/resources/      # 配置文件
│   └── pom.xml                  # Maven配置
├── python-learning-frontend/    # 前端Vue3项目
│   ├── src/
│   │   ├── api/                 # API接口
│   │   ├── components/          # 组件库
│   │   ├── views/               # 页面视图
│   │   ├── router/              # 路由配置
│   │   ├── store/               # 状态管理
│   │   ├── types/               # TypeScript类型
│   │   └── utils/               # 工具函数
│   ├── public/                  # 静态资源
│   └── package.json             # 项目配置
├── start-all.sh                 # 完整启动脚本
├── start-backend.sh             # 后端启动脚本
├── start-frontend.sh            # 前端启动脚本
├── stop-all.sh                  # 停止脚本
└── README.md                    # 项目说明
```

## 🛠️ 环境要求

### 系统要求
- **操作系统**：macOS / Linux / Windows (WSL2)
- **内存**：≥ 8GB RAM
- **磁盘空间**：≥ 2GB 可用空间

### 软件依赖
- **Java**：17 或更高版本
- **Node.js**：16 或更高版本
- **npm**：7 或更高版本
- **Maven**：3.6 或更高版本
- **MySQL**：8.0 或更高版本
- **Redis**：7.0 或更高版本

## ⚡ 快速开始

### 1. 环境准备

确保以下服务已安装并运行：

```bash
# 安装MySQL (macOS)
brew install mysql
brew services start mysql

# 安装Redis (macOS)
brew install redis
brew services start redis

# 验证服务状态
mysql --version
redis-cli ping
```

### 2. 一键启动（推荐）

```bash
# 进入项目根目录
cd /usr/local/share/pl-space/auto-pl-polit

# 一键启动前后端服务
./start-all.sh
```

### 3. 分别启动

#### 启动后端服务
```bash
./start-backend.sh
```

#### 启动前端服务
```bash
./start-frontend.sh
```

### 4. 访问系统

- **前端应用**：http://localhost:3000
- **后端API**：http://localhost:8080
- **API文档**：http://localhost:8080/swagger-ui.html

## 📋 功能模块

### 🔐 用户管理模块
- 用户注册/登录/登出
- 个人信息管理
- 角色权限控制
- 多协议认证支持

### 📚 学习中心模块
- Python核心语法学习
- 智能学习路径推荐
- 学习进度跟踪
- 练习和测验系统

### 🔄 代码转换模块
- Java到Python代码转换
- 批量代码转换
- 转换历史记录
- 代码验证和优化

### 📊 数据可视化模块
- 学习进度图表
- 成绩统计分析
- 用户行为分析
- 实时数据监控

## 🔧 开发指南

### 后端开发

```bash
# 进入后端项目
cd python-learning-backend

# 编译项目
mvn clean compile

# 运行测试
mvn test

# 打包部署
mvn clean package
```

### 前端开发

```bash
# 进入前端项目
cd python-learning-frontend

# 安装依赖
npm install

# 开发模式
npm run dev

# 构建生产版本
npm run build

# 代码检查
npm run lint

# 类型检查
npm run type-check
```

## 📊 数据库配置

### 创建数据库

```sql
-- 创建数据库
CREATE DATABASE python_learning CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建用户（可选）
CREATE USER 'python_learning_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON python_learning.* TO 'python_learning_user'@'localhost';
FLUSH PRIVILEGES;
```

### 数据库连接配置

在 `application.yml` 中配置数据库连接：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/python_learning
    username: root
    password: root
```

## 🔒 安全配置

### JWT配置

```yaml
app:
  jwt:
    secret: your-secret-key
    expiration: 86400
    issuer: python-learning-backend
```

### 权限配置

系统采用RBAC权限模型，支持细粒度权限控制。

## 🚀 部署说明

### 开发环境部署

1. 确保所有依赖服务运行正常
2. 运行启动脚本
3. 访问系统进行测试

### 生产环境部署

详见 `DEPLOYMENT.md` 文档。

## 📞 技术支持

### 问题反馈

如遇到问题，请检查：

1. 所有依赖服务是否正常运行
2. 端口是否被占用（3000, 8080）
3. 数据库连接配置是否正确
4. 查看日志文件获取详细错误信息

### 日志文件

- **后端日志**：`python-learning-backend/backend.log`
- **前端日志**：`python-learning-frontend/frontend.log`

## 📄 许可证

本项目采用 MIT 许可证。

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

---

**开发团队**：Company Team  
**版本**：1.0.0  
**最后更新**：2024年
