<template>
  <div class="dashboard-container">
    <!-- 顶部统计卡片 -->
    <div class="stats-grid">
      <el-card class="stat-card" shadow="hover">
        <div class="stat-content">
          <div class="stat-icon learning-icon">
            <i class="el-icon-school"></i>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.totalLearningTime }}小时</div>
            <div class="stat-label">总学习时长</div>
          </div>
        </div>
      </el-card>
      
      <el-card class="stat-card" shadow="hover">
        <div class="stat-content">
          <div class="stat-icon module-icon">
            <i class="el-icon-notebook-2"></i>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.completedModules }}个</div>
            <div class="stat-label">完成模块</div>
          </div>
        </div>
      </el-card>
      
      <el-card class="stat-card" shadow="hover">
        <div class="stat-content">
          <div class="stat-icon conversion-icon">
            <i class="el-icon-copy-document"></i>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.totalConversions }}次</div>
            <div class="stat-label">代码转换</div>
          </div>
        </div>
      </el-card>
      
      <el-card class="stat-card" shadow="hover">
        <div class="stat-content">
          <div class="stat-icon level-icon">
            <i class="el-icon-trophy"></i>
          </div>
          <div class="stat-info">
            <div class="stat-value">Lv.{{ stats.level }}</div>
            <div class="stat-label">当前等级</div>
          </div>
        </div>
      </el-card>
    </div>

    <!-- 主要内容区域 -->
    <div class="main-content">
      <!-- 左侧：学习进度 -->
      <div class="left-panel">
        <el-card class="progress-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span class="card-title">学习进度</span>
              <el-button type="text" @click="goToLearning">查看全部</el-button>
            </div>
          </template>
          
          <div class="progress-list">
            <div 
              v-for="progress in learningProgress" 
              :key="progress.moduleId" 
              class="progress-item"
            >
              <div class="progress-info">
                <div class="module-name">{{ progress.moduleTitle }}</div>
                <div class="progress-percentage">{{ progress.progressPercentage }}%</div>
              </div>
              <el-progress 
                :percentage="progress.progressPercentage" 
                :show-text="false"
                :stroke-width="6"
                class="progress-bar"
              />
            </div>
          </div>
        </el-card>

        <!-- 最近转换记录 -->
        <el-card class="conversion-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span class="card-title">最近转换</span>
              <el-button type="text" @click="goToConverter">查看全部</el-button>
            </div>
          </template>
          
          <div class="conversion-list">
            <div 
              v-for="record in recentConversions" 
              :key="record.id" 
              class="conversion-item"
            >
              <div class="conversion-info">
                <div class="conversion-title">{{ record.fileName || '未命名文件' }}</div>
                <div class="conversion-time">{{ formatTime(record.timestamp) }}</div>
              </div>
              <div class="conversion-score">
                <el-tag :type="getScoreType(record.conversionScore)">
                  {{ (record.conversionScore * 100).toFixed(1) }}%
                </el-tag>
              </div>
            </div>
          </div>
        </el-card>
      </div>

      <!-- 右侧：推荐内容 -->
      <div class="right-panel">
        <!-- 推荐学习模块 -->
        <el-card class="recommendation-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span class="card-title">推荐学习</span>
            </div>
          </template>
          
          <div class="recommendation-list">
            <div 
              v-for="module in recommendedModules" 
              :key="module.id" 
              class="recommendation-item"
              @click="startModule(module.id)"
            >
              <div class="module-icon">
                <i :class="getModuleIcon(module.moduleType)"></i>
              </div>
              <div class="module-info">
                <div class="module-title">{{ module.title }}</div>
                <div class="module-desc">{{ module.description }}</div>
                <div class="module-meta">
                  <span class="difficulty">{{ module.difficultyLevel }}</span>
                  <span class="time">{{ module.estimatedTime }}分钟</span>
                </div>
              </div>
            </div>
          </div>
        </el-card>

        <!-- 快速操作 -->
        <el-card class="quick-actions-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span class="card-title">快速操作</span>
            </div>
          </template>
          
          <div class="quick-actions">
            <el-button 
              type="primary" 
              icon="el-icon-copy-document" 
              class="quick-btn"
              @click="goToConverter"
            >
              代码转换
            </el-button>
            <el-button 
              type="success" 
              icon="el-icon-school" 
              class="quick-btn"
              @click="goToLearning"
            >
              开始学习
            </el-button>
            <el-button 
              type="info" 
              icon="el-icon-notebook-2" 
              class="quick-btn"
              @click="goToKnowledge"
            >
              知识库
            </el-button>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { formatTime } from '@/utils'

const router = useRouter()

// 统计数据
const stats = ref({
  totalLearningTime: 0,
  completedModules: 0,
  totalConversions: 0,
  level: 1
})

// 学习进度
const learningProgress = ref([
  { moduleId: 1, moduleTitle: '基础语法', progressPercentage: 75 },
  { moduleId: 2, moduleTitle: '面向对象编程', progressPercentage: 45 },
  { moduleId: 3, moduleTitle: '函数式编程', progressPercentage: 20 }
])

// 最近转换记录
const recentConversions = ref([
  { id: 1, fileName: 'UserService.java', timestamp: new Date(Date.now() - 3600000), conversionScore: 0.85 },
  { id: 2, fileName: 'Calculator.java', timestamp: new Date(Date.now() - 7200000), conversionScore: 0.92 },
  { id: 3, fileName: 'Main.java', timestamp: new Date(Date.now() - 86400000), conversionScore: 0.78 }
])

