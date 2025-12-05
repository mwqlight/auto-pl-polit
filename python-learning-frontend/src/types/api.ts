/**
 * API相关类型定义
 */

// 基础响应类型
export interface BaseResponse<T = any> {
  code: number
  message: string
  data: T
  timestamp: number
}

// API响应类型（兼容不同命名风格）
export interface ApiResponse<T = any> extends BaseResponse<T> {
}

// 分页响应类型
export interface PaginationResponse<T = any> {
  items: T[]
  total: number
  page: number
  size: number
  pages: number
}

// 列表响应类型
export interface ListResponse<T = any> extends BaseResponse<PaginationResponse<T>> {}

// 错误响应类型
export interface ErrorResponse {
  code: number
  message: string
  details?: string
  timestamp: number
}

// 用户相关类型
export interface User {
  id: number
  username: string
  email: string
  avatar?: string
  bio?: string
  createTime: string
  lastLoginTime: string
  status: 'active' | 'inactive' | 'banned'
}

// 用户登录请求
export interface LoginRequest {
  username: string
  password: string
  rememberMe?: boolean
}

// 用户登录响应
export interface LoginResponse {
  user: User
  token: string
  refreshToken: string
  expiresIn: number
}

// 用户注册请求
export interface RegisterRequest {
  username: string
  email: string
  password: string
  captcha: string
}

// 用户信息更新请求
export interface UpdateUserRequest {
  username?: string
  email?: string
  avatar?: string
  bio?: string
}

// 密码修改请求
export interface ChangePasswordRequest {
  currentPassword: string
  newPassword: string
}

// 学习相关类型
export interface LearningModule {
  id: string
  name: string
  description: string
  icon: string
  progress: number
  totalLessons: number
  completedLessons: number
  estimatedTime: number
  difficulty: 'beginner' | 'intermediate' | 'advanced'
}

// 课程类型
export interface Lesson {
  id: string
  moduleId: string
  title: string
  description: string
  content: string
  codeExamples: CodeExample[]
  exercises: Exercise[]
  order: number
  estimatedTime: number
  completed: boolean
  progress: number
}

// 代码示例类型
export interface CodeExample {
  id: string
  title: string
  description: string
  javaCode: string
  pythonCode: string
  explanation: string
  tags: string[]
}

// 练习题目类型
export interface Exercise {
  id: string
  title: string
  description: string
  question: string
  javaTemplate: string
  pythonTemplate: string
  testCases: TestCase[]
  difficulty: 'easy' | 'medium' | 'hard'
  hints?: string[]
  solution?: string
}

// 测试用例类型
export interface TestCase {
  input: string
  expectedOutput: string
  description?: string
}

// 学习进度类型
export interface LearningProgress {
  userId: number
  lessonId: string
  progress: number
  completed: boolean
  lastStudyTime: string
  timeSpent: number
  quizScores: QuizScore[]
}

// 测验分数类型
export interface QuizScore {
  quizId: string
  score: number
  total: number
  timeSpent: number
  completedAt: string
}

// 代码转换相关类型
export interface CodeConversionRequest {
  javaCode: string
  targetLanguage: 'python'
  options?: ConversionOptions
}

// 转换选项
export interface ConversionOptions {
  preserveComments?: boolean
  includeTypeHints?: boolean
  optimizeForReadability?: boolean
  handleExceptions?: boolean
}

// 代码转换响应
export interface CodeConversionResponse {
  pythonCode: string
  conversionTime: number
  warnings: ConversionWarning[]
  suggestions: ConversionSuggestion[]
}

// 转换警告
export interface ConversionWarning {
  type: 'syntax' | 'semantic' | 'performance'
  message: string
  lineNumber?: number
  severity: 'low' | 'medium' | 'high'
}

// 转换建议
export interface ConversionSuggestion {
  type: 'optimization' | 'best-practice' | 'style'
  message: string
  codeSnippet?: string
}

// 转换历史记录
export interface ConversionHistory {
  id: string
  userId: number
  javaCode: string
  pythonCode: string
  conversionTime: number
  createdAt: string
  tags?: string[]
}

