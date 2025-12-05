<template>
  <header class="header-nav">
    <div class="header-content">
      <!-- Logo和标题 -->
      <div class="logo-section">
        <div class="logo">
          <div class="logo-icon">
            <svg width="32" height="32" viewBox="0 0 32 32" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M16 2L20 12L30 12L22 18L26 28L16 22L6 28L10 18L2 12L12 12L16 2Z" fill="url(#gradient)" />
              <defs>
                <linearGradient id="gradient" x1="0%" y1="0%" x2="100%" y2="100%">
                  <stop offset="0%" stop-color="#00d4ff" />
                  <stop offset="100%" stop-color="#0099ff" />
                </linearGradient>
              </defs>
            </svg>
          </div>
          <h1 class="app-title">Python驾驶舱</h1>
        </div>
      </div>

      <!-- 导航菜单 -->
      <nav class="nav-menu">
        <router-link 
          v-for="item in navItems" 
          :key="item.path" 
          :to="item.path"
          class="nav-item"
          :class="{ active: $route.path === item.path }"
        >
          <el-icon><component :is="item.icon" /></el-icon>
          <span>{{ item.name }}</span>
        </router-link>
      </nav>

      <!-- 用户操作区 -->
      <div class="user-section">
        <div class="system-status">
          <el-tooltip content="后端服务正常" placement="bottom">
            <div class="status-indicator active"></div>
          </el-tooltip>
          <span class="status-text">系统在线</span>
        </div>
        
        <el-dropdown trigger="click">
          <div class="user-avatar">
            <el-avatar :size="36" :src="userAvatar" />
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>个人中心</el-dropdown-item>
              <el-dropdown-item>学习记录</el-dropdown-item>
              <el-dropdown-item divided>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import {
  Monitor,
  School,
  DataAnalysis,
  Setting
} from '@element-plus/icons-vue'

const route = useRoute()

const navItems = ref([
  { name: '代码转换', path: '/converter', icon: Monitor },
  { name: '语法学习', path: '/learning', icon: School },
  { name: '知识库', path: '/knowledge', icon: DataAnalysis },
  { name: '系统设置', path: '/settings', icon: Setting }
])

const userAvatar = ref('https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png')
</script>

<style lang="scss" scoped>
.header-nav {
  height: 60px;
  background: rgba(16, 18, 27, 0.8);
  backdrop-filter: blur(20px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  position: relative;
  z-index: 1000;
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
  padding: 0 24px;
  max-width: 1400px;
  margin: 0 auto;
}

.logo-section {
  .logo {
    display: flex;
    align-items: center;
    gap: 12px;
    
    .logo-icon {
      display: flex;
      align-items: center;
    }
    
    .app-title {
      font-size: 20px;
      font-weight: 700;
      background: linear-gradient(135deg, #00d4ff 0%, #0099ff 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
      margin: 0;
    }
  }
}

.nav-menu {
  display: flex;
  gap: 8px;
  
  .nav-item {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 8px 16px;
    border-radius: 8px;
    color: rgba(255, 255, 255, 0.7);
    text-decoration: none;
    transition: all 0.3s ease;
    
    &:hover {
      background: rgba(255, 255, 255, 0.1);
      color: rgba(255, 255, 255, 0.9);
    }
    
    &.active {
      background: linear-gradient(135deg, rgba(0, 212, 255, 0.2) 0%, rgba(0, 153, 255, 0.2) 100%);
      color: #00d4ff;
      border: 1px solid rgba(0, 212, 255, 0.3);
    }
    
    .el-icon {
      font-size: 18px;
    }
    
    span {
      font-weight: 500;
    }
  }
}

.user-section {
  display: flex;
  align-items: center;
  gap: 16px;
  
  .system-status {
    display: flex;
    align-items: center;
    gap: 8px;
    color: rgba(255, 255, 255, 0.7);
    
    .status-indicator {
      width: 8px;
      height: 8px;
      border-radius: 50%;
      background: #00ff88;
      animation: pulse 2s infinite;
      
      &.active {
        background: #00ff88;
      }
      
      @keyframes pulse {
        0% { opacity: 1; }
        50% { opacity: 0.5; }
        100% { opacity: 1; }
      }
    }
    
    .status-text {
      font-size: 12px;
    }
  }
  
  .user-avatar {
    cursor: pointer;
    transition: transform 0.3s ease;
    
    &:hover {
      transform: scale(1.05);
    }
  }
}
</style>