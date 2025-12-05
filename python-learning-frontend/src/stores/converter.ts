import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { CodeConversionRequest, CodeConversionResponse, ConversionHistory, ConversionOptions } from '@/types/api'
import { request } from '@/utils/request'

export const useConverterStore = defineStore('converter', () => {
  // 状态
  const javaCode = ref<string>('')
  const pythonCode = ref<string>('')
  const conversionHistory = ref<ConversionHistory[]>([])
  const isConverting = ref<boolean>(false)
  const conversionOptions = ref<ConversionOptions>({
    preserveComments: true,
    includeTypeHints: true,
    optimizeForReadability: true,
    handleExceptions: true
  })

  // 计算属性
  const conversionTime = ref<number>(0)
  const conversionStats = computed(() => {
    const totalConversions = conversionHistory.value.length
    const successfulConversions = conversionHistory.value.filter(h => h.pythonCode.length > 0).length
    const averageTime = totalConversions > 0 
      ? conversionHistory.value.reduce((sum, h) => sum + h.conversionTime, 0) / totalConversions 
      : 0
    
    return {
      totalConversions,
      successfulConversions,
      successRate: totalConversions > 0 ? (successfulConversions / totalConversions) * 100 : 0,
      averageTime: Math.round(averageTime * 100) / 100
    }
  })

  const recentConversions = computed(() => {
    return conversionHistory.value
      .slice(-5)
      .reverse()
      .map(history => ({
        id: history.id,
        javaCode: history.javaCode.substring(0, 100) + (history.javaCode.length > 100 ? '...' : ''),
        pythonCode: history.pythonCode.substring(0, 100) + (history.pythonCode.length > 100 ? '...' : ''),
        conversionTime: history.conversionTime,
        createdAt: history.createdAt
      }))
  })

  // 代码转换方法
  const convertCode = async (code?: string, options?: ConversionOptions) => {
    const codeToConvert = code || javaCode.value
    const optionsToUse = options || conversionOptions.value
    
    if (!codeToConvert.trim()) {
      return { success: false, error: '请输入Java代码' }
    }

    isConverting.value = true
    const startTime = performance.now()
    
    try {
      const requestData: CodeConversionRequest = {
        javaCode: codeToConvert,
        targetLanguage: 'python',
        options: optionsToUse
      }

      const response = await request.post('/api/converter/convert', requestData)
      
      if (response.code === 200) {
        const conversionResponse: CodeConversionResponse = response.data
        
        pythonCode.value = conversionResponse.pythonCode
        conversionTime.value = conversionResponse.conversionTime
        
        // 添加到历史记录
        const historyItem: ConversionHistory = {
          id: generateId(),
          userId: 1, // 模拟用户ID
          javaCode: codeToConvert,
          pythonCode: conversionResponse.pythonCode,
          conversionTime: conversionResponse.conversionTime,
          createdAt: new Date().toISOString(),
          tags: extractTags(codeToConvert)
        }
        
        conversionHistory.value.push(historyItem)
        saveConversionHistory()
        
        return { 
          success: true, 
          data: conversionResponse,
          history: historyItem
        }
      } else {
        return { success: false, error: response.message }
      }
    } catch (error) {
      console.error('代码转换失败:', error)
      
      // 模拟转换作为备用方案
      const mockResult = mockConvertCode(codeToConvert, optionsToUse)
      const endTime = performance.now()
      
      pythonCode.value = mockResult.pythonCode
      conversionTime.value = endTime - startTime
      
      const historyItem: ConversionHistory = {
        id: generateId(),
        userId: 1,
        javaCode: codeToConvert,
        pythonCode: mockResult.pythonCode,
        conversionTime: endTime - startTime,
        createdAt: new Date().toISOString(),
        tags: extractTags(codeToConvert)
      }
      
      conversionHistory.value.push(historyItem)
      saveConversionHistory()
      
      return { 
        success: true, 
        data: mockResult,
        history: historyItem,
        isMock: true
      }
    } finally {
      isConverting.value = false
    }
  }

  // 清空代码
  const clearCode = () => {
    javaCode.value = ''
    pythonCode.value = ''
  }

  // 复制代码到剪贴板
  const copyToClipboard = async (code: string) => {
    try {
      await navigator.clipboard.writeText(code)
      return { success: true }
    } catch (error) {
      console.error('复制失败:', error)
      return { success: false, error: '复制失败' }
    }
  }

  // 下载代码
  const downloadCode = (code: string, filename: string) => {
    const blob = new Blob([code], { type: 'text/plain' })
    const url = URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = filename
    link.click()
    URL.revokeObjectURL(url)
  }

  // 获取转换历史
  const fetchConversionHistory = async () => {
    try {
      const response = await request.get('/api/converter/history')
      
      if (response.code === 200) {
        conversionHistory.value = response.data
        return { success: true, data: response.data }
      } else {
        return { success: false, error: response.message }
      }
    } catch (error) {
      console.error('获取转换历史失败:', error)
      // 加载本地历史记录
      loadConversionHistory()
      return { success: true, data: conversionHistory.value }
    }
  }

  // 删除历史记录
  const deleteHistoryItem = (id: string) => {
    conversionHistory.value = conversionHistory.value.filter(item => item.id !== id)
    saveConversionHistory()
  }

  // 清空历史记录
  const clearHistory = () => {
    conversionHistory.value = []
    localStorage.removeItem('conversionHistory')
  }

  // 更新转换选项
  const updateConversionOptions = (options: Partial<ConversionOptions>) => {
    conversionOptions.value = { ...conversionOptions.value, ...options }
    localStorage.setItem('conversionOptions', JSON.stringify(conversionOptions.value))
  }

  // 保存历史记录到本地
  const saveConversionHistory = () => {
    try {
      localStorage.setItem('conversionHistory', JSON.stringify(conversionHistory.value))
    } catch (error) {
      console.error('保存转换历史失败:', error)
    }
  }

  // 从本地加载历史记录
  const loadConversionHistory = () => {
    try {
      const savedHistory = localStorage.getItem('conversionHistory')
      if (savedHistory) {
        conversionHistory.value = JSON.parse(savedHistory)
      }
    } catch (error) {
      console.error('加载转换历史失败:', error)
    }
  }

  // 加载转换选项
  const loadConversionOptions = () => {
    try {
      const savedOptions = localStorage.getItem('conversionOptions')
      if (savedOptions) {
        conversionOptions.value = { ...conversionOptions.value, ...JSON.parse(savedOptions) }
      }
    } catch (error) {
      console.error('加载转换选项失败:', error)
    }
  }

  // 模拟代码转换
  const mockConvertCode = (javaCode: string, options: ConversionOptions): CodeConversionResponse => {
    // 简单的Java到Python转换规则
    let pythonCode = javaCode
      .replace(/public\s+class\s+(\w+)/g, 'class $1:')
      .replace(/public\s+static\s+void\s+main\s*\([^)]*\)/g, 'if __name__ == "__main__":')
      .replace(/System\.out\.print(ln)?\(/g, 'print(')
      .replace(/int\s+(\w+)\s*=/g, '$1 =')
      .replace(/String\s+(\w+)\s*=/g, '$1 =')
      .replace(/boolean\s+(\w+)\s*=/g, '$1 =')
      .replace(/double\s+(\w+)\s*=/g, '$1 =')
      .replace(/\/\/.*$/gm, '# $&'.replace('//', ''))
      .replace(/\/\*[\s\S]*?\*\//g, match => `"""${match.replace(/\/\*|\*\//g, '')}"""`)
      .replace(/&&/g, 'and')
      .replace(/\|\|/g, 'or')
      .replace(/!/g, 'not ')
      .replace(/true/g, 'True')
      .replace(/false/g, 'False')
      .replace(/null/g, 'None')

    // 处理缩进
    const lines = pythonCode.split('\n')
    let indentLevel = 0
    const resultLines: string[] = []
    
    for (const line of lines) {
      const trimmedLine = line.trim()
      
      if (trimmedLine.endsWith('{')) {
        resultLines.push('  '.repeat(indentLevel) + trimmedLine.replace('{', ':'))
        indentLevel++
      } else if (trimmedLine.startsWith('}')) {
        indentLevel = Math.max(0, indentLevel - 1)
        resultLines.push('  '.repeat(indentLevel) + '# End of block')
      } else {
        resultLines.push('  '.repeat(indentLevel) + trimmedLine)
      }
    }
    
    pythonCode = resultLines.join('\n')

    return {
      pythonCode,
      conversionTime: Math.random() * 100 + 50, // 模拟转换时间
      warnings: [],
      suggestions: [
        {
          type: 'best-practice',
          message: '建议添加类型注解以提高代码可读性',
          codeSnippet: 'def function_name(param: type) -> return_type:'
        }
      ]
    }
  }

  // 生成唯一ID
  const generateId = (): string => {
    return Date.now().toString(36) + Math.random().toString(36).substr(2)
  }

  // 提取代码标签
  const extractTags = (code: string): string[] => {
    const tags: string[] = []
    
    if (code.includes('class')) tags.push('class')
    if (code.includes('interface')) tags.push('interface')
    if (code.includes('extends')) tags.push('inheritance')
    if (code.includes('implements')) tags.push('interface')
    if (code.includes('public') || code.includes('private') || code.includes('protected')) tags.push('access-modifiers')
    if (code.includes('static')) tags.push('static')
    if (code.includes('final')) tags.push('final')
    if (code.includes('try') || code.includes('catch') || code.includes('throw')) tags.push('exception')
    if (code.includes('for') || code.includes('while')) tags.push('loop')
    if (code.includes('if') || code.includes('else') || code.includes('switch')) tags.push('condition')
    
    return tags
  }

  // 初始化转换器
  const initializeConverter = () => {
    loadConversionHistory()
    loadConversionOptions()
  }

  return {
    // 状态
    javaCode,
    pythonCode,
    conversionHistory,
    isConverting,
    conversionOptions,
    conversionTime,
    
    // 计算属性
    conversionStats,
    recentConversions,
    
    // 方法
    convertCode,
    clearCode,
    copyToClipboard,
    downloadCode,
    fetchConversionHistory,
    deleteHistoryItem,
    clearHistory,
    updateConversionOptions,
    initializeConverter
  }
})