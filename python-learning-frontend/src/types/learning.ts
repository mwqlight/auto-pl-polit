// 学习相关类型定义
export interface LearningModule {
  id: number
  title: string
  description: string
  moduleType: ModuleType
  difficultyLevel: DifficultyLevel
  estimatedTime: number // 分钟
  prerequisites: number[]
  lessons: Lesson[]
  completed: boolean
  progress: number
  lastAccessed?: string
}

export interface Lesson {
  id: number
  title: string
  content: string
  codeExamples: CodeExample[]
  exercises: Exercise[]
  completed: boolean
  duration: number
}

export interface CodeExample {
  id: number
  title: string
  description: string
  javaCode: string
  pythonCode: string
  explanation: string
  tags: string[]
}

export interface Exercise {
  id: number
  title: string
  description: string
  starterCode: string
  solutionCode: string
  hints: string[]
  difficulty: DifficultyLevel
  testCases: TestCase[]
}

export interface TestCase {
  input: string
  expectedOutput: string
  description: string
}

export enum ModuleType {
  BASIC_SYNTAX = 'BASIC_SYNTAX',
  OOP = 'OOP',
  FUNCTIONAL = 'FUNCTIONAL',
  EXCEPTIONS = 'EXCEPTIONS',
  DATA_STRUCTURES = 'DATA_STRUCTURES',
  ALGORITHMS = 'ALGORITHMS'
}

export enum DifficultyLevel {
  BEGINNER = 'BEGINNER',
  INTERMEDIATE = 'INTERMEDIATE',
  ADVANCED = 'ADVANCED',
  EXPERT = 'EXPERT'
}

export interface LearningProgress {
  userId: number
  moduleId: number
  progressPercentage: number
  completedLessons: number
  totalLessons: number
  timeSpent: number
  lastAccessed: string
  completed: boolean
  quizScore?: number
}