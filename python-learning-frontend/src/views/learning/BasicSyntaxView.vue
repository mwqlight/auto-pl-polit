<template>
  <div class="basic-syntax-container">
    <!-- 模块标题 -->
    <div class="module-header">
      <h2 class="module-title">Python基础语法</h2>
      <p class="module-description">学习Python编程的基础语法和核心概念</p>
    </div>

    <!-- 课程列表 -->
    <div class="lessons-container">
      <el-card 
        v-for="lesson in lessons" 
        :key="lesson.id"
        class="lesson-card"
        shadow="hover"
        :class="{ 'completed': lesson.completed, 'active': lesson.id === activeLesson }"
        @click="selectLesson(lesson.id)"
      >
        <div class="lesson-header">
          <div class="lesson-icon">
            <i :class="lesson.icon"></i>
          </div>
          <div class="lesson-info">
            <h3 class="lesson-title">{{ lesson.title }}</h3>
            <p class="lesson-desc">{{ lesson.description }}</p>
          </div>
          <div class="lesson-status">
            <el-tag 
              v-if="lesson.completed" 
              type="success" 
              size="small"
            >
              已完成
            </el-tag>
            <el-tag 
              v-else-if="lesson.id === activeLesson" 
              type="primary" 
              size="small"
            >
              学习中
            </el-tag>
            <el-tag v-else type="info" size="small">
              未开始
            </el-tag>
          </div>
        </div>
        
        <div class="lesson-progress">
          <el-progress 
            :percentage="lesson.progress" 
            :show-text="false"
            :stroke-width="4"
          />
        </div>
      </el-card>
    </div>

    <!-- 课程内容 -->
    <div v-if="currentLesson" class="lesson-content">
      <el-card shadow="never">
        <template #header>
          <div class="content-header">
            <h3>{{ currentLesson.title }}</h3>
            <div class="content-actions">
              <el-button 
                type="primary" 
                size="small"
                @click="markAsCompleted"
                :disabled="currentLesson.completed"
              >
                {{ currentLesson.completed ? '已完成' : '标记完成' }}
              </el-button>
            </div>
          </div>
        </template>

        <!-- 理论内容 -->
        <div class="theory-section">
          <h4>理论讲解</h4>
          <div class="theory-content" v-html="currentLesson.theory"></div>
        </div>

        <!-- 代码示例 -->
        <div class="code-section">
          <h4>代码示例</h4>
          <div class="code-example">
            <pre><code class="language-python">{{ currentLesson.codeExample }}</code></pre>
            <div class="code-actions">
              <el-button 
                type="primary" 
                size="small"
                @click="runCode"
              >
                运行代码
              </el-button>
              <el-button size="small" @click="copyCode">
                复制代码
              </el-button>
            </div>
          </div>
        </div>

        <!-- 练习题目 -->
        <div class="exercise-section">
          <h4>练习题目</h4>
          <div class="exercise-content">
            <p>{{ currentLesson.exercise }}</p>
            <div class="exercise-actions">
              <el-button type="success" size="small" @click="showSolution">
                查看答案
              </el-button>
            </div>
          </div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { learningApi } from '@/api/modules/learning'

interface Lesson {
  id: string
  title: string
  description: string
  icon: string
  completed: boolean
  progress: number
  theory: string
  codeExample: string
  exercise: string
  solution: string
}

// 响应式数据
const activeLesson = ref('variables')
const lessons = ref<Lesson[]>([])
const loading = ref(false)

// 计算属性
const currentLesson = computed(() => {
  return lessons.value.find(lesson => lesson.id === activeLesson.value) || lessons.value[0]
})

// 生命周期
onMounted(() => {
  loadLessons()
})

