#!/bin/bash

# Python语言学习驾驶舱系统 - 前端启动脚本

echo "==========================================="
echo "Python语言学习驾驶舱系统 - 前端服务启动"
echo "==========================================="

# 检查Node.js版本
if ! command -v node &> /dev/null; then
    echo "❌ Node.js未安装，请先安装Node.js"
    exit 1
fi

echo "✅ Node.js版本: $(node --version)"

# 检查npm版本
if ! command -v npm &> /dev/null; then
    echo "❌ npm未安装，请先安装npm"
    exit 1
fi

echo "✅ npm版本: $(npm --version)"

# 进入前端项目目录
cd python-learning-frontend

# 检查node_modules是否存在
if [ ! -d "node_modules" ]; then
    echo "📦 安装前端依赖..."
    npm install
    
    if [ $? -ne 0 ]; then
        echo "❌ 依赖安装失败"
        exit 1
    fi
    echo "✅ 依赖安装完成"
else
    echo "✅ 依赖已存在，跳过安装"
fi

# 启动开发服务器
echo "🚀 启动前端开发服务器..."
echo "前端地址: http://localhost:3000"
echo "API代理: http://localhost:8080"
echo "==========================================="

npm run dev