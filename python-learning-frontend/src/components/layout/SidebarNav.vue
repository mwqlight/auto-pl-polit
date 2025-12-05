<template>
  <aside class="sidebar-nav">
    <div class="sidebar-content">
      <!-- 模块导航 -->
      <div class="nav-modules">
        <div 
          v-for="module in modules" 
          :key="module.id"
          class="module-item"
          :class="{ active: activeModule === module.id }"
          @click="setActiveModule(module.id)"
        >
          <div class="module-icon">
            <el-icon><component :is="module.icon" /></el-icon>
          </div>
          <div class="module-info">
            <h4 class="module-title">{{ module.title }}</h4>
            <p class="module-desc">{{ module.description }}</p>
          </div>
          <div class="module-progress">
            <el-progress 
              :percentage="module.progress" 
              :stroke-width="4"
              :show-text="false"
              :color="getProgressColor(module.progress)"
            />
            <span class="progress-text">{{ module.progress }}%</span>
          </div>
        </div>
      </div>

      <!-- 快速操作 -->
      <div class="quick-actions">
        <h3 class="section-title">快速操作</h3>
        <div class="action-buttons">
          <el-button 
            type="primary" 
            size="small" 
            class="action-btn"
            @click="quickConvert"
          >
            <el-icon><Magic /></el-icon>
            快速转换
          </el-button>
          <el-button 
            type="success" 
            size="small" 
            class="action-btn"
            @click="startLearning"
          >
            <el-icon><PlayCircle /></el-icon>
            开始学习
          </el-button>
        </div>
      </div>

      <!-- 学习统计 -->
      <div class="learning-stats">
        <h3 class="section-title">学习统计</h3>
        <div class="stats-grid">
          <div class="stat-item">
            <div class="stat-value">{{ stats.totalLessons }}</div>
            <div class="stat-label">总课程</div>
          </div>
          <div class="stat-item">
            <div class="stat-value">{{ stats.completedLessons }}</div>
            <div class="stat-label">已完成</div>
          </div>
          <div class="stat-item">
            <div class="stat-value">{{ stats.conversionCount }}</div>
            <div class="stat-label">转换次数</div>
          </div>
        </div>
      </div>
    </div>
  </aside>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import {
  Magic,
  PlayCircle,
  Code,
  School,
  DataBoard
} from '@element-plus/icons-vue'

const activeModule = ref('converter')

const modules = ref([
  {
    id: 'converter',
    title: '代码转换工作站',
    description: 'Java到Python智能转换',
    icon: Code,
    progress: 75
  },
  {
    id: 'learning',
    title: '语法学习中心',
    description: 'Python核心语法学习',
    icon: School,
    progress: 45
  },
  {
    id: 'knowledge',
    title: '应用知识库',
    description: '全场景应用案例',
    icon: DataBoard,
    progress: 30
  }
])

const stats = ref({
  totalLessons: 156,
  completedLessons: 89,
  conversionCount: 234
})

const setActiveModule = (moduleId: string) => {
  activeModule.value = moduleId
}

const getProgressColor = (progress: number) => {
  if (progress >= 80) return '#00ff88'
  if (progress >= 50) return '#00d4ff'
  if (progress >= 30) return '#ffaa00'
  return '#ff4444'
}

const quickConvert = () => {
  // 快速转换逻辑
  console.log('快速转换')
}

const startLearning = () => {
  // 开始学习逻辑
  console.log('开始学习')
}
</script>

<style lang="scss" scoped>
.sidebar-nav {
  width: 280px;
  background: rgba(16, 18, 27, 0.6);
  backdrop-filter: blur(20px);
  border-right: 1px solid rgba(255, 255, 255, 0.1);
  overflow-y: auto;
}

.sidebar-content {
  padding: 24px;
  height: 100%;
  display: flex;
  flex-direction: column;
  gap: 32px;
}

.nav-modules {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.module-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 16px;
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.05);
  cursor: pointer;
  transition: all 0.3s ease;
  border: 1px solid transparent;
  
  &:hover {
    background: rgba(255, 255, 255, 0.1);
    transform: translateY(-2px);
  }
  
  &.active {
    background: linear-gradient(135deg, rgba(0, 212, 255, 0.15) 0%, rgba(0, 153, 255, 0.15) 100%);
    border-color: rgba(0, 212, 255, 0.3);
    box-shadow: 0 4px 20px rgba(0, 212, 255, 0.2);
  }
  
  .module-icon {
    .el-icon {
      font-size: 24px;
      color: #00d4ff;
    }
  }
  
  .module-info {
    flex: 1;
    
    .module-title {
      font-size: 14px;
      font-weight: 600;
      color: #fff;
      margin: 0 0 4px 0;
    }
    
    .module-desc {
      font-size: 12px;
      color: rgba(255, 255, 255, 0.6);
      margin: 0;
    }
  }
  
  .module-progress {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 4px;
    min-width: 60px;
    
    .progress-text {
      font-size: 11px;
      color: rgba(255, 255, 255, 0.7);
    }
  }
}

.quick-actions {
  .section-title {
    font-size: 14px;
    font-weight: 600;
    color: #fff;
    margin: 0 0 16px 0;
  }
  
  .action-buttons {
    display: flex;
    flex-direction: column;
    gap: 8px;
    
    .action-btn {
      width: 100%;
      justify-content: flex-start;
      
      .el-icon {
        margin-right: 8px;
      }
    }
  }
}

.learning-stats {
  .section-title {
    font-size: 14px;
    font-weight: 600;
    color: #fff;
    margin: 0 0 16px 0;
  }
  
  .stats-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 12px;
    
    .stat-item {
      text-align: center;
      padding: 12px;
      background: rgba(255, 255, 255, 0.05);
      border-radius: 8px;
      
      .stat-value {
        font-size: 20px;
        font-weight: 700;
        color: #00d4ff;
        margin-bottom: 4px;
      }
      
      .stat-label {
        font-size: 11px;
        color: rgba(255, 255, 255, 0.6);
      }
    }
  }
}

// 滚动条样式
.sidebar-nav::-webkit-scrollbar {
  width: 4px;
}

.sidebar-nav::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.1);
}

.sidebar-nav::-webkit-scrollbar-thumb {
  background: rgba(0, 212, 255, 0.5);
  border-radius: 2px;
}
</style>