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
import { ElCard, ElButton, ElMessage, ElMessageBox } from 'element-plus'
import { PlayCircle, CopyDocument, CheckCircle, BookOpen } from '@element-plus/icons-vue'
import { learningApi } from '@/api/modules/learning'
import type { Lesson } from '@/types/api'

// 响应式数据
const lessons = ref<Lesson[]>([])
const activeLesson = ref<number | null>(null)
const loading = ref(false)

// 计算属性：当前选中的课程
const currentLesson = computed(() => {
  return lessons.value.find(lesson => lesson.id === activeLesson.value) || null
})

// 加载课程数据
const loadLessons = async () => {
  loading.value = true
  try {
    const response = await learningApi.getAllLessons(3) // 3是异常处理模块的ID
    if (response.code === 200 && response.data) {
      lessons.value = response.data
      // 默认选择第一个课程
      if (lessons.value.length > 0) {
        activeLesson.value = lessons.value[0].id
      }
    } else {
      ElMessage.error('加载课程失败：' + response.message)
    }
  } catch (error) {
    console.error('加载课程失败:', error)
    ElMessage.error('加载课程失败，请稍后重试')
  } finally {
    loading.value = false
  }
}

// 方法定义
const selectLesson = (lessonId: number) => {
  activeLesson.value = lessonId
}

const markAsCompleted = async () => {
  const lesson = lessons.value.find(l => l.id === activeLesson.value)
  if (lesson && !lesson.completed) {
    try {
      await ElMessageBox.confirm(
        '确定要标记该课程为完成吗？',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'info'
        }
      )
      
      const response = await learningApi.updateProgress({
        moduleId: 3,
        lessonId: lesson.id,
        completed: true,
        progress: 100
      })
      
      if (response.code === 200) {
        lesson.completed = true
        lesson.progress = 100
        ElMessage.success(`课程"${lesson.title}"已标记为完成`)
      } else {
        ElMessage.error('更新进度失败：' + response.message)
      }
    } catch (error: any) {
      if (error !== 'cancel') {
        console.error('标记完成失败:', error)
        ElMessage.error('标记完成失败，请稍后重试')
      }
    }
  }
}

const runCode = async () => {
  ElMessage.info('代码执行功能需要后端服务支持')
}

const copyCode = async () => {
  try {
    await navigator.clipboard.writeText(currentLesson.value?.codeExample || '')
    ElMessage.success('代码已复制到剪贴板')
  } catch (err) {
    ElMessage.error('复制失败，请手动复制代码')
  }
}

const showSolution = () => {
  ElMessage.info(`答案：${currentLesson.value?.solution}`)
}

// 页面加载时获取课程数据
onMounted(() => {
  loadLessons()
})
</script>
</template>