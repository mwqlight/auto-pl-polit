#!/bin/bash

# Python语言学习驾驶舱系统 - 停止脚本

echo "==========================================="
echo "Python语言学习驾驶舱系统 - 停止服务"
echo "==========================================="

# 检查PID文件是否存在
if [ ! -f ".pids" ]; then
    echo "❌ 未找到运行中的服务进程"
    echo "请检查服务是否正在运行"
    exit 1
fi

# 读取PID
PIDS=$(cat .pids)
BACKEND_PID=$(echo $PIDS | cut -d' ' -f1)
FRONTEND_PID=$(echo $PIDS | cut -d' ' -f2)

# 停止服务
echo "🛑 正在停止服务..."

# 停止前端服务
if kill -0 $FRONTEND_PID 2>/dev/null; then
    echo "停止前端服务 (PID: $FRONTEND_PID)..."
    kill $FRONTEND_PID 2>/dev/null
    sleep 2
fi

# 停止后端服务
if kill -0 $BACKEND_PID 2>/dev/null; then
    echo "停止后端服务 (PID: $BACKEND_PID)..."
    kill $BACKEND_PID 2>/dev/null
    sleep 3
fi

# 清理PID文件
rm -f .pids

echo "✅ 所有服务已停止"
echo "==========================================="