<template>
  <footer class="footer-status">
    <div class="footer-content">
      <!-- 系统状态信息 -->
      <div class="status-info">
        <div class="status-item">
          <span class="label">后端服务:</span>
          <span class="value online">在线</span>
        </div>
        <div class="status-item">
          <span class="label">数据库:</span>
          <span class="value online">正常</span>
        </div>
        <div class="status-item">
          <span class="label">缓存:</span>
          <span class="value online">正常</span>
        </div>
        <div class="status-item">
          <span class="label">响应时间:</span>
          <span class="value">{{ responseTime }}ms</span>
        </div>
      </div>

      <!-- 系统信息 -->
      <div class="system-info">
        <span class="version">v1.0.0</span>
        <span class="separator">|</span>
        <span class="timestamp">{{ currentTime }}</span>
        <span class="separator">|</span>
        <span class="memory">内存: {{ memoryUsage }}MB</span>
      </div>

      <!-- 操作按钮 -->
      <div class="action-buttons">
        <el-button 
          size="small" 
          type="text" 
          class="action-btn"
          @click="showLogs"
        >
          <el-icon><View /></el-icon>
          查看日志
        </el-button>
        <el-button 
          size="small" 
          type="text" 
          class="action-btn"
          @click="refreshStatus"
        >
          <el-icon><Refresh /></el-icon>
          刷新状态
        </el-button>
      </div>
    </div>
  </footer>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { View, Refresh } from '@element-plus/icons-vue'

const responseTime = ref(128)
const currentTime = ref('')
const memoryUsage = ref(256)

let timer: number

const updateTime = () => {
  currentTime.value = new Date().toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
  })
}

const updateMemoryUsage = () => {
  // 模拟内存使用情况
  memoryUsage.value = Math.floor(256 + Math.random() * 50)
}

const showLogs = () => {
  console.log('查看系统日志')
}

const refreshStatus = () => {
  updateMemoryUsage()
  responseTime.value = Math.floor(100 + Math.random() * 50)
}

onMounted(() => {
  updateTime()
  updateMemoryUsage()
  
  timer = window.setInterval(() => {
    updateTime()
  }, 1000)
})

onUnmounted(() => {
  if (timer) {
    clearInterval(timer)
  }
})
</script>

<style lang="scss" scoped>
.footer-status {
  height: 40px;
  background: rgba(16, 18, 27, 0.8);
  backdrop-filter: blur(20px);
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  position: relative;
  z-index: 1000;
}

.footer-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
  padding: 0 24px;
  max-width: 1400px;
  margin: 0 auto;
  font-size: 12px;
}

.status-info {
  display: flex;
  align-items: center;
  gap: 24px;
  
  .status-item {
    display: flex;
    align-items: center;
    gap: 8px;
    
    .label {
      color: rgba(255, 255, 255, 0.6);
    }
    
    .value {
      font-weight: 600;
      
      &.online {
        color: #00ff88;
        
        &::before {
          content: '';
          display: inline-block;
          width: 6px;
          height: 6px;
          border-radius: 50%;
          background: #00ff88;
          margin-right: 6px;
          animation: pulse 2s infinite;
        }
      }
    }
    
    @keyframes pulse {
      0% { opacity: 1; }
      50% { opacity: 0.5; }
      100% { opacity: 1; }
    }
  }
}

.system-info {
  display: flex;
  align-items: center;
  gap: 12px;
  color: rgba(255, 255, 255, 0.6);
  
  .separator {
    color: rgba(255, 255, 255, 0.3);
  }
  
  .version {
    color: #00d4ff;
    font-weight: 600;
  }
  
  .timestamp {
    font-family: 'Monaco', 'Consolas', monospace;
  }
  
  .memory {
    font-family: 'Monaco', 'Consolas', monospace;
  }
}

.action-buttons {
  display: flex;
  align-items: center;
  gap: 8px;
  
  .action-btn {
    color: rgba(255, 255, 255, 0.7);
    padding: 4px 8px;
    
    &:hover {
      color: #00d4ff;
      background: rgba(0, 212, 255, 0.1);
    }
    
    .el-icon {
      font-size: 14px;
      margin-right: 4px;
    }
  }
}
</style>