import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'element-plus/theme-chalk/dark/css-vars.css'

import App from './App.vue'
import router from './router'

// 全局样式
import './styles/index.scss'

// 导入store
import { useUserStore } from '@/stores/user'
import { useLearningStore } from '@/stores/learning'
import { useConverterStore } from '@/stores/converter'

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

// 初始化应用
const initializeApp = async () => {
  // 初始化用户状态
  const userStore = useUserStore()
  userStore.initializeUser()

  // 初始化学习数据
  const learningStore = useLearningStore()
  await learningStore.initializeLearningData()

  // 初始化转换器
  const converterStore = useConverterStore()
  converterStore.initializeConverter()

  console.log('Python学习平台前端应用已启动')

  // 移除加载动画
  const loadingScreen = document.querySelector('.loading-screen')
  if (loadingScreen) {
    setTimeout(() => {
      loadingScreen.remove()
    }, 500)
  }
}

// 启动应用初始化
initializeApp().catch((error) => {
  console.error('应用初始化失败:', error)
  
  // 即使初始化失败，也移除加载动画
  const loadingScreen = document.querySelector('.loading-screen')
  if (loadingScreen) {
    loadingScreen.remove()
  }
})