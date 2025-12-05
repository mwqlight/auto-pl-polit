<template>
  <div class="exceptions-container">
    <!-- 模块标题 -->
    <div class="module-header">
      <h2 class="module-title">异常处理</h2>
      <p class="module-description">学习Python中的异常处理机制和最佳实践</p>
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
const activeLesson = ref('basic-handling')
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
    // 假设异常处理模块的ID是3
    const response = await learningApi.getCoursesByModule(3)
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

<style scoped>
.exceptions-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.module-header {
  text-align: center;
  margin-bottom: 30px;
}

.module-title {
  font-size: 2.5rem;
  color: #2c3e50;
  margin-bottom: 10px;
}

.module-description {
  font-size: 1.1rem;
  color: #7f8c8d;
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
}

.lesson-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.lesson-card.active {
  border: 2px solid #409eff;
}

.lesson-card.completed {
  border-left: 4px solid #67c23a;
}

.lesson-header {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.lesson-icon {
  font-size: 24px;
  color: #409eff;
  margin-right: 15px;
}

.lesson-info {
  flex: 1;
}

.lesson-title {
  font-size: 1.2rem;
  font-weight: 600;
  margin-bottom: 5px;
  color: #2c3e50;
}

.lesson-desc {
  font-size: 0.9rem;
  color: #7f8c8d;
  margin: 0;
}

.lesson-status {
  margin-left: 10px;
}

.lesson-progress {
  margin-top: 10px;
}

.lesson-content {
  margin-top: 30px;
}

.content-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.content-header h3 {
  margin: 0;
  color: #2c3e50;
}

.theory-section,
.code-section,
.exercise-section {
  margin-bottom: 30px;
}

.theory-section h4,
.code-section h4,
.exercise-section h4 {
  color: #2c3e50;
  border-bottom: 2px solid #eaeaea;
  padding-bottom: 10px;
  margin-bottom: 15px;
}

.theory-content {
  line-height: 1.6;
}

.theory-content :deep(p) {
  margin-bottom: 15px;
}

.theory-content :deep(ul),
.theory-content :deep(ol) {
  margin-bottom: 15px;
  padding-left: 20px;
}

.theory-content :deep(li) {
  margin-bottom: 5px;
}

.theory-content :deep(pre) {
  background-color: #f8f9fa;
  border: 1px solid #e9ecef;
  border-radius: 4px;
  padding: 15px;
  overflow-x: auto;
  margin: 15px 0;
}

.theory-content :deep(code) {
  background-color: #f8f9fa;
  padding: 2px 4px;
  border-radius: 3px;
  font-family: 'Courier New', monospace;
}

.code-example {
  background-color: #f8f9fa;
  border: 1px solid #e9ecef;
  border-radius: 4px;
  overflow: hidden;
}

.code-example pre {
  margin: 0;
  padding: 20px;
  overflow-x: auto;
  background-color: #2d2d2d;
  color: #f8f8f2;
}

.code-example code {
  font-family: 'Courier New', monospace;
  font-size: 0.9rem;
}

.code-actions {
  padding: 15px 20px;
  background-color: #f8f9fa;
  border-top: 1px solid #e9ecef;
  display: flex;
  gap: 10px;
}

.exercise-content {
  background-color: #f8f9fa;
  border: 1px solid #e9ecef;
  border-radius: 4px;
  padding: 20px;
}

.exercise-actions {
  margin-top: 15px;
}

@media (max-width: 768px) {
  .exceptions-container {
    padding: 10px;
  }
  
  .lessons-container {
    grid-template-columns: 1fr;
  }
  
  .content-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
}
</style>