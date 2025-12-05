<template>
  <div class="login-container">
    <!-- 粒子背景 -->
    <div class="particles-background"></div>
    
    <!-- 登录表单 -->
    <div class="login-form-container">
      <el-card class="login-card" shadow="always">
        <template #header>
          <div class="login-header">
            <h1 class="login-title">Python语言学习驾驶舱</h1>
            <p class="login-subtitle">登录您的账户</p>
          </div>
        </template>
        
        <el-form 
          ref="loginFormRef" 
          :model="loginForm" 
          :rules="loginRules" 
          class="login-form"
          @submit.prevent="handleLogin"
        >
          <el-form-item prop="username">
            <el-input
              v-model="loginForm.username"
              placeholder="请输入用户名"
              size="large"
              prefix-icon="el-icon-user"
              clearable
            />
          </el-form-item>
          
          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              size="large"
              prefix-icon="el-icon-lock"
              show-password
              clearable
            />
          </el-form-item>
          
          <el-form-item>
            <el-button 
              type="primary" 
              size="large" 
              class="login-btn"
              :loading="loading"
              @click="handleLogin"
            >
              登录
            </el-button>
          </el-form-item>
          
          <div class="login-actions">
            <el-checkbox v-model="rememberMe">记住我</el-checkbox>
            <el-link type="primary" :underline="false">忘记密码？</el-link>
          </div>
          
          <div class="login-divider">
            <span>或</span>
          </div>
          
          <el-button 
            type="success" 
            size="large" 
            class="register-btn"
            @click="goToRegister"
          >
            注册新账户
          </el-button>
        </el-form>
      </el-card>
      
      <!-- 系统信息 -->
      <div class="system-info">
        <p>© 2024 Python语言学习驾驶舱系统 v{{ version }}</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store'
import { ElMessage } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

// 表单引用
const loginFormRef = ref<FormInstance>()

// 表单数据
const loginForm = reactive({
  username: '',
  password: ''
})

// 表单验证规则
const loginRules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
  ]
}

// 状态
const loading = ref(false)
const rememberMe = ref(false)
const version = ref(import.meta.env.VITE_APP_VERSION || '1.0.0')

// 登录处理
const handleLogin = async () => {
  if (!loginFormRef.value) return
  
  const valid = await loginFormRef.value.validate()
  if (!valid) return
  
  loading.value = true
  
  try {
    // 模拟登录请求
    await new Promise(resolve => setTimeout(resolve, 1000))
    
    // 模拟用户数据
    const mockUser = {
      id: 1,
      username: loginForm.username,
      email: `${loginForm.username}@example.com`,
      level: 1,
      experience: 0,
      totalLearningTime: 0,
      completedModules: 0,
      joinDate: new Date().toISOString(),
      lastLogin: new Date().toISOString()
    }
    
    const mockToken = 'mock-jwt-token-' + Date.now()
    
    // 存储用户信息和token
    userStore.setUser(mockUser)
    userStore.setToken(mockToken)
    
    ElMessage.success('登录成功！')
    
    // 跳转到仪表板
    router.push('/dashboard')
  } catch (error) {
    console.error('登录失败:', error)
    ElMessage.error('登录失败，请检查用户名和密码')
  } finally {
    loading.value = false
  }
}

// 跳转到注册页面
const goToRegister = () => {
  ElMessage.info('注册功能开发中...')
}

// 初始化
onMounted(() => {
  // 检查是否已登录
  if (userStore.isAuthenticated) {
    router.push('/dashboard')
  }
  
  // 尝试从本地存储恢复用户名
  const savedUsername = localStorage.getItem('rememberedUsername')
  if (savedUsername) {
    loginForm.username = savedUsername
    rememberMe.value = true
  }
})
</script>

<style scoped lang="scss">
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, var(--el-color-primary) 0%, var(--el-color-primary-light-3) 100%);
  position: relative;
  overflow: hidden;
}

.particles-background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: 
    radial-gradient(circle at 20% 80%, rgba(120, 119, 198, 0.3) 0%, transparent 50%),
    radial-gradient(circle at 80% 20%, rgba(255, 119, 198, 0.3) 0%, transparent 50%),
    radial-gradient(circle at 40% 40%, rgba(120, 219, 255, 0.3) 0%, transparent 50%);
  animation: float 6s ease-in-out infinite;
}

@keyframes float {
  0%, 100% { transform: translateY(0px) rotate(0deg); }
  50% { transform: translateY(-20px) rotate(180deg); }
}

.login-form-container {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 400px;
  padding: 20px;
}

.login-card {
  border: none;
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  
  :deep(.el-card__header) {
    border-bottom: 1px solid var(--el-border-color-light);
    padding: 30px 20px 20px;
  }
}

.login-header {
  text-align: center;
  
  .login-title {
    margin: 0 0 10px 0;
    font-size: 24px;
    font-weight: 600;
    color: var(--el-text-color-primary);
    background: linear-gradient(135deg, var(--el-color-primary), var(--el-color-primary-light-3));
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
  }
  
  .login-subtitle {
    margin: 0;
    font-size: 14px;
    color: var(--el-text-color-secondary);
  }
}

.login-form {
  padding: 20px 0 0;
  
  .el-form-item {
    margin-bottom: 20px;
  }
  
  :deep(.el-input__wrapper) {
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    
    &:hover {
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.15);
    }
  }
}

.login-btn {
  width: 100%;
  height: 45px;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 500;
  background: linear-gradient(135deg, var(--el-color-primary), var(--el-color-primary-light-3));
  border: none;
  
  &:hover {
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(var(--el-color-primary-rgb), 0.4);
  }
  
  &:active {
    transform: translateY(0);
  }
}

.login-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  font-size: 14px;
}

.login-divider {
  position: relative;
  text-align: center;
  margin: 20px 0;
  
  &::before {
    content: '';
    position: absolute;
    top: 50%;
    left: 0;
    right: 0;
    height: 1px;
    background: var(--el-border-color-light);
  }
  
  span {
    background: white;
    padding: 0 15px;
    color: var(--el-text-color-secondary);
    font-size: 12px;
  }
}

.register-btn {
  width: 100%;
  height: 45px;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 500;
  background: linear-gradient(135deg, var(--el-color-success), var(--el-color-success-light-3));
  border: none;
  
  &:hover {
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(var(--el-color-success-rgb), 0.4);
  }
}

.system-info {
  text-align: center;
  margin-top: 20px;
  
  p {
    margin: 0;
    font-size: 12px;
    color: rgba(255, 255, 255, 0.8);
  }
}

@media (max-width: 480px) {
  .login-form-container {
    padding: 10px;
    max-width: 100%;
  }
  
  .login-card {
    :deep(.el-card__header) {
      padding: 20px 15px 15px;
    }
  }
  
  .login-title {
    font-size: 20px !important;
  }
}
</style>