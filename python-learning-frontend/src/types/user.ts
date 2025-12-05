// 用户相关类型定义
export interface User {
  id: number
  username: string
  email: string
  avatar?: string
  level: number
  experience: number
  totalLearningTime: number
  completedModules: number
  joinDate: string
  lastLogin: string
}

export interface LoginRequest {
  username: string
  password: string
}

export interface RegisterRequest {
  username: string
  email: string
  password: string
  confirmPassword: string
}

export interface UserProfile extends User {
  bio?: string
  website?: string
  github?: string
  learningGoals: string[]
  favoriteTopics: string[]
}