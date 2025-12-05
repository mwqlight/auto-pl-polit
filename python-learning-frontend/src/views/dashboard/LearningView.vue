<template>
  <div class="learning-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <h1 class="page-title">Python学习中心</h1>
      <p class="page-subtitle">系统学习Python编程语言，从基础到精通</p>
    </div>

    <!-- 学习模块导航 -->
    <div class="learning-nav">
      <el-menu 
        :default-active="activeModule" 
        mode="horizontal" 
        class="learning-menu"
        @select="handleModuleSelect"
      >
        <el-menu-item index="basic-syntax">
          <i class="el-icon-s-promotion"></i>
          <span>基础语法</span>
        </el-menu-item>
        <el-menu-item index="oop">
          <i class="el-icon-c-scale-to-original"></i>
          <span>面向对象</span>
        </el-menu-item>
        <el-menu-item index="functional">
          <i class="el-icon-s-operation"></i>
          <span>函数式编程</span>
        </el-menu-item>
        <el-menu-item index="exceptions">
          <i class="el-icon-warning-outline"></i>
          <span>异常处理</span>
        </el-menu-item>
      </el-menu>
    </div>

    <!-- 学习内容区域 -->
    <div class="learning-content">
      <router-view />
    </div>

    <!-- 学习进度统计 -->
    <div class="learning-stats">
      <el-card shadow="hover">
        <template #header>
          <span>学习进度统计</span>
        </template>
        
        <div class="stats-grid">
          <div class="stat-item">
            <div class="stat-value">{{ overallProgress }}%</div>
            <div class="stat-label">总体进度</div>
          </div>
          <div class="stat-item">
            <div class="stat-value">{{ completedModules }}</div>
            <div class="stat-label">完成模块</div>
          </div>
          <div class="stat-item">
            <div class="stat-value">{{ totalLearningTime }}</div>
            <div class="stat-label">学习时长(小时)</div>
          </div>
          <div class="stat-item">
            <div class="stat-value">Lv.{{ currentLevel }}</div>
            <div class="stat-label">当前等级</div>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

// 响应式数据
const activeModule = ref('basic-syntax')

// 学习统计数据
const overallProgress = ref(45)
const completedModules = ref(3)
const totalLearningTime = ref(156)
const currentLevel = ref(3)

// 处理模块选择
const handleModuleSelect = (index: string) => {
  activeModule.value = index
  router.push(`/learning/${index}`)
}

// 初始化
onMounted(() => {
  // 根据当前路由设置激活的模块
  const path = route.path
  if (path.includes('basic-syntax')) {
    activeModule.value = 'basic-syntax'
  } else if (path.includes('oop')) {
    activeModule.value = 'oop'
  } else if (path.includes('functional')) {
    activeModule.value = 'functional'
  } else if (path.includes('exceptions')) {
    activeModule.value = 'exceptions'
  }
})
</script>

<style scoped lang="scss">
.learning-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.page-header {
  text-align: center;
  margin-bottom: 30px;
  
  .page-title {
    font-size: 28px;
    font-weight: 600;
    color: var(--el-text-color-primary);
    margin-bottom: 8px;
    background: linear-gradient(135deg, var(--el-color-primary), var(--el-color-primary-light-3));
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
  }
  
  .page-subtitle {
    font-size: 16px;
    color: var(--el-text-color-secondary);
    margin: 0;
  }
}

.learning-nav {
  margin-bottom: 30px;
  
  .learning-menu {
    justify-content: center;
    border: none;
    background: transparent;
    
    :deep(.el-menu-item) {
      margin: 0 10px;
      border-radius: 8px;
      transition: all 0.3s ease;
      
      &:hover {
        background: var(--el-color-primary-light-9);
        transform: translateY(-2px);
      }
      
      &.is-active {
        background: linear-gradient(135deg, var(--el-color-primary), var(--el-color-primary-light-3));
        color: white;
        
        i {
          color: white;
        }
      }
      
      i {
        margin-right: 8px;
        color: var(--el-text-color-secondary);
      }
    }
  }
}

.learning-content {
  min-height: 500px;
  margin-bottom: 30px;
}

.learning-stats {
  .stats-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20px;
    
    .stat-item {
      text-align: center;
      padding: 20px;
      background: var(--el-fill-color-light);
      border-radius: 8px;
      
      .stat-value {
        font-size: 32px;
        font-weight: bold;
        color: var(--el-color-primary);
        margin-bottom: 8px;
      }
      
      .stat-label {
        font-size: 14px;
        color: var(--el-text-color-secondary);
      }
    }
  }
}

@media (max-width: 768px) {
  .learning-container {
    padding: 10px;
  }
  
  .learning-nav {
    .learning-menu {
      :deep(.el-menu-item) {
        margin: 0 5px;
        font-size: 12px;
        
        i {
          margin-right: 4px;
        }
      }
    }
  }
  
  .stats-grid {
    grid-template-columns: repeat(2, 1fr) !important;
  }
}

@media (max-width: 480px) {
  .stats-grid {
    grid-template-columns: 1fr !important;
  }
}
</style>