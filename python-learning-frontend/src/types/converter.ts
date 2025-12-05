// 代码转换相关类型定义
export interface CodeConversionRequest {
  sourceCode: string
  sourceLanguage: 'java' | 'javascript' | 'c++' | 'c#'
  targetLanguage: 'python'
  options?: ConversionOptions
}

export interface ConversionOptions {
  indentSize?: number
  namingConvention?: 'snake_case' | 'camelCase'
  includeComments?: boolean
  handleExceptions?: boolean
  optimizeImports?: boolean
  preserveStructure?: boolean
}

export interface CodeConversionResponse {
  success: boolean
  convertedCode: string
  conversionScore: number
  warnings: string[]
  errors: string[]
  executionTime: number
}

export interface ConversionHistory {
  id: number
  sourceCode: string
  targetCode: string
  sourceLanguage: string
  targetLanguage: string
  timestamp: string
  conversionScore: number
  fileName?: string
  fileSize?: number
}