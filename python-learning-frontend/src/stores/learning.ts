import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { LearningModule, Course, LearningProgress, QuizScore } from '@/types/api'
import { learningApi } from '@/api/modules/learning'

export const useLearningStore = defineStore('learning', () => {
  // 状态
  const modules = ref<LearningModule[]>([])
  const currentModule = ref<LearningModule | null>(null)
  const currentCourse = ref<Course | null>(null)
  const progress = ref<LearningProgress[]>([])
  const quizScores = ref<QuizScore[]>([])
  const studyHistory = ref<any[]>([])

  // 计算属性
  const totalProgress = computed(() => {
    if (modules.value.length === 0) return 0
    const totalModules = modules.value.length
    const completedModules = modules.value.filter(module => module.progress === 100).length
    return Math.round((completedModules / totalModules) * 100)
  })

  const completedCourses = computed(() => {
    return progress.value.filter(p => p.completed).length
  })

  const totalStudyTime = computed(() => {
    return progress.value.reduce((total, p) => total + p.timeSpent, 0)
  })

  const averageQuizScore = computed(() => {
    if (quizScores.value.length === 0) return 0
    const totalScore = quizScores.value.reduce((sum, score) => sum + score.score, 0)
    return Math.round((totalScore / quizScores.value.length) * 100) / 100
  })

  // 获取学习模块列表
  const fetchModules = async () => {
    try {
      const response = await learningApi.getModules()
      
      if (response.code === 200) {
        modules.value = response.data
        return { success: true, data: response.data }
      } else {
        return { success: false, error: response.message }
      }
    } catch (error) {
      console.error('获取学习模块失败:', error)
      // 返回模拟数据
      modules.value = getMockModules()
      return { success: true, data: modules.value }
    }
  }

  // 获取课程详情
  const fetchCourse = async (courseId: string) => {
    try {
      const response = await learningApi.getCourse(parseInt(courseId))
      
      if (response.code === 200) {
        currentCourse.value = response.data
        return { success: true, data: response.data }
      } else {
        return { success: false, error: response.message }
      }
    } catch (error) {
      console.error('获取课程详情失败:', error)
      // 返回模拟数据
      currentCourse.value = getMockCourse(courseId)
      return { success: true, data: currentCourse.value }
    }
  }

  // 更新学习进度
  const updateProgress = async (courseId: string, newProgress: number) => {
    try {
      const response = await learningApi.updateProgress(parseInt(courseId), newProgress)
      
      if (response.code === 200) {
        // 更新本地进度
        const existingProgress = progress.value.find(p => p.courseId === courseId)
        if (existingProgress) {
          existingProgress.progress = newProgress
          existingProgress.completed = newProgress === 100
          existingProgress.lastStudyTime = new Date().toISOString()
        } else {
          progress.value.push({
            userId: 1, // 模拟用户ID
            courseId,
            progress: newProgress,
            completed: newProgress === 100,
            lastStudyTime: new Date().toISOString(),
            timeSpent: 0,
            quizScores: []
          })
        }
        
        // 更新模块进度
        updateModuleProgress(courseId)
        
        return { success: true, data: response.data }
      } else {
        return { success: false, error: response.message }
      }
    } catch (error) {
      console.error('更新学习进度失败:', error)
      return { success: false, error: '更新学习进度失败' }
    }
  }

  // 标记课程完成
  const markCourseCompleted = async (courseId: string) => {
    return await updateProgress(courseId, 100)
  }

  // 记录学习时间
  const recordStudyTime = async (courseId: string, timeSpent: number) => {
    try {
      const existingProgress = progress.value.find(p => p.courseId === courseId)
      if (existingProgress) {
        existingProgress.timeSpent += timeSpent
      }
      
      // 记录学习历史
      studyHistory.value.push({
        courseId,
        timeSpent,
        studyTime: new Date().toISOString()
      })
      
      return { success: true }
    } catch (error) {
      console.error('记录学习时间失败:', error)
      return { success: false, error: '记录学习时间失败' }
    }
  }

  // 提交测验成绩
  const submitQuizScore = async (courseId: string, scoreData: Omit<QuizScore, 'quizId'>) => {
    try {
      const quizScore: QuizScore = {
        quizId: `${courseId}-quiz`,
        ...scoreData
      }
      
      quizScores.value.push(quizScore)
      
      // 更新课程进度
      await updateProgress(courseId, 100)
      
      return { success: true, data: quizScore }
    } catch (error) {
      console.error('提交测验成绩失败:', error)
      return { success: false, error: '提交测验成绩失败' }
    }
  }

  // 获取学习统计
  const fetchLearningStats = async () => {
    try {
      const response = await learningApi.getLearningStats()
      
      if (response.code === 200) {
        return { success: true, data: response.data }
      } else {
        return { success: false, error: response.message }
      }
    } catch (error) {
      console.error('获取学习统计失败:', error)
      return { success: false, error: '获取学习统计失败' }
    }
  }

  // 更新模块进度
  const updateModuleProgress = (courseId: string) => {
    const courseModuleId = courseId.split('-')[0]
    const module = modules.value.find(m => m.id === courseModuleId)
    
    if (module) {
      const moduleCourses = progress.value.filter(p => p.courseId.startsWith(courseModuleId))
      const completedCourses = moduleCourses.filter(p => p.completed).length
      const totalCourses = 4 // 每个模块4个课程
      
      module.progress = Math.round((completedCourses / totalCourses) * 100)
      module.completedLessons = completedCourses
    }
  }

  // 模拟数据
  const getMockModules = (): LearningModule[] => [
    {
      id: 'basic-syntax',
      name: '基础语法',
      description: '学习Python基础语法和核心概念',
      icon: 'code',
      progress: 0,
      totalLessons: 4,
      completedLessons: 0,
      estimatedTime: 120,
      difficulty: 'beginner'
    },
    {
      id: 'oop',
      name: '面向对象编程',
      description: '掌握Python面向对象编程思想',
      icon: 'object',
      progress: 0,
      totalLessons: 4,
      completedLessons: 0,
      estimatedTime: 180,
      difficulty: 'intermediate'
    },
    {
      id: 'functional',
      name: '函数式编程',
      description: '学习Python函数式编程特性',
      icon: 'function',
      progress: 0,
      totalLessons: 4,
      completedLessons: 0,
      estimatedTime: 150,
      difficulty: 'intermediate'
    },
    {
      id: 'exceptions',
      name: '异常处理',
      description: '掌握Python异常处理机制',
      icon: 'error',
      progress: 0,
      totalLessons: 4,
      completedLessons: 0,
      estimatedTime: 90,
      difficulty: 'beginner'
    }
  ]

  const getMockCourse = (courseId: string): Course => {
    const courses: Record<string, Course> = {
      'basic-syntax-variables': {
        id: 'basic-syntax-variables',
        moduleId: 'basic-syntax',
        title: '变量和数据类型',
        description: '学习Python变量声明和基本数据类型',
        content: 'Python变量和数据类型的基础知识...',
        codeExamples: [],
        exercises: [],
        order: 1,
        estimatedTime: 30,
        completed: false,
        progress: 0
      },
      'basic-syntax-operators': {
        id: 'basic-syntax-operators',
        moduleId: 'basic-syntax',
        title: '运算符',
        description: '掌握Python各种运算符的使用',
        content: 'Python运算符的详细讲解...',
        codeExamples: [],
        exercises: [],
        order: 2,
        estimatedTime: 25,
        completed: false,
        progress: 0
      },
      'basic-syntax-control': {
        id: 'basic-syntax-control',
        moduleId: 'basic-syntax',
        title: '控制流程',
        description: '学习条件判断和循环控制',
        content: 'Python控制流程语句详解...',
        codeExamples: [],
        exercises: [],
        order: 3,
        estimatedTime: 35,
        completed: false,
        progress: 0
      },
      'basic-syntax-functions': {
        id: 'basic-syntax-functions',
        moduleId: 'basic-syntax',
        title: '函数',
        description: '掌握函数定义和调用',
        content: 'Python函数编程基础...',
        codeExamples: [],
        exercises: [],
        order: 4,
        estimatedTime: 30,
        completed: false,
        progress: 0
      }
    }
    
    return courses[courseId] || {
      id: courseId,
      moduleId: courseId.split('-')[0],
      title: '默认课程',
      description: '课程描述',
      content: '课程内容',
      codeExamples: [],
      exercises: [],
      order: 1,
      estimatedTime: 30,
      completed: false,
      progress: 0
    }
  }

  // 初始化学习数据
  const initializeLearningData = async () => {
    await fetchModules()
    
    // 加载本地进度
    const savedProgress = localStorage.getItem('learningProgress')
    if (savedProgress) {
      try {
        progress.value = JSON.parse(savedProgress)
        
        // 更新模块进度
        modules.value.forEach(module => {
          updateModuleProgress(module.id)
        })
      } catch (error) {
        console.error('加载学习进度失败:', error)
      }
    }
  }

  // 保存学习数据到本地
  const saveLearningData = () => {
    localStorage.setItem('learningProgress', JSON.stringify(progress.value))
    localStorage.setItem('quizScores', JSON.stringify(quizScores.value))
    localStorage.setItem('studyHistory', JSON.stringify(studyHistory.value))
  }

  return {
    // 状态
    modules,
    currentModule,
    currentCourse,
    progress,
    quizScores,
    studyHistory,
    
    // 计算属性
    totalProgress,
    completedCourses,
    totalStudyTime,
    averageQuizScore,
    
    // 方法
    fetchModules,
    fetchCourse,
    updateProgress,
    markCourseCompleted,
    recordStudyTime,
    submitQuizScore,
    fetchLearningStats,
    initializeLearningData,
    saveLearningData
  }
})