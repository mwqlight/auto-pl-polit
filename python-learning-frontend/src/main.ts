import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'element-plus/theme-chalk/dark/css-vars.css'

import App from './App.vue'
import router from './router'

// 全局样式
import './styles/index.scss'

// 创建应用实例
const app = createApp(App)

// 使用状态管理
const pinia = createPinia()
app.use(pinia)

// 使用路由
app.use(router)

// 使用Element Plus
app.use(ElementPlus)

// 挂载应用
app.mount('#app')

// 初始化用户状态
import { useUserStore } from '@/stores/user'
const userStore = useUserStore()
userStore.initializeUser()

// 初始化学习数据
import { useLearningStore } from '@/stores/learning'
const learningStore = useLearningStore()
learningStore.initializeLearningData()

// 初始化转换器
import { useConverterStore } from '@/stores/converter'
const converterStore = useConverterStore()
converterStore.initializeConverter()

console.log('Python学习平台前端应用已启动')

// 移除加载动画
document.addEventListener('DOMContentLoaded', () => {
  const loadingScreen = document.querySelector('.loading-screen')
  if (loadingScreen) {
    setTimeout(() => {
      loadingScreen.remove()
    }, 500)
  }
})