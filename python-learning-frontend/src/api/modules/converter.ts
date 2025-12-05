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
   * 转换代码
   */
  convertCode: (data: CodeConversionRequest): Promise<ApiResponse<CodeConversionResponse>> => {
    return request.post('/api/v1/converter/convert', data)
  },

  /**
   * 获取转换历史
   */
  getConversionHistory: (userId: number = 1, limit: number = 10): Promise<ApiResponse<ConversionHistory[]>> => {
    return request.get('/api/v1/converter/history', {
      params: { userId, limit }
    })
  },

  /**
   * 获取转换历史详情
   */
  getConversionDetail: (historyId: number): Promise<ApiResponse<ConversionHistory>> => {
    return request.get(`/api/v1/converter/history/${historyId}`)
  },

  /**
   * 删除转换历史
   */
  deleteConversionHistory: (historyId: number): Promise<ApiResponse<void>> => {
    return request.delete(`/api/v1/converter/history/${historyId}`)
  },

  /**
   * 清空转换历史
   */
  clearConversionHistory: (userId: number = 1): Promise<ApiResponse<void>> => {
    return request.delete('/api/v1/converter/history', {
      params: { userId }
    })
  },

  /**
   * 获取转换统计
   */
  getConversionStats: (userId: number = 1): Promise<ApiResponse<{
    totalConversions: number
    totalSuccess: number
    totalFailed: number
    averageScore: number
    totalLinesProcessed: number
    totalExecutionTime: number
  }>> => {
    return request.get('/api/v1/converter/stats', {
      params: { userId }
    })
  },

  /**
   * 获取转换选项
   */
  getConversionOptions: (): Promise<ApiResponse<ConversionOptions>> => {
    return request.get('/api/v1/converter/options')
  },

  /**
   * 保存转换选项
   */
  saveConversionOptions: (options: ConversionOptions): Promise<ApiResponse<void>> => {
    return request.put('/api/v1/converter/options', options)
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
    return request.post('/api/v1/converter/validate/java', { code })
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
    return request.post('/api/v1/converter/validate/python', { code })
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
    return request.get('/api/v1/converter/stats')
  }
}