import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import { useUserStore } from '@/stores/user'

// 路由配置
const routes: RouteRecordRaw[] = [
  {
    path: '/',
    redirect: '/converter'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/user/LoginView.vue'),
    meta: {
      title: '登录',
      requiresAuth: false,
      hideLayout: true
    }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/user/RegisterView.vue'),
    meta: {
      title: '注册',
      requiresAuth: false,
      hideLayout: true
    }
  },
  {
    path: '/converter',
    name: 'Converter',
    component: () => import('@/views/converter/ConverterView.vue'),
    meta: {
      title: 'Java-to-Python代码转换工作站',
      requiresAuth: false,
      icon: 'converter'
    }
  },
  {
    path: '/learning',
    name: 'Learning',
    component: () => import('@/views/learning/LearningCenterView.vue'),
    meta: {
      title: 'Python核心语法学习中心',
      requiresAuth: false,
      icon: 'learning'
    }
  },
  {
    path: '/knowledge',
    name: 'Knowledge',
    component: () => import('@/views/knowledge/KnowledgeBaseView.vue'),
    meta: {
      title: 'Python全场景应用知识库',
      requiresAuth: false,
      icon: 'knowledge'
    }
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/views/error/NotFoundView.vue'),
    meta: {
      title: '页面未找到',
      requiresAuth: false,
      hideLayout: true
    }
  }
]

// 创建路由实例
const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    // 返回页面时保持滚动位置
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0 }
    }
  }
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  
  // 设置页面标题
  if (to.meta.title) {
    document.title = `${to.meta.title} - Python学习平台`
  }
  
  // 检查是否需要认证
  if (to.meta.requiresAuth) {
    if (userStore.isAuthenticated) {
      next()
    } else {
      next({
        name: 'Login',
        query: { redirect: to.fullPath }
      })
    }
  } else {
    // 如果已经登录，访问登录页时跳转到首页
    if ((to.name === 'Login' || to.name === 'Register') && userStore.isAuthenticated) {
      next('/dashboard')
    } else {
      next()
    }
  }
})

// 路由后置守卫
router.afterEach((to) => {
  // 可以在这里添加页面访问统计等
  console.log(`路由跳转: ${from?.name || '初始'} -> ${to.name}`)
})

// 错误处理
router.onError((error) => {
  console.error('路由错误:', error)
  // 可以在这里添加错误上报
})

export default router