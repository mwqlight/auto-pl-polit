<template>
  <div class="learning-center-view">
    <!-- 页面标题 -->
    <div class="page-header">
      <h1 class="page-title">
        <el-icon><Reading /></el-icon>
        Python 核心语法学习中心
      </h1>
      <p class="page-desc">系统化学习Python编程语言，从基础到高级语法</p>
    </div>

    <!-- 学习中心主体 -->
    <div class="learning-container">
      <!-- 左侧导航 -->
      <div class="sidebar-nav">
        <div class="nav-section">
          <h3>基础语法</h3>
          <el-menu 
            :default-active="activeChapter"
            @select="handleChapterSelect"
            background-color="transparent"
            text-color="rgba(255,255,255,0.7)"
            active-text-color="#00d4ff"
          >
            <el-menu-item index="1">
              <el-icon><Document /></el-icon>
              <span>变量和数据类型</span>
            </el-menu-item>
            <el-menu-item index="2">
              <el-icon><Document /></el-icon>
              <span>运算符和表达式</span>
            </el-menu-item>
            <el-menu-item index="3">
              <el-icon><Document /></el-icon>
              <span>控制流程</span>
            </el-menu-item>
            <el-menu-item index="4">
              <el-icon><Document /></el-icon>
              <span>函数定义</span>
            </el-menu-item>
          </el-menu>
        </div>

        <div class="nav-section">
          <h3>高级特性</h3>
          <el-menu 
            :default-active="activeChapter"
            @select="handleChapterSelect"
            background-color="transparent"
            text-color="rgba(255,255,255,0.7)"
            active-text-color="#00d4ff"
          >
            <el-menu-item index="5">
              <el-icon><Document /></el-icon>
              <span>面向对象编程</span>
            </el-menu-item>
            <el-menu-item index="6">
              <el-icon><Document /></el-icon>
              <span>异常处理</span>
            </el-menu-item>
            <el-menu-item index="7">
              <el-icon><Document /></el-icon>
              <span>模块和包</span>
            </el-menu-item>
            <el-menu-item index="8">
              <el-icon><Document /></el-icon>
              <span>装饰器</span>
            </el-menu-item>
          </el-menu>
        </div>

        <div class="progress-section">
          <h3>学习进度</h3>
          <div class="progress-stats">
            <div class="progress-item">
              <div class="progress-label">完成度</div>
              <div class="progress-value">{{ progress.completion }}%</div>
              <el-progress 
                :percentage="progress.completion" 
                :show-text="false"
                color="#00d4ff"
              />
            </div>
            <div class="progress-item">
              <div class="progress-label">已学习</div>
              <div class="progress-value">{{ progress.learned }}/{{ progress.total }}</div>
            </div>
            <div class="progress-item">
              <div class="progress-label">学习时间</div>
              <div class="progress-value">{{ progress.timeSpent }}</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧内容区 -->
      <div class="content-area">
        <!-- 章节内容 -->
        <div class="chapter-content">
          <div class="chapter-header">
            <h2>{{ currentChapter.title }}</h2>
            <div class="chapter-actions">
              <el-button size="small" @click="toggleCodeExample">
                <el-icon><Document /></el-icon>
                {{ showCodeExample ? '隐藏代码' : '显示代码' }}
              </el-button>
              <el-button size="small" @click="runCodeExample" :loading="runningCode">
                <el-icon><VideoPlay /></el-icon>
                {{ runningCode ? '运行中...' : '运行代码' }}
              </el-button>
              <el-button size="small" @click="markAsComplete">
                <el-icon><Check /></el-icon>
                {{ currentChapter.completed ? '已学习' : '标记完成' }}
              </el-button>
            </div>
          </div>

          <div class="chapter-body">
            <!-- 理论内容 -->
            <div class="theory-section">
              <h3>概念讲解</h3>
              <div class="theory-content" v-html="currentChapter.theory"></div>
            </div>

            <!-- 代码示例 -->
            <div v-if="showCodeExample" class="code-section">
              <h3>代码示例</h3>
              <div class="code-editor">
                <pre class="code-display">{{ currentChapter.codeExample }}</pre>
              </div>
              
              <!-- 运行结果 -->
              <div v-if="codeResult" class="code-result">
                <h4>运行结果</h4>
                <pre class="result-output">{{ codeResult }}</pre>
              </div>
            </div>

            <!-- 对比学习 -->
            <div class="comparison-section">
              <h3>Java vs Python 对比</h3>
              <div class="comparison-grid">
                <div class="comparison-item">
                  <h4>Java 实现</h4>
                  <pre class="comparison-code">{{ currentChapter.javaComparison }}</pre>
                </div>
                <div class="comparison-item">
                  <h4>Python 实现</h4>
                  <pre class="comparison-code">{{ currentChapter.pythonComparison }}</pre>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 导航控制 -->
        <div class="navigation-controls">
          <el-button 
            :disabled="activeChapter <= 1"
            @click="goToPreviousChapter"
          >
            <el-icon><ArrowLeft /></el-icon>
            上一章
          </el-button>
          <el-button 
            type="primary"
            :disabled="activeChapter >= chapters.length"
            @click="goToNextChapter"
          >
            <el-icon><ArrowRight /></el-icon>
            下一章
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import {
  Reading,
  Document,
  VideoPlay,
  Check,
  ArrowLeft,
  ArrowRight
} from '@element-plus/icons-vue'
import { learningApi } from '@/api/modules/learning'

