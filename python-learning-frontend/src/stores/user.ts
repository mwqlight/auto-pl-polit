import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { User, LoginRequest, RegisterRequest, UpdateUserRequest, ChangePasswordRequest, UserSettings } from '@/types/api'
import { request } from '@/utils/request'

export const useUserStore = defineStore('user', () => {
  // 状态
  const user = ref<User | null>(null)
  const token = ref<string | null>(localStorage.getItem('token'))
  const refreshToken = ref<string | null>(localStorage.getItem('refreshToken'))
  const settings = ref<UserSettings>({
    notifications: {
      emailNotifications: true,
      courseReminders: true,
      achievementAlerts: true,
      systemUpdates: false
    },
    learning: {
      defaultLanguage: 'zh-CN',
      autoSaveInterval: 30,
      showHints: true,
      enableVoice: false,
      difficultyLevel: 'beginner'
    },
    appearance: {
      theme: 'auto',
      codeTheme: 'github',
      fontSize: 14,
      lineHeight: 1.5
    },
    privacy: {
      showProfile: true,
      showProgress: true,
      allowDataCollection: false,
      autoDeleteData: false
    }
  })

  // 计算属性
  const isAuthenticated = computed(() => !!token.value)
  const userInfo = computed(() => user.value)
  const userSettings = computed(() => settings.value)

  // 登录方法
  const login = async (loginData: LoginRequest) => {
    try {
      // 模拟登录请求
      const response = await request.post('/api/auth/login', loginData)
      
      if (response.code === 200) {
        const { user: userData, token: authToken, refreshToken: refresh } = response.data
        
        // 保存用户信息和token
        user.value = userData
        token.value = authToken
        refreshToken.value = refresh
        
        // 保存到本地存储
        localStorage.setItem('token', authToken)
        localStorage.setItem('refreshToken', refresh)
        localStorage.setItem('user', JSON.stringify(userData))
        
        // 加载用户设置
        loadUserSettings()
        
        return { success: true, data: response.data }
      } else {
        return { success: false, error: response.message }
      }
    } catch (error) {
      console.error('登录失败:', error)
      return { success: false, error: '登录失败，请检查网络连接' }
    }
  }

  // 注册方法
  const register = async (registerData: RegisterRequest) => {
    try {
      const response = await request.post('/api/auth/register', registerData)
      
      if (response.code === 200) {
        return { success: true, data: response.data }
      } else {
        return { success: false, error: response.message }
      }
    } catch (error) {
      console.error('注册失败:', error)
      return { success: false, error: '注册失败，请检查网络连接' }
    }
  }

  // 登出方法
  const logout = async () => {
    try {
      // 调用登出接口
      await request.post('/api/auth/logout')
    } catch (error) {
      console.error('登出失败:', error)
    } finally {
      // 清除本地状态
      user.value = null
      token.value = null
      refreshToken.value = null
      
      // 清除本地存储
      localStorage.removeItem('token')
      localStorage.removeItem('refreshToken')
      localStorage.removeItem('user')
      localStorage.removeItem('userSettings')
      
      // 重定向到登录页
      window.location.href = '/login'
    }
  }

  // 获取用户信息
  const fetchUserInfo = async () => {
    try {
      const response = await request.get('/api/user/info')
      
      if (response.code === 200) {
        user.value = response.data
        localStorage.setItem('user', JSON.stringify(response.data))
        return { success: true, data: response.data }
      } else {
        return { success: false, error: response.message }
      }
    } catch (error) {
      console.error('获取用户信息失败:', error)
      return { success: false, error: '获取用户信息失败' }
    }
  }

  // 更新用户信息
  const updateUserInfo = async (userData: UpdateUserRequest) => {
    try {
      const response = await request.put('/api/user/info', userData)
      
      if (response.code === 200) {
        user.value = { ...user.value, ...userData }
        localStorage.setItem('user', JSON.stringify(user.value))
        return { success: true, data: response.data }
      } else {
        return { success: false, error: response.message }
      }
    } catch (error) {
      console.error('更新用户信息失败:', error)
      return { success: false, error: '更新用户信息失败' }
    }
  }

  // 修改密码
  const changePassword = async (passwordData: ChangePasswordRequest) => {
    try {
      const response = await request.post('/api/user/change-password', passwordData)
      
      if (response.code === 200) {
        return { success: true, data: response.data }
      } else {
        return { success: false, error: response.message }
      }
    } catch (error) {
      console.error('修改密码失败:', error)
      return { success: false, error: '修改密码失败' }
    }
  }

  // 加载用户设置
  const loadUserSettings = () => {
    try {
      const savedSettings = localStorage.getItem('userSettings')
      if (savedSettings) {
        settings.value = { ...settings.value, ...JSON.parse(savedSettings) }
      }
    } catch (error) {
      console.error('加载用户设置失败:', error)
    }
  }

  // 保存用户设置
  const saveUserSettings = async (newSettings: Partial<UserSettings>) => {
    try {
      settings.value = { ...settings.value, ...newSettings }
      localStorage.setItem('userSettings', JSON.stringify(settings.value))
      
      // 同步到服务器
      await request.put('/api/user/settings', settings.value)
      
      return { success: true }
    } catch (error) {
      console.error('保存用户设置失败:', error)
      return { success: false, error: '保存用户设置失败' }
    }
  }

  // 刷新token
  const refreshAuthToken = async () => {
    try {
      if (!refreshToken.value) {
        throw new Error('没有刷新token')
      }
      
      const response = await request.post('/api/auth/refresh', {
        refreshToken: refreshToken.value
      })
      
      if (response.code === 200) {
        const { token: newToken, refreshToken: newRefreshToken } = response.data
        
        token.value = newToken
        refreshToken.value = newRefreshToken
        
        localStorage.setItem('token', newToken)
        localStorage.setItem('refreshToken', newRefreshToken)
        
        return { success: true }
      } else {
        throw new Error(response.message)
      }
    } catch (error) {
      console.error('刷新token失败:', error)
      logout()
      return { success: false, error: 'token已过期，请重新登录' }
    }
  }

  // 初始化用户状态
  const initializeUser = () => {
    if (token.value) {
      // 从本地存储加载用户信息
      const savedUser = localStorage.getItem('user')
      if (savedUser) {
        try {
          user.value = JSON.parse(savedUser)
        } catch (error) {
          console.error('解析用户信息失败:', error)
        }
      }
      
      // 加载用户设置
      loadUserSettings()
      
      // 验证token是否有效
      fetchUserInfo().catch((error) => {
        console.error('初始化用户信息失败:', error)
        logout()
      })
    }
  }

  return {
    // 状态
    user,
    token,
    refreshToken,
    settings,
    
    // 计算属性
    isAuthenticated,
    userInfo,
    userSettings,
    
    // 方法
    login,
    register,
    logout,
    fetchUserInfo,
    updateUserInfo,
    changePassword,
    saveUserSettings,
    refreshAuthToken,
    initializeUser
  }
})