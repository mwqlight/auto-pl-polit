import request from '@/utils/request'
import type { 
  ApiResponse, 
  LearningModule, 
  Course, 
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
  getModules: (): Promise<ApiResponse<LearningModule[]>> => {
    return request.get('/api/v1/learning/modules')
  },

  /**
   * 获取模块详情
   */
  getModule: (moduleId: number): Promise<ApiResponse<LearningModule>> => {
    return request.get(`/api/v1/learning/modules/${moduleId}`)
  },

  /**
   * 获取模块下的课程列表
   */
  getCoursesByModule: (moduleId: number): Promise<ApiResponse<Course[]>> => {
    return request.get(`/api/v1/learning/modules/${moduleId}/courses`)
  },

  /**
   * 获取课程详情
   */
  getCourse: (courseId: number): Promise<ApiResponse<Course>> => {
    return request.get(`/api/v1/learning/courses/${courseId}`)
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
  updateProgress: (courseId: number, progress: number): Promise<ApiResponse<void>> => {
    return request.put(`/api/v1/learning/courses/${courseId}/progress`, { progress })
  },

  /**
   * 标记课程完成
   */
  completeCourse: (courseId: number): Promise<ApiResponse<void>> => {
    return request.post(`/api/v1/learning/courses/${courseId}/complete`)
  },

  /**
   * 提交测验结果
   */
  submitQuiz: (courseId: number, score: number, answers: any[]): Promise<ApiResponse<QuizResult>> => {
    return request.post(`/api/v1/learning/courses/${courseId}/quiz`, { score, answers })
  },

  /**
   * 获取测验历史
   */
  getQuizHistory: (courseId: number): Promise<ApiResponse<QuizResult[]>> => {
    return request.get(`/api/v1/learning/courses/${courseId}/quiz-history`)
  },

  /**
   * 记录学习时间
   */
  recordLearningTime: (courseId: number, duration: number): Promise<ApiResponse<void>> => {
    return request.post(`/api/v1/learning/courses/${courseId}/time`, { duration })
  },

  /**
   * 搜索课程
   */
  searchCourses: (keyword: string, page: number = 1, size: number = 10): Promise<ApiResponse<PaginationResponse<Course>>> => {
    return request.get('/api/v1/learning/courses/search', {
      params: { keyword, page, size }
    })
  }
}