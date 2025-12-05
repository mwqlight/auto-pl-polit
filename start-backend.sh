#!/bin/bash

# Python语言学习驾驶舱系统 - 后端启动脚本

echo "==========================================="
echo "Python语言学习驾驶舱系统 - 后端服务启动"
echo "==========================================="

# 检查Java版本
if ! command -v java &> /dev/null; then
    echo "❌ Java未安装，请先安装Java 17或更高版本"
    exit 1
fi

echo "✅ Java版本: $(java --version | head -n1)"

# 检查Maven版本
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven未安装，请先安装Maven"
    exit 1
fi

echo "✅ Maven版本: $(mvn --version | head -n1)"

# 检查MySQL服务
if ! pgrep -x "mysqld" > /dev/null; then
    echo "⚠️  MySQL服务未运行，请确保MySQL已启动"
    echo "   启动命令: brew services start mysql"
fi

# 检查Redis服务
if ! pgrep -x "redis-server" > /dev/null; then
    echo "⚠️  Redis服务未运行，请确保Redis已启动"
    echo "   启动命令: brew services start redis"
fi

# 进入后端项目目录
cd python-learning-backend

# 清理并编译项目
echo "🔨 编译后端项目..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "❌ 编译失败"
    exit 1
fi

echo "✅ 编译成功"

# 启动Spring Boot应用
echo "🚀 启动后端服务..."
echo "服务地址: http://localhost:8080"
echo "API文档: http://localhost:8080/swagger-ui.html"
echo "数据库: MySQL (localhost:3306)"
echo "缓存: Redis (localhost:6379)"
echo "==========================================="

mvn spring-boot:run