// 加载课程数据
const loadLessons = async () => {
  loading.value = true
  try {
    // 这里可以根据实际的模块ID来获取课程
    // 暂时假设基础语法模块的ID是1
    const response = await learningApi.getCoursesByModule(1)
    if (response.code === 200) {
      // 将从后端获取的课程数据转换为Lesson接口的格式
      lessons.value = response.data.map((course: any) => ({
        id: course.id.toString(),
        title: course.title,
        description: course.description || '',
        icon: 'el-icon-s-flag', // 暂时使用固定图标
        completed: course.completed || false,
        progress: course.completed ? 100 : 0,
        theory: course.description || '',
        codeExample: course.codeExample || '',
        exercise: course.exercise || '',
        solution: course.solution || ''
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

// 选择课程
const selectLesson = (lessonId: string) => {
  activeLesson.value = lessonId
}

// 标记课程完成
const markAsCompleted = () => {
  if (currentLesson.value) {
    currentLesson.value.completed = !currentLesson.value.completed
    currentLesson.value.progress = currentLesson.value.completed ? 100 : 0
    ElMessage.success(currentLesson.value.completed ? '课程标记为已完成' : '取消已完成标记')
  }
}

// 运行代码
const runCode = async () => {
  // 这里可以连接到后端的代码运行API
  ElMessage.success('代码运行功能将在后续版本中实现')
}

// 复制代码
const copyCode = () => {
  if (currentLesson.value) {
    navigator.clipboard.writeText(currentLesson.value.codeExample)
    ElMessage.success('代码已复制到剪贴板')
  }
}

// 查看答案
const showSolution = () => {
  if (currentLesson.value) {
    ElMessage.success('答案：' + currentLesson.value.solution)
  }
}
</script>

<style scoped lang="scss">
.basic-syntax-container {
  padding: 20px;
}

.module-header {
  text-align: center;
  margin-bottom: 30px;
  
  .module-title {
    font-size: 24px;
    font-weight: 600;
    color: var(--el-text-color-primary);
    margin-bottom: 8px;
  }
  
  .module-description {
    font-size: 16px;
    color: var(--el-text-color-secondary);
    margin: 0;
  }
}

.lessons-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.lesson-card {
  cursor: pointer;
  transition: all 0.3s ease;
  border: 2px solid transparent;
  
  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }
  
  &.active {
    border-color: var(--el-color-primary);
  }
  
  &.completed {
    opacity: 0.8;
  }
  
  .lesson-header {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
    
    .lesson-icon {
      width: 40px;
      height: 40px;
      background: var(--el-color-primary-light-9);
      border-radius: 8px;
      display: flex;
      align-items: center;
      justify-content: center;
      margin-right: 15px;
      
      i {
        font-size: 20px;
        color: var(--el-color-primary);
      }
    }
    
    .lesson-info {
      flex: 1;
      
      .lesson-title {
        font-size: 16px;
        font-weight: 600;
        margin: 0 0 4px 0;
        color: var(--el-text-color-primary);
      }
      
      .lesson-desc {
        font-size: 14px;
        color: var(--el-text-color-secondary);
        margin: 0;
      }
    }
  }
  
  .lesson-progress {
    margin-top: 10px;
  }
}

.lesson-content {
  .content-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    
    h3 {
      margin: 0;
      font-size: 18px;
      font-weight: 600;
    }
  }
  
  .theory-section, .code-section, .exercise-section {
    margin-bottom: 30px;
    
    h4 {
      font-size: 16px;
      font-weight: 600;
      margin-bottom: 15px;
      color: var(--el-text-color-primary);
      border-left: 4px solid var(--el-color-primary);
      padding-left: 10px;
    }
  }
  
  .theory-content {
    line-height: 1.6;
    
    ul {
      margin: 10px 0;
      padding-left: 20px;
    }
    
    li {
      margin: 5px 0;
    }
    
    code {
      background: var(--el-fill-color-light);
      padding: 2px 6px;
      border-radius: 3px;
      font-family: 'Courier New', monospace;
    }
  }
  
  .code-example {
    pre {
      background: #f6f8fa;
      border-radius: 6px;
      padding: 16px;
      overflow-x: auto;
      margin-bottom: 15px;
      
      code {
        font-family: 'Courier New', monospace;
        font-size: 14px;
        line-height: 1.4;
        color: #24292e;
      }
    }
    
    .code-actions {
      display: flex;
      gap: 10px;
    }
  }
  
  .exercise-content {
    p {
      line-height: 1.6;
      margin-bottom: 15px;
    }
  }
}

@media (max-width: 768px) {
  .lessons-container {
    grid-template-columns: 1fr;
  }
  
  .lesson-card {
    .lesson-header {
      .lesson-icon {
        width: 30px;
        height: 30px;
        margin-right: 10px;
        
        i {
          font-size: 16px;
        }
      }
    }
  }
}
</style>