// 知识库相关类型
export interface KnowledgeBaseItem {
  id: string
  title: string
  content: string
  category: 'syntax' | 'oop' | 'functional' | 'exceptions' | 'libraries' | 'tools'
  tags: string[]
  difficulty: 'beginner' | 'intermediate' | 'advanced'
  relatedItems: string[]
  lastUpdated: string
  author?: string
}

// 知识库文章类型
export interface KnowledgeBaseArticle {
  id: number
  title: string
  content: string
  categoryId: number
  categoryName: string
  tags: string[]
  difficulty: 'beginner' | 'intermediate' | 'advanced'
  viewCount: number
  likeCount: number
  favoriteCount: number
  commentCount: number
  lastUpdated: string
  author?: string
  isLiked?: boolean
  isFavorited?: boolean
}

// 知识库分类类型
export interface KnowledgeBaseCategory {
  id: number
  name: string
  description?: string
  articleCount: number
  icon?: string
  order: number
}

// 搜索请求
export interface SearchRequest {
  query: string
  category?: string
  difficulty?: string
  tags?: string[]
  page?: number
  size?: number
}

// 搜索响应
export interface SearchResponse<T = any> extends PaginationResponse<T> {
  query: string
  filters: SearchFilters
  suggestions?: string[]
}

// 搜索过滤器
export interface SearchFilters {
  categories: string[]
  difficulties: string[]
  tags: string[]
}

// 文件上传相关类型
export interface FileUploadRequest {
  file: File
  category?: string
  tags?: string[]
}

// 文件上传响应
export interface FileUploadResponse {
  id: string
  filename: string
  url: string
  size: number
  mimeType: string
  uploadedAt: string
}

// 通知相关类型
export interface Notification {
  id: string
  type: 'system' | 'learning' | 'achievement' | 'reminder'
  title: string
  content: string
  read: boolean
  createdAt: string
  actionUrl?: string
  priority: 'low' | 'medium' | 'high'
}

// 成就相关类型
export interface Achievement {
  id: string
  name: string
  description: string
  icon: string
  category: 'learning' | 'coding' | 'community' | 'mastery'
  difficulty: 'bronze' | 'silver' | 'gold' | 'platinum'
  progress: number
  target: number
  unlocked: boolean
  unlockedAt?: string
}

// 统计数据类型
export interface UserStats {
  userId: number
  totalLearningTime: number
  completedLessons: number
  conversionCount: number
  achievementCount: number
  averageQuizScore: number
  streakDays: number
  lastActiveDate: string
}

// 设置相关类型
export interface UserSettings {
  notifications: NotificationSettings
  learning: LearningSettings
  appearance: AppearanceSettings
  privacy: PrivacySettings
}

// 通知设置
export interface NotificationSettings {
  emailNotifications: boolean
  courseReminders: boolean
  achievementAlerts: boolean
  systemUpdates: boolean
}

// 学习设置
export interface LearningSettings {
  defaultLanguage: 'zh-CN' | 'en-US'
  autoSaveInterval: number
  showHints: boolean
  enableVoice: boolean
  difficultyLevel: 'beginner' | 'intermediate' | 'advanced'
}

// 外观设置
export interface AppearanceSettings {
  theme: 'light' | 'dark' | 'auto'
  codeTheme: string
  fontSize: number
  lineHeight: number
}

// 隐私设置
export interface PrivacySettings {
  showProfile: boolean
  showProgress: boolean
  allowDataCollection: boolean
  autoDeleteData: boolean
}

// API请求配置
export interface ApiRequestConfig {
  showLoading?: boolean
  showError?: boolean
  retryCount?: number
  timeout?: number
}

// 上传进度回调
export type UploadProgressCallback = (progress: number) => void

// 错误处理回调
export type ErrorHandler = (error: ErrorResponse) => void

// API方法类型定义
export interface ApiMethods {
  // 用户相关
  login: (data: LoginRequest) => Promise<LoginResponse>
  register: (data: RegisterRequest) => Promise<BaseResponse>
  getUserInfo: () => Promise<User>
  updateUserInfo: (data: UpdateUserRequest) => Promise<User>
  changePassword: (data: ChangePasswordRequest) => Promise<BaseResponse>
  
  // 学习相关
  getLearningModules: () => Promise<LearningModule[]>
  getLesson: (lessonId: string) => Promise<Lesson>
  updateProgress: (data: { lessonId: string; progress: number }) => Promise<BaseResponse>
  
