import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { learningApi } from '@/api/modules/learning'
import type { LearningModule, Lesson, LearningProgress, QuizResult } from '@/types/api'

export const useLearningStore = defineStore('learning', () => {
  // 学习模块列表
  const modules = ref<LearningModule[]>([])
  // 当前课程
  const currentLesson = ref<Lesson | null>(null)
  // 学习进度
  const progress = ref<LearningProgress[]>([])
  // 测验结果
  const quizResults = ref<QuizResult[]>([])

  // 计算属性：已完成课程数
  const completedLessons = computed(() => {
    return progress.value.filter(p => p.completed).length
  })

  // 计算属性：总学习时间（小时）
  const totalLearningTime = computed(() => {
    // 这里可以从后端获取真实的学习时间
    return 12.5
  })

  // 方法：加载学习模块
  const loadModules = async () => {
    try {
      const response = await learningApi.getPublishedModules()
      if (response.code === 200) {
        modules.value = response.data || []
      } else {
        console.error('获取学习模块失败:', response.message)
      }
    } catch (error) {
      console.error('加载学习模块失败:', error)
    }
  }

  // 方法：加载课程详情
  const loadLesson = async (lessonId: number) => {
    try {
      const response = await learningApi.getLessonDetail(lessonId)
      if (response.code === 200) {
        currentLesson.value = response.data || null
      } else {
        console.error('获取课程详情失败:', response.message)
      }
    } catch (error) {
      console.error('加载课程详情失败:', error)
    }
  }

  // 方法：更新学习进度
  const updateProgress = async (lessonId: number, progress: number) => {
    try {
      await learningApi.updateProgress(lessonId, progress)
      // 更新本地进度
      const existingProgress = progress.value.find(p => p.lessonId === lessonId.toString())
      if (existingProgress) {
        existingProgress.progress = progress
        existingProgress.completed = progress >= 100
      } else {
        progress.value.push({
          userId: 1,
          lessonId: lessonId.toString(),
          progress,
          completed: progress >= 100,
          lastAccessed: new Date().toISOString()
        })
      }
    } catch (error) {
      console.error('更新学习进度失败:', error)
    }
  }

  // 方法：提交测验结果
  const submitQuiz = async (lessonId: number, score: number, answers: any[]) => {
    try {
      const response = await learningApi.submitQuiz(lessonId, score, answers)
      if (response.code === 200) {
        const result = response.data
        if (result) {
          quizResults.value.push(result)
        }
        return result
      } else {
        console.error('提交测验结果失败:', response.message)
        return null
      }
    } catch (error) {
      console.error('提交测验结果失败:', error)
      return null
    }
  }

  // 方法：记录学习时间
  const recordLearningTime = async (lessonId: number, duration: number) => {
    try {
      await learningApi.recordLearningTime(lessonId, duration)
    } catch (error) {
      console.error('记录学习时间失败:', error)
    }
  }

  // 方法：初始化学习数据
  const initializeLearningData = async () => {
    try {
      // 加载学习模块
      await loadModules()
      // 加载学习进度
      const progressResponse = await learningApi.getLearningProgress()
      if (progressResponse.code === 200) {
        progress.value = progressResponse.data || []
      } else {
        console.error('获取学习进度失败:', progressResponse.message)
      }
      // 加载测验结果
      const quizResponse = await learningApi.getQuizResults()
      if (quizResponse.code === 200) {
        quizResults.value = quizResponse.data || []
      } else {
        console.error('获取测验结果失败:', quizResponse.message)
      }
    } catch (error) {
      console.error('初始化学习数据失败:', error)
    }
  }

  return {
    modules,
    currentLesson,
    progress,
    quizResults,
    completedLessons,
    totalLearningTime,
    loadModules,
    loadLesson,
    updateProgress,
    submitQuiz,
    recordLearningTime,
    initializeLearningData
  }
})