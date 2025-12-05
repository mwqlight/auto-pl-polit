import request from '@/utils/request'
import type { 
  ApiResponse, 
  CodeConversionRequest, 
  CodeConversionResponse,
  ConversionHistory,
  ConversionOptions
} from '@/types/api'

/**
 * 代码转换相关API
 */
export const converterApi = {
  /**
   * 转换Java代码为Python代码
   */
  convertJavaToPython: (data: CodeConversionRequest): Promise<ApiResponse<CodeConversionResponse>> => {
    return request.post('/v1/converter/java-to-python', data)
  },

  /**
   * 获取转换历史
   */
  getConversionHistory: (page: number = 1, size: number = 10): Promise<ApiResponse<{
    items: ConversionHistory[]
    total: number
    page: number
    size: number
  }>> => {
    return request.get('/v1/converter/history', {
      params: { page, size }
    })
  },

  /**
   * 获取转换历史详情
   */
  getConversionDetail: (historyId: number): Promise<ApiResponse<ConversionHistory>> => {
    return request.get(`/v1/converter/history/${historyId}`)
  },

  /**
   * 删除转换历史
   */
  deleteConversionHistory: (historyId: number): Promise<ApiResponse<void>> => {
    return request.delete(`/v1/converter/history/${historyId}`)
  },

  /**
   * 清空转换历史
   */
  clearConversionHistory: (): Promise<ApiResponse<void>> => {
    return request.delete('/v1/converter/history')
  },

  /**
   * 获取转换选项
   */
  getConversionOptions: (): Promise<ApiResponse<ConversionOptions>> => {
    return request.get('/v1/converter/options')
  },

  /**
   * 保存转换选项
   */
  saveConversionOptions: (options: ConversionOptions): Promise<ApiResponse<void>> => {
    return request.put('/v1/converter/options', options)
  },

  /**
   * 验证Java代码语法
   */
  validateJavaCode: (code: string): Promise<ApiResponse<{
    valid: boolean
    errors: Array<{
      line: number
      column: number
      message: string
      severity: 'error' | 'warning'
    }>
  }>> => {
    return request.post('/v1/converter/validate/java', { code })
  },

  /**
   * 验证Python代码语法
   */
  validatePythonCode: (code: string): Promise<ApiResponse<{
    valid: boolean
    errors: Array<{
      line: number
      column: number
      message: string
      severity: 'error' | 'warning'
    }>
  }>> => {
    return request.post('/v1/converter/validate/python', { code })
  },

  /**
   * 获取转换统计
   */
  getConversionStats: (): Promise<ApiResponse<{
    totalConversions: number
    successfulConversions: number
    averageConversionTime: number
    mostConvertedPatterns: Array<{
      pattern: string
      count: number
    }>
  }>> => {
    return request.get('/v1/converter/stats')
  }
}