  // 代码转换
  convertCode: (data: CodeConversionRequest) => Promise<CodeConversionResponse>
  getConversionHistory: (params?: { page?: number; size?: number }) => Promise<PaginationResponse<ConversionHistory>>
  
  // 知识库
  searchKnowledge: (params: SearchRequest) => Promise<SearchResponse<KnowledgeBaseItem>>
  getKnowledgeItem: (id: string) => Promise<KnowledgeBaseItem>
  getAllArticles: () => Promise<ApiResponse<KnowledgeBaseArticle[]>>
  getArticleById: (id: number) => Promise<ApiResponse<KnowledgeBaseArticle>>
  getArticlesByCategory: (categoryId: number) => Promise<ApiResponse<KnowledgeBaseArticle[]>>
  searchArticles: (keyword: string) => Promise<ApiResponse<KnowledgeBaseArticle[]>>
  getAllCategories: () => Promise<ApiResponse<KnowledgeBaseCategory[]>>
  getCategoryById: (id: number) => Promise<ApiResponse<KnowledgeBaseCategory>>
  getPopularArticles: (limit?: number) => Promise<ApiResponse<KnowledgeBaseArticle[]>>
  getLatestArticles: (limit?: number) => Promise<ApiResponse<KnowledgeBaseArticle[]>>
  searchKnowledgeBase: (data: SearchRequest) => Promise<ApiResponse<SearchResponse>>
  recordArticleView: (articleId: number) => Promise<ApiResponse<void>>
  likeArticle: (articleId: number) => Promise<ApiResponse<void>>
  unlikeArticle: (articleId: number) => Promise<ApiResponse<void>>
  favoriteArticle: (articleId: number) => Promise<ApiResponse<void>>
  unfavoriteArticle: (articleId: number) => Promise<ApiResponse<void>>
  getFavoriteArticles: (userId?: number) => Promise<ApiResponse<KnowledgeBaseArticle[]>>
  getArticleComments: (articleId: number) => Promise<ApiResponse<any[]>>
  addArticleComment: (articleId: number, content: string) => Promise<ApiResponse<any>>
  deleteArticleComment: (commentId: number) => Promise<ApiResponse<void>>
  
  // 文件操作
  uploadFile: (data: FileUploadRequest, onProgress?: UploadProgressCallback) => Promise<FileUploadResponse>
  
  // 通知
  getNotifications: (params?: { unreadOnly?: boolean }) => Promise<Notification[]>
  markNotificationRead: (id: string) => Promise<BaseResponse>
  
  // 统计
  getUserStats: () => Promise<UserStats>
  getAchievements: () => Promise<Achievement[]>
}

// 枚举类型定义
export enum ApiErrorCode {
  // 成功
  SUCCESS = 200,
  
  // 客户端错误
  BAD_REQUEST = 400,
  UNAUTHORIZED = 401,
  FORBIDDEN = 403,
  NOT_FOUND = 404,
  
  // 服务器错误
  INTERNAL_ERROR = 500,
  SERVICE_UNAVAILABLE = 503,
  
  // 业务错误
  USER_NOT_FOUND = 10001,
  USER_EXISTS = 10002,
  INVALID_CREDENTIALS = 10003,
  LESSON_NOT_FOUND = 10004,
  CONVERSION_FAILED = 10005,
  FILE_UPLOAD_FAILED = 10006
}

export enum UserStatus {
  ACTIVE = 'active',
  INACTIVE = 'inactive',
  BANNED = 'banned'
}

export enum CourseDifficulty {
  BEGINNER = 'beginner',
  INTERMEDIATE = 'intermediate',
  ADVANCED = 'advanced'
}

export enum ExerciseDifficulty {
  EASY = 'easy',
  MEDIUM = 'medium',
  HARD = 'hard'
}

export enum NotificationType {
  SYSTEM = 'system',
  LEARNING = 'learning',
  ACHIEVEMENT = 'achievement',
  REMINDER = 'reminder'
}

export enum AchievementDifficulty {
  BRONZE = 'bronze',
  SILVER = 'silver',
  GOLD = 'gold',
  PLATINUM = 'platinum'
}