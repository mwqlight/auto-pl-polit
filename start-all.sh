#!/bin/bash

# Python语言学习驾驶舱系统 - 完整启动脚本

echo "==========================================="
echo "Python语言学习驾驶舱系统 - 完整启动"
echo "==========================================="

# 检查依赖环境
echo "🔍 检查系统环境..."

# 检查Java
if ! command -v java &> /dev/null; then
    echo "❌ Java未安装，请先安装Java 17或更高版本"
    exit 1
fi
echo "✅ Java版本: $(java --version | head -n1)"

# 检查Maven
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven未安装，请先安装Maven"
    exit 1
fi
echo "✅ Maven版本: $(mvn --version | head -n1)"

# 检查Node.js
if ! command -v node &> /dev/null; then
    echo "❌ Node.js未安装，请先安装Node.js"
    exit 1
fi
echo "✅ Node.js版本: $(node --version)"

# 检查npm
if ! command -v npm &> /dev/null; then
    echo "❌ npm未安装，请先安装npm"
    exit 1
fi
echo "✅ npm版本: $(npm --version)"

# 检查服务状态
echo "🔍 检查服务状态..."

# 检查MySQL
if ! pgrep -x "mysqld" > /dev/null; then
    echo "⚠️  MySQL服务未运行，尝试启动..."
    brew services start mysql 2>/dev/null || echo "❌ 无法启动MySQL，请手动启动"
fi

# 检查Redis
if ! pgrep -x "redis-server" > /dev/null; then
    echo "⚠️  Redis服务未运行，尝试启动..."
    brew services start redis 2>/dev/null || echo "❌ 无法启动Redis，请手动启动"
fi

echo ""
echo "🚀 开始启动前后端服务..."
echo ""

# 启动后端服务
echo "🔨 启动后端服务..."
cd python-learning-backend
mvn clean compile > /dev/null 2>&1

if [ $? -ne 0 ]; then
    echo "❌ 后端编译失败"
    exit 1
fi

# 后台启动后端服务
mvn spring-boot:run > backend.log 2>&1 &
BACKEND_PID=$!

# 等待后端启动
echo "⏳ 等待后端服务启动..."
sleep 10

# 检查后端是否启动成功
if curl -s http://localhost:8080/actuator/health > /dev/null 2>&1; then
    echo "✅ 后端服务启动成功 (PID: $BACKEND_PID)"
else
    echo "❌ 后端服务启动失败，请检查backend.log文件"
    kill $BACKEND_PID 2>/dev/null
    exit 1
fi

# 启动前端服务
echo "🎨 启动前端服务..."
cd ../python-learning-frontend

# 检查并安装前端依赖
if [ ! -d "node_modules" ]; then
    echo "📦 安装前端依赖..."
    npm install > /dev/null 2>&1
    
    if [ $? -ne 0 ]; then
        echo "❌ 前端依赖安装失败"
        kill $BACKEND_PID 2>/dev/null
        exit 1
    fi
    echo "✅ 前端依赖安装完成"
fi

# 后台启动前端服务
npm run dev > frontend.log 2>&1 &
FRONTEND_PID=$!

# 等待前端启动
echo "⏳ 等待前端服务启动..."
sleep 8

# 检查前端是否启动成功
if curl -s http://localhost:3000 > /dev/null 2>&1; then
    echo "✅ 前端服务启动成功 (PID: $FRONTEND_PID)"
else
    echo "❌ 前端服务启动失败，请检查frontend.log文件"
    kill $BACKEND_PID 2>/dev/null
    kill $FRONTEND_PID 2>/dev/null
    exit 1
fi

echo ""
echo "🎉 系统启动完成！"
echo "==========================================="
echo "前端地址: http://localhost:3000"
echo "后端地址: http://localhost:8080"
echo "API文档: http://localhost:8080/swagger-ui.html"
echo ""
echo "后端日志: python-learning-backend/backend.log"
echo "前端日志: python-learning-frontend/frontend.log"
echo ""
echo "停止服务命令: kill $BACKEND_PID $FRONTEND_PID"
echo "==========================================="

# 保存PID到文件
echo "$BACKEND_PID $FRONTEND_PID" > .pids

echo "系统正在运行中..."
echo "按 Ctrl+C 停止所有服务"

# 等待用户中断
trap 'echo ""; echo "正在停止服务..."; kill $BACKEND_PID $FRONTEND_PID 2>/dev/null; rm -f .pids; echo "服务已停止"; exit 0' INT

wait