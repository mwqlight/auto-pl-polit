import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { User } from '@/types/user'

// 用户状态管理
export const useUserStore = defineStore('user', () => {
  const user = ref<User | null>(null)
  const token = ref<string | null>(localStorage.getItem('token'))
  const isAuthenticated = computed(() => !!token.value)

  const setUser = (userData: User) => {
    user.value = userData
  }

  const setToken = (newToken: string) => {
    token.value = newToken
    localStorage.setItem('token', newToken)
  }

  const logout = () => {
    user.value = null
    token.value = null
    localStorage.removeItem('token')
  }

  return {
    user,
    token,
    isAuthenticated,
    setUser,
    setToken,
    logout
  }
})

// 应用状态管理
export const useAppStore = defineStore('app', () => {
  const theme = ref<'light' | 'dark'>('dark')
  const sidebarCollapsed = ref(false)
  const loading = ref(false)
  const globalError = ref<string | null>(null)

  const toggleTheme = () => {
    theme.value = theme.value === 'light' ? 'dark' : 'light'
    document.documentElement.setAttribute('data-theme', theme.value)
  }

  const toggleSidebar = () => {
    sidebarCollapsed.value = !sidebarCollapsed.value
  }

  const setLoading = (isLoading: boolean) => {
    loading.value = isLoading
  }

  const setError = (error: string | null) => {
    globalError.value = error
  }

  return {
    theme,
    sidebarCollapsed,
    loading,
    globalError,
    toggleTheme,
    toggleSidebar,
    setLoading,
    setError
  }
})

// 代码转换状态管理
export const useConverterStore = defineStore('converter', () => {
  const sourceCode = ref('')
  const targetCode = ref('')
  const conversionHistory = ref<any[]>([])
  const isConverting = ref(false)
  const conversionOptions = ref({
    indentSize: 4,
    namingConvention: 'snake_case' as 'snake_case' | 'camelCase',
    includeComments: true,
    handleExceptions: true,
    optimizeImports: true
  })

  const convertCode = async (code: string) => {
    isConverting.value = true
    try {
      // 这里会调用后端的转换API
      // 暂时模拟转换结果
      await new Promise(resolve => setTimeout(resolve, 1000))
      targetCode.value = simulateJavaToPythonConversion(code)
      
      // 添加到历史记录
      conversionHistory.value.unshift({
        id: Date.now(),
        sourceCode: code,
        targetCode: targetCode.value,
        timestamp: new Date(),
        options: { ...conversionOptions.value }
      })
    } catch (error) {
      console.error('代码转换失败:', error)
      throw error
    } finally {
      isConverting.value = false
    }
  }

  const clearHistory = () => {
    conversionHistory.value = []
  }

  return {
    sourceCode,
    targetCode,
    conversionHistory,
    isConverting,
    conversionOptions,
    convertCode,
    clearHistory
  }
})

// 模拟Java到Python的代码转换
function simulateJavaToPythonConversion(javaCode: string): string {
  // 简单的转换规则示例
  let pythonCode = javaCode
    .replace(/public class/g, 'class')
    .replace(/public static void main\(String\[\] args\)/g, 'if __name__ == "__main__":')
    .replace(/System\.out\.println/g, 'print')
    .replace(/;/g, '')
    .replace(/\/\/.*$/gm, '# $&'.replace('//', ''))
    .replace(/\{\s*/g, ':')
    .replace(/\s*\}/g, '')
    .replace(/int\s+(\w+)\s*=/g, '$1 =')
    .replace(/String\s+(\w+)\s*=/g, '$1 =')
    .replace(/boolean\s+(\w+)\s*=/g, '$1 =')

  return pythonCode
}