// 推荐学习模块
const recommendedModules = ref([
  { id: 1, title: '异常处理', description: '学习Python中的异常处理机制', moduleType: 'EXCEPTIONS', difficultyLevel: '中级', estimatedTime: 45 },
  { id: 2, title: '数据结构', description: '掌握Python常用数据结构', moduleType: 'DATA_STRUCTURES', difficultyLevel: '中级', estimatedTime: 60 },
  { id: 3, title: '算法基础', description: '学习基础算法实现', moduleType: 'ALGORITHMS', difficultyLevel: '高级', estimatedTime: 90 }
])

// 获取模块图标
const getModuleIcon = (type: string) => {
  const icons = {
    BASIC_SYNTAX: 'el-icon-s-promotion',
    OOP: 'el-icon-c-scale-to-original',
    FUNCTIONAL: 'el-icon-s-operation',
    EXCEPTIONS: 'el-icon-warning-outline',
    DATA_STRUCTURES: 'el-icon-s-grid',
    ALGORITHMS: 'el-icon-cpu'
  }
  return icons[type as keyof typeof icons] || 'el-icon-question'
}

// 获取分数类型
const getScoreType = (score: number) => {
  if (score >= 0.9) return 'success'
  if (score >= 0.7) return 'warning'
  return 'danger'
}

// 导航方法
const goToLearning = () => {
  router.push('/learning')
}

const goToConverter = () => {
  router.push('/converter')
}

const goToKnowledge = () => {
  router.push('/knowledge')
}

const startModule = (moduleId: number) => {
  router.push(`/learning/module/${moduleId}`)
}

// 初始化数据
onMounted(() => {
  // 模拟加载数据
  setTimeout(() => {
    stats.value = {
      totalLearningTime: 156,
      completedModules: 8,
      totalConversions: 42,
      level: 3
    }
  }, 500)
})
</script>

<style scoped lang="scss">
.dashboard-container {
  padding: 20px;
  min-height: calc(100vh - 120px);
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 30px;
}

.stat-card {
  border: none;
  border-radius: 12px;
  background: linear-gradient(135deg, var(--el-color-primary-light-9), var(--el-color-primary-light-7));
  
  &:nth-child(2) {
    background: linear-gradient(135deg, var(--el-color-success-light-9), var(--el-color-success-light-7));
  }
  
  &:nth-child(3) {
    background: linear-gradient(135deg, var(--el-color-warning-light-9), var(--el-color-warning-light-7));
  }
  
  &:nth-child(4) {
    background: linear-gradient(135deg, var(--el-color-danger-light-9), var(--el-color-danger-light-7));
  }
}

.stat-content {
  display: flex;
  align-items: center;
  padding: 20px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.2);
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 15px;
  
  i {
    font-size: 28px;
    color: white;
  }
}

.stat-info {
  flex: 1;
  
  .stat-value {
    font-size: 24px;
    font-weight: bold;
    color: white;
    margin-bottom: 5px;
  }
  
  .stat-label {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.8);
  }
}

.main-content {
  display: grid;
  grid-template-columns: 1fr 350px;
  gap: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  
  .card-title {
    font-size: 16px;
    font-weight: 600;
    color: var(--el-text-color-primary);
  }
}

.progress-item {
  margin-bottom: 15px;
  
  &:last-child {
    margin-bottom: 0;
  }
  
  .progress-info {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 8px;
    
    .module-name {
      font-size: 14px;
      color: var(--el-text-color-primary);
    }
    
    .progress-percentage {
      font-size: 12px;
      color: var(--el-text-color-secondary);
    }
  }
  
  .progress-bar {
    margin-top: 5px;
  }
}

.conversion-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid var(--el-border-color-light);
  
  &:last-child {
    border-bottom: none;
  }
  
  .conversion-info {
    flex: 1;
    
    .conversion-title {
      font-size: 14px;
      color: var(--el-text-color-primary);
      margin-bottom: 4px;
    }
    
    .conversion-time {
      font-size: 12px;
      color: var(--el-text-color-secondary);
    }
  }
}

.recommendation-item {
  display: flex;
  align-items: center;
  padding: 15px;
  margin-bottom: 10px;
  border-radius: 8px;
  background: var(--el-fill-color-light);
  cursor: pointer;
  transition: all 0.3s ease;
  
  &:hover {
    background: var(--el-color-primary-light-9);
    transform: translateX(5px);
  }
  
  .module-icon {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    background: var(--el-color-primary);
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 15px;
    
    i {
      font-size: 18px;
      color: white;
    }
  }
  
  .module-info {
    flex: 1;
    
    .module-title {
      font-size: 14px;
      font-weight: 600;
      color: var(--el-text-color-primary);
      margin-bottom: 4px;
    }
    
    .module-desc {
      font-size: 12px;
      color: var(--el-text-color-secondary);
      margin-bottom: 6px;
      line-height: 1.4;
    }
    
    .module-meta {
      display: flex;
      gap: 10px;
      
      span {
        font-size: 11px;
        padding: 2px 6px;
        border-radius: 4px;
        
        &.difficulty {
          background: var(--el-color-warning-light-8);
          color: var(--el-color-warning);
        }
        
        &.time {
          background: var(--el-color-info-light-8);
          color: var(--el-color-info);
        }
      }
    }
  }
}

.quick-actions {
  display: flex;
  flex-direction: column;
  gap: 10px;
  
  .quick-btn {
    width: 100%;
    height: 45px;
    font-size: 14px;
    justify-content: flex-start;
  }
}

@media (max-width: 1200px) {
  .stats-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .main-content {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .stats-grid {
    grid-template-columns: 1fr;
  }
}
</style>