// 响应式数据
const activeChapter = ref('1')
const showCodeExample = ref(true)
const runningCode = ref(false)
const codeResult = ref('')
const learningModules = ref<any[]>([])
const courses = ref<any[]>([])
const loading = ref(false)

const progress = reactive({
  completion: 25,
  learned: 2,
  total: 8,
  timeSpent: '15分钟'
})

// 章节数据
const chapters = ref([
  // 这里将从后端API获取数据
])

// 计算属性
const currentChapter = computed(() => {
  const chapter = chapters.value.find((item: any) => item.id === activeChapter.value)
  return chapter || chapters.value[0]
})

// 生命周期
onMounted(() => {
  loadLearningModules()
})

// 加载学习模块
const loadLearningModules = async () => {
  loading.value = true
  try {
    const response = await learningApi.getModules()
    if (response.code === 200) {
      learningModules.value = response.data
      // 加载第一个模块的课程
      if (learningModules.value.length > 0) {
        loadCoursesByModule(learningModules.value[0].id)
      }
    } else {
      ElMessage.error('加载学习模块失败')
    }
  } catch (error) {
    ElMessage.error('加载学习模块失败')
    console.error('加载学习模块失败:', error)
  } finally {
    loading.value = false
  }
}

// 加载模块下的课程
const loadCoursesByModule = async (moduleId: number) => {
  loading.value = true
  try {
    const response = await learningApi.getCoursesByModule(moduleId)
    if (response.code === 200) {
      courses.value = response.data
      // 将课程转换为章节数据结构
      chapters.value = courses.value.map((course: any, index: number) => ({
        id: (index + 1).toString(),
        title: course.title,
        theory: course.description || '',
        codeExample: course.codeExample || '',
        javaComparison: course.javaComparison || '',
        pythonComparison: course.pythonComparison || '',
        completed: course.completed || false
      }))
    } else {
      ElMessage.error('加载课程失败')
    }
  } catch (error) {
    ElMessage.error('加载课程失败')
    console.error('加载课程失败:', error)
  } finally {
    loading.value = false
  }
}

// 章节选择
const handleChapterSelect = (key: string) => {
  activeChapter.value = key
}

// 切换代码示例显示
const toggleCodeExample = () => {
  showCodeExample.value = !showCodeExample.value
}

// 运行代码示例
const runCodeExample = async () => {
  runningCode.value = true
  codeResult.value = ''

  try {
    // 这里可以连接到后端的代码运行API
    // 暂时模拟运行结果
    setTimeout(() => {
      codeResult.value = '运行成功！\n\n' + currentChapter.value.codeExample
      runningCode.value = false
    }, 1500)
  } catch (error) {
    codeResult.value = '运行失败：' + error
    runningCode.value = false
  }
}

// 标记章节完成
const markAsComplete = () => {
  if (currentChapter.value) {
    currentChapter.value.completed = !currentChapter.value.completed
    // 更新学习进度
    updateProgress()
    ElMessage.success(currentChapter.value.completed ? '标记为已完成' : '取消已完成标记')
  }
}

// 更新学习进度
const updateProgress = () => {
  const completedChapters = chapters.value.filter((chapter: any) => chapter.completed).length
  const totalChapters = chapters.value.length
  progress.completion = totalChapters > 0 ? Math.round((completedChapters / totalChapters) * 100) : 0
  progress.learned = completedChapters
  progress.total = totalChapters
}

// 上一章
const goToPreviousChapter = () => {
  const currentIndex = parseInt(activeChapter.value)
  if (currentIndex > 1) {
    activeChapter.value = (currentIndex - 1).toString()
  }
}

// 下一章
const goToNextChapter = () => {
  const currentIndex = parseInt(activeChapter.value)
  if (currentIndex < chapters.value.length) {
    activeChapter.value = (currentIndex + 1).toString()
  }
}
</script>

<style lang="scss" scoped>
.learning-center-view {
  padding: 24px;
  height: 100%;
  overflow-y: auto;
}

.page-header {
  margin-bottom: 32px;
  
  .page-title {
    display: flex;
    align-items: center;
    gap: 12px;
    font-size: 28px;
    font-weight: 700;
    color: #fff;
    margin: 0 0 8px 0;
    
    .el-icon {
      color: #00d4ff;
    }
  }
  
  .page-desc {
    color: rgba(255, 255, 255, 0.7);
    font-size: 16px;
    margin: 0;
  }
}

.learning-container {
  display: grid;
  grid-template-columns: 280px 1fr;
  gap: 24px;
  height: calc(100vh - 200px);
}

.sidebar-nav {
  display: flex;
  flex-direction: column;
  gap: 20px;
  
  .nav-section {
    background: rgba(255, 255, 255, 0.05);
    border-radius: 12px;
    padding: 16px;
    
    h3 {
      color: #fff;
      font-size: 16px;
      font-weight: 600;
      margin: 0 0 12px 0;
    }
    
    .el-menu {
      border: none;
      
      .el-menu-item {
        height: 40px;
        line-height: 40px;
        margin-bottom: 4px;
        border-radius: 6px;
        
        &.is-active {
          background: rgba(0, 212, 255, 0.2);
        }
        
        &:hover {
          background: rgba(255, 255, 255, 0.1);
        }
      }
    }
  }
  
  .progress-section {
    background: rgba(255, 255, 255, 0.05);
    border-radius: 12px;
    padding: 16px;
    
    h3 {
      color: #fff;
      font-size: 16px;
      font-weight: 600;
      margin: 0 0 16px 0;
    }
    
    .progress-stats {
      .progress-item {
        margin-bottom: 16px;
        
        .progress-label {
          color: rgba(255, 255, 255, 0.7);
          font-size: 12px;
          margin-bottom: 4px;
        }
        
        .progress-value {
          color: #00d4ff;
          font-size: 18px;
          font-weight: 600;
          margin-bottom: 8px;
        }
      }
    }
  }
}

.content-area {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.chapter-content {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 12px;
  padding: 24px;
  flex: 1;
  overflow-y: auto;
  
  .chapter-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 24px;
    
    h2 {
      color: #fff;
      font-size: 24px;
      font-weight: 700;
      margin: 0;
    }
    
    .chapter-actions {
      display: flex;
      gap: 8px;
    }
  }
  
  .chapter-body {
    .theory-section {
      margin-bottom: 24px;
      
      h3 {
        color: #fff;
        font-size: 18px;
        font-weight: 600;
        margin: 0 0 16px 0;
      }
      
      .theory-content {
        color: rgba(255, 255, 255, 0.8);
        line-height: 1.6;
        
        h4 {
          color: #fff;
          margin: 16px 0 8px 0;
        }
        
        ul {
          margin: 8px 0;
          padding-left: 20px;
          
          li {
            margin-bottom: 4px;
          }
        }
      }
    }
    
    .code-section {
      margin-bottom: 24px;
      
      h3 {
        color: #fff;
        font-size: 18px;
        font-weight: 600;
        margin: 0 0 16px 0;
      }
      
      .code-editor {
        background: rgba(16, 18, 27, 0.8);
        border-radius: 8px;
        padding: 16px;
        margin-bottom: 16px;
        
        .code-display {
          color: #00ff88;
          font-family: 'Monaco', 'Consolas', monospace;
          font-size: 14px;
          line-height: 1.5;
          margin: 0;
          white-space: pre-wrap;
        }
      }
      
      .code-result {
        background: rgba(16, 18, 27, 0.8);
        border-radius: 8px;
        padding: 16px;
        
        h4 {
          color: #fff;
          font-size: 14px;
          font-weight: 600;
          margin: 0 0 8px 0;
        }
        
        .result-output {
          color: #ffd700;
          font-family: 'Monaco', 'Consolas', monospace;
          font-size: 14px;
          line-height: 1.5;
          margin: 0;
          white-space: pre-wrap;
        }
      }
    }
    
    .comparison-section {
      h3 {
        color: #fff;
        font-size: 18px;
        font-weight: 600;
        margin: 0 0 16px 0;
      }
      
      .comparison-grid {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 16px;
        
        .comparison-item {
          h4 {
            color: #fff;
            font-size: 14px;
            font-weight: 600;
            margin: 0 0 8px 0;
          }
          
          .comparison-code {
            background: rgba(16, 18, 27, 0.8);
            border-radius: 8px;
            padding: 16px;
            color: #ff6b6b;
            font-family: 'Monaco', 'Consolas', monospace;
            font-size: 12px;
            line-height: 1.4;
            margin: 0;
            white-space: pre-wrap;
            max-height: 200px;
            overflow-y: auto;
          }
        }
      }
    }
  }
}

.navigation-controls {
  display: flex;
  justify-content: space-between;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 12px;
  padding: 16px 24px;
  
  .el-button {
    padding: 10px 20px;
    
    &:disabled {
      opacity: 0.5;
      cursor: not-allowed;
    }
  }
}
</style>