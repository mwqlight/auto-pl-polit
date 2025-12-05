import request from '@/utils/request'
import type { 
  ApiResponse, 
  LearningModule, 
  Lesson, 
  LearningProgress,
  QuizResult,
  PaginationResponse
} from '@/types/api'

/**
 * 学习相关API
 */
export const learningApi = {
  /**
   * 获取所有学习模块
   */
  getAllModules: (): Promise<ApiResponse<LearningModule[]>> => {
    return request.get('/api/v1/learning-modules')
  },

  /**
   * 获取已发布的学习模块
   */
  getPublishedModules: (): Promise<ApiResponse<LearningModule[]>> => {
    return request.get('/api/v1/learning-modules/published')
  },

  /**
   * 根据ID获取学习模块
   */
  getModuleById: (id: number): Promise<ApiResponse<LearningModule>> => {
    return request.get(`/api/v1/learning-modules/${id}`)
  },

  /**
   * 根据难度级别获取学习模块
   */
  getModulesByDifficulty: (difficulty: string): Promise<ApiResponse<LearningModule[]>> => {
    return request.get(`/api/v1/learning-modules/difficulty/${difficulty}`)
  },

  /**
   * 搜索学习模块
   */
  searchModules: (keyword: string): Promise<ApiResponse<LearningModule[]>> => {
    return request.get('/api/v1/learning-modules/search', { params: { keyword } })
  },

  /**
   * 获取所有课程
   */
  getAllLessons: (): Promise<ApiResponse<Lesson[]>> => {
    return request.get('/api/v1/lessons')
  },

  /**
   * 根据模块ID获取课程
   */
  getLessonsByModuleId: (moduleId: number): Promise<ApiResponse<Lesson[]>> => {
    return request.get(`/api/v1/lessons/module/${moduleId}`)
  },

  /**
   * 根据ID获取课程
   */
  getLessonById: (id: number): Promise<ApiResponse<Lesson>> => {
    return request.get(`/api/v1/lessons/${id}`)
  },

  /**
   * 获取免费课程
   */
  getFreeLessons: (): Promise<ApiResponse<Lesson[]>> => {
    return request.get('/api/v1/lessons/free')
  },

  /**
   * 搜索课程
   */
  searchLessons: (keyword: string): Promise<ApiResponse<Lesson[]>> => {
    return request.get('/api/v1/lessons/search', { params: { keyword } })
  },

  /**
   * 获取课程详情（包含代码示例）
   */
  getLessonDetail: (id: number): Promise<ApiResponse<Lesson>> => {
    return request.get(`/api/v1/lessons/${id}/detail`)
  },

  /**
   * 获取用户学习进度
   */
  getLearningProgress: (): Promise<ApiResponse<LearningProgress[]>> => {
    return request.get('/api/v1/learning/progress')
  },

  /**
   * 更新学习进度
   */
  updateProgress: (lessonId: number, progress: number): Promise<ApiResponse<void>> => {
    return request.put(`/api/v1/learning/lessons/${lessonId}/progress`, { progress })
  },

  /**
   * 标记课程完成
   */
  completeLesson: (lessonId: number): Promise<ApiResponse<void>> => {
    return request.post(`/api/v1/learning/lessons/${lessonId}/complete`)
  },

  /**
   * 提交测验结果
   */
  submitQuiz: (lessonId: number, score: number, answers: any[]): Promise<ApiResponse<QuizResult>> => {
    return request.post(`/api/v1/learning/lessons/${lessonId}/quiz`, { score, answers })
  },

  /**
   * 获取测验历史
   */
  getQuizHistory: (lessonId: number): Promise<ApiResponse<QuizResult[]>> => {
    return request.get(`/api/v1/learning/lessons/${lessonId}/quiz-history`)
  },

  /**
   * 记录学习时间
   */
  recordLearningTime: (lessonId: number, duration: number): Promise<ApiResponse<void>> => {
    return request.post(`/api/v1/learning/lessons/${lessonId}/time`, { duration })